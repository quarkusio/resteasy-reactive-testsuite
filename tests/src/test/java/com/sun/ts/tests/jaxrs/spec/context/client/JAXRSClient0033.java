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

package com.sun.ts.tests.jaxrs.spec.context.client;

import static com.sun.ts.tests.jaxrs.spec.context.server.StringBeanEntityProviderWithInjectables.*;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.provider.StringBean;
import com.sun.ts.tests.jaxrs.spec.context.server.StringBeanEntityProviderWithInjectables;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0033 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_context_client_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.context.client.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.spec.context.client.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    StringBeanEntityProviderWithInjectables.class);
                }
            });

    private static final long serialVersionUID = -2921113736906329195L;

    public JAXRSClient0033() {
        setContextRoot("/jaxrs_spec_context_client_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0033().run(args);
    }

    /* Run test */
    /*
     * @testName: clientWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
     * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
     * JAXRS:SPEC:100; JAXRS:JAVADOC:754;
     * 
     * @test_Strategy: @Context available to providers
     * 
     * An instance can be injected into a class field or method parameter using
     * the @Context annotation.
     * 
     * The lifecycle of components registered using this class-based register(...)
     * method is fully managed by the JAX-RS implementation or any underlying IoC
     * container supported by the implementation.
     */
    @Test
    @Disabled(QuarkusRest.Unsupported_Client_Server_Injection_Separation)
    public void clientWriterTest() throws Fault {
        addProvider(StringBeanEntityProviderWithInjectables.class);
        setRequestContentEntity(new StringBean("stringbean"));
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        invoke();
        assertInjection("@Context injection did not work properly:");
    }

    /*
     * @testName: clientReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:93; JAXRS:SPEC:93.3; JAXRS:SPEC:94;
     * JAXRS:SPEC:95; JAXRS:SPEC:96; JAXRS:SPEC:97; JAXRS:SPEC:98; JAXRS:SPEC:99;
     * JAXRS:SPEC:100; JAXRS:JAVADOC:754;
     * 
     * @test_Strategy: @Context available to providers
     * 
     * An instance can be injected into a class field or method parameter using
     * the @Context annotation.
     * 
     * The lifecycle of components registered using this class-based register(...)
     * method is fully managed by the JAX-RS implementation or any underlying IoC
     * container supported by the implementation.
     */
    @Test
    @Disabled(QuarkusRest.Unsupported_Client_Server_Injection_Separation)
    public void clientReaderTest() throws Fault {
        addProvider(StringBeanEntityProviderWithInjectables.class);
        setRequestContentEntity("stringbean");
        setProperty(Property.REQUEST, buildRequest(Request.POST, "echo"));
        invoke();
        StringBean content = getResponseBody(StringBean.class);
        assertNotNull(content, "response body is null");
        logMsg("Injectables are", content.get());
        assertInjection(content.get(), "@Context injection did not work properly:");
    }

    // ////////////////////////////////////////////////////////////////////
    private void assertInjection(String body, Object failMessage) throws Fault {
        assertEquals('1', body.charAt(6), failMessage, notInjected(body, 6),
                "has not been injected"); // Providers
        assertEquals('1', body.charAt(8), failMessage, notInjected(body, 8),
                "has not been injected"); // Configuration
        logMsg("@Context injected as expected");
    }

    private void assertInjection(Object failMessage) throws Fault {
        String body = getResponseBody();
        assertInjection(body, failMessage);
    }
}
