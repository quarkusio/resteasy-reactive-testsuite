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

package com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.writerinterceptorcontext;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.WriterClient0097;
import com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ContextOperation;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0096 extends WriterClient0097<ContextOperation> {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path",
                    "/jaxrs_ee_rs_ext_interceptor_containerwriter_writerinterceptorcontext_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.writerinterceptorcontext.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorTwoBody.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.InterceptorBodyTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.ReaderClient0036.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBean.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.JAXRSClient0034.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorCallbackMethods.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.WriterInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBeanEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InputStreamReaderProvider.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.TemplateInterceptorBody.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.common.impl.ReplacingOutputStream.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorOneBody.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ProceedException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.InterceptorBodyOne.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.JAXRSClient0035.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.InterceptorBodyTwo.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.containerwriter.writerinterceptorcontext.Resource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.TemplateWriterInterceptor.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.TemplateReaderInterceptor.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.readerinterceptorcontext.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.OnWriteExceptionThrowingStringBean.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.ProceedExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.api.rs.ext.interceptor.reader.interceptorcontext.ReaderInterceptorTwo.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.interceptor.writer.writerinterceptorcontext.WriterInterceptorOne.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBeanEntityProvider.class);
                }
            });

    private static final long serialVersionUID = -8158424518609416304L;

    public JAXRSClient0096() {
        setContextRoot(
                "/jaxrs_ee_rs_ext_interceptor_containerwriter_writerinterceptorcontext_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0096 theTests = new JAXRSClient0096();
        theTests.run(args);
    }

    /* Run test */
    /*
     * @testName: getEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:933; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get object to be written as HTTP entity.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getEntityTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.ENTITY);
        invoke(ContextOperation.GETENTITY);
    }

    /*
     * @testName: getHeadersOperationOnlyTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     * 
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getHeadersOperationOnlyTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.OPERATION);
        invoke(ContextOperation.GETHEADERS);
    }

    /*
     * @testName: getHeadersTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getHeadersTest() throws Fault {
        Property p = Property.UNORDERED_SEARCH_STRING;
        setProperty(p, TemplateInterceptorBody.OPERATION);
        for (int i = 0; i != 5; i++)
            setProperty(p, TemplateInterceptorBody.PROPERTY + i);
        invoke(ContextOperation.GETHEADERS);
    }

    /*
     * @testName: getHeadersIsMutableTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:934; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get mutable map of HTTP headers.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getHeadersIsMutableTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.PROPERTY);
        invoke(ContextOperation.GETHEADERSISMUTABLE);
    }

    /*
     * @testName: getOutputStreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:935; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Get the output stream for the object to be written.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void getOutputStreamTest() throws Fault {
        Property p = Property.UNORDERED_SEARCH_STRING;
        setProperty(p, TemplateInterceptorBody.ENTITY);
        setProperty(p, TemplateInterceptorBody.NULL);
        invoke(ContextOperation.GETOUTPUTSTREAM);
    }

    /*
     * @testName: proceedThrowsIOExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:936; JAXRS:JAVADOC:937; JAXRS:JAVADOC:930;
     * JAXRS:JAVADOC:931;
     * 
     * @test_Strategy: Proceed to the next interceptor in the chain.
     * Throws:IOException - if an IO exception arises.
     * 
     * proceed is actually called in every clientwriter.writerinterceptorcontext
     * test
     *
     * WriterInterceptor.aroundWriteTo
     * 
     * WriterInterceptor.aroundWriteTo throws IOException
     */
    @Test
    public void proceedThrowsIOExceptionTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.IOE);
        invoke(ContextOperation.PROCEEDTHROWSIOEXCEPTION);
    }

    /*
     * @testName: proceedThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:936; JAXRS:JAVADOC:1009; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Proceed to the next interceptor in the chain.
     * Throws:WebApplicationException thrown by the wrapped {@code
     * MessageBodyWriter.writeTo} method.
     * 
     * proceed is actually called in every clientwriter.writerinterceptorcontext
     * test
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void proceedThrowsWebApplicationExceptionTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.WAE);
        invoke(ContextOperation.PROCEEDTHROWSWEBAPPEXCEPTION);
    }

    /*
     * @testName: setEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:938; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update object to be written as HTTP entity.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setEntityTest() throws Fault {
        setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.OPERATION);
        invoke(ContextOperation.SETENTITY);
    }

    /*
     * @testName: setOutputStreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:939; JAXRS:JAVADOC:930;
     * 
     * @test_Strategy: Update the output stream for the object to be written.
     *
     * WriterInterceptor.aroundWriteTo
     */
    @Test
    public void setOutputStreamTest() throws Fault {
        setProperty(Property.SEARCH_STRING,
                TemplateInterceptorBody.ENTITY.replace('t', 'x'));
        invoke(ContextOperation.SETOUTPUTSTREAM);
    }

}
