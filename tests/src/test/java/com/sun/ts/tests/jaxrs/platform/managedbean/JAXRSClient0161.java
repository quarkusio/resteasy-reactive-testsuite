/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.platform.managedbean;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@Disabled(QuarkusRest.Unsupported_Managed_Bean)
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0161 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_platform_managedbean_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.platform.managedbean.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.platform.managedbean.ManagedBeanRootResource.class,
                                    com.sun.ts.tests.jaxrs.platform.managedbean.ApplicationHolderSingleton.class,
                                    com.sun.ts.tests.jaxrs.platform.managedbean.InterceptorSingleton.class,
                                    com.sun.ts.tests.jaxrs.platform.managedbean.StringBuilderProvider.class);
                }
            });

    private static final long serialVersionUID = -4731342614849053652L;

    public JAXRSClient0161() {
        setContextRoot("/jaxrs_platform_managedbean_web/managedbean");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0161().run(args);
    }

    /* Run test */
    /*
     * @testName: postConstructOnResourceTest
     * 
     * @assertion_ids: JAXRS:SPEC:49;
     * 
     * @test_Strategy: In a product that also supports Managed Beans,
     * implementations MUST support use of Managed Beans as root resource classes
     * 
     * check postconstruct has been called (managed been property)
     */
    @Test
    public void postConstructOnResourceTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "resourcevalue"));
        setProperty(SEARCH_STRING, "1000");
        invoke();
    }

    /*
     * @testName: postConstructOnProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:49;
     * 
     * @test_Strategy: In a product that also supports Managed Beans,
     * implementations MUST support use of Managed Beans as provider subclasses
     * 
     * check postconstruct has been called (managed been property)
     */
    @Test
    public void postConstructOnProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "providervalue"));
        setProperty(SEARCH_STRING, "1000");
        invoke();
    }

    /*
     * @testName: postConstructOnApplicationTest
     * 
     * @assertion_ids: JAXRS:SPEC:49;
     * 
     * @test_Strategy: In a product that also supports Managed Beans,
     * implementations MUST support use of Managed Beans as Application subclasses
     * 
     * check postconstruct has been called (managed been property)
     */
    @Test
    public void postConstructOnApplicationTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "applicationvalue"));
        setProperty(SEARCH_STRING, "100");
        invoke();
    }

    /*
     * @testName: interceptorOnResourceTest
     * 
     * @assertion_ids: JAXRS:SPEC:49;
     * 
     * @test_Strategy: In a product that also supports Managed Beans,
     * implementations MUST support use of Managed Beans as provider subclasses
     * 
     * increase value on stringbuilderprovider by writing then checked it was
     * intercepted (managed bean property)
     */
    @Test
    public void interceptorOnResourceTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "interceptedresourcevalue"));
        setProperty(SEARCH_STRING, "10005");
        invoke();
    }

    /*
     * @testName: rootResourceManagedBeanJndiLookupTest
     * 
     * @assertion_ids: JAXRS:SPEC:49;
     * 
     * @test_Strategy: In a product that also supports Managed Beans,
     * implementations MUST support use of Managed Beans as root resource classes
     * 
     * try JNDI lookup
     */
    @Test
    public void rootResourceManagedBeanJndiLookupTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "lookup"));
        setProperty(SEARCH_STRING, "1000");
        invoke();
    }

    /*
     * @testName: injectPriorPostConstructOnRootResourceTest
     * 
     * @assertion_ids: JAXRS:SPEC:53; JAXRS:SPEC:53.1;
     * 
     * @test_Strategy: The following additional requirements apply when using
     * Managed Beans as resource classes:
     * 
     * Field and property injection of JAX-RS resources MUST be performed prior to
     * the container invoking any
     * 
     * @PostConstruct annotated method
     */
    @Test
    public void injectPriorPostConstructOnRootResourceTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "priorroot"));
        setProperty(Property.SEARCH_STRING, String.valueOf(true));
        invoke();
    }

    /*
     * @testName: injectPriorPostConstructOnApplicationTest
     * 
     * @assertion_ids: JAXRS:SPEC:53; JAXRS:SPEC:53.1;
     * 
     * @test_Strategy: The following additional requirements apply when using
     * Managed Beans as Application subclasses:
     * 
     * Field and property injection of JAX-RS resources MUST be performed prior to
     * the container invoking any
     * 
     * @PostConstruct annotated method
     */
    @Test
    public void injectPriorPostConstructOnApplicationTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "priorapp"));
        setProperty(Property.SEARCH_STRING, String.valueOf(true));
        invoke();
    }

    /*
     * @testName: injectPriorPostConstructOnProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:53; JAXRS:SPEC:53.1;
     * 
     * @test_Strategy: The following additional requirements apply when using
     * Managed Beans as providers:
     * 
     * Field and property injection of JAX-RS resources MUST be performed prior to
     * the container invoking any
     * 
     * @PostConstruct annotated method
     */
    @Test
    public void injectPriorPostConstructOnProviderTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "priorprovider"));
        setProperty(Property.SEARCH_STRING, String.valueOf(true));
        invoke();
    }

    /*
     * @testName: noInjectOrResourceKeywordTest
     * 
     * @assertion_ids: JAXRS:SPEC:53; JAXRS:SPEC:53.3;
     * 
     * @test_Strategy: The following additional requirements apply when using
     * Managed Beans as resource classes:
     * 
     * Implementations MUST NOT require use of @Inject or
     * 
     * @Resource to trigger injection of JAX-RS annotated fields or properties.
     * Implementations MAY support such usage but SHOULD warn users about
     * non-portability.
     */
    @Test
    public void noInjectOrResourceKeywordTest() throws Fault {
        String req = buildRequest(Request.GET, "nokeyword;matrix=",
                String.valueOf(serialVersionUID));
        setProperty(Property.REQUEST, req);
        setProperty(Property.SEARCH_STRING, String.valueOf(serialVersionUID));
        invoke();
    }
}
