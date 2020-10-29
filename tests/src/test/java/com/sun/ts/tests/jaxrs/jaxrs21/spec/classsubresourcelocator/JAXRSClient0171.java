/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.jaxrs21.spec.classsubresourcelocator;

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
 * @since 2.1
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0171 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_spec_classsubresourcelocator_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.spec.classsubresourcelocator.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.spec.classsubresourcelocator.SubResource.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.spec.classsubresourcelocator.Resource.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0171() {
        setContextRoot("/jaxrs_jaxrs21_spec_classsubresourcelocator_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0171 c = new JAXRSClient0171();
        c.run(args);
    }

    /* Run test */
    /*
     * @testName: subResourceLocatorAsClassTest
     * 
     * @assertion_ids: JAXRS:SPEC:125;
     * 
     * @test_Strategy:
     */
    @Test
    public void subResourceLocatorAsClassTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "sub"));
        setProperty(Property.SEARCH_STRING, "OK");
        invoke();
    }

}
