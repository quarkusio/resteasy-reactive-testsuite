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

package com.sun.ts.tests.jaxrs.spec.client.typedentities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Supplier;

import javax.activation.DataSource;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.impl.SinglevaluedMap;
import com.sun.ts.tests.jaxrs.common.impl.StringDataSource;
import com.sun.ts.tests.jaxrs.common.impl.StringStreamingOutput;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0025 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_client_typedentities_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.client.typedentities.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.client.typedentities.EntityMessageReader.class,
                                    com.sun.ts.tests.jaxrs.spec.client.typedentities.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.client.typedentities.EntityMessageWriter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity.class);
                }
            });

    private static final long serialVersionUID = 1339633069677106930L;

    private static final String entity = Resource.class.getName();

    public JAXRSClient0025() {
        setContextRoot("/jaxrs_spec_client_typedentities_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0025().run(args);
    }

    /* Run test */
    /*
     * @testName: clientAnyReaderUsageTest
     * 
     * @assertion_ids: JAXRS:SPEC:69;
     * 
     * @test_Strategy: JAX-RS implementations are REQUIRED to use entity providers
     */
    @Test
    public void clientAnyReaderUsageTest() throws Fault {
        addProvider(new EntityMessageReader());
        setProperty(Property.REQUEST, buildRequest(Request.GET, "readerprovider"));
        setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
        setProperty(Property.SEARCH_STRING, Resource.class.getName());
        bufferEntity(true);
        invoke();

        ReadableWritableEntity entity = getResponse()
                .readEntity(ReadableWritableEntity.class);
        assertFault(entity != null, "Returned Entity is null!");
        assertFault(entity.toString().equals(Resource.class.getName()),
                "Returned Entity", entity.toString(), "is unexpected");
    }

    /*
     * @testName: clientAnyWriterUsageTest
     * 
     * @assertion_ids: JAXRS:SPEC:69;
     * 
     * @test_Strategy: JAX-RS implementations are REQUIRED to use entity providers
     */
    // RESTEasy passes this test because org.jboss.resteasy.plugins.providers.DefaultTextPlain uses valueOf / constructor in order to read the object
    // however the spec doesn't mention this way at all
    @Disabled(QuarkusRest.Underspecified)
    @Test
    public void clientAnyWriterUsageTest() throws Fault {
        ReadableWritableEntity entity = new ReadableWritableEntity(
                String.valueOf(serialVersionUID));
        addProvider(new EntityMessageWriter());
        setProperty(Property.REQUEST, buildRequest(Request.POST, "writerprovider"));
        setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
        setRequestContentEntity(entity);
        setProperty(Property.SEARCH_STRING, entity.toXmlString());
        invoke();
    }

    // ///////////////////////////////////////////////////////////////////////
    // Standard readers

    /*
     * @testName: clientByteArrayReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientByteArrayReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        toStringTest(byte[].class);
    }

    /*
     * @testName: clientStringReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientStringReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        toStringTest(String.class);
    }

    /*
     * @testName: clientInputStreamReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientInputStreamReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        InputStream responseEntity = getResponse().readEntity(InputStream.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
        InputStreamReader reader = new InputStreamReader(responseEntity);
        readerTest(reader);
    }

    /*
     * @testName: clientReaderReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientReaderReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        Reader responseEntity = getResponse().readEntity(Reader.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
        readerTest(responseEntity);
    }

    /*
     * @testName: clientFileReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientFileReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        File responseEntity = getResponse().readEntity(File.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
        FileReader fr;
        try {
            fr = new FileReader(responseEntity);
        } catch (FileNotFoundException e) {
            throw new Fault(e);
        }
        readerTest(fr);
        responseEntity.deleteOnExit();
    }

    /*
     * @testName: clientDataSourceReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_DataSource)
    @Test
    public void clientDataSourceReaderTest() throws Fault {
        standardReaderInvocation(MediaType.WILDCARD_TYPE);
        DataSource responseEntity = getResponse().readEntity(DataSource.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(responseEntity.getInputStream());
        } catch (IOException e) {
            throw new Fault(e);
        }
        readerTest(reader);
    }

    /*
     * @testName: clientSourceReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_Source)
    @Test
    public void clientSourceReaderTest() throws Fault {
        standardReaderInvocation(MediaType.TEXT_XML_TYPE);
        Source responseEntity = getResponse().readEntity(Source.class);
        assertFault(responseEntity != null, "Returned Entity is null!");

        standardReaderInvocation(MediaType.APPLICATION_XML_TYPE);
        responseEntity = getResponse().readEntity(Source.class);
        assertFault(responseEntity != null, "Returned Entity is null!");

        standardReaderInvocation(MediaType.APPLICATION_ATOM_XML_TYPE);
        responseEntity = getResponse().readEntity(Source.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
    }

    /*
     * @testName: clientJaxbElementReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void clientJaxbElementReaderTest() throws Fault {
        GenericType<JAXBElement<String>> type = new GenericType<JAXBElement<String>>() {
        };

        standardReaderInvocation(MediaType.TEXT_XML_TYPE);
        JAXBElement<?> responseEntity = getResponse().readEntity(type);
        assertFault(responseEntity != null, "Returned Entity is null!");

        standardReaderInvocation(MediaType.APPLICATION_XML_TYPE);
        responseEntity = getResponse().readEntity(type);
        assertFault(responseEntity != null, "Returned Entity is null!");

        standardReaderInvocation(MediaType.APPLICATION_ATOM_XML_TYPE);
        responseEntity = getResponse().readEntity(type);
        assertFault(responseEntity != null, "Returned Entity is null!");

        String s = responseEntity.getValue().toString();
        assertFault(s.equals(entity), "Returned Entity", s, "is unexpected");
    }

    /*
     * @testName: clientMultivaluedMapReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientMultivaluedMapReaderTest() throws Fault {
        standardReaderInvocation(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        @SuppressWarnings("unchecked")
        MultivaluedMap<String, String> responseEntity = getResponse()
                .readEntity(MultivaluedMap.class);
        assertFault(responseEntity != null, "Returned Entity is null!");
        boolean ok = responseEntity.containsKey(entity)
                || responseEntity.containsValue(entity);
        assertFault(ok, "Returned Entity", responseEntity,
                "does not contains supposed value");
    }

    // ///////////////////////////////////////////////////////////////////////
    // Writer test

    /*
     * @testName: clientByteArrayWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientByteArrayWriterTest() throws Fault {
        standardWriterInvocation(entity.getBytes());
    }

    /*
     * @testName: clientStringWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientStringWriterTest() throws Fault {
        standardWriterInvocation(entity);
    }

    /*
     * @testName: clientInputStreamWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientInputStreamWriterTest() throws Fault {
        ByteArrayInputStream bais = new ByteArrayInputStream(entity.getBytes());
        standardWriterInvocation(bais);
        close(bais);
    }

    /*
     * @testName: clientReaderWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientReaderWriterTest() throws Fault {
        ByteArrayInputStream bais = new ByteArrayInputStream(entity.getBytes());
        Reader reader = new InputStreamReader(bais);
        standardWriterInvocation(reader);
        close(reader);
    }

    /*
     * @testName: clientFileWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientFileWriterTest() throws Fault {
        File file = createFileEntity(entity);
        standardWriterInvocation(file);
        file.deleteOnExit();
    }

    /*
     * @testName: clientDataSourceWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_DataSource)
    @Test
    public void clientDataSourceWriterTest() throws Fault {
        DataSource ds = new StringDataSource(entity, MediaType.WILDCARD_TYPE);
        standardWriterInvocation(ds);
    }

    /*
     * @testName: clientSourceWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_Source)
    @Test
    public void clientSourceWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_XML_TYPE));
        File file = createFileEntity("<xml>" + entity + "</xml>");
        Source source = new StreamSource(file);
        standardWriterInvocation(source);
        file.deleteOnExit();
    }

    /*
     * @testName: clientJaxbElementWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void clientJaxbElementWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_XML_TYPE));
        JAXBElement<String> element = new JAXBElement<String>(new QName(""),
                String.class, entity);
        standardWriterInvocation(element);
    }

    /*
     * @testName: clientMultivaluedMapWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Test
    public void clientMultivaluedMapWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        MultivaluedMap<String, String> map = new SinglevaluedMap<String, String>();
        map.add(entity, entity);
        standardWriterInvocation(map);
    }

    /*
     * @testName: clientStreamingOutputWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:70;
     * 
     * @test_Strategy: See Section 4.2.4 for a list of entity providers that MUST
     * be supported by all JAX-RS implementations
     */
    @Disabled(QuarkusRest.Unsupported_Streaming_Output)
    @Test
    public void clientStreamingOutputWriterTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        StreamingOutput output = new StringStreamingOutput(entity);
        standardWriterInvocation(output);
    }

    // ///////////////////////////////////////////////////////////////////////
    // Helper methods

    protected void standardReaderInvocation(MediaType mediaType) throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "standardreader"));
        setProperty(Property.SEARCH_STRING, entity);
        setProperty(Property.REQUEST_HEADERS, buildAccept(mediaType));
        bufferEntity(true);
        invoke();
    }

    protected void standardWriterInvocation(Object objectEntity) throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "standardwriter"));
        setRequestContentEntity(objectEntity);
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    protected <T> void toStringTest(Class<T> clazz) throws Fault {
        T responseEntity = getResponse().readEntity(clazz);
        assertFault(responseEntity != null, "Returned Entity is null!");
        String s = responseEntity.toString();
        if (s.startsWith("[B"))
            s = new String((byte[]) responseEntity);
        assertFault(s.equals(entity), "Was expected returned entity", entity, "got",
                s);
    }

    void readerTest(Reader reader) throws Fault {
        BufferedReader bf = new BufferedReader(reader);
        String s = null;
        try {
            s = bf.readLine();
        } catch (IOException e) {
            throw new Fault(e);
        }
        assertEquals(s, entity, "Returned Entity", s, "is unexpected");
        close(reader);
    }

    void close(Closeable closable) throws Fault {
        try {
            closable.close();
        } catch (IOException e) {
            throw new Fault(e);
        }
    }

    File createFileEntity(String entity) throws Fault {
        File file;
        FileWriter fr = null;
        try {
            file = File.createTempFile("tckjaxrs", ".tmp");
            assertFault(file.canWrite(), "file is not for writing");
            fr = new FileWriter(file);
            fr.write(entity);
            fr.flush();
            fr.close();
        } catch (IOException e) {
            try {
                fr.close();
            } catch (IOException ee) {
            }
            throw new Fault(e);
        }
        return file;
    }

}
