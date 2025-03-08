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

package com.sun.ts.tests.jaxrs.spec.provider.standardnotnull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.function.Supplier;

import jakarta.activation.DataSource;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.xml.bind.JAXBElement;
import javax.xml.transform.Source;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.util.JaxrsUtil;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0003 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_provider_standardnotnull_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.provider.standardnotnull.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.standardnotnull.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.impl.StringStreamingOutput.class);
                }
            });

    private static final long serialVersionUID = -3528892672938533655L;

    public JAXRSClient0003() {
        setContextRoot("/jaxrs_spec_provider_standardnotnull_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0003().run(args);
    }

    /*
     * @testName: serverByteArrayProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.1; JAXRS:SPEC:75;
     * 
     * @test_Strategy: byte[] All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverByteArrayProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "bytearray"));
        invoke();
    }

    /*
     * @testName: clientByteArrayProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: byte[] All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientByteArrayProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        byte[] array = new byte[0];
        array = getResponseBody(array.getClass());
        assertTrue(array == null || array.length == 0,
                "byte [] reader gets unexpected", array);
    }

    /*
     * @testName: serverStringProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.2; JAXRS:SPEC:75;
     * 
     * @test_Strategy: java.lang.String All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverStringProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "string"));
        invoke();
    }

    /*
     * @testName: clientStringProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: java.lang.String All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientStringProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        String entity = getResponseBody(String.class);
        assertTrue(entity == null || entity.length() == 0,
                "String reader gets unexpected", entity);
    }

    /*
     * @testName: serverInputStreamProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.3; JAXRS:SPEC:75;
     * 
     * @test_Strategy: java.io.InputStream All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverInputStreamProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "inputstream"));
        invoke();
    }

    /*
     * @testName: clientInputStreamProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: java.io.InputStream All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientInputStreamProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        InputStream entity = getResponseBody(InputStream.class);
        String content = null;
        if (entity != null)
            try {
                content = JaxrsUtil.readFromStream(entity);
            } catch (Exception e) {
                throw new Fault(e);
            }
        assertTrue(content == null || content.length() == 0,
                "InputStream reader gets unexpected", content);
    }

    /*
     * @testName: serverReaderProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.4; JAXRS:SPEC:75;
     * 
     * @test_Strategy: java.io.Reader All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverReaderProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "reader"));
        invoke();
    }

    /*
     * @testName: clientReaderProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: java.io.Reader All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientReaderProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Reader entity = getResponseBody(Reader.class);
        String content = null;
        if (entity != null)
            try {
                content = JaxrsUtil.readFromReader(entity);
            } catch (Exception e) {
                throw new Fault(e);
            }
        assertTrue(content == null || content.length() == 0,
                "Reader reader gets unexpected", content);
    }

    /*
     * @testName: serverFileProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.5; JAXRS:SPEC:75;
     * 
     * @test_Strategy: java.io.File All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverFileProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "file"));
        invoke();
    }

    /*
     * @testName: clientFileProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: java.io.File All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientFileProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        File entity = getResponseBody(File.class);
        String content = null;
        if (entity != null)
            try {
                content = JaxrsUtil.readFromFile(entity);
            } catch (Exception e) {
                throw new Fault(e);
            }
        assertTrue(content == null || content.length() == 0,
                "File reader gets unexpected", content);
    }

    /*
     * @testName: serverDataSourceProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.6; JAXRS:SPEC:75;
     * 
     * @test_Strategy: DataSource All media types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Disabled(QuarkusRest.Unsupported_DataSource)
    @Test
    public void serverDataSourceProviderTest() throws Fault {
        setProperty(Property.SEARCH_STRING, Resource.NOTNULL);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "datasource"));
        invoke();
    }

    /*
     * @testName: clientDataSourceProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: DataSource All media types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Disabled(QuarkusRest.Unsupported_DataSource)
    @Test
    public void clientDataSourceProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        DataSource entity = getResponseBody(DataSource.class);
        String content = null;
        if (entity != null)
            try {
                content = JaxrsUtil.readFromStream(entity.getInputStream());
            } catch (Exception e) {
                throw new Fault(e);
            }
        assertTrue(content == null || content.length() == 0,
                "DataSource reader gets unexpected", content);
    }

    /*
     * @testName: serverSourceProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.9; JAXRS:SPEC:75;
     * 
     * @test_Strategy: Source XML types.
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Disabled(QuarkusRest.Unsupported_Source)
    @Test
    public void serverSourceProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_XML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "source"));
        invoke();
    }

    /*
     * @testName: clientSourceProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: Source XML types.
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Disabled(QuarkusRest.Unsupported_Source)
    @Test
    public void clientSourceProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_XML);
        Source entity = getResponseBody(Source.class);
        String content = null;
        if (entity != null)
            content = entity.getSystemId();
        assertTrue(content == null || content.length() == 0,
                "Source reader gets unexpected", content);
    }

    /*
     * @testName: serverMultivaluedMapProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.9; JAXRS:SPEC:75;
     * 
     * @test_Strategy: MultivaluedMap<String,String> Form content
     * (application/x-www-form-urlencoded).
     * 
     * When reading zero-length request entities, all implementation-supplied
     * MessageBodyReader implementations except the JAXB-related one MUST create a
     * corresponding Java object that represents zero-length data; they MUST NOT
     * return null.
     */
    @Test
    public void serverMultivaluedMapProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "map"));
        invoke();
    }

    /*
     * @testName: clientMultivaluedMapProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: MultivaluedMap<String,String> Form content
     * (application/x-www-form-urlencoded).
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Test
    public void clientMultivaluedMapProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_FORM_URLENCODED);
        @SuppressWarnings("unchecked")
        MultivaluedMap<String, String> entity = getResponseBody(
                MultivaluedMap.class);
        assertTrue(entity == null || entity.size() == 0,
                "MultivaluedMap reader gets unexpected", entity);
    }

    /*
     * @testName: serverJaxbProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:76;
     * 
     * @test_Strategy: JAXBElement XML media types
     * 
     * The pre-packaged JAXB MessageBodyReader implementation MUST throw a
     * WebApplicationException with a client error response (HTTP 400) for
     * zero-length request entities.
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void serverJaxbProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_XML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "jaxb"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: clientJaxbProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: JAXBElement XML media types
     * 
     * for a zero-length response entities returns null or a corresponding Java
     * object that represents zero-length data.
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void clientJaxbProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "null"));
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.APPLICATION_XML_TYPE));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
        GenericType<JAXBElement<String>> type = new GenericType<JAXBElement<String>>() {
        };
        JAXBElement<String> entity = getResponse().readEntity(type);
        assertNull(entity, "JAXBElement of content type is unexpectedly not null",
                entity);
        logMsg("Read JAXBElement entity is null as expected");
    }

    /*
     * @testName: serverStreamingOutputProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:75;
     * 
     * @test_Strategy: JAXBElement XML media types
     * 
     * The pre-packaged JAXB MessageBodyReader implementation MUST throw a
     * WebApplicationException with a client error response (HTTP 400) for
     * zero-length request entities.
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void serverStreamingOutputProviderTest() throws Fault {
        String content = "StreamingOutput";
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "streamingoutput"));
        setProperty(Property.CONTENT, content);
        setProperty(Property.SEARCH_STRING, content);
        invoke();
    }

    /*
     * @testName: serverBooleanProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:76;
     * 
     * @test_Strategy: Boolean, text/plain media type
     * 
     * The pre-packaged JAXB and the prepackaged primitive type MessageBodyReaders
     * MUST throw a BadRequestException (400 status) for zero-length request
     * entities.
     */
    @Test
    public void serverBooleanProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "boolean"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: clientBooleanProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: Boolean, text/plain media type
     * 
     * the pre-packaged primitive type MessageBodyReader implementations MUST
     * throw a NoContentException for zero-length message entities.
     * 
     * readEntity throws Processing Exception
     */
    @Test
    public void clientBooleanProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.TEXT_PLAIN_TYPE);
        try {
            Boolean entity = getResponseBody(Boolean.class);
            fault("No expcetion thrown and entity is", entity);
        } catch (Exception e) {
            ProcessingException p = assertCause(e, ProcessingException.class,
                    "Processing exception has not been thrown", e);
            NoContentException nce = assertCause(e, NoContentException.class,
                    "NoContentException has not been thrown", p);
            logMsg("Found expected NoContentException", nce);
        }
    }

    /*
     * @testName: serverCharProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:76;
     * 
     * @test_Strategy: Character, text/plain media type
     * 
     * The pre-packaged JAXB and the prepackaged primitive type MessageBodyReaders
     * MUST throw a BadRequestException (400 status) for zero-length request
     * entities.
     */
    @Test
    public void serverCharProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "character"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: clientCharProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: Character, text/plain media type
     * 
     * the pre-packaged primitive type MessageBodyReader implementations MUST
     * throw a NoContentException for zero-length message entities.
     * 
     * readEntity throws Processing Exception
     */
    @Test
    public void clientCharProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.TEXT_PLAIN_TYPE);
        try {
            Character entity = getResponseBody(Character.class);
            fault("No expcetion thrown and entity is", entity);
        } catch (Exception e) {
            ProcessingException p = assertCause(e, ProcessingException.class,
                    "Processing exception has not been thrown", e);
            NoContentException nce = assertCause(e, NoContentException.class,
                    "NoContentException has not been thrown", p);
            logMsg("Found expected NoContentException", nce);
        }
    }

    /*
     * @testName: serverIntegerProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:76;
     * 
     * @test_Strategy: Integer, text/plain media type
     * 
     * The pre-packaged JAXB and the prepackaged primitive type MessageBodyReaders
     * MUST throw a BadRequestException (400 status) for zero-length request
     * entities.
     */
    @Test
    public void serverIntegerProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "integer"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: clientIntegerProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: Integer, text/plain media type
     * 
     * the pre-packaged primitive type MessageBodyReader implementations MUST
     * throw a NoContentException for zero-length message entities.
     * 
     * readEntity throws Processing Exception
     */
    @Test
    public void clientIntegerProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.TEXT_PLAIN_TYPE);
        try {
            Integer entity = getResponseBody(Integer.class);
            fault("No expcetion thrown and entity is", entity);
        } catch (Exception e) {
            ProcessingException p = assertCause(e, ProcessingException.class,
                    "Processing exception has not been thrown", e);
            NoContentException nce = assertCause(e, NoContentException.class,
                    "NoContentException has not been thrown", p);
            logMsg("Found expected NoContentException", nce);
        }
    }

    /*
     * @testName: serverBigDecimalProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.8; JAXRS:SPEC:76;
     * 
     * @test_Strategy: BigDecimal, text/plain media type
     * 
     * The pre-packaged JAXB and the prepackaged primitive type MessageBodyReaders
     * MUST throw a BadRequestException (400 status) for zero-length request
     * entities.
     */
    @Test
    public void serverBigDecimalProviderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.TEXT_PLAIN_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "bigdecimal"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.BAD_REQUEST));
        invoke();
    }

    /*
     * @testName: clientBigDecimalProviderTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:863;
     * 
     * @test_Strategy: Integer, text/plain media type
     * 
     * the pre-packaged primitive type MessageBodyReader implementations MUST
     * throw a NoContentException for zero-length message entities.
     * 
     * readEntity throws Processing Exception
     */
    @Test
    public void clientBigDecimalProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entity"));
        invoke();
        Response response = getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE,
                MediaType.TEXT_PLAIN_TYPE);
        try {
            BigDecimal entity = getResponseBody(BigDecimal.class);
            fault("No expcetion thrown and entity is", entity);
        } catch (Exception e) {
            ProcessingException p = assertCause(e, ProcessingException.class,
                    "Processing exception has not been thrown", e);
            NoContentException nce = assertCause(e, NoContentException.class,
                    "NoContentException has not been thrown", p);
            logMsg("Found expected NoContentException", nce);
        }
    }

    // ////////////////////////////////////////////////////////////////////////

}
