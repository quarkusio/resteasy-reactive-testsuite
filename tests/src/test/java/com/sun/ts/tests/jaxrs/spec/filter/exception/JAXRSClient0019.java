/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.filter.exception;

import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

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
/**
 * Test the interceptor is called when any entity provider is called
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0019 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_filter_exception_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AbstractAddFilter.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AddOneFilter.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AddOneInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.NeverUsedExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AddTenGlobalInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.RuntimeExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AddTenGlobalFilter.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.AbstractAddInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.ExceptionNameBinding.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.PostMatchingThrowingFilter.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.exception.PreMatchingThrowingFilter.class);
                }
            });

    private static final long serialVersionUID = 4992831116540554144L;

    public JAXRSClient0019() {
        setContextRoot("/jaxrs_spec_filter_exception_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0019().run(args);
    }

    /* Run test */

    /*
     * @testName: throwExceptionOnPostMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91; JAXRS:SPEC:91.1;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * If a web resource had been matched before the exception was thrown, then
     * all the filters in the ContainerResponse chain for that resource MUST be
     * invoked;
     * 
     */
    @Test
    public void throwExceptionOnPostMatchingFilterTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":100");
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "11111");
        invoke();
        logMsg("Exception has been handled as expected");
    }

    /*
     * @testName: throwExceptionOnPreMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91; JAXRS:SPEC:91.2;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * Otherwise, only globally bound filters in the Container Response chain MUST
     * be invoked
     */
    @Test
    public void throwExceptionOnPreMatchingFilterTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PreMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":100");
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "10110");
        invoke();
        logMsg("Exception has been handled as expected");
    }

    /*
     * @testName: throwExceptionOnInterceptorTest
     * 
     * @assertion_ids: JAXRS:SPEC:90;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     */
    @Test
    public void throwExceptionOnInterceptorTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER + ":100");
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "11111");
        invoke();
        logMsg("Exception has been handled as expected");
    }

    /*
     * @testName: noNameBoundInterceptorTest
     * 
     * @assertion_ids: JAXRS:SPEC:90;
     * 
     * @test_Strategy: Just to be sure we have only one global binding interceptor
     */
    @Test
    public void noNameBoundInterceptorTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "nobinding"));
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "10020");
        invoke();
        logMsg("Only Globally bound interceptor has been invoked as expected");
    }

    /*
     * @testName: throwSecondExceptionFromMapperFirstFromInterceptorTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromMapperFirstFromInterceptorTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER + ":"
                        + RuntimeExceptionMapper.THROW_AGAIN);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName: throwSecondExceptionFromMapperFirstFromPreMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromMapperFirstFromPreMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PreMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + RuntimeExceptionMapper.THROW_AGAIN);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName: throwSecondExceptionFromMapperFirstFromPostMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromMapperFirstFromPostMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + RuntimeExceptionMapper.THROW_AGAIN);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName: throwSecondExceptionFromInterceptorFirstFromInterceptorTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromInterceptorFirstFromInterceptorTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER + ":"
                        + AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName:
     * throwSecondExceptionFromInterceptorFirstFromPreMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromInterceptorFirstFromPreMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PreMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName:
     * throwSecondExceptionFromInterceptorFirstFromPostMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwSecondExceptionFromInterceptorFirstFromPostMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName: throwNoExceptionFromPostMatchingFilterFirstFromInterceptorTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     */
    @Test
    public void throwNoExceptionFromPostMatchingFilterFirstFromInterceptorTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                AddTenGlobalInterceptor.EXCEPTION_FIRING_HEADER + ":"
                        + PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "111011");
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName:
     * throwNoExceptionFromPostMatchingFilterFirstFromPostMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     * 
     * The request filter is not used on response from mapper
     */
    @Test
    public void throwNoExceptionFromPostMatchingFilterFirstFromPostMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "111011");
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }

    /*
     * @testName:
     * throwNoExceptionFromPostMatchingFilterFirstFromPreMatchingFilterTest
     * 
     * @assertion_ids: JAXRS:SPEC:90; JAXRS:SPEC:91;
     * 
     * @test_Strategy: When a filter or interceptor method throws an exception,
     * the JAX-RS runtime will attempt to map the exception
     * 
     * At most one exception mapper will be used in a single request processing
     * cycle to avoid potentially infinite loops.
     * 
     * The request filter is not used on response from mapper
     */
    @Test
    public void throwNoExceptionFromPostMatchingFilterFirstFromPreMatchingFilterTest()
            throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setProperty(Property.REQUEST_HEADERS,
                PreMatchingThrowingFilter.EXCEPTION_FIRING_HEADER + ":"
                        + PostMatchingThrowingFilter.EXCEPTION_FIRING_HEADER);
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "110010");
        invoke();
        logMsg("Exception has not been handled second time as expected");
    }
}
