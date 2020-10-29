/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.filter.namebinding;

import java.util.function.Supplier;

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
public class JAXRSClient0024 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_filter_namebinding_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.AbstractAddInterceptor.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.AddOneInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.ComplementNameBinding.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.SingleNameBinding.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.AllMethodBindingResource.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.namebinding.AddTenInterceptor.class);
                }
            });

    private static final long serialVersionUID = -1559382208933998735L;

    public JAXRSClient0024() {
        setContextRoot("/jaxrs_spec_filter_namebinding_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0024().run(args);
    }

    /* Run test */

    /*
     * @testName: noInterceptorBoundTest
     * 
     * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Similarly, a resource method can be decorated with multiple
     * binding annotations. Each binding annotation instance in a resource method
     * denotes a set of filters and interceptors whose class definitions are
     * decorated with that annotation (possibly among others). The final set of
     * (static) filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void noInterceptorBoundTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "one"));
        setProperty(Property.SEARCH_STRING, "1");
        invoke();
        logMsg("No interceptor has been bound as expected");
    }

    /*
     * @testName: singleInterceptorBoundTest
     * 
     * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Similarly, a resource method can be decorated with multiple
     * binding annotations. Each binding annotation instance in a resource method
     * denotes a set of filters and interceptors whose class definitions are
     * decorated with that annotation (possibly among others). The final set of
     * (static) filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void singleInterceptorBoundTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "ten"));
        setProperty(Property.SEARCH_STRING, "11");
        invoke();
        logMsg("Interceptor has been bound as expected");
    }

    /*
     * @testName: onlyPartOfUnionOfInterceptorsBoundTest
     * 
     * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Similarly, a resource method can be decorated with multiple
     * binding annotations. Each binding annotation instance in a resource method
     * denotes a set of filters and interceptors whose class definitions are
     * decorated with that annotation (possibly among others). The final set of
     * (static) filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void onlyPartOfUnionOfInterceptorsBoundTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "complement"));
        setProperty(Property.SEARCH_STRING, "10000");
        invoke();
        logMsg("No interceptor has been bound as expected");
    }

    /*
     * @testName: readerWriterInterceptorBoundTest
     * 
     * @assertion_ids: JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Similarly, a resource method can be decorated with multiple
     * binding annotations. Each binding annotation instance in a resource method
     * denotes a set of filters and interceptors whose class definitions are
     * decorated with that annotation (possibly among others). The final set of
     * (static) filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void readerWriterInterceptorBoundTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        setRequestContentEntity("1111");
        setProperty(Property.SEARCH_STRING, "1113");
        invoke();
        logMsg("Reader and Writer interceptor has been bound as expected");
    }

    /*
     * @testName: resourceAnnotatedFirstMethodInterceptedTest
     * 
     * @assertion_ids: JAXRS:SPEC:87; JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Binding annotations that decorate resource classes apply to
     * all the resource methods defined in them. A filter or interceptor class can
     * be decorated with multiple binding annotations.
     * 
     * Similarly, a resource method can be decorated with multiple binding
     * annotations. Each binding annotation instance in a resource method denotes
     * a set of filters and interceptors whose class definitions are decorated
     * with that annotation (possibly among others). The final set of (static)
     * filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void resourceAnnotatedFirstMethodInterceptedTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "all/hundred"));
        setProperty(Property.SEARCH_STRING, "101");
        invoke();
        logMsg("Both name bound interceptors has been bound as expected");
    }

    /*
     * @testName: resourceAnnotatedSecondMethodInterceptedTest
     * 
     * @assertion_ids: JAXRS:SPEC:87; JAXRS:SPEC:88; JAXRS:SPEC:89;
     * 
     * @test_Strategy: Binding annotations that decorate resource classes apply to
     * all the resource methods defined in them. A filter or interceptor class can
     * be decorated with multiple binding annotations.
     * 
     * Similarly, a resource method can be decorated with multiple binding
     * annotations. Each binding annotation instance in a resource method denotes
     * a set of filters and interceptors whose class definitions are decorated
     * with that annotation (possibly among others). The final set of (static)
     * filters and interceptors is the union of all these sets
     * 
     * returning filters or interceptors from the methods getClasses or
     * getSingletons in an application subclass will bind them globally only if
     * they are not decorated with a name binding annotation.
     */
    @Test
    public void resourceAnnotatedSecondMethodInterceptedTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "all/thousand"));
        setProperty(Property.SEARCH_STRING, "1011");
        invoke();
        logMsg("Both name bound interceptors has been bound as expected");
    }
}
