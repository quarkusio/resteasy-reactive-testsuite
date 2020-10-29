/*
 * Copyright (c) 2014, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.ee.rs.beanparam.query.plain;

import java.util.function.Supplier;

import javax.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.ee.rs.Constants;
import com.sun.ts.tests.jaxrs.ee.rs.beanparam.BeanParamCommonClient0117;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 * @since 2.0.1
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0111 extends BeanParamCommonClient0117 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_beanparam_query_plain_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.query.plain.AppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.query.bean.QueryBeanParamEntity.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.JaxrsParamClient0151.CollectionName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.query.plain.Resource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.Constants.class, com.sun.ts.tests.jaxrs.ee.rs.ParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.query.bean.InnerQueryBeanParamEntity.class);
                }
            });

    private static final long serialVersionUID = 201L;

    public JAXRSClient0111() {
        setContextRoot("/jaxrs_ee_rs_beanparam_query_plain_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0111().run(args);
    }

    /* Run test */

    /*
     * @testName: queryParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.2; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamEntityWithConstructorTest() throws Fault {
        super.paramEntityWithConstructorTest();
    }

    /*
     * @testName: queryParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.3; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamEntityWithValueOfTest() throws Fault {
        super.paramEntityWithValueOfTest();
    }

    /*
     * @testName: queryParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.3; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamEntityWithFromStringTest() throws Fault {
        super.paramEntityWithFromStringTest();
    }

    /*
     * @testName: queryParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: queryParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamSortedSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: queryParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryParamListEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: queryFieldParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.2; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamEntityWithConstructorTest() throws Fault {
        super.fieldEntityWithConstructorTest();
    }

    /*
     * @testName: queryFieldParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.3; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamEntityWithValueOfTest() throws Fault {
        super.fieldEntityWithValueOfTest();
    }

    /*
     * @testName: queryFieldParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.3; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamEntityWithFromStringTest() throws Fault {
        super.fieldEntityWithFromStringTest();
    }

    /*
     * @testName: queryFieldParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: queryFieldParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamSortedSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: queryFieldParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:5.4; JAXRS:SPEC:6;
     * 
     * @test_Strategy: Verify that named QueryParam is handled properly
     */
    @Test
    public void queryFieldParamListEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: queryParamEntityWithEncodedTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:7; JAXRS:SPEC:12;
     * JAXRS:SPEC:12.2;
     * 
     * @test_Strategy: Verify that named QueryParam @Encoded is handled
     */
    @Test
    public void queryParamEntityWithEncodedTest() throws Fault {
        super.paramEntityWithEncodedTest();
    }

    /*
     * @testName: queryFieldParamEntityWithEncodedTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:7;
     * 
     * @test_Strategy: Verify that named QueryParam @Encoded is handled
     */
    @Test
    public void queryFieldParamEntityWithEncodedTest() throws Fault {
        super.fieldEntityWithEncodedTest();
    }

    /*
     * @testName: queryParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see Section 3.2.
     */
    @Test
    public void queryParamThrowingWebApplicationExceptionTest() throws Fault {
        super.paramThrowingWebApplicationExceptionTest();
        super.paramThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: queryFieldThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.2; JAXRS:SPEC:8;
     * 
     * @test_Strategy: A WebApplicationException thrown during construction of
     * field or property values using 2 or 3 above is processed directly as
     * described in section 3.3.4.
     */
    @Test
    public void queryFieldThrowingWebApplicationExceptionTest() throws Fault {
        super.fieldThrowingWebApplicationExceptionTest();
        super.fieldThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: queryParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2.
     */
    @Test
    public void queryParamThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.NOT_FOUND.name());
        super.paramThrowingIllegalArgumentExceptionTest();
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.NOT_FOUND.name());
        super.paramThrowingIllegalArgumentExceptionTest();
    }

    /*
     * @testName: queryFieldThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.1; JAXRS:SPEC:10;
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
    public void queryFieldThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.NOT_FOUND.name());
        super.fieldThrowingIllegalArgumentExceptionTest();
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.NOT_FOUND.name());
        super.fieldThrowingIllegalArgumentExceptionTest();
    }

    @Override
    protected String buildRequest(String param) {
        return buildRequest(Request.GET, fieldBeanParam, "?", param, "&",
                Constants.INNER, param);
    }

    @Override
    protected//
    String buildRequestForException(String param, int entity) throws Fault {
        if (entity == 1)
            return buildRequest(Request.GET, fieldBeanParam, "?", param);
        else
            return buildRequest(Request.GET, fieldBeanParam, "?", Constants.INNER,
                    param);
    }

}
