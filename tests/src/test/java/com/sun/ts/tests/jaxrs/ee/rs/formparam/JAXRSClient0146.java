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

package com.sun.ts.tests.jaxrs.ee.rs.formparam;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

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
public class JAXRSClient0146 extends JaxrsParamClient0151 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_formparam_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.WebApplicationExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingExceptionGivenByName.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithFromString.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityThrowingWebApplicationException.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityWithConstructor.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.formparam.FormParamTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.RuntimeExceptionMapper.class);
                }
            });

    private static final String ENCODED = "_%60%27%24X+Y%40%22a+a%22";

    private static final String DECODED = "_`'$X Y@\"a a\"";

    public JAXRSClient0146() {
        setContextRoot("/jaxrs_ee_formparam_web/FormParamTest");
    }

    private static final long serialVersionUID = 1L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        JAXRSClient0146 theTests = new JAXRSClient0146();
        theTests.run(args);
    }

    /*
     * @testName: nonDefaultFormParamNothingSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test sending no content;
     */
    @Test
    public void nonDefaultFormParamNothingSentTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "PostNonDefParam", null);
    }

    /*
     * @testName: defaultFormParamSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test sending override of default argument content;
     */
    @Test
    public void defaultFormParamSentTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "PostDefParam", ENCODED);
    }

    /*
     * @testName: defaultFormParamNoArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test sending no argument content, receiving default;
     */
    @Test
    public void defaultFormParamNoArgSentTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "PostDefParam", "default");
    }

    /*
     * @testName: defaultFormParamPutNoArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test sending no argument content, PUT, receiving default;
     */
    @Test
    public void defaultFormParamPutNoArgSentTest() throws Fault {
        defaultFormParamAndInvoke(Request.PUT, "DefParam", "DefParam");
    }

    /*
     * @testName: defaultFormParamPutArgSentTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test sending argument content, PUT;
     */
    @Test
    public void defaultFormParamPutArgSentTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.PUT, "DefParam", DECODED);
    }

    /*
     * @testName: defaultFormParamValueOfTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithValueOf from default;
     */
    @Test
    public void defaultFormParamValueOfTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "ParamEntityWithValueOf",
                "ValueOf");
    }

    /*
     * @testName: nonDefaultFormParamValueOfTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithValueOf from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamValueOfTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "ParamEntityWithValueOf", DECODED);
    }

    /*
     * @testName: defaultFormParamFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromStringTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "ParamEntityWithFromString",
                "FromString");
    }

    /*
     * @testName: nonDefaultFormParamFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromStringTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "ParamEntityWithFromString",
                ENCODED);
    }

    /*
     * @testName: defaultFormParamFromConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromConstructorTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "Constructor", "Constructor");
    }

    /*
     * @testName: nonDefaultFormParamFromConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromConstructorTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "Constructor", DECODED);
    }

    /*
     * @testName: defaultFormParamFromListConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from default;
     */
    @Test
    public void defaultFormParamFromListConstructorTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "ListConstructor",
                "ListConstructor");
    }

    /*
     * @testName: nonDefaultFormParamFromListConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithConstructor from sending a
     * String;
     */
    @Test
    public void nonDefaultFormParamFromListConstructorTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "ListConstructor", DECODED);
    }

    /*
     * @testName: defaultFormParamFromSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromSetFromStringTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "SetFromString", "SetFromString");
    }

    /*
     * @testName: nonDefaultFormParamFromSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSetFromStringTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "SetFromString", ENCODED);
    }

    /*
     * @testName: defaultFormParamFromSortedSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromSortedSetFromStringTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "SortedSetFromString",
                "SortedSetFromString");
    }

    /*
     * @testName: nonDefaultFormParamFromSortedSetFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromSortedSetFromStringTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "SortedSetFromString", ENCODED);
    }

    /*
     * @testName: defaultFormParamFromListFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12; JAXRS:SPEC:12.1;
     * 
     * @test_Strategy: Test creating a ParamEntityWithFromString from default;
     */
    @Test
    public void defaultFormParamFromListFromStringTest() throws Fault {
        defaultFormParamAndInvoke(Request.POST, "ListFromString", "ListFromString");
    }

    /*
     * @testName: nonDefaultFormParamFromListFromStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:4; JAXRS:SPEC:12;
     * 
     * @test_Strategy: Test creating a ParamEntityWithListConstructor from sending
     * a String;
     */
    @Test
    public void nonDefaultFormParamFromListFromStringTest() throws Fault {
        setProperty(Property.CONTENT, "default_argument=" + ENCODED);
        defaultFormParamAndInvoke(Request.POST, "ListFromString", ENCODED);
    }

    /*
     * @testName: formParamEntityWithEncodedTest
     * 
     * @assertion_ids: JAXRS:SPEC:7; JAXRS:SPEC:12;JAXRS:SPEC:12.2;
     * 
     * @test_Strategy: Verify that named FormParam @Encoded is handled
     */
    @Test
    public void formParamEntityWithEncodedTest() throws Fault {
        searchEqualsEncoded = true;
        super.paramEntityWithEncodedTest();
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
    }

    /*
     * @testName: formParamThrowingIllegalArgumentExceptionTest
     * 
     * @assertion_ids: JAXRS:SPEC:12.3;
     * 
     * @test_Strategy: Exceptions thrown during construction of parameter values
     * are treated the same as exceptions thrown during construction of field or
     * bean property values, see section 3.2. Exceptions thrown during
     * construction of @FormParam annotated parameter values are treated the same
     * as if the parameter were annotated with @HeaderParam.
     */
    @Test
    public void formParamThrowingIllegalArgumentExceptionTest() throws Fault {
        setProperty(Property.UNORDERED_SEARCH_STRING, Status.BAD_REQUEST.name());
        super.paramThrowingIllegalArgumentExceptionTest();
    }

    // ///////////////////////////////////////////////////////////////////////

    private void defaultFormParamAndInvoke(Request request, String method,
            String arg) throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        setProperty(Property.REQUEST, buildRequest(request, method));
        setProperty(Property.SEARCH_STRING, FormParamTest.response(arg));
        invoke();
    }

    @Override
    protected String buildRequest(String param) {
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        setProperty(Property.CONTENT,
                "default_argument=" + param.replace("=", "%3d"));
        return buildRequest(Request.POST, segmentFromParam(param));
    }

    // not used at the moment
    @Override
    protected String getDefaultValueOfParam(String param) {
        return null;
    }
}
