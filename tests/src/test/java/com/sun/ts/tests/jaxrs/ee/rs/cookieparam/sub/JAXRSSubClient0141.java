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

package com.sun.ts.tests.jaxrs.ee.rs.cookieparam.sub;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSSubClient0141
        extends com.sun.ts.tests.jaxrs.ee.rs.cookieparam.JAXRSClient0143 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_cookieparam_sub_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.cookieparam.sub.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.JaxrsParamClient0151.CollectionName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.cookieparam.CookieParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.cookieparam.sub.CookieSubResource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSSubClient0141() {
        setContextRoot("/jaxrs_ee_rs_cookieparam_sub_web/Resource/subresource");
    }

    /*
     * @testName: cookieParamSubTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * JAXRS:JAVADOC:145; JAXRS:JAVADOC:2;
     * 
     * @test_Strategy: Client invokes GET on root resource at /CookieParamTest;
     * Resource will respond with a cookie; Client verify the cookie is received;
     * Client send request again with the cookie; Verify that the cookie is
     * received using CookieParam in the resource; Verify on the client side from
     * response.
     */
    @Test
    public void cookieParamSubTest() throws Fault {
        super.cookieParamTest();
    }

    /*
     * @testName: cookieParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.2; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamEntityWithConstructorTest() throws Fault {
        super.paramEntityWithConstructorTest();
    }

    /*
     * @testName: cookieParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamEntityWithValueOfTest() throws Fault {
        super.paramEntityWithValueOfTest();
    }

    /*
     * @testName: cookieParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamEntityWithFromStringTest() throws Fault {
        super.paramEntityWithFromStringTest();
    }

    /*
     * @testName: cookieParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: cookieParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamListEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: cookieParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void cookieParamSortedSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: cookieFieldParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.2; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamEntityWithConstructorTest() throws Fault {
        super.fieldEntityWithConstructorTest();
    }

    /*
     * @testName: cookieFieldParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamEntityWithValueOfTest() throws Fault {
        super.fieldEntityWithValueOfTest();
    }

    /*
     * @testName: cookieFieldParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.3; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamEntityWithFromStringTest() throws Fault {
        super.fieldEntityWithFromStringTest();
    }

    /*
     * @testName: cookieFieldParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: cookieFieldParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamListEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: cookieFieldSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:5.4; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldSortedSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: cookieParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:12.3; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2.
     * 
     */
    @Test
    public void cookieParamThrowingWebApplicationExceptionTest() throws Fault {
        super.paramThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: cookieFieldThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.4; JAXRS:SPEC:8; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:4;
     * 
     * @test_Strategy: A WebApplicationException thrown during construction of
     * field or property values using 2 or 3 above is processed directly as
     * described in section 3.3.4.
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldThrowingWebApplicationExceptionTest() throws Fault {
        super.fieldThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: cookieParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.2; JAXRS:SPEC:20;
     * JAXRS:SPEC:20.2; JAXRS:SPEC:10;
     * 
     * @test_Strategy: Other exceptions thrown during construction of field or
     * property values using 2 or 3 above are treated as client errors:
     *
     * if the field or property is annotated with @HeaderParam or @CookieParam
     * then an implementation MUST generate a WebApplicationException that wraps
     * the thrown exception with a client error response (400 status) and no
     * entity.
     *
     */
    @Test
    public void cookieParamThrowingIllegalArgumentExceptionTest() throws Fault {
        super.paramThrowingIllegalArgumentExceptionTest();
    }

    /*
     * @testName: cookieFieldParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3; JAXRS:SPEC:20; JAXRS:SPEC:20.2;
     * JAXRS:SPEC:4;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2.
     * 
     * An implementation is only required to set the annotated field and bean
     * property values of instances created by the implementation runtime. (Check
     * the resource with resource locator is injected field properties)
     */
    @Test
    public void cookieFieldParamThrowingIllegalArgumentExceptionTest()
            throws Fault {
        super.fieldThrowingIllegalArgumentExceptionTest();
    }

}
