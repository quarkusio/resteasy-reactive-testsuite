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

package com.sun.ts.tests.jaxrs.servlet3.rs.ext.paramconverter.autodiscovery;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.provider.StringBeanParamConverter;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0155 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_servlet3_rs_ext_paramconverter_autodiscovery_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.servlet3.rs.ext.paramconverter.autodiscovery.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBeanParamConverterProvider.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBeanParamConverter.class,
                                    com.sun.ts.tests.jaxrs.servlet3.rs.ext.paramconverter.autodiscovery.Resource.class);
                }
            });

    /**
     * 
     */
    private static final long serialVersionUID = 8764917394183731977L;

    public JAXRSClient0155() {
        setContextRoot(
                "/jaxrs_servlet3_rs_ext_paramconverter_autodiscovery_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0155 theTests = new JAXRSClient0155();
        theTests.run(args);
    }

    /* Run test */

    /*
     * @testName: isParamCoverterFoundByAutodiscoveryUsedTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:919; JAXRS:SPEC:59;
     * 
     * @test_Strategy: Providers implementing ParamConverterProvider contract must
     * be annotated with @Provider annotation to be automatically discovered by
     * the JAX-RS runtime during a provider scanning phase.
     * 
     * 2.3.2 When an Application subclass is present in the archive, if both
     * Application.getClasses and Application.getSingletons return an empty list
     * then all root resource classes and providers packaged in the web
     * application MUST be included and the JAX-RS implementation is REQUIRED to
     * discover them automatically.
     */
    @Test
    public void isParamCoverterFoundByAutodiscoveryUsedTest() throws Fault {
        String query = "ABCDEFGH";
        setPropertyRequest(Request.GET, "sbquery?query=", query);
        setProperty(Property.SEARCH_STRING, StringBeanParamConverter.VALUE);
        setProperty(Property.SEARCH_STRING, query);
        invoke();
    }

    /*
     * @assertion_ids: JAXRS:JAVADOC:919; JAXRS:SPEC:59;
     * 
     * @test_Strategy: Providers implementing ParamConverterProvider contract must
     * be annotated with @Provider annotation to be automatically discovered by
     * the JAX-RS runtime during a provider scanning phase.
     * 
     * 2.3.2 When an Application subclass is present in the archive, if both
     * Application.getClasses and Application.getSingletons return an empty list
     * then all root resource classes and providers packaged in the web
     * application MUST be included and the JAX-RS implementation is REQUIRED to
     * discover them automatically.
     * 
     * check whether it pass in a case of writer only TODO: IN MR public void
     * isParamCoverterUsedForWritingTest() throws Fault { String query = "OK";
     * setPropertyRequest(Request.GET, ""); setProperty(Property.EXPECTED_HEADERS,
     * Resource.HEADER_NAME + ":" + query); invoke(); }
     */

    // ////////////////////////////////////////////////////////////////////
    private void setPropertyRequest(Request request, String... resource) {
        StringBuilder sb = new StringBuilder();
        for (String r : resource)
            sb.append(r);
        setProperty(Property.REQUEST, buildRequest(request, sb.toString()));
    }

}
