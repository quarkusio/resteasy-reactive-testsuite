/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.get;

import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0147 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_get_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.get.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.get.HttpMethodGetTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.get.RecursiveLocator.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.get.SubResource.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0147() {
        setContextRoot("/jaxrs_ee_rs_get_web/GetTest");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0147().run(args);
    }

    /* Run test */
    /*
     * @testName: getTest1
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes GET on root resource at /GetTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void getTest1() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(GET, ""));
        setProperty(Property.SEARCH_STRING, "CTS-get text/plain");
        invoke();
    }

    /*
     * @testName: getTest2
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes GET on root resource at /GetTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void getTest2() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(GET, ""));
        setProperty(Property.SEARCH_STRING, "CTS-get text/html");
        invoke();
    }

    /*
     * @testName: getSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes GET on a sub resource at /GetTest/sub;
     * Verify that right Method is invoked.
     */
    @Test
    public void getSubTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(GET, "sub"));
        setProperty(Property.SEARCH_STRING, "CTS-get text/plain");
        invoke();
    }

    /*
     * @testName: headTest1
     * 
     * @assertion_ids: JAXRS:SPEC:17.2; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes Head on root resource at /GetTest; which no
     * request method designated for HEAD; Verify that corresponding GET Method is
     * invoked.
     */
    @Test
    public void headTest1() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest("HEAD", ""));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: text-plain");
        invoke();
    }

    /*
     * @testName: headTest2
     * 
     * @assertion_ids: JAXRS:SPEC:17.2; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes HEAD on root resource at /GetTest; which no
     * request method designated for HEAD; Verify that corresponding GET Method is
     * invoked.
     */
    @Test
    public void headTest2() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest("HEAD", ""));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: text-html");
        invoke();
    }

    /*
     * @testName: headSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:17.2; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes HEAD on sub resource at /GetTest/sub; which
     * no request method designated for HEAD; Verify that corresponding GET Method
     * is invoked instead.
     */
    @Test
    public void headSubTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest("HEAD", "sub"));
        setProperty(Property.EXPECTED_HEADERS, "CTS-HEAD: sub-text-plain");
        invoke();
    }

    /*
     * @testName: optionSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:18.2; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes OPTIONS on a sub resource at /GetTest/sub;
     * which no request method designated for OPTIONS. Verify that an automatic
     * response is generated.
     */
    @Test
    public void optionSubTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest("OPTIONS", "sub"));
        setProperty(Property.EXPECTED_HEADERS, "ALLOW:GET,OPTIONS,HEAD");
        invoke();
    }

    /*
     * @testName: dynamicGetTest
     * 
     * @assertion_ids: JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: The presence or absence of a request method designator
     * (e.g. @GET) differentiates between the two: o Absent
     */
    @Test
    public void dynamicGetTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(GET, "123"));
        setProperty(Property.SEARCH_STRING, SubResource.ID);
        invoke();
    }

    /*
     * @testName: recursiveResourceLocatorTest
     * 
     * @assertion_ids: JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: The presence or absence of a request method designator
     * (e.g. @GET) differentiates between the two: o Absent
     *
     * Any returned object is treated as a resource class instance and used to
     * either handle the request or to further resolve the object that will handle
     * the request
     * 
     */
    @Test
    public void recursiveResourceLocatorTest() throws Fault {
        StringBuilder sb = new StringBuilder();
        sb.append("recursive");
        for (int i = 0; i != 10; i++)
            sb.append("/lvl");
        setProperty(Property.REQUEST, buildRequest(GET, sb.toString()));
        setProperty(Property.SEARCH_STRING, "10");
        invoke();
    }

}
