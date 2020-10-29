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

package com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.security;

import java.util.Properties;
import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.ContextOperation;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 *                     user;
 *                     password;                     
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0136 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_container_requestcontext_security_web")
            .overrideConfigKey("quarkus.http.auth.basic", "true")
            .overrideConfigKey("quarkus.security.users.embedded.plain-text", "true")
            .overrideConfigKey("quarkus.security.users.embedded.enabled", "true")
            .overrideConfigKey("quarkus.security.users.embedded.users.j2ee", "jb0ss")
            .overrideConfigKey("quarkus.security.users.embedded.roles.j2ee", "DIRECTOR")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.security.TSAppConfig.class,
                                    ContextOperation.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.security.RequestFilter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.container.requestcontext.security.Resource.class);
                }
            });

    private static final long serialVersionUID = -3020219607348263568L;

    protected String user = "j2ee";

    protected String password = "jb0ss";

    public JAXRSClient0136() {
        setContextRoot(
                "/jaxrs_ee_rs_container_requestcontext_security_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0136().run(args);
    }

    public void setup(String[] args, Properties p) throws Fault {
        user = p.getProperty("user");
        password = p.getProperty("password");
        assertFault(!isNullOrEmpty(user), "user was not in build.proerties");
        assertFault(!isNullOrEmpty(password),
                "password was not in build.proerties");
        super.setup(args, p);
    }

    /*
     * @testName: getSecurityContextTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:664;
     * 
     * @test_Strategy: Get the injectable security context information for the
     * current request, the user is authenticated.
     */
    @Test
    public void getSecurityContextTest() throws Fault {
        setProperty(Property.BASIC_AUTH_USER, user);
        setProperty(Property.BASIC_AUTH_PASSWD, password);
        setProperty(Property.SEARCH_STRING, user);
        String request = buildRequest(Request.POST, "");
        setProperty(Property.REQUEST, request);
        invoke();
    }

    /*
     * @testName: noSecurityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:664;
     * 
     * @test_Strategy: Make sure the authorization is needed
     */
    @Test
    @Disabled(QuarkusRest.Test_Doesnt_Make_Sense) //it assumes that security is applied before the filter
    public void noSecurityTest() throws Fault {
        String request = buildRequest(Request.POST, "");
        setProperty(Property.REQUEST, request);
        setProperty(Property.STATUS_CODE, getStatusCode(Status.UNAUTHORIZED));
        invoke();
    }

    // ////////////////////////////////////////////////////////////////////////////

}
