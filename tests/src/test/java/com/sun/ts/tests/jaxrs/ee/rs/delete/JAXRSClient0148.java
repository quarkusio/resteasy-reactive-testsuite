/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */
package com.sun.ts.tests.jaxrs.ee.rs.delete;

import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0148 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_delete_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.delete.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.delete.HttpMethodDeleteTest.class);
                }
            });

    private static final long serialVersionUID = 204493956987397506L;

    public JAXRSClient0148() {
        setContextRoot("/jaxrs_ee_rs_delete_web");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0148().run(args);
    }

    /*
     * @class.setup_props: webServerHost; webServerPort; ts_home;
     */
    /* Run test */
    /*
     * @testName: deleteTest1
     * 
     * @assertion_ids: JAXRS:JAVADOC:6; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes Delete on root resource at /DeleteTest;
     * Verify that right Method is invoked.
     */
    @Test
    public void deleteTest1() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/plain");
        setProperty(REQUEST, "DELETE " + getContextRoot() + "/DeleteTest HTTP/1.1");
        setProperty(SEARCH_STRING, "CTS-Delete text/plain");
        invoke();
    }

    /*
     * @testName: deleteTest2
     * 
     * @assertion_ids: JAXRS:JAVADOC:6; JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes Delete on root resource at /DeleteTest;
     * Verify that right Method is invoked.
     */
    @Test
    public void deleteTest2() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/html");
        setProperty(REQUEST,
                "DELETE " + getContextRoot() + "/DeleteTest  HTTP/1.1");
        setProperty(SEARCH_STRING, "CTS-Delete text/html");
        invoke();
    }

    /*
     * @testName: deleteSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:10;
     * JAXRS:JAVADOC:8;
     * 
     * @test_Strategy: Client invokes Delete on a sub resource at /DeleteTest/sub;
     * Verify that right Method is invoked.
     */
    @Test
    public void deleteSubTest() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/html");
        setProperty(REQUEST,
                "DELETE " + getContextRoot() + "/DeleteTest/sub HTTP/1.1");
        setProperty(SEARCH_STRING, "CTS-Delete text/html");
        invoke();
    }

    /*
     * @testName: deleteSubTest1
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:SPEC:21; JAXRS:SPEC:25.6;
     * JAXRS:JAVADOC:6; JAXRS:JAVADOC:10; JAXRS:JAVADOC:8;
     * 
     * @test_Strategy: Client invokes Delete on a sub resource at /DeleteTest/sub;
     * Verify that no Method is invoked.
     */
    @Test
    public void deleteSubTest1() throws Fault {
        setProperty(REQUEST_HEADERS, "Accept:text/plain");
        setProperty(REQUEST,
                "DELETE " + getContextRoot() + "/DeleteTest/sub HTTP/1.1");
        setProperty(STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
        invoke();
    }
}
