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

/**
 * Adds quarkus.rest.single-default-produces=false config
 */
public class QuarkusTestProcessor6 {
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Path srcPath = Paths.get("src/test/java");
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            class State {
                private Path client;
            }

            private Stack<State> states = new Stack<>();

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                State state = new State();
                states.push(state);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                State state = states.peek();
                if (file.getFileName().toString().matches(".*Client\\d\\d\\d\\d\\.java")) {
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
                if (state.client != null) {
                    System.err.println("Adding config to test class " + state.client);
                    addConfigToTest(state.client);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    protected static void addConfigToTest(Path client) {
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
                    writer.write("            .overrideConfigKey(\"quarkus.rest.single-default-produces\", \"false\")\n");
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
