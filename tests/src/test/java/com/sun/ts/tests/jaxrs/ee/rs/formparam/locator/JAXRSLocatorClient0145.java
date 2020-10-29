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

package com.sun.ts.tests.jaxrs.ee.rs.formparam.locator;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.ee.rs.formparam.JAXRSClient0146;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSLocatorClient0145 extends JAXRSClient0146 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_formparam_locator_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.locator.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.locator.LocatorResource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.locator.MiddleResource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.common.AbstractMessageBodyRW.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.FormParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    public JAXRSLocatorClient0145() {
        setContextRoot("/jaxrs_ee_formparam_locator_web/resource/locator");
    }

    private static final long serialVersionUID = 1L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSLocatorClient0145 theTests = new JAXRSLocatorClient0145();
        theTests.run(args);
    }

    /*
     * @testName: nonDefaultFormParamNothingSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test sending no content;
     */
    @Test
    public void nonDefaultFormParamNothingSentTest() throws Fault {
        super.nonDefaultFormParamNothingSentTest();
    }

    /*
     * @testName: nonDefaultFormParamValueOfTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithValueOf from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamValueOfTest() throws Fault {
        super.nonDefaultFormParamValueOfTest();
    }

    /*
     * @testName: nonDefaultFormParamFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromStringTest() throws Fault {
        _contextRoot += "encoded";
        super.nonDefaultFormParamFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromConstructorTest() throws Fault {
        super.nonDefaultFormParamFromConstructorTest();
    }

    /*
     * @testName: nonDefaultFormParamFromListConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromListConstructorTest() throws Fault {
        super.nonDefaultFormParamFromListConstructorTest();
    }

    /*
     * @testName: nonDefaultFormParamFromSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSetFromStringTest() throws Fault {
        _contextRoot += "encoded";
        super.nonDefaultFormParamFromSetFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromSortedSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSortedSetFromStringTest() throws Fault {
        _contextRoot += "encoded";
        super.nonDefaultFormParamFromSortedSetFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromListFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromListFromStringTest() throws Fault {
        _contextRoot += "encoded";
        super.nonDefaultFormParamFromListFromStringTest();
    }

    /*
     * @assertion_ids: JAXRS:SPEC:7; JAXRS:SPEC:12;JAXRS:SPEC:12.2; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named FormParam @Encoded is handled
     */
    @Disabled(QuarkusRest.Test_Doesnt_Make_Sense)
    @Test
    public void formParamEntityWithEncodedTest() throws Fault {
        _contextRoot += "encoded";
        super.paramEntityWithEncodedTest();
    }

}
