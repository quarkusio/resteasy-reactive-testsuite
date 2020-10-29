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

package com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate;

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
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0137 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_container_requestcontext_illegalstate_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.TemplateFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.RequestTemplateFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.ResponseFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.ResponseTemplateFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.RequestFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.illegalstate.Resource.class);
                }
            });

    private static final long serialVersionUID = -8112756483664393579L;

    public JAXRSClient0137() {
        setContextRoot(
                "/jaxrs_ee_rs_container_requestcontext_illegalstate_web/resource");
        setPrintEntity(true);
    }

    public static void main(String[] args) {
        new JAXRSClient0137().run(args);
    }

    /*
     * @testName: setMethodTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:669; JAXRS:SPEC:85;
     * 
     * @test_Strategy: Throws IllegalStateException - in case the method is not
     * invoked from a pre-matching request filter.
     */
    @Test
    public void setMethodTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.SETMETHOD);
    }

    /*
     * @testName: setRequestUriOneUriTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:672; JAXRS:SPEC:85;
     * 
     * @test_Strategy: Trying to invoke the method in a filter bound to a resource
     * method results in an IllegalStateException being thrown.
     * 
     * ContainerRequestContext.abortWith
     */
    @Test
    public void setRequestUriOneUriTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.SETREQUESTURI1);
    }

    /*
     * @testName: setRequestUriTwoUrisTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:674; JAXRS:SPEC:85;
     * 
     * @test_Strategy: Trying to invoke the method in a filter bound to a resource
     * method results in an IllegalStateException being thrown.
     * 
     * ContainerRequestContext.abortWith
     */
    @Test
    public void setRequestUriTwoUrisTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.SETREQUESTURI2);
    }

    /*
     * @testName: abortWithTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:649;
     * 
     * @test_Strategy: throws IllegalStateException in case the method is invoked
     * from a response filter.
     */
    @Test
    public void abortWithTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.ABORTWITH);
    }

    /*
     * @testName: setEntityStreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:668;
     * 
     * @test_Strategy: throws IllegalStateException in case the method is invoked
     * from a response filter.
     */
    @Test
    public void setEntityStreamTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.SETENTITYSTREAM);
    }

    /*
     * @testName: setSecurityContextTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:676;
     * 
     * @test_Strategy: throws IllegalStateException in case the method is invoked
     * from a response filter.
     */
    @Test
    public void setSecurityContextTest() throws Fault {
        setProperty(Property.SEARCH_STRING, RequestFilter.ISEXCEPTION);
        invokeRequestAndCheckResponse(ContextOperation.SETSECURITYCONTEXT);
    }

    // ////////////////////////////////////////////////////////////////////////////

    protected void invokeRequestAndCheckResponse(ContextOperation operation)
            throws Fault {
        String op = operation.name();
        String request = buildRequest(Request.GET, op.toLowerCase());
        String header = RequestFilter.OPERATION + ":" + op;
        setProperty(Property.REQUEST, request);
        setProperty(Property.REQUEST_HEADERS, header);
        invoke();
    }
}
