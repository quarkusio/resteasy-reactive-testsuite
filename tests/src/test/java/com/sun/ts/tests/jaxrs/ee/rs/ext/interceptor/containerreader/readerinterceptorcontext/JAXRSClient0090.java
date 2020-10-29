/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerreader.readerinterceptorcontext;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody;
import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ContextOperation;
import com.sun.ts.tests.jaxrs.common.client.TextCaser;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerreader.ReaderClient0091;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0090 extends ReaderClient0091<ContextOperation> {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path",
                    "/jaxrs_ee_rs_ext_interceptor_containerreader_readerinterceptorcontext_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerreader.readerinterceptorcontext.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorTwoBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.ReaderClient0036.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBean.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.JAXRSClient0035.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.JAXRSClient0034.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorCallbackMethods.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBeanEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InputStreamReaderProvider.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerreader.readerinterceptorcontext.Resource.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.TemplateReaderInterceptor.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorOneBody.class);
                }
            });

    private static final long serialVersionUID = 3006391868445878375L;

    public JAXRSClient0090() {
        setContextRoot(
                "/jaxrs_ee_rs_ext_interceptor_containerreader_readerinterceptorcontext_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0090 theTests = new JAXRSClient0090();
        theTests.run(args);
    }

    /* Run test */

    /*
     * @testName: getHeadersOperationOnlyTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void getHeadersOperationOnlyTest() throws Fault {
        setOperationAndEntity(ContextOperation.GETHEADERS);
        setProperty(Property.SEARCH_STRING_IGNORE_CASE,
                TemplateInterceptorBody.OPERATION);
        setPrintEntity(true);
        invoke();
    }

    /*
     * @testName: getHeadersHeadersSetTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void getHeadersHeadersSetTest() throws Fault {
        Property p = Property.UNORDERED_SEARCH_STRING;
        setOperationAndEntity(ContextOperation.GETHEADERS);
        setProperty(p, TemplateInterceptorBody.OPERATION);
        setTextCaser(TextCaser.LOWER);
        for (int i = 0; i != 5; i++) {
            addHeader(TemplateInterceptorBody.PROPERTY + i, "any");
            setProperty(p, TemplateInterceptorBody.PROPERTY + i);
        }
        invoke();
    }

    /* Run test */
    /*
     * @testName: getHeadersIsMutableTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void getHeadersIsMutableTest() throws Fault {
        setOperationAndEntity(ContextOperation.GETHEADERSISMUTABLE);
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.PROPERTY);
        invoke();
    }

    /*
     * @testName: getInputStreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:924; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Get the input stream of the object to be read.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void getInputStreamTest() throws Fault {
        String entity = "getInputStreamEntity";
        setOperationAndEntity(ContextOperation.GETINPUTSTREAM);
        setRequestContentEntity(entity);
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    /*
     * @testName: proceedThrowsIOExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:925; JAXRS:JAVADOC:926; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Throws: IOException - if an IO error arises
     * 
     * proceed is actually called in every
     * containerreader.readerinterceptorcontext test
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void proceedThrowsIOExceptionTest() throws Fault {
        setOperationAndEntity(ContextOperation.PROCEEDTHROWSIOEXCEPTION);
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.IOE);
        invoke();
    }

    /*
     * @testName: proceedThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:925; JAXRS:JAVADOC:1008; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Throws: WebApplicationException - thrown by the wrapped
     * {@code MessageBodyReader.readFrom} method.
     * 
     * Proceed is tested in any of the interceptor tests.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void proceedThrowsWebApplicationExceptionTest() throws Fault {
        setOperationAndEntity(ContextOperation.PROCEEDTHROWSWEBAPPEXCEPTION);
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.WAE);
        invoke("errorbean");
    }

    /*
     * @testName: setInputStreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:927; JAXRS:JAVADOC:920;
     * 
     * @test_Strategy: Update the input stream of the object to be read.
     * 
     * ReaderInterceptor.aroundReadFrom
     */
    @Test
    public void setInputStreamTest() throws Fault {
        setOperationAndEntity(ContextOperation.SETINPUTSTREAM);
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.ENTITY2);
        invoke();
    }

    // =====================

}
