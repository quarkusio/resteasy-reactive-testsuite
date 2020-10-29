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

package com.sun.ts.tests.jaxrs.spec.resource.locator;

import java.util.function.Supplier;

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
public class JAXRSClient0015 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_resource_locator_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.resource.locator.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.locator.EntityWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.locator.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.locator.LocatorEntity.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.locator.SubResource.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0015() {
        setContextRoot("/jaxrs_spec_resource_locator_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0015().run(args);
    }

    /* Run test */
    /*
     * @testName: checkEntityIsNotSetTest
     * 
     * @assertion_ids: JAXRS:SPEC:4;
     * 
     * @test_Strategy: An implementation is only required to set the annotated
     * field and bean property values of instances created by the implementation
     * runtime. Objects returned by sub-resource locators (see Section 3.4.1) are
     * expected to be initialized by their creator and field and bean properties
     * are not modified by the implementation runtime.
     */
    @Test
    public void checkEntityIsNotSetTest() throws Fault {
        String request = buildRequest(Request.POST,
                "sub;resmatrix=resarg;submatrix=subarg;entity=entityarg;");
        setProperty(Property.REQUEST, request);
        setProperty(Property.SEARCH_STRING, "resMatrix=resarg");
        setProperty(Property.SEARCH_STRING, "subMatrix=null");
        setProperty(Property.SEARCH_STRING, "entity=null");
        invoke();
    }
}
