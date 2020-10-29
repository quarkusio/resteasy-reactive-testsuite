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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Renames every test class to have a unique name
 */
public class QuarkusTestProcessor4 {

    public static int testClassCount = 0;

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        Path srcPath = Paths.get("src/test/java");
        Map<String, String> renames = new HashMap<>();
        Files.walkFileTree(srcPath, new FileVisitor<Path>() {

            class State {
                private Path client;
                private boolean hasBuildXml;
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
                if (file.endsWith("build.xml")) {
                    state.hasBuildXml = true;
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
                if (state.client != null && state.hasBuildXml) {
                    Path relativeClient = srcPath.relativize(state.client);
                    String oldFQN = relativeClient.toString().replace('/', '.');
                    // get rid of ".java"
                    oldFQN = oldFQN.substring(0, oldFQN.length() - 5);
                    String oldName = relativeClient.getFileName().toString();
                    // get rid of ".java"
                    oldName = oldName.substring(0, oldName.length() - 5);
                    String newName = String.format("%s%04d", oldName, testClassCount++);
                    renames.put(oldFQN, newName);
                    System.err.println("Renaming test " + oldFQN + " to " + newName);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        for (Entry<String, String> entry : renames.entrySet()) {
            String oldFQN = entry.getKey();
            String newName = entry.getValue();
            Path clientPath = srcPath.resolve(oldFQN.replace('.', '/') + ".java");
            System.err.println("ACTION Renaming test " + oldFQN + " to " + newName);
            renameTest(clientPath, newName, renames);
        }
    }

    protected static void renameTest(Path client, String newName, Map<String, String> renames) {
        String original = client.toString();
        String target = client.toString() + ".tmp";
        try (BufferedReader reader = new BufferedReader(new FileReader(original));
                BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            String line;
            String oldName = client.getFileName().toString();
            // get rid of ".java"
            oldName = oldName.substring(0, oldName.length() - 5);
            Pattern importPattern = Pattern.compile("import\\s+(.*);");
            Map<String, String> localRenames = new HashMap<>();
            localRenames.put(oldName, newName);
            while ((line = reader.readLine()) != null) {
                // let's not do renames on import lines
                Matcher matcher = importPattern.matcher(line);
                if (matcher.matches()) {
                    String imp = matcher.group(1);
                    String renameTo = renames.get(imp);
                    if (renameTo != null) {
                        String localRename = imp.substring(imp.lastIndexOf('.') + 1);
                        localRenames.put(localRename, renameTo);
                        System.err.println(" Adding local rename " + localRename + "->" + renameTo);
                        String pkgPart = imp.substring(0, imp.lastIndexOf('.'));
                        writer.write("import " + pkgPart + "." + renameTo + ";");
                    } else {
                        writer.write(line);
                    }
                } else {
                    line = replaceRenames(line, localRenames, renames);
                    writer.write(line);
                }
                writer.newLine();
                writer.flush();
            }
            Files.delete(client);
            Path newClient = client.getParent().resolve(Paths.get(newName + ".java"));
            Files.deleteIfExists(newClient);
            Files.move(Paths.get(target), newClient);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String replaceRenames(String line, Map<String, String> localRenames, Map<String, String> renames) {
        for (Entry<String, String> entry : localRenames.entrySet()) {
            String from = entry.getKey();
            // cheap
            if (line.contains(from)) {
                System.err.println(" Line matches rename " + from + "->" + entry.getValue() + ": " + line);
                // make sure we're not matching partial names
                Matcher matcher = Pattern.compile("([^a-zA-Z.])" + from + "\\b").matcher(line);
                if (matcher.find()) {
                    line = matcher.replaceAll("$1" + entry.getValue());
                    System.err.println("  Regex match");
                } else {
                    System.err.println("  No regex match");
                }
            }
        }
        for (Entry<String, String> entry : renames.entrySet()) {
            String from = entry.getKey();
            if (line.contains(from)) {
                String pkgPart = from.substring(0, from.lastIndexOf('.'));
                line = line.replace(from, pkgPart + "." + entry.getValue());
            }
        }
        return line;
    }
}
