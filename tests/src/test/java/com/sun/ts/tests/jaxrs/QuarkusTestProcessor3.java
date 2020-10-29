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
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Adds context-path
 */
public class QuarkusTestProcessor3 {
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Path srcPath = Paths.get("src/test/java");
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            class State {
                private String baseDir;
                private Path client;
                private String contextPath;
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
                        for (int i = 0; i < properties.getLength(); i++) {
                            Node property = properties.item(i);
                            if (property.getNodeType() == Node.ELEMENT_NODE) {
                                Element el = (Element) property;
                                String name = el.getAttribute("name");
                                if ("app.name".equals(name)) {
                                    String value = el.getAttribute("value");
                                    if (value != null) {
                                        state.contextPath = value;
                                        break;
                                    }
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
                if (state.client != null && state.contextPath != null) {
                    System.err.println("Adding context path " + state.contextPath + "_web to test class " + state.client);
                    addContextPathToTest(state.client, "/" + state.contextPath + "_web");
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    protected static void addContextPathToTest(Path client, String contextPath) {
        /*
         * @RegisterExtension
         * static QuarkusUnitTest test = new QuarkusUnitTest()
         * .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_get_web")
         */
        String original = client.toString();
        String target = client.toString() + ".tmp";
        try (BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String line;
            boolean addedContext = false;
            while ((line = reader.readLine()) != null) {
                if (!addedContext && line.startsWith("    static QuarkusUnitTest test = new QuarkusUnitTest()")) {
                    addedContext = true;
                    writer.write(line);
                    writer.newLine();
                    writer.write("            .overrideConfigKey(\"quarkus.http.root-path\", \"" + contextPath + "\")\n");
                    // let's not write the original line twice
                    continue;
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
