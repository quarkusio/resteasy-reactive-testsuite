/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.options;

import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0105 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_options_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.options.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.options.Resource.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0105() {
        setContextRoot("/jaxrs_ee_rs_options_web/Options");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0105().run(args);
    }

    /* Run test */
    /*
     * @testName: optionsTest
     * 
     * @assertion_ids: JAXRS:SPEC:18; JAXRS:SPEC:18.1;
     * 
     * @test_Strategy: Call a method annotated with a request method designator
     * for OPTIONS
     */
    @Test
    public void optionsTest() throws Fault {
        setProperty(REQUEST, buildRequest("OPTIONS", "options"));
        setProperty(STATUS_CODE, getStatusCode(Status.ACCEPTED));
        invoke();
    }

    /*
     * @testName: autoResponseTest
     * 
     * @assertion_ids: JAXRS:SPEC:18; JAXRS:SPEC:18.2;
     * 
     * @test_Strategy: Generate an automatic response using the metadata provided
     * by the JAX-RS annotations on the matching class and its methods.
     */
    @Test
    public void autoResponseTest() throws Fault {
        setProperty(REQUEST, buildRequest("OPTIONS", "get"));
        setProperty(STATUS_CODE, "!" + getStatusCode(Status.NOT_FOUND));
        invoke();
    }
}
