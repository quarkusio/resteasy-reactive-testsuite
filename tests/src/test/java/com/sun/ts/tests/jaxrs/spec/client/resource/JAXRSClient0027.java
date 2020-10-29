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

package com.sun.ts.tests.jaxrs.spec.client.resource;

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
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0027 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_client_resource_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.client.resource.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.client.resource.Resource.class);
                }
            });

    private static final long serialVersionUID = 1339633069677106930L;

    public JAXRSClient0027() {
        setContextRoot("/jaxrs_spec_client_resource_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0027().run(args);
    }

    /* Run test */
    /*
     * @testName: checkClientConceptTest
     * 
     * @assertion_ids: JAXRS:SPEC:65;
     * 
     * @test_Strategy: Conceptually, the steps required to submit a request are
     * the following: (i) obtain an instance of Client (ii) create a WebTarget
     * (iii) create a request from the WebTarget and (iv) submit a request or get
     * a prepared Invocation for later submission
     */
    @Test
    public void checkClientConceptTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "concept"));
        setProperty(Property.SEARCH_STRING, "concept");
        invoke();
    }
}
