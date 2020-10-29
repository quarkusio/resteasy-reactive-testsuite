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

package com.sun.ts.tests.jaxrs.ee.rs.beanparam.form.plain;

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
public class JAXRSClient0113 extends BeanParamCommonClient0117 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_beanparam_form_plain_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.form.plain.AppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.form.bean.FormBeanParamEntity.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.JaxrsParamClient0151.CollectionName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.form.bean.InnerFormBeanParamEntity.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.beanparam.form.plain.Resource.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.Constants.class, com.sun.ts.tests.jaxrs.ee.rs.ParamTest.class);
                }
            });

    private static final long serialVersionUID = 201L;

    public JAXRSClient0113() {
        setContextRoot("/jaxrs_ee_rs_beanparam_form_plain_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0113().run(args);
    }

    /* Run test */

    /*
     * @testName: formParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.2; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamEntityWithConstructorTest() throws Fault {
        super.paramEntityWithConstructorTest();
    }

    /*
     * @testName: formParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamEntityWithValueOfTest() throws Fault {
        super.paramEntityWithValueOfTest();
    }

    /*
     * @testName: formParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamEntityWithFromStringTest() throws Fault {
        super.paramEntityWithFromStringTest();
    }

    /*
     * @testName: formParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: formParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamSortedSetEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: formParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * JAXRS:JAVADOC:12; JAXRS:JAVADOC:12.1;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formParamListEntityWithFromStringTest() throws Fault {
        super.paramCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: formFieldParamEntityWithConstructorTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.2; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamEntityWithConstructorTest() throws Fault {
        super.fieldEntityWithConstructorTest();
    }

    /*
     * @testName: formFieldParamEntityWithValueOfTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamEntityWithValueOfTest() throws Fault {
        super.fieldEntityWithValueOfTest();
    }

    /*
     * @testName: formFieldParamEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.3; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamEntityWithFromStringTest() throws Fault {
        super.fieldEntityWithFromStringTest();
    }

    /*
     * @testName: formFieldParamSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SET);
    }

    /*
     * @testName: formFieldParamSortedSetEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamSortedSetEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.SORTED_SET);
    }

    /*
     * @testName: formFieldParamListEntityWithFromStringTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.5; JAXRS:SPEC:5.4; JAXRS:JAVADOC:6;
     * 
     * @test_Strategy: Verify that named FormParam is handled properly
     */
    @Test
    public void formFieldParamListEntityWithFromStringTest() throws Fault {
        super.fieldCollectionEntityWithFromStringTest(CollectionName.LIST);
    }

    /*
     * @testName: formParamEntityWithEncodedTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.1; JAXRS:SPEC:7; JAXRS:SPEC:12.2;
     * 
     * @test_Strategy: Verify that named FormParam @Encoded is handled
     */
    @Test
    public void formParamEntityWithEncodedTest() throws Fault {
        super.paramEntityWithEncodedTest();
    }

    /*
     * @testName: formFieldParamEntityWithEncodedTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.1; JAXRS:SPEC:7;
     * 
     * @test_Strategy: Verify that named FormParam @Encoded is handled
     */
    @Test
    public void formFieldParamEntityWithEncodedTest() throws Fault {
        super.fieldEntityWithEncodedTest();
    }

    /*
     * @testName: formParamThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see Section 3.2.
     */
    @Test
    public void formParamThrowingWebApplicationExceptionTest() throws Fault {
        super.paramThrowingWebApplicationExceptionTest();
        super.paramThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: formFieldThrowingWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:3.1; JAXRS:SPEC:8;
     * 
     * @test_Strategy: A WebApplicationException thrown during construction of
     * field or property values using 2 or 3 above is processed directly as
     * described in section 3.3.4.
     */
    @Test
    public void formFieldThrowingWebApplicationExceptionTest() throws Fault {
        super.fieldThrowingWebApplicationExceptionTest();
        super.fieldThrowingWebApplicationExceptionTest();
    }

    /*
     * @testName: formParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2.
     */
    @Test
    public void formParamThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.paramThrowingIllegalArgumentExceptionTest();
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.paramThrowingIllegalArgumentExceptionTest();
    }

    /*
     * @testName: formFieldThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:9; JAXRS:SPEC:9.2; JAXRS:SPEC:10;
     * 
     * @test_Strategy: Other exceptions thrown during construction of field or
     * property values using 2 or 3 above are treated as client errors:
     *
     * if the field or property is annotated with @FormParam or @CookieParam then
     * an implementation MUST generate a WebApplicationException that wraps the
     * thrown exception with a client error response (400 status) and no entity.
     */
    @Test
    public void formFieldThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.fieldThrowingIllegalArgumentExceptionTest();
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.fieldThrowingIllegalArgumentExceptionTest();
    }

    @Override
    protected String buildRequest(String param) {
        if (!"".equals(param)) {
            setProperty(Property.CONTENT, "ANYTHING&", param, "&", Constants.INNER,
                    param);
        } else {
            setProperty(Property.CONTENT, "ANYTHING");
        }
        return buildRequest(Request.POST, fieldBeanParam);
    }

    @Override
    protected//
    String buildRequestForException(String param, int entity) throws Fault {
        if (entity == 1)
            setProperty(Property.CONTENT, "ANYTHING&", param);
        else
            setProperty(Property.CONTENT, "ANYTHING&", Constants.INNER, param);
        return buildRequest(Request.POST, fieldBeanParam);
    }
}
