/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.produceconsume;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

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
public class JAXRSClient0103 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_produceconsume_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.produceconsume.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.produceconsume.Resource.class);
                }
            });

    private static final long serialVersionUID = 3927081991341346347L;

    public JAXRSClient0103() {
        setContextRoot("/jaxrs_ee_rs_produceconsume_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0103().run(args);
    }

    /* Run test */
    /*
     * @testName: anyPlainTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:26.6; JAXRS:SPEC:25.8;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     *//*
        * @produces text/plain
        */
    @Test
    public void anyPlainTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "plain"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
    }

    /*
     * @testName: anyWidgetsxmlTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:26.6; JAXRS:SPEC:25.8;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     *//*
        * @produces text/plain
        */
    @Test
    public void anyWidgetsxmlTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "widgetsxml"));
        setProperty(Property.SEARCH_STRING, Resource.WIDGETS_XML);
        invoke();
    }

    /*
     * @testName: anyUnknownTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:26.6; JAXRS:SPEC:25.8;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     *//*
        * @produces unknown/unknown
        */
    @Test
    public void anyUnknownTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "unknown"));
        setProperty(Property.SEARCH_STRING, Resource.UNKNOWN);
        invoke();
    }

    /*
     * @testName: widgetsXmlAnyTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:25.8; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     * text/plain @produces *.*
     */
    @Test
    public void widgetsXmlAnyTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Accept: " + Resource.WIDGETS_XML);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: plainAnyTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:25.8; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     * text/plain @produces *.*
     */
    @Test
    public void plainAnyTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.GET, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: unknownAnyTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:25.8; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     * unknown/unknown @produces *.*
     */
    @Test
    public void unknownAnyTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Accept: " + Resource.UNKNOWN);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: htmlPlainTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:26.7; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     * text/plain @produces text/html
     */
    @Test
    public void htmlPlainTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.GET, "plain"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
        invoke();
    }

    /*
     * @testName: htmlUnknownTest
     * 
     * @assertion_ids: JAXRS:SPEC:21; JAXRS:SPEC:26.7; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Produces does not match the request Accept header. test accept
     * text/plain @produces unknown/unknown
     */
    @Test
    public void htmlUnknownTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.GET, "plain"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
        invoke();
    }

    /*
     * @testName: plainPlusProducePlainTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.8; JAXRS:JAVADOC:1;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Accept header. accept
     * text/plain @Consumes text/plain+xml
     */
    @Test
    public void plainPlusProducePlainTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plus"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
    }

    /*
     * @testName: plainPlusProduceXmlTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.8; JAXRS:JAVADOC:1;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Accept header. accept
     * text/plain @Consumes text/plain+xml
     */
    @Test
    public void plainPlusProduceXmlTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_XML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plus"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_XML);
        invoke();
    }

    // ----------------------------------------------------------------------
    /*
     * @testName: anyPlainConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type
     *//*
        * @Consumes text/plain
        */
    @Test
    public void anyPlainConsumesTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
    }

    /*
     * @testName: anyWidgetsxmlConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type
     *//*
        * @Consumes text/plain
        */
    @Test
    public void anyWidgetsxmlConsumesTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "widgetsxml"));
        setProperty(Property.SEARCH_STRING, Resource.WIDGETS_XML);
        invoke();
    }

    /*
     * @testName: anyUnknownConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type
     *//*
        * @Consumes unknown/unknown
        */
    @Test
    public void anyUnknownConsumesTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "unknown"));
        setProperty(Property.SEARCH_STRING, Resource.UNKNOWN);
        invoke();
    }

    /*
     * @testName: widgetsXmlAnyConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type text/plain @Consumes *.*
     */
    @Test
    public void widgetsXmlAnyConsumesTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + Resource.WIDGETS_XML);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: plainAnyConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type text/plain @Consumes *.*
     */
    @Test
    public void plainAnyConsumesTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: unknownAnyConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type unknown/unknown @Consumes *.*
     */
    @Test
    public void unknownAnyConsumesTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Content-type: " + Resource.UNKNOWN);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "any"));
        setProperty(Property.SEARCH_STRING, MediaType.WILDCARD);
        invoke();
    }

    /*
     * @testName: htmlPlainConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type text/html @Consumes text/plain
     */
    @Test
    public void htmlPlainConsumesTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.UNSUPPORTED_MEDIA_TYPE));
        invoke();
    }

    /*
     * @testName: htmlUnknownConsumesTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header. test
     * content-type text/html @Consumes text/plain
     */
    @Test
    public void htmlUnknownConsumesTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.UNSUPPORTED_MEDIA_TYPE));
        invoke();
    }

    /*
     * @testName: plainPlusConsumePlainTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header.
     * content-type text/plain @Consumes text/plain+xml
     */
    @Test
    public void plainPlusConsumePlainTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plus"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
    }

    /*
     * @testName: plainPlusConsumeXmlTest
     * 
     * @assertion_ids: JAXRS:SPEC:22; JAXRS:SPEC:25.7; JAXRS:JAVADOC:1;
     * 
     * @test_Strategy: An implementation MUST NOT invoke a method whose effective
     * value of @Consumes does not match the request Content-Type header.
     * content-type text/plain @Consumes text/plain+xml
     */
    @Test
    public void plainPlusConsumeXmlTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_XML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "plus"));
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_XML);
        invoke();
    }
}
