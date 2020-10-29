/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.put;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import io.quarkus.test.QuarkusUnitTest;

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0109 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_put_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.put.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.put.HttpMethodPutTest.class);
                }
            });

    private static final long serialVersionUID = -71817508809693264L;

    public JAXRSClient0109() {
        setContextRoot("/jaxrs_ee_rs_put_web");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0109().run(args);
    }

    /*
     * @class.setup_props: webServerHost; webServerPort; ts_home;
     */
    /* Run test */
    /*
     * @testName: putTest1
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes PUT on root resource at /PutTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void putTest1() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Accept:text/plain");
        setProperty(Property.CONTENT, "dummy");
        setProperty(Property.REQUEST, buildRequest(Request.PUT, "PutTest"));
        setProperty(Property.SEARCH_STRING, "CTS-put text/plain");
        invoke();
    }

    /*
     * @testName: putTest2
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes PUT on root resource at /PutTest; Verify
     * that right Method is invoked.
     */
    @Test
    public void putTest2() throws Fault {
        setProperty(Property.CONTENT, "dummy");
        setProperty(Property.REQUEST_HEADERS, "Accept:text/html");
        setProperty(Property.REQUEST, buildRequest(Request.PUT, "PutTest"));
        setProperty(Property.SEARCH_STRING, "CTS-put text/html");
        invoke();
    }

    /*
     * @testName: putSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:20.1; JAXRS:JAVADOC:6; JAXRS:JAVADOC:8;
     * JAXRS:JAVADOC:10;
     * 
     * @test_Strategy: Client invokes PUT on a sub resource at /PutTest/sub;
     * Verify that right Method is invoked.
     */
    @Test
    public void putSubTest() throws Fault {
        setProperty(Property.CONTENT, "dummy");
        setProperty(Property.REQUEST, buildRequest(Request.PUT, "PutTest/sub"));
        setProperty(Property.SEARCH_STRING, "CTS-put text/html");
        invoke();
    }
}
