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
package com.sun.ts.tests.jaxrs.ee.rs.headerparam;

import java.util.function.Supplier;

import jakarta.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.ee.rs.JaxrsParamClient0151;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0108 extends JaxrsParamClient0151 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_headerparam_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.headerparam.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.JaxrsParamClient0151.CollectionName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.headerparam.HeaderParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0108() {
        setContextRoot("/jaxrs_ee_rs_headerparam_web/HeaderParamTest");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0108().run(args);
    }

    /* Run test */
    /*
     * @testName: headerParamStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:3;
     * JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes HEAD on root resource at /HeaderParamTest;
     * Verify that right Method is invoked.
     */
    @Test
    public void headerParamStringTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: cts");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "stringtest1=cts");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: cts1");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: cts2");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "stringtest1=cts1");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: cts1");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST2: cts2");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "stringtest1=cts|stringtest2=cts2");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: cts1");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST2: cts2");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-STRINGTEST1: newone");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "stringtest1=cts1|stringtest2=cts2");

        invoke();
    }

    /*
     * @testName: headerParamNoQueryTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamNoQueryTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "No HeaderParam");
        invoke();
    }

    /*
     * @testName: headerParamIntTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamIntTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: 2147483647");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "inttest1=2147483647");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: -2147483648");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: 2147483647");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "inttest1=-2147483648");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: -2147483648");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST2: 2147483647");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "inttest1=-2147483648|inttest2=2147483647");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: -2147483648");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST2: 2147483647");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-INTTEST1: 1234");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "inttest1=-2147483648|inttest2=2147483647");
        invoke();
    }

    /*
     * @testName: headerParamDoubleTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamDoubleTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 123");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "doubletest1=123.0");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 123.1");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 345");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "doubletest1=123.1");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 12.345");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST2: 34.567");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "doubletest1=12.345|doubletest2=34.567");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 23.456");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST2: 0.56789");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-DOUBLETEST1: 1.234");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "doubletest1=23.456|doubletest2=0.56789");
        invoke();
    }

    /*
     * @testName: headerParamFloatTest
     *
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     *
     * @test_Strategy: Client invokes Request.GET on a resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamFloatTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 123");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "floattest1=123.0");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 123.1");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 345");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "floattest1=123.1");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 12.345");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST2: 34.567");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "floattest1=12.345|floattest2=34.567");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 23.456");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST2: 0.56789");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-FLOATTEST1: 1.234");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "floattest1=23.456|floattest2=0.56789");
        invoke();
    }

    /*
     * @testName: headerParamLongTest
     *
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     *
     * @test_Strategy: Client invokes Request.GET on a sub resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamLongTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: -9223372036854775808");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "longtest1=-9223372036854775808");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: 9223372036854775807");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: -9223372036854775808");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "longtest1=9223372036854775807");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: -9223372036854775808");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST2: 9223372036854775807");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "longtest1=-9223372036854775808|longtest2=9223372036854775807");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: -9223372036854775808");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST2: 9223372036854775807");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-LONGTEST1: 1234");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING,
                "longtest1=-9223372036854775808|longtest2=9223372036854775807");
        invoke();
    }

    /*
     * @testName: headerParamShortTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a sub resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamShortTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: -32768");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "shorttest1=-32768");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: 32767");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: -32768");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "shorttest1=32767");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: 32767");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST2: -32768");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "shorttest1=32767|shorttest2=-32768");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: 32767");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST2: -32768");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-SHORTTEST1: 1234");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "shorttest1=32767|shorttest2=-32768");
        invoke();
    }

    /*
     * @testName: headerParamByteTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a sub resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamByteTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST1: 127");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "bytetest1=127");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST1: -128");
        setProperty(Property.REQUEST_HEADERS, "X-CTSTEST-HEADERTEST-BYTETEST1: 26");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "bytetest1=-128");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST1: 127");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST2: -128");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "bytetest1=127|bytetest2=-128");
        invoke();

        setProperty(Property.REQUEST_HEADERS, "X-CTSTEST-HEADERTEST-BYTETEST1: 0");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST2: -128");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BYTETEST1: 127");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "bytetest2=-128");
        setProperty(Property.UNEXPECTED_RESPONSE_MATCH, "bytetest1");
        invoke();
    }

    /*
     * @testName: headerParamBooleanTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.1; JAXRS:JAVADOC:5;
     * 
     * @test_Strategy: Client invokes Request.GET on a sub resource at
     * /HeaderParamTest; Verify that right Method is invoked.
     */
    @Test
    public void headerParamBooleanTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: true");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "booleantest1=true");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: true");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: false");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "booleantest1=true");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: false");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST2: true");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "booleantest2=true");
        invoke();

        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: true");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST2: false");
        setProperty(Property.REQUEST_HEADERS,
                "X-CTSTEST-HEADERTEST-BOOLEANTEST1: false");
        setProperty(Property.REQUEST, buildRequest(Request.GET, ""));
        setProperty(Property.SEARCH_STRING, "booleantest1=true");
        invoke();
    }

    /*
     * @testName: headerParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.2; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamEntityWithConstructorTest() throws Fault {
        super.paramEntityWithConstructorTest();
    }

    /*
     * @testName: headerParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamEntityWithValueOfTest() throws Fault {
        super.paramEntityWithValueOfTest();
    }

    /*
     * @testName: headerParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamEntityWithFromStringTest() throws Fault {
        super.paramEntityWithFromStringTest();
    }

    /*
     * @testName: headerParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: headerParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamSortedSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: headerParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerParamListEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: headerFieldParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.2; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamEntityWithConstructorTest() throws Fault {
        super.fieldEntityWithConstructorTest();
    }

    /*
     * @testName: headerFieldParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamEntityWithValueOfTest() throws Fault {
        super.fieldEntityWithValueOfTest();
    }

    /*
     * @testName: headerFieldParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamEntityWithFromStringTest() throws Fault {
        super.fieldEntityWithFromStringTest();
    }

    /*
     * @testName: headerFieldParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: headerFieldParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamSortedSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: headerFieldParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void headerFieldParamListEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: headerParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see Section 3.2.
     */
    @Test
    public void headerParamThrowingWebApplicationExceptionTest() throws Fault {
        super.paramThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: headerFieldThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.1; JAXRS:SPEC:8;
     * 
     * @test_Strategy: A WebApplicationException thrown during construction of
     * field or property values using 2 or 3 above is processed directly as
     * described in section 3.3.4.
     */
    @Test
    public void headerFieldThrowingWebApplicationExceptionTest() throws Fault {
        super.fieldThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: headerParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2.
     */
    @Test
    public void headerParamThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.paramThrowingIllegalArgumentExceptionTest();
    }

    /*
     * @testName: headerFieldThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.2; JAXRS:SPEC:10;
     * 
     * @test_Strategy: Other exceptions thrown during construction of field or
     * property values using 2 or 3 above are treated as client errors:
     *
     * if the field or property is annotated with @HeaderParam or @CookieParam
     * then an implementation MUST generate a WebApplicationException that wraps
     * the thrown exception with a client error response (400 status) and no
     * entity.
     */
    @Test
    public void headerFieldThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.fieldThrowingIllegalArgumentExceptionTest();
    }

    @Override
    protected String buildRequest(String param) {
        setProperty(Property.REQUEST_HEADERS, param.replace("=", ":"));
        return buildRequest(Request.GET, "");
    }

    @Override
    protected String getDefaultValueOfParam(String param) {
        StringBuilder sb = new StringBuilder();
        sb.append(param).append("=");
        sb.append(HeaderParamTest.class.getSimpleName());
        return sb.toString();
    }
}
