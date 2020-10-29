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

package com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.basic;

import java.util.function.Supplier;

import javax.ws.rs.core.Response;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TestServlet;
import com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TestServlet.Scheme;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *                     user;
 *                     password;
 *                     authuser;
 *                     authpassword;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSBasicClient0126
        extends com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.JAXRSClient0127 {

    private static final long serialVersionUID = 340277879725875946L;

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_core_securitycontext_basic_web")
            .overrideConfigKey("quarkus.http.auth.basic", "true")
            .overrideConfigKey("quarkus.security.users.embedded.plain-text", "true")
            .overrideConfigKey("quarkus.security.users.embedded.enabled", "true")
            .overrideConfigKey("quarkus.security.users.embedded.users.j2ee", "jb0ss1")
            .overrideConfigKey("quarkus.security.users.embedded.roles.j2ee", "DIRECTOR")
            .overrideConfigKey("quarkus.security.users.embedded.users.javajoe", "jb0ss2")
            .overrideConfigKey("quarkus.security.users.embedded.roles.javajoe", "OTHERROLE")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.securitycontext.TestServlet.class);
                }
            });

    public JAXRSBasicClient0126() {
        setContextRoot("/jaxrs_ee_core_securitycontext_basic_web/Servlet");
        user = "j2ee";
        password = "jb0ss1";
        authuser = "javajoe";
        authpassword = "jb0ss2";
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSBasicClient0126 theTests = new JAXRSBasicClient0126();
        theTests.run(args);
    }

    /* Run test */

    /*
     * @testName: noAuthorizationTest
     * 
     * @assertion_ids:
     * 
     * @test_Strategy: Send no authorization, make sure of 401 response
     */
    @org.junit.jupiter.api.Test
    public void noAuthorizationTest() throws Fault {
        super.noAuthorizationTest();
    }

    /*
     * @testName: basicAuthorizationAdminTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
     * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
     * 
     * @test_Strategy: Send basic authorization, check security context
     */
    @org.junit.jupiter.api.Test
    public void basicAuthorizationAdminTest() throws Fault {
        setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
        setProperty(Property.BASIC_AUTH_USER, user);
        setProperty(Property.BASIC_AUTH_PASSWD, password);

        setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
        setProperty(Property.SEARCH_STRING, TestServlet.Role.DIRECTOR.name());
        setProperty(Property.SEARCH_STRING, user);
        setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
        invokeRequest();
    }

    /*
     * @testName: basicAuthorizationIncorrectUserTest
     * 
     * @assertion_ids:
     * 
     * @test_Strategy: Send basic authorization, check security context
     */
    @org.junit.jupiter.api.Test
    public void basicAuthorizationIncorrectUserTest() throws Fault {
        setProperty(Property.STATUS_CODE,
                getStatusCode(Response.Status.UNAUTHORIZED));
        setProperty(Property.BASIC_AUTH_USER, Scheme.NOSCHEME.name());
        setProperty(Property.BASIC_AUTH_PASSWD, password);
        invokeRequest();
    }

    /*
     * @testName: basicAuthorizationIncorrectPasswordTest
     * 
     * @assertion_ids:
     * 
     * @test_Strategy: Send basic authorization, check security context
     */
    @org.junit.jupiter.api.Test
    public void basicAuthorizationIncorrectPasswordTest() throws Fault {
        setProperty(Property.STATUS_CODE,
                getStatusCode(Response.Status.UNAUTHORIZED));
        setProperty(Property.BASIC_AUTH_USER, authuser);
        setProperty(Property.BASIC_AUTH_PASSWD, password);
        invokeRequest();
    }

    /*
     * @testName: basicAuthorizationStandardUserTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:169; JAXRS:JAVADOC:170; JAXRS:JAVADOC:171;
     * JAXRS:JAVADOC:172; JAXRS:SPEC:40;
     * 
     * @test_Strategy: Send basic authorization with made up Realm, check security
     * context
     */
    @org.junit.jupiter.api.Test
    public void basicAuthorizationStandardUserTest() throws Fault {
        setProperty(Property.STATUS_CODE, getStatusCode(Response.Status.OK));
        setProperty(Property.BASIC_AUTH_USER, authuser);
        setProperty(Property.BASIC_AUTH_PASSWD, authpassword);

        setProperty(Property.SEARCH_STRING, TestServlet.Security.UNSECURED.name());
        setProperty(Property.SEARCH_STRING, TestServlet.Role.OTHERROLE.name());
        setProperty(Property.SEARCH_STRING, authuser);
        setProperty(Property.SEARCH_STRING, TestServlet.Scheme.BASIC.name());
        invokeRequest();
    }
}
