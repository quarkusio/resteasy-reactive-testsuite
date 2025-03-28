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

package com.sun.ts.tests.jaxrs.ee.rs.ext.providers;

import com.sun.ts.tests.common.webclient.http.HttpResponse;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;
import java.io.IOException;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumProvider;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
public class JAXRSProvidersClient0098 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_ext_providers_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.providers.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.providers.ProvidersServlet.class,
                                    com.sun.ts.tests.jaxrs.common.AbstractMessageBodyRW.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.exceptionmapper.AnyExceptionExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.application.ApplicationServlet.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityMessageReader.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.application.ApplicationHolderSingleton.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumProvider.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityMessageWriter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityAnnotation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumContextResolver.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.TextPlainEnumContextResolver.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.exceptionmapper.IOExceptionExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity.class);
                }
            });

    private static final long serialVersionUID = -935293219512493643L;

    protected int expectedSingletons = 1;

    protected int expectedClasses = 1;

    public JAXRSProvidersClient0098() {
        TSAppConfig cfg = new TSAppConfig();
        setContextRoot("/jaxrs_ee_ext_providers_web/ProvidersServlet");
        expectedClasses = cfg.getClasses().size();
        expectedSingletons = cfg.getSingletons().size();
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSProvidersClient0098 theTests = new JAXRSProvidersClient0098();
        theTests.run(args);
    }

    /* Run test */

    /*
     * @testName: getSingletonsTest
     *
     * @assertion_ids: JAXRS:JAVADOC:23
     *
     * @test_Strategy: Check that vi does not modify the getSingletons()
     */
    @Test
    public void getSingletonsTest() throws Fault {
        setProperty(REQUEST, buildRequest(GET, "GetSingletons"));
        setProperty(STATUS_CODE, getStatusCode(Status.OK));
        invoke();
        assertFault(getReturnedNumber() == expectedSingletons,
                "Application.getSingletons() return incorrect value:",
                getReturnedNumber());
    }

    /*
     * @testName: getClassesTest
     *
     * @assertion_ids: JAXRS:JAVADOC:22; JAXRS:SPEC:40;
     *
     * @test_Strategy: Check the implementation injects TSAppConfig
     */
    @Test
    public void getClassesTest() throws Fault {
        setProperty(REQUEST, buildRequest(GET, "GetClasses"));
        setProperty(STATUS_CODE, getStatusCode(Status.OK));
        invoke();
        assertFault(getReturnedNumber() == expectedClasses,
                "Application.getClasses() return incorrect value:",
                getReturnedNumber());
    }

    /*
     * @testName: isRegisteredTextPlainContextResolverTest
     *
     * @assertion_ids: JAXRS:JAVADOC:269; JAXRS:JAVADOC:280; JAXRS:JAVADOC:299;
     * JAXRS:SPEC:40; JAXRS:SPEC:80; JAXRS:SPEC:81;
     *
     * @test_Strategy: Register ContextResolver and try to get proper Provider
     *
     * When injecting an instance of one of the types listed in section 9.2, the
     * instance supplied MUST be capable of selecting the correct context for a
     * particular request.
     *
     * Context providers MAY return null from the getContext method if they do not
     * wish to provide their context for a particular Java type.
     *
     * Context provider implementations MAY restrict the media types they support
     * using the @Produces annotation.
     */
    @Test
    public void isRegisteredTextPlainContextResolverTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredTextPlainContextResolver"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredAppJsonContextResolverTest
     *
     * @assertion_ids: JAXRS:JAVADOC:269; JAXRS:JAVADOC:280; JAXRS:JAVADOC:299;
     * JAXRS:SPEC:40; JAXRS:SPEC:80; JAXRS:SPEC:81;
     *
     * @test_Strategy: Register ContextResolver and try to get proper Provider
     *
     * When injecting an instance of one of the types listed in section 9.2, the
     * instance supplied MUST be capable of selecting the correct context for a
     * particular request.
     *
     * Context providers MAY return null from the getContext method if they do not
     * wish to provide their context for a particular Java type.
     *
     * Context provider implementations MAY restrict the media types they support
     * using the @Produces annotation. Absence implies that any media type is
     * supported.
     */
    @Test
    public void isRegisteredAppJsonContextResolverTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredAppJsonContextResolver"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredExceptionMapperRuntimeExceptionTest
     *
     * @assertion_ids: JAXRS:JAVADOC:270; JAXRS:JAVADOC:281; JAXRS:SPEC:40;
     *
     * @test_Strategy: Try to get proper ExceptionMapper
     */
    @Test
    public void isRegisteredExceptionMapperRuntimeExceptionTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredExceptionMapperRuntimeEx"));
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
    }

    /*
     * @testName: isRegisteredExceptionMapperNullExceptionTest
     *
     * @assertion_ids: JAXRS:JAVADOC:281;
     *
     * @test_Strategy: Try to get proper ExceptionMapper
     */
    @Test
    public void isRegisteredExceptionMapperNullExceptionTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredExceptionMapperNullEx"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: isRegisteredRuntimeExceptionExceptionMapperTest
     *
     * @assertion_ids: JAXRS:JAVADOC:281; JAXRS:JAVADOC:300; JAXRS:SPEC:40;
     *
     * @test_Strategy: Try to get RuntimeExceptionExceptionMapper but there is
     * none
     */
    @Test
    public void isRegisteredRuntimeExceptionExceptionMapperTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredRuntimeExceptionMapper"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredIOExceptionExceptionMapperTest
     *
     * @assertion_ids: JAXRS:JAVADOC:281;
     *
     * @test_Strategy: Try to get IOExceptionExceptionMapper
     */
    @Test
    public void isRegisteredIOExceptionExceptionMapperTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredIOExceptionMapper"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
        invoke();
    }

    /*
     * @testName: isRegisteredMessageBodyWriterWildcardTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
     * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
     *
     * @test_Strategy: Check what is returned for wildcard is for text/plain
     */
    @Test
    public void isRegisteredMessageBodyWriterWildcardTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredWriterWildcard"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredMessageBodyWriterXmlTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
     * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
     *
     * @test_Strategy: Check BodyWriter is returned for text/xml
     */
    @Test
    public void isRegisteredMessageBodyWriterXmlTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredMessageWriterXml"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredMessageBodyReaderWildcardTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:282;
     * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
     *
     * @test_Strategy: Check what is returned for wildcard is for text/plain
     */
    @Test
    public void isRegisteredMessageBodyReaderWildcardTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredMessageReaderWildCard"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: isRegisteredMessageBodReaderXmlTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:282;
     * JAXRS:JAVADOC:299; JAXRS:SPEC:40;
     *
     * @test_Strategy: Check BodyReader is returned for text/xml
     */
    @Test
    public void isRegisteredMessageBodReaderXmlTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(GET, "isRegisteredMessageReaderXml"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: writeBodyEntityUsingWriterTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:283;
     * JAXRS:JAVADOC:132; JAXRS:JAVADOC:275; JAXRS:JAVADOC:276; JAXRS:JAVADOC:304;
     *
     * @test_Strategy: Check BodyWriter is used for text/xml to write entity
     */
    @Test
    public void writeBodyEntityUsingWriterTest() throws Fault {
        String ename = EnumProvider.JAXRS.name();
        String search = new ReadableWritableEntity(ename).toXmlString();
        setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest(GET, "writeBodyEntityUsingWriter"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        setProperty(Property.SEARCH_STRING, search);
        invoke();
    }

    /*
     * @testName: writeHeaderEntityUsingWriterTest
     *
     * @assertion_ids: JAXRS:JAVADOC:87; JAXRS:JAVADOC:276; JAXRS:JAVADOC:132;
     * JAXRS:JAVADOC:275; JAXRS:JAVADOC:277; JAXRS:JAVADOC:304;
     *
     * @test_Strategy: Check HeaderWriter is used for text/xml to write entity
     */
    @Test
    public void writeHeaderEntityUsingWriterTest() throws Fault {
        String ename = EnumProvider.JAXRS.name();
        String search = new ReadableWritableEntity(ename).toXmlString();
        setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest(GET, "writeHeaderEntityUsingWriter"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        setProperty(Property.EXPECTED_HEADERS,
                ReadableWritableEntity.NAME + ":" + search);
        invoke();
    }

    /*
     * @testName: writeIOExceptionUsingWriterTest
     *
     * @assertion_ids: JAXRS:JAVADOC:281; JAXRS:JAVADOC:304; JAXRS:JAVADOC:87;
     * JAXRS:JAVADOC:132; JAXRS:JAVADOC:277; JAXRS:JAVADOC:278;
     *
     * @test_Strategy: Check EntityWriter is used and IOException is written using
     * mapper
     */
    @Test
    public void writeIOExceptionUsingWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest(GET, "writeIOExceptionUsingWriter"));
        // Depending whether the response has been committed
        setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
    }

    /*
     * @testName: writeIOExceptionWithoutWriterTest
     *
     * @assertion_ids: JAXRS:JAVADOC:304; JAXRS:JAVADOC:281; JAXRS:SPEC:16.2;
     *
     * @test_Strategy: Check IOExceptionExceptionMapper is chosen
     */
    @Test
    public void writeIOExceptionWithoutWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Accept: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest(GET, "writeIOExceptionWithoutWriter"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
        invoke();
    }

    /*
     * @testName: readEntityFromHeaderTest
     *
     * @assertion_ids: JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:JAVADOC:138;
     * JAXRS:JAVADOC:304; JAXRS:JAVADOC:282;
     *
     * @test_Strategy: Put entity to header and read it using reader
     */
    @Test
    public void readEntityFromHeaderTest() throws Fault {
        ReadableWritableEntity entity;
        entity = new ReadableWritableEntity(EnumProvider.JAXRS.name());
        String header = ReadableWritableEntity.NAME + ":" + entity.toXmlString();
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST_HEADERS, header);
        setProperty(Property.REQUEST, buildRequest("POST", "readEntityFromHeader"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: readEntityFromBodyTest
     *
     * @assertion_ids: JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:JAVADOC:138;
     * JAXRS:JAVADOC:304; JAXRS:JAVADOC:282;
     *
     * @test_Strategy: Put entity to body and read it using reader
     */
    @Test
    public void readEntityFromBodyTest() throws Fault {
        ReadableWritableEntity entity;
        entity = new ReadableWritableEntity(EnumProvider.JAXRS.name());
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST, buildRequest("POST", "readEntityFromBody"));
        setProperty(Property.CONTENT, entity.toXmlString());
        setProperty(Property.STATUS_CODE, getStatusCode(Status.OK));
        invoke();
    }

    /*
     * @testName: readEntityIOExceptionTest
     *
     * @assertion_ids: JAXRS:JAVADOC:273; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
     * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272;
     *
     * @test_Strategy: Put entity to body and read it using reader
     */
    @Test
    public void readEntityIOExceptionTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest("POST", "readEntityIOException"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.ACCEPTED));
        invoke();
    }

    /*
     * @testName: readEntityWebException400Test
     *
     * @assertion_ids: JAXRS:JAVADOC:274; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
     * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:SPEC:16.2;
     *
     * @test_Strategy: Put entity to body and read it using reader
     */
    @Test
    public void readEntityWebException400Test() throws Fault {
        String code = ReadableWritableEntity.NAME + ":" + Status.BAD_REQUEST.name();
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest("POST", "readEntityWebException"));
        setProperty(Property.REQUEST_HEADERS, code);
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: readEntityWebException410Test
     *
     * @assertion_ids: JAXRS:JAVADOC:274; JAXRS:JAVADOC:138; JAXRS:JAVADOC:304;
     * JAXRS:JAVADOC:282; JAXRS:JAVADOC:271; JAXRS:JAVADOC:272; JAXRS:SPEC:16.2;
     *
     * @test_Strategy: Put entity to body and read it using reader
     */
    @Test
    public void readEntityWebException410Test() throws Fault {
        String code = ReadableWritableEntity.NAME + ":" + Status.GONE.name();
        setProperty(Property.REQUEST_HEADERS,
                "Content-Type: " + MediaType.TEXT_XML);
        setProperty(Property.REQUEST,
                buildRequest("POST", "readEntityWebException"));
        setProperty(Property.REQUEST_HEADERS, code);
        setProperty(Property.STATUS_CODE, getStatusCode(Status.GONE));
        invoke();
    }

    // ///////////////////////////////////////////////////////////////////////

    protected int getReturnedNumber() throws Fault {
        HttpResponse response = _testCase.getResponse();
        String body;
        try {
            body = response.getResponseBodyAsString();
        } catch (IOException e) {
            throw new Fault(e);
        }
        return Integer.parseInt(body);
    }

}
