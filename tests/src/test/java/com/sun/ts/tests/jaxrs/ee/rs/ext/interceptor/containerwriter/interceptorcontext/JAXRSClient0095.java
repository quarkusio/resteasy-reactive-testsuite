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

package com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.interceptorcontext;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.ContextOperation;
import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InputStreamReaderProvider;
import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody;
import com.sun.ts.tests.jaxrs.common.util.JaxrsUtil;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.WriterClient0097;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0095 extends WriterClient0097<ContextOperation> {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_ext_interceptor_containerwriter_interceptorcontext_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.interceptorcontext.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorTwoBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.ReaderClient0036.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBean.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.JAXRSClient0034.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyOne.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.interceptorcontext.WriterInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorCallbackMethods.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBeanEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InputStreamReaderProvider.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorOneBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.JAXRSClient0035.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.interceptorcontext.WriterInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyTwo.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.TemplateWriterInterceptor.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.TemplateReaderInterceptor.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.interceptorcontext.Resource.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorTwo.class);
                }
            });

    private static final long serialVersionUID = -3980167967224950515L;

    public JAXRSClient0095() {
        setContextRoot(
                "/jaxrs_ee_rs_ext_interceptor_containerwriter_interceptorcontext_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0095 theTests = new JAXRSClient0095();
        theTests.run(args);
    }

    /* Run test */
    /*
     * @testName: getAnnotationsTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:903; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get an array of the annotations formally declared on the
     * artifact that initiated the intercepted entity provider invocation.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getAnnotationsTest() throws Fault {
        Annotation[] annotations = ContextOperation.class.getAnnotations();
        for (Annotation a : annotations)
            setProperty(Property.UNORDERED_SEARCH_STRING,
                    a.annotationType().getName());
        invoke(ContextOperation.GETANNOTATIONS);
    }

    /*
     * @testName: getGenericTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:904; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get an array of the annotations formally declared on the
     * artifact that initiated the intercepted entity provider invocation.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getGenericTypeTest() throws Fault {
        setProperty(Property.SEARCH_STRING, String.class.getName());
        invoke(ContextOperation.GETGENERICTYPE);
    }

    /*
     * @testName: getMediaTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:905; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get media type of HTTP entity.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getMediaTypeTest() throws Fault {
        setProperty(Property.SEARCH_STRING, MediaType.APPLICATION_JSON);
        invoke(ContextOperation.GETMEDIATYPE);
    }

    /*
     * @testName: getPropertyIsNullTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:906; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Returns null if there is no property by that name.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getPropertyIsNullTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.NULL);
        invoke(ContextOperation.GETPROPERTY);
    }

    /*
     * @testName: getPropertyNamesIsReadOnlyTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1007; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Returns immutable java.util.Collection containing the
     * property names available within the context of the current request/response
     * exchange context.
     * 
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getPropertyNamesIsReadOnlyTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, TemplateInterceptorBody.NULL);
        invoke(ContextOperation.GETPROPERTYNAMESISREADONLY);
    }

    /*
     * @testName: getPropertyNamesTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1007; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Returns an enumeration containing the property names
     * available within the context of the current request/response exchange
     * context.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getPropertyNamesTest() throws Fault {
        for (int i = 0; i != 5; i++)
            setProperty(Property.UNORDERED_SEARCH_STRING,
                    TemplateInterceptorBody.PROPERTY + i);
        invoke(ContextOperation.GETPROPERTYNAMES);
    }

    /*
     * @testName: getTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:908; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get Java type supported by corresponding message body
     * provider.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getTypeTest() throws Fault {
        setProperty(Property.SEARCH_STRING, String.class.getName());
        invoke(ContextOperation.GETTYPE);
    }

    /*
     * @testName: removePropertyTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:909; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Removes a property with the given name from the current
     * request/response exchange context. After removal, subsequent calls to
     * getProperty(java.lang.String) to retrieve the property value will return
     * null.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void removePropertyTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.NULL);
        invoke(ContextOperation.REMOVEPROPERTY);
    }

    /*
     * @testName: setAnnotationsTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:910; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update annotations on the formal declaration of the
     * artifact that initiated the intercepted entity provider invocation.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setAnnotationsTest() throws Fault {
        Annotation[] annotations = ContextOperation.class.getAnnotations();
        for (Annotation a : annotations)
            setProperty(Property.UNORDERED_SEARCH_STRING,
                    a.annotationType().getName());
        invoke(ContextOperation.SETANNOTATIONS);
    }

    /*
     * @testName: setAnnotationsNullThrowsNPETest
     * 
     * @assertion_ids: JAXRS:JAVADOC:910; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Throws NullPointerException - in case the input parameter
     * is null.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setAnnotationsNullThrowsNPETest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.NPE);
        invoke(ContextOperation.SETANNOTATIONSNULL);
    }

    /*
     * @testName: setGenericTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:911; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update type of the object to be produced or written.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setGenericTypeTest() throws Fault {
        setProperty(Property.SEARCH_STRING, "[B");
        invoke(ContextOperation.SETGENERICTYPE);
    }

    /*
     * @testName: setMediaTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:912; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update media type of HTTP entity.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setMediaTypeTest() throws Fault {
        setProperty(Property.SEARCH_STRING, MediaType.APPLICATION_FORM_URLENCODED);
        invoke(ContextOperation.SETMEDIATYPE);
    }

    /*
     * @testName: setPropertyTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:913; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Binds an object to a given property name in the current
     * request/response exchange context. If the name specified is already used
     * for a property, this method will replace the value of the property with the
     * new value.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setPropertyTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.ENTITY2);
        invoke(ContextOperation.SETPROPERTY);
    }

    /*
     * @testName: setPropertyNullTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:913; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: If a null value is passed, the effect is the same as
     * calling the removeProperty(String) method.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setPropertyNullTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.NULL);
        invoke(ContextOperation.SETPROPERTYNULL);
    }

    /*
     * @testName: setTypeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:914; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update Java type before calling message body provider.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setTypeTest() throws Fault {
        addProvider(InputStreamReaderProvider.class);
        invoke(ContextOperation.SETTYPE);
        InputStreamReader isr = getResponseBody(InputStreamReader.class);
        try {
            String entity = JaxrsUtil.readFromReader(isr);
            assertFault(entity.contains(InputStreamReader.class.getName()),
                    "Expected", InputStreamReader.class.getName(), "not found");
            logMsg("#setType set correct type", entity);
        } catch (IOException e) {
            throw new Fault(e);
        }
    }
}
