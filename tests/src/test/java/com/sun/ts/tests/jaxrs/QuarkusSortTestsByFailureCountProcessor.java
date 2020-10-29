package com.sun.ts.tests.jaxrs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Shows failing test classes by number of failure/errors
 */
public class QuarkusSortTestsByFailureCountProcessor {

    static class TestResult implements Comparable<TestResult> {
        int failures, errors;
        String classname;

        TestResult(String classname) {
            this.classname = classname;
        }

        @Override
        public int compareTo(TestResult o) {
            int total = failures + errors;
            int otherTotal = o.errors + o.failures;
            int order = Integer.compare(total, otherTotal);
            if (order != 0)
                return order;
            return classname.compareTo(o.classname);
        }
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Path srcPath = Paths.get("target/surefire-reports");
        Map<String, TestResult> results = new HashMap<>();
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
                                TestResult testResult = results.computeIfAbsent(classname, v -> new TestResult(v));
                                for (int j = 0; j < failures.getLength(); j++) {
                                    Node failure = failures.item(j);
                                    if (failure.getNodeType() == Node.ELEMENT_NODE) {
                                        methods.add(name);
                                        testResult.failures++;
                                    }
                                }
                                NodeList errors = el.getElementsByTagName("error");
                                for (int j = 0; j < errors.getLength(); j++) {
                                    Node failure = errors.item(j);
                                    if (failure.getNodeType() == Node.ELEMENT_NODE) {
                                        methods.add(name);
                                        testResult.errors++;
                                    }
                                }
                            }
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
        // purge all successful tests
        TreeSet<TestResult> values = new TreeSet<>(results.values());
        values.removeIf(res -> res.errors == 0 && res.failures == 0);
        for (TestResult result : values) {
            System.err.println(result.classname + ": " + result.errors + " ERRORS, " + result.failures + " FAILURES");
        }
    }

}
