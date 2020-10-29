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

package com.sun.ts.tests.jaxrs.ee.rs.formparam.sub;

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
public class JAXRSSubClient0144 extends JAXRSClient0146 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_formparam_sub_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.sub.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.sub.SubResource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.FormParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    public JAXRSSubClient0144() {
        setContextRoot("/jaxrs_ee_formparam_sub_web/resource/sub");
    }

    private static final long serialVersionUID = 1L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSSubClient0144 theTests = new JAXRSSubClient0144();
        theTests.run(args);
    }

    /*
     * @testName: nonDefaultFormParamNothingSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test sending no content;
     */
    @Test
    public void nonDefaultFormParamNothingSentTest() throws Fault {
        super.nonDefaultFormParamNothingSentTest();
    }

    /*
     * @testName: defaultFormParamSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test sending override of default argument content;
     */
    @Test
    public void defaultFormParamSentTest() throws Fault {
        super.defaultFormParamSentTest();
    }

    /*
     * @testName: defaultFormParamNoArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test sending no argument content, receiving default;
     */
    @Test
    public void defaultFormParamNoArgSentTest() throws Fault {
        super.defaultFormParamNoArgSentTest();
    }

    /*
     * @testName: defaultFormParamPutNoArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test sending no argument content, PUT, receiving default;
     */
    @Test
    public void defaultFormParamPutNoArgSentTest() throws Fault {
        super.defaultFormParamPutNoArgSentTest();
    }

    /*
     * @testName: defaultFormParamPutArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test sending argument content, PUT;
     */
    @Test
    public void defaultFormParamPutArgSentTest() throws Fault {
        super.defaultFormParamPutArgSentTest();
    }

    /*
     * @testName: defaultFormParamValueOfTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithValueOf from default;
     */
    @Test
    public void defaultFormParamValueOfTest() throws Fault {
        super.defaultFormParamValueOfTest();
    }

    /*
     * @testName: nonDefaultFormParamValueOfTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithValueOf from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamValueOfTest() throws Fault {
        super.nonDefaultFormParamValueOfTest();
    }

    /*
     * @testName: defaultFormParamFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromStringTest() throws Fault {
        super.defaultFormParamFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromStringTest() throws Fault {
        super.nonDefaultFormParamFromStringTest();
    }

    /*
     * @testName: defaultFormParamFromConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromConstructorTest() throws Fault {
        super.defaultFormParamFromConstructorTest();
    }

    /*
     * @testName: nonDefaultFormParamFromConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromConstructorTest() throws Fault {
        super.nonDefaultFormParamFromConstructorTest();
    }

    /*
     * @testName: defaultFormParamFromListConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from default;
     */
    @Test
    public void defaultFormParamFromListConstructorTest() throws Fault {
        super.defaultFormParamFromListConstructorTest();
    }

    /*
     * @testName: nonDefaultFormParamFromListConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromListConstructorTest() throws Fault {
        super.nonDefaultFormParamFromListConstructorTest();
    }

    /*
     * @testName: defaultFormParamFromSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromSetFromStringTest() throws Fault {
        super.defaultFormParamFromSetFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSetFromStringTest() throws Fault {
        super.nonDefaultFormParamFromSetFromStringTest();
    }

    /*
     * @testName: defaultFormParamFromSortedSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromSortedSetFromStringTest() throws Fault {
        super.defaultFormParamFromSortedSetFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromSortedSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSortedSetFromStringTest() throws Fault {
        super.nonDefaultFormParamFromSortedSetFromStringTest();
    }

    /*
     * @testName: defaultFormParamFromListFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     * 
     */
    @Test
    public void defaultFormParamFromListFromStringTest() throws Fault {
        super.defaultFormParamFromListFromStringTest();
    }

    /*
     * @testName: nonDefaultFormParamFromListFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromListFromStringTest() throws Fault {
        super.nonDefaultFormParamFromListFromStringTest();
    }

    /*
     * @assertion_ids: JAXRS:SPEC:7; JAXRS:SPEC:12;JAXRS:SPEC:12.2; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Verify that named FormParam @Encoded is handled
     */
    @Disabled(QuarkusRest.Test_Doesnt_Make_Sense)
    @Test
    public void formParamEntityWithEncodedTest() throws Fault {
        super.paramEntityWithEncodedTest();
    }

    /*
     * @testName: formParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3; JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see Section 3.2.
     */
    @Test
    public void formParamThrowingWebApplicationExceptionTest() throws Fault {
        super.formParamThrowingIllegalArgumentExceptionTest();
    }

    /*
     * @testName: formParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3; JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2. Exceptions thrown during
     * construction of @FormParam annotated parameter values are treated the same
     * as if the parameter were annotated with @HeaderParam.
     */
    @Test
    public void formParamThrowingIllegalArgumentExceptionTest() throws Fault {
        super.formParamThrowingIllegalArgumentExceptionTest();
    }
}
