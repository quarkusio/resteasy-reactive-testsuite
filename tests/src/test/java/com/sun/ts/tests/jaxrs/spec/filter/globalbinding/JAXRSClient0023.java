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

package com.sun.ts.tests.jaxrs.spec.filter.globalbinding;

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
public class JAXRSClient0023 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_filter_globalbinding_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.AbstractAddInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.AbstractAddFilter.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.AddOneInterceptor.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.GlobalNameBinding.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.globalbinding.AddTenFilter.class);
                }
            });

    private static final long serialVersionUID = -3785330089447087404L;

    public JAXRSClient0023() {
        setContextRoot("/jaxrs_spec_filter_globalbinding_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0023().run(args);
    }

    /* Run test */

    /*
     * @testName: nameBoundResourceTest
     * 
     * @assertion_ids: JAXRS:SPEC:89;
     * 
     * @test_Strategy: If providers are decorated with at least one name binding
     * annotation, the application subclass must be annotated as shown above in
     * order for those filters or interceptors to be globally bound
     */
    @Test
    public void nameBoundResourceTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "bind"));
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "12");
        invoke();
        logMsg("Bound as expected");
    }

    /*
     * @testName: globalBoundResourceTest
     * 
     * @assertion_ids: JAXRS:SPEC:89;
     * 
     * @test_Strategy: If providers are decorated with at least one name binding
     * annotation, the application subclass must be annotated as shown above in
     * order for those filters or interceptors to be globally bound
     */
    @Test
    public void globalBoundResourceTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, "nobind"));
        setProperty(Property.CONTENT, "0");
        setProperty(Property.SEARCH_STRING, "12");
        invoke();
        logMsg("Bound as expected");
    }

}
