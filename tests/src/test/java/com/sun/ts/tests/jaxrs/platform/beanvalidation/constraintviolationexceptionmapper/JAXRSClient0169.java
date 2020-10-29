/*
 * Copyright (c) 2015, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.platform.beanvalidation.constraintviolationexceptionmapper;

import java.util.function.Supplier;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
/**
 * Test the exception mapper is used
 * 
 * @since 2.1.0
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0169 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path",
                    "/jaxrs_platform_beanvalidation_constraintviolationexceptionmapper_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.constraintviolationexceptionmapper.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.constraintviolationexceptionmapper.ConstraintViolationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDefinitionValidator.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDeclarationAnnotation.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBean.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotShortNorFiveStringBean.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDefinitionResource.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotFiveNorShortStringBeanValidator.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotNullOrOneStringBean.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDefinitionAnnotation.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ValidateExecutableResource.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotNullOrOneStringBeanValidator.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDeclarationValidator.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotFiveNorShort.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotShortNorFiveEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.ConstraintDeclarationResource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.StringBeanEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.platform.beanvalidation.annotation.NotNullOrOne.class);
                }
            });

    private static final long serialVersionUID = 210L;

    public JAXRSClient0169() {
        setContextRoot(
                "/jaxrs_platform_beanvalidation_constraintviolationexceptionmapper_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0169().run(args);
    }

    /* Run test */

    /*
     * @testName: beanIsInvalidForBeingFiveCharsLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.3;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Test
    public void beanIsInvalidForBeingFiveCharsLongTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "notshortnorfive"));
        setProperty(Property.CONTENT, "12345");
        setProperty(Property.SEARCH_STRING,
                ConstraintViolationException.class.getName());
        invoke();
        logMsg("ExceptionMapper works as expected");
    }

    /*
     * @testName: returnIsInvalidForBeingFiveCharsLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.2;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Test
    public void returnIsInvalidForBeingFiveCharsLongTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "returnnotshortnorfive"));
        setProperty(Property.CONTENT, "12345");
        setProperty(Property.SEARCH_STRING,
                ConstraintViolationException.class.getName());
        invoke();
        logMsg("ExceptionMapper works as expected");

    }

    /*
     * @testName: beanAnnotatedIsInvalidForBeingFiveCharsLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.3;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Test
    public void beanAnnotatedIsInvalidForBeingFiveCharsLongTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "directannotatedarg"));
        setProperty(Property.CONTENT, "12345");
        setProperty(Property.SEARCH_STRING,
                ConstraintViolationException.class.getName());
        invoke();
        logMsg("ExceptionMapper works as expected");

    }

    /*
     * @testName: beanAnnotatedReturnIsInvalidForBeingFiveCharsLongTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.2;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Test
    public void beanAnnotatedReturnIsInvalidForBeingFiveCharsLongTest()
            throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "directannotatedreturn"));
        setProperty(Property.CONTENT, "12345");
        setProperty(Property.SEARCH_STRING,
                ConstraintViolationException.class.getName());
        invoke();
        logMsg("ExceptionMapper works as expected");
    }

    /*
     * @testName: constraintDeclarationExceptionThrownTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.1;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Test
    public void constraintDeclarationExceptionThrownTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST,
                "declaration/constraintdeclarationexception"));
        setProperty(Property.CONTENT, "throw ConstraintDeclarationException()");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("ExceptionMapper works as expected");

    }

    /*
     * @testName: constraintDefinitionExceptionThrownTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.1;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     */
    @Disabled(QuarkusRest.BV_Integration_Issue)
    @Test
    public void constraintDefinitionExceptionThrownTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.POST, "definition/constraintdefinitionexception"));
        setProperty(Property.CONTENT, "throw ConstraintDefinitionException()");
        setProperty(Property.STATUS_CODE,
                getStatusCode(Status.INTERNAL_SERVER_ERROR));
        invoke();
        logMsg("ExceptionMapper works as expected");

    }

    /*
     * @testName: validateExecutableIsInvalidForBeingShortTest
     * 
     * @assertion_ids: JAXRS:SPEC:101; JAXRS:SPEC:102; JAXRS:SPEC:102.2;
     * 
     * @test_Strategy: JAX-RS implementations MUST follow the constraint
     * annotation rules defined in Bean Validation 1.1. JSR
     * 
     * Due to validation of whole bean status 400 is returned
     */
    @Test
    public void validateExecutableIsInvalidForBeingShortTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "executable/nogetter"));
        setProperty(Property.SEARCH_STRING,
                ConstraintViolationException.class.getName());
        invoke();
        logMsg("ExceptionMapper works as expected");
    }
}
