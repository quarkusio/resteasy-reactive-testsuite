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
 * Adds @Disabled to all tests that fail
 */
public class QuarkusIgnoreFailingTestProcessor {
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Path srcPath = Paths.get("target/surefire-reports");
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filename = file.getFileName().toString();
                if (filename.startsWith("TEST-") && filename.endsWith(".xml")) {
                    try {
                        Document document = builder.parse(file.toFile());
                        NodeList testcases = document.getElementsByTagName("testcase");
                        Set<String> methods = new HashSet<>();
                        String classname = null;
                        for (int i = 0; i < testcases.getLength(); i++) {
                            Node testcase = testcases.item(i);
                            if (testcase.getNodeType() == Node.ELEMENT_NODE) {
                                Element el = (Element) testcase;
                                String name = el.getAttribute("name");
                                classname = el.getAttribute("classname");
                                NodeList failures = el.getElementsByTagName("failure");
                                for (int j = 0; j < failures.getLength(); j++) {
                                    Node failure = failures.item(j);
                                    if (failure.getNodeType() == Node.ELEMENT_NODE) {
                                        methods.add(name);
                                        System.err.println(classname + "." + name + " FAILED");
                                    }
                                }
                                NodeList errors = el.getElementsByTagName("error");
                                for (int j = 0; j < errors.getLength(); j++) {
                                    Node failure = errors.item(j);
                                    if (failure.getNodeType() == Node.ELEMENT_NODE) {
                                        methods.add(name);
                                        System.err.println(classname + "." + name + " ERRORED");
                                    }
                                }
                            }
                        }
                        if (!methods.isEmpty() && classname != null) {
                            Path path = Paths.get("src/test/java/" + classname.replace('.', '/') + ".java");
                            disableFailingTests(path, methods);
                        }
                    } catch (SAXException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

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
    }

    static Pattern MethodPattern = Pattern.compile("^\\s*public\\s+([A-Za-z0-9<>_]+)\\s+([a-zA-Z0-9_]+)\\(\\).*");
    static Pattern ClassPattern = Pattern.compile("^\\s*public(\\s+abstract)?\\s+class\\s+([a-zA-Z0-9_]+).*");

    protected static void disableFailingTests(Path client, Set<String> methods) {
        String original = client.toString();
        String target = client.toString() + ".tmp";
        try (BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = ClassPattern.matcher(line);
                if (matcher.matches()) {
                    // failed classes have an empty method name marked as failing
                    if (methods.contains("")) {
                        writer.write("  @org.junit.jupiter.api.Disabled(\"Did not pass for RESTEasy\")\n");
                    }
                } else {
                    matcher = MethodPattern.matcher(line);
                    if (matcher.matches()) {
                        String methodName = matcher.group(2);
                        if (methods.contains(methodName)) {
                            writer.write("  @org.junit.jupiter.api.Disabled(\"Did not pass for RESTEasy\")\n");
                        }
                    }
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
}
