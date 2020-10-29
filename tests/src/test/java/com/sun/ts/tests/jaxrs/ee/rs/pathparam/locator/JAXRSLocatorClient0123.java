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

package com.sun.ts.tests.jaxrs.ee.rs.pathparam.locator;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSLocatorClient0123
        extends com.sun.ts.tests.jaxrs.ee.rs.pathparam.JAXRSClient0124 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_pathparam_locator_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.pathparam.locator.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.pathparam.locator.MiddleResource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.pathparam.PathParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.pathparam.locator.PathSegmentImpl.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.pathparam.locator.LocatorResource.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSLocatorClient0123() {
        setContextRoot("/jaxrs_ee_rs_pathparam_locator_web/resource/locator");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSLocatorClient0123().run(args);
    }

    /* Run test */
    /*
     * @testName: test1
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked while using PathParam with primitive
     * type String.
     */
    @org.junit.jupiter.api.Test
    public void test1() throws Fault {
        super.test1();
    }

    /*
     * @testName: test2
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:JAVADOC:114;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked while using PathParam primitive type
     * String and PathSegment.
     */
    @org.junit.jupiter.api.Test
    public void test2() throws Fault {
        super.test2();
    }

    /*
     * @testName: test3
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:JAVADOC:114;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked while using PathParam primitive type
     * int, float and PathSegment.
     */
    @org.junit.jupiter.api.Test
    public void test3() throws Fault {
        super.test3();
    }

    /*
     * @testName: test4
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:JAVADOC:114;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked using PathParam primitive type double,
     * boolean, byte, and PathSegment.
     */
    @org.junit.jupiter.api.Test
    public void test4() throws Fault {
        super.test4();
    }

    /*
     * @testName: test5
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:JAVADOC:114;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked using PathParam primitive type long,
     * String, short, boolean and PathSegment.
     */
    @org.junit.jupiter.api.Test
    public void test5() throws Fault {
        super.test5();
    }

    /*
     * @testName: test6
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:JAVADOC:9; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Client invokes GET on root resource at /PathParamTest;
     * Verify that right Method is invoked using PathParam primitive type
     * List<String>.
     */
    @Disabled(QuarkusRest.Nuts)
    @org.junit.jupiter.api.Test
    public void test6() throws Fault {
        super.test6();
    }

    /*
     * @testName: pathParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:5.2; JAXRS:SPEC:12;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named Param is handled properly
     */
    @Test
    public void pathParamEntityWithConstructorTest() throws Fault {
        super.paramEntityWithConstructorTest();
    }

    /*
     * @testName: pathParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:5.3; JAXRS:SPEC:12;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named Param is handled properly
     */
    @Test
    public void pathParamEntityWithValueOfTest() throws Fault {
        super.pathParamEntityWithValueOfTest();
    }

    /*
     * @testName: pathParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:5.3; JAXRS:SPEC:12;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named Param is handled properly
     */
    @Test
    public void pathParamEntityWithFromStringTest() throws Fault {
        _contextRoot += "encoded";
        super.pathParamEntityWithFromStringTest();
    }

    /*
     * @testName: pathParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:5.4; JAXRS:SPEC:12;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named PathParam is handled properly
     */
    @Test
    public void pathParamSetEntityWithFromStringTest() throws Fault {
        super.pathParamSetEntityWithFromStringTest();
    }

    /*
     * @testName: pathParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.3; JAXRS:SPEC:5.4; JAXRS:SPEC:12;
     * JAXRS:SPEC:20; JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Verify that named PathParam is handled properly
     */
    @Test
    public void pathParamListEntityWithFromStringTest() throws Fault {
        super.pathParamListEntityWithFromStringTest();
    }

    /*
     * @testName: pathParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.1; JAXRS:SPEC:8; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: A WebApplicationException thrown during construction of
     * field or property values using 2 or 3 above is processed directly as
     * described in section 3.3.4.
     */
    @Test
    public void pathParamThrowingWebApplicationExceptionTest() throws Fault {
        super.pathParamThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: pathParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.1; JAXRS:SPEC:10; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.3;
     * 
     * @test_Strategy: Other exceptions thrown during construction of field or
     * property values using 2 or 3 above are treated as client errors:
     * 
     * if the field or property is annotated with @MatrixParam,
     * 
     * @QueryParam or @PathParam then an implementation MUST generate a
     * WebApplicationException that wraps the thrown exception with a not found
     * response (404 status) and no entity;
     */
    @Test
    public void pathParamThrowingIllegalArgumentExceptionTest() throws Fault {
        super.pathParamThrowingIllegalArgumentExceptionTest();
    }

    @Override
    protected String buildRequest(Request type, String... path) {
        return super.buildRequest(Request.POST, path);
    }

    @Override
    protected String buildRequest(String param) {
        return super.buildRequest(param).replace(Request.GET.name(),
                Request.POST.name());
    }

}
