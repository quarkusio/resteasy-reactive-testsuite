package com.sun.ts.tests.jaxrs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Initial converter, did not set context path, or use web.war.classes properties
 */
public class QuarkusTestProcessor {
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Set<String> classFiles = collectClassFiles();
        Path srcPath = Paths.get("src/test/java");
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            class State {
                private String baseDir;
                private Path client;
                private Set<String> resources = new HashSet<String>();
            }

            private Stack<State> states = new Stack<>();

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                State state = new State();
                state.baseDir = srcPath.relativize(dir).toString();
                states.push(state);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                State state = states.peek();
                if (file.endsWith("build.xml")) {
                    try {
                        Document document = builder.parse(file.toFile());
                        NodeList properties = document.getElementsByTagName("property");
                        String[] resources = null;
                        for (int i = 0; i < properties.getLength(); i++) {
                            Node property = properties.item(i);
                            if (property.getNodeType() == Node.ELEMENT_NODE) {
                                Element el = (Element) property;
                                String name = el.getAttribute("name");
                                if ("resource.classes".equals(name)) {
                                    String value = el.getAttribute("value");
                                    if (value != null) {
                                        value = value.replace("${basedir}", state.baseDir);
                                        resources = value.split("\\s*,\\s*");
                                        break;
                                    }
                                }
                            }
                        }
                        if (resources != null) {
                            for (String resource : resources) {
                                if (resource.indexOf('*') != -1) {
                                    String regex = wildcardToRegex(resource);
                                    state.resources.addAll(findClassMatches(classFiles, regex));
                                } else {
                                    state.resources.add(resource);
                                }
                            }
                        }
                    } catch (SAXException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else if (file.getFileName().toString().endsWith("Client.java")) {
                    state.client = file;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                State state = states.pop();
                if (state.client != null && !state.resources.isEmpty()) {
                    System.err.println("Adding resources to test class " + state.client);
                    addResourcesToTest(state.client, state.resources);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    protected static void addResourcesToTest(Path client, Set<String> resources) {
        /*
         * @RegisterExtension
         * static QuarkusUnitTest test = new QuarkusUnitTest()
         * .setArchiveProducer(new Supplier<JavaArchive>() {
         * 
         * @Override
         * public JavaArchive get() {
         * return ShrinkWrap.create(JavaArchive.class)
         * .addClasses(Foo.class, Bar.class);
         * }
         * });
         */
        String original = client.toString();
        String target = client.toString() + ".tmp";
        try (BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String line;
            boolean addedImports = false;
            boolean sawClass = false;
            boolean delayedClass = false;
            while ((line = reader.readLine()) != null) {
                if (!addedImports && line.startsWith("package ")) {
                    addedImports = true;
                    writer.write(line);
                    writer.newLine();
                    writer.newLine();
                    writer.write("import java.util.function.Supplier;\n");
                    writer.write("import org.jboss.shrinkwrap.api.ShrinkWrap;\n");
                    writer.write("import org.jboss.shrinkwrap.api.spec.JavaArchive;\n");
                    writer.write("import org.junit.jupiter.api.Test;\n");
                    writer.write("import org.junit.jupiter.api.extension.RegisterExtension;\n");
                    writer.write("import io.quarkus.test.QuarkusUnitTest;\n\n");
                    // let's not write the original line twice
                    continue;
                }
                if (!sawClass && line.startsWith("public class ")) {
                    if (line.contains("{")) {
                        sawClass = true;
                        writer.write(line);
                        writer.newLine();
                        writer.newLine();
                        writeExtension(writer, resources);
                        // let's not write the original line twice
                        continue;
                    } else {
                        delayedClass = true;
                    }
                } else if (delayedClass && line.contains("{")) {
                    delayedClass = false;
                    writer.write(line);
                    writer.newLine();
                    writer.newLine();
                    writeExtension(writer, resources);
                    // let's not write the original line twice
                    continue;
                } else if (line.equals("  @org.junit.jupiter.api.Test")) {
                    line = "  @Test";
                }
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            Files.delete(client);
            Files.move(Paths.get(target), client);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeExtension(BufferedWriter writer, Set<String> resources) throws IOException {
        writer.write("    @RegisterExtension\n");
        writer.write("    static QuarkusUnitTest test = new QuarkusUnitTest()\n");
        writer.write("            .setArchiveProducer(new Supplier<JavaArchive>() {\n");
        writer.write("                @Override\n");
        writer.write("                public JavaArchive get() {\n");
        writer.write("                    return ShrinkWrap.create(JavaArchive.class)\n");
        writer.write("                            .addClasses(\n");
        boolean first = true;
        for (String resource : resources) {
            writer.write("                            ");
            if (first)
                first = false;
            else
                writer.write(", ");
            writer.write(resource.replace('/', '.').replace('$', '.'));
            writer.write("\n");
        }
        writer.write("                            );\n");
        writer.write("                }\n");
        writer.write("            });\n\n");
    }

    protected static Set<String> findClassMatches(Set<String> classFiles, String regex) {
        Set<String> ret = new HashSet<>();
        for (String classFile : classFiles) {
            if (classFile.matches(regex)) {
                ret.add(classFile);
            }
        }
        return ret;
    }

    protected static String wildcardToRegex(String wildcard) {
        Pattern regex = Pattern.compile("[^*]+|(\\*)");
        Matcher m = regex.matcher(wildcard);
        StringBuffer b = new StringBuffer();
        while (m.find()) {
            if (m.group(1) != null)
                m.appendReplacement(b, ".*");
            else
                m.appendReplacement(b, "\\\\Q" + m.group(0) + "\\\\E");
        }
        m.appendTail(b);
        return b.toString();
    }

    private static Set<String> collectClassFiles() throws IOException {
        Path srcPath = Paths.get("target/test-classes");
        Set<String> ret = new HashSet<>();
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".class")) {
                    String f = srcPath.relativize(file).toString();
                    ret.add(f);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
        return ret;
    }
}
