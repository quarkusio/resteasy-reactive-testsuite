/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.ts.tests.jaxrs.ee.rs.head;

import java.io.IOException;
import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.common.webclient.http.HttpResponse;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0104 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_head_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.head.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.head.HttpMethodHeadTest.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0104() {
        setContextRoot("/jaxrs_ee_rs_head_web/HeadTest");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0104().run(args);
    }

    /* Run test */
    /*
     * @testName: headTest1
     * 
     * @assertion_ids: JAXRS:SPEC:17.1; JAXRS:SPEC:20.1; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:8; JAXRS:JAVADOC:10; JAXRS:JAVADOC:148;
     * 
     * @test_Strategy: Client invokes HEAD on root resource at /HeadTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void headTest1() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/plain");
        setProperty(REQUEST, buildRequest("HEAD", ""));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: text-plain");
        invoke();
    }

    /*
     * @testName: headTest2
     * 
     * @assertion_ids: JAXRS:SPEC:17.1; JAXRS:SPEC:20.1; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:8; JAXRS:JAVADOC:10; JAXRS:JAVADOC:148;
     * 
     * @test_Strategy: Client invokes HEAD on root resource at /HeadTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void headTest2() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/html");
        setProperty(REQUEST, buildRequest("HEAD", ""));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: text-html");
        invoke();
    }

    /*
     * @testName: headSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:17.1; JAXRS:SPEC:20.1; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:8; JAXRS:JAVADOC:10; JAXRS:JAVADOC:148;
     * 
     * @test_Strategy: Client invokes HEAD on a sub resource at /HeadTest/sub;
     * Verify that right Method is invoked.
     */
    @Test
    public void headSubTest() throws Fault {
        setProperty(REQUEST, buildRequest("HEAD", "sub"));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD:  sub-text-html");
        invoke();
    }

    /*
     * @testName: headGetTest
     * 
     * @assertion_ids: JAXRS:SPEC:17.2;
     * 
     * @test_Strategy: Call a method annotated with a request method designator
     * for GET and discard any returned entity
     */
    @Test
    public void headGetTest() throws Fault, IOException {
        setProperty(REQUEST, buildRequest("HEAD", "get"));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: get");
        invoke();
        HttpResponse request = _testCase.getResponse();
        assertFault(request.getResponseBodyAsRawString() == null,
                "Unexpected entity in request body");
    }

}
