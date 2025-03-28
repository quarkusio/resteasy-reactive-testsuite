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

package com.sun.ts.tests.jaxrs.spec.provider.writer;

import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;

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
public class JAXRSClient0011 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_provider_writer_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.DefaultEntityWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.AppJavaEntityWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.AppAnyEntityWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.OkResponse.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.EntityForWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.writer.AppXmlObjectWriter.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0011() {
        setContextRoot("/jaxrs_spec_provider_writer_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0011().run(args);
    }

    /* Run test */
    /*
     * @testName: responseSubClassTest
     * 
     * @assertion_ids: JAXRS:SPEC:63; JAXRS:SPEC:63.1;
     * 
     * @test_Strategy: Obtain the object that will be mapped to the response
     * entity body. For a return type of Response or subclasses the object is the
     * value of the entity property
     * 
     */
    @Test
    public void responseSubClassTest() throws Fault {
        setWriter(AppAnyEntityWriter.class);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "subresponse"));
        // App/octet-stream
        setProperty(Property.SEARCH_STRING,
                AppAnyEntityWriter.class.getSimpleName());
        setProperty(Property.SEARCH_STRING,
                AppAnyEntityWriter.class.getSimpleName().toUpperCase());
        invoke();
    }

    /*
     * @testName: supportXmlByDefaultWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:63; JAXRS:SPEC:63.2; JAXRS:SPEC:63.3;
     * JAXRS:SPEC:63.4; JAXRS:SPEC:63.5;
     * 
     * @test_Strategy: Select the set of MessageBodyWriter providers that support
     * (see Section 4.2.3) the object and media type of the response entity body
     * 
     * Sort the selected MessageBodyWriter providers with a primary key of generic
     * type where providers whose generic type is the nearest superclass of the
     * object class are sorted first and a secondary key of media type (see
     * Section 4.2.3).
     */
    @Test
    public void supportXmlByDefaultWriterTest() throws Fault {
        setWriter(DefaultEntityWriter.class);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "supportxml"));
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppAnyEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                DefaultEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                DefaultEntityWriter.class.getSimpleName().toUpperCase());
        invoke();
    }

    /*
     * @testName: supportXmlByXmlWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:63; JAXRS:SPEC:63.2; JAXRS:SPEC:63.3;
     * JAXRS:SPEC:63.4; JAXRS:SPEC:63.5;
     * 
     * @test_Strategy: Select the set of MessageBodyWriter providers that support
     * (see Section 4.2.3) the object and media type of the response entity body
     * 
     * Sort the selected MessageBodyWriter providers with a primary key of generic
     * type where providers whose generic type is the nearest superclass of the
     * object class are sorted first and a secondary key of media type (see
     * Section 4.2.3).
     */
    @Test
    public void supportXmlByXmlWriterTest() throws Fault {
        setWriter(AppXmlObjectWriter.class);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "supportxml"));
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppAnyEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppXmlObjectWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                DefaultEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppXmlObjectWriter.class.getSimpleName().toUpperCase());
        invoke();
    }

    /*
     * @testName: supportAllTest
     * 
     * @assertion_ids: JAXRS:SPEC:63; JAXRS:SPEC:63.2; JAXRS:SPEC:63.3;
     * JAXRS:SPEC:63.4; JAXRS:SPEC:63.5;
     * 
     * @test_Strategy: Select the set of MessageBodyWriter providers that support
     * (see Section 4.2.3) the object and media type of the response entity body
     * 
     * Sort the selected MessageBodyWriter providers with a primary key of generic
     * type where providers whose generic type is the nearest superclass of the
     * object class are sorted first and a secondary key of media type (see
     * Section 4.2.3).
     */
    @Test
    public void supportAllTest() throws Fault {
        setWriter(DefaultEntityWriter.class);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "supportall"));
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppAnyEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                DefaultEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                DefaultEntityWriter.class.getSimpleName().toUpperCase());
        // should not be there app/java does not match app/octet-stream
        // app/xml should not be there, due to primary key in sorting
        invoke();
    }

    /*
     * @testName: supportAppJavaTest
     * 
     * @assertion_ids: JAXRS:SPEC:63; JAXRS:SPEC:63.2; JAXRS:SPEC:63.3;
     * JAXRS:SPEC:63.4; JAXRS:SPEC:63.5;
     * 
     * @test_Strategy: Select the set of MessageBodyWriter providers that support
     * (see Section 4.2.3) the object and media type of the response entity body
     * 
     * Sort the selected MessageBodyWriter providers with a primary key of generic
     * type where providers whose generic type is the nearest superclass of the
     * object class are sorted first and a secondary key of media type (see
     * Section 4.2.3).
     */
    @Test
    public void supportAppJavaTest() throws Fault {
        setWriter(AppJavaEntityWriter.class);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "supportmedia"));
        setProperty(Property.CONTENT,
                new MediaType("application", "java").toString());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppJavaEntityWriter.class.getSimpleName());
        setProperty(Property.UNORDERED_SEARCH_STRING,
                AppJavaEntityWriter.class.getSimpleName().toUpperCase());
        invoke();
    }

    private void setWriter(Class<?> clazz) throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "writer"));
        setProperty(Property.CONTENT, clazz.getName());
        invoke();
        setPrintEntity(true);
    }

}
