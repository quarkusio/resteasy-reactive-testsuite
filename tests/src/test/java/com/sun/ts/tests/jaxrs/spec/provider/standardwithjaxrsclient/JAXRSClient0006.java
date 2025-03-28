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

package com.sun.ts.tests.jaxrs.spec.provider.standardwithjaxrsclient;

import java.math.BigDecimal;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0006 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_provider_standardwithjaxrsclient_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.provider.standardwithjaxrsclient.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.standardwithjaxrsclient.Resource.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0006() {
        setContextRoot("/jaxrs_spec_provider_standardwithjaxrsclient_web/resource");
    }

    private void setPropertyAndInvoke(String resourceMethod, MediaType md)
            throws Fault {
        String ct = buildContentType(md);
        setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
        if (!MediaType.WILDCARD_TYPE.equals(md))
            setProperty(Property.EXPECTED_HEADERS, ct);
        setProperty(Property.REQUEST_HEADERS, ct);
        setProperty(Property.REQUEST_HEADERS, buildAccept(md));
        invoke();
    }

    private <T> void assertEntity(Class<T> entityClass, T value) throws Fault {
        T entity = getResponseBody(entityClass);
        assertNotNull(entity, "response body is null");
        assertEquals(entity, value, "Expected", value, "differs from gotten",
                entity);
        logMsg("Found expected entity", entity);
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0006().run(args);
    }

    /*
     * @testName: mapElementProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.9;
     * 
     * @test_Strategy: An implementation MUST include pre-packaged
     * MessageBodyReader and MessageBodyWriter implementations for the following
     * Java and media type combinations
     * 
     * MultivaluedMap<String,String> Form content
     * (application/x-www-form-urlencoded).
     */
    @SuppressWarnings("unchecked")
    @Test
    public void mapElementProviderTest() throws Fault {
        String element = "map";
        MultivaluedMap<String, String> map = new MultivaluedHashMap<String, String>();
        map.add(element, element);

        setRequestContentEntity(map);
        setPropertyAndInvoke("map", MediaType.APPLICATION_FORM_URLENCODED_TYPE);

        map = getResponseBody(MultivaluedMap.class);
        assertNotNull(map, "returned MultivaluedMap is null");
        String returned = map.getFirst(element);
        assertNotNull(returned, "Returned map does not contain expected element",
                element);
        assertEquals(returned, element, "returned element", returned,
                "differs from expected", element);
        logMsg("found expected MultivaluedMap", map);
    }

    /*
     * @testName: readWriteProviderBoolTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.11;
     * 
     * @test_Strategy: java.lang.Boolean. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     * 
     */
    @Test
    public void readWriteProviderBoolTest() throws Fault {
        setProperty(Property.CONTENT, "false");
        setProperty(Property.SEARCH_STRING, "false");
        setPropertyAndInvoke("bool", MediaType.TEXT_PLAIN_TYPE);
    }

    /*
     * @testName: readWriteProviderBooleanTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.11;
     * 
     * @test_Strategy: java.lang.Boolean. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     * 
     */
    @Test
    public void readWriteProviderBooleanTest() throws Fault {
        Boolean bool = Boolean.valueOf(true);
        setRequestContentEntity(bool);
        setPropertyAndInvoke("boolean", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(Boolean.class, bool);
    }

    /*
     * @testName: readWriteProviderCharTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.12;
     * 
     * @test_Strategy: java.lang.Character. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderCharTest() throws Fault {
        setProperty(Property.CONTENT, "x");
        setProperty(Property.SEARCH_STRING, "x");
        setPropertyAndInvoke("char", MediaType.TEXT_PLAIN_TYPE);
    }

    /*
     * @testName: readWriteProviderCharacterTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.12;
     * 
     * @test_Strategy: java.lang.Character. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderCharacterTest() throws Fault {
        Character c = Character.valueOf('x');
        setRequestContentEntity(c);
        setPropertyAndInvoke("character", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(Character.class, c);
    }

    /*
     * @testName: readWriteProviderIntTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderIntTest() throws Fault {
        setProperty(Property.CONTENT, String.valueOf(Integer.MAX_VALUE));
        setProperty(Property.SEARCH_STRING, String.valueOf(Integer.MAX_VALUE));
        setPropertyAndInvoke("int", MediaType.TEXT_PLAIN_TYPE);
    }

    /*
     * @testName: readWriteProviderLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderLongTest() throws Fault {
        setProperty(Property.CONTENT, String.valueOf(Long.MAX_VALUE));
        setProperty(Property.SEARCH_STRING, String.valueOf(Long.MAX_VALUE));
        setPropertyAndInvoke("long", MediaType.TEXT_PLAIN_TYPE);
    }

    /*
     * @testName: readWriteProviderIntegerTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderIntegerTest() throws Fault {
        Integer i = new Integer(Integer.MAX_VALUE);
        setRequestContentEntity(i);
        setPropertyAndInvoke("integer", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(Integer.class, i);
    }

    /*
     * @testName: readWriteProviderBigLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderBigLongTest() throws Fault {
        Long l = new Long(Long.MAX_VALUE);
        setRequestContentEntity(l);
        setPropertyAndInvoke("biglong", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(Long.class, l);
    }

    /*
     * @testName: readWriteProviderDoubleTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderDoubleTest() throws Fault {
        Double pi = Math.PI;
        setRequestContentEntity(pi);
        setPropertyAndInvoke("bigdouble", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(Double.class, pi);
    }

    /*
     * @testName: readWriteProviderBigDecimalTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.13;
     * 
     * @test_Strategy: java.lang.Number. Only for text/plain. Corresponding
     * primitive types supported via boxing/unboxing conversion.
     */
    @Test
    public void readWriteProviderBigDecimalTest() throws Fault {
        BigDecimal bd = new BigDecimal(Integer.MAX_VALUE);
        setRequestContentEntity(bd);
        setPropertyAndInvoke("bigdecimal", MediaType.TEXT_PLAIN_TYPE);
        assertEntity(BigDecimal.class, bd);
    }

}
