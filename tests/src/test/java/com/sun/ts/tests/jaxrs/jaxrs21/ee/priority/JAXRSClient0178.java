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

package com.sun.ts.tests.jaxrs.jaxrs21.ee.priority;

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
public class JAXRSClient0178 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_ee_priority_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.TckPriorityException.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ExceptionMapperThree.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ExceptionResource.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ParamConverterProviderOne.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ParamConverterProviderAnother.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ParamConverterResource.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ExceptionMapperOne.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ExceptionMapperTwo.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.priority.ParamConverterProviderTwo.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0178() {
        setContextRoot("/jaxrs_jaxrs21_ee_priority_web");
    }

    public static void main(String[] args) {
        new JAXRSClient0178().run(args);
    }

    /* Run test */

    /*
     * @testName: exceptionMapperPriorityTest
     * 
     * @assertion_ids: JAXRS:SPEC:127;
     * 
     * @test_Strategy:
     */
    @Test
    public void exceptionMapperPriorityTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "exception"));
        setProperty(Property.SEARCH_STRING, ExceptionMapperOne.class.getName());
        invoke();
    }

    /*
     * @testName: paramConverterPriorityTest
     * 
     * @assertion_ids: JAXRS:SPEC:127;
     * 
     * @test_Strategy:
     */
    @Test
    public void paramConverterPriorityTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "converter?id=something"));
        setProperty(Property.SEARCH_STRING,
                ParamConverterProviderOne.class.getName());
        invoke();
    }

}
