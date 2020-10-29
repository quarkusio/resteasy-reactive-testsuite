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

package com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring;

import java.util.function.Supplier;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0017 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_resource_valueofandfromstring_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring.EnumWithFromStringAndValueOf.class,
                                    com.sun.ts.tests.jaxrs.spec.resource.valueofandfromstring.ParamEntityWithFromStringAndValueOf.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ParamEntityPrototype.class);
                }
            });

    private static final long serialVersionUID = 6626213314312507899L;

    private static final String DATA = "ASDFGHJKLQWERTYUIOPPPPPPP";

    public JAXRSClient0017() {
        setContextRoot("/jaxrs_spec_resource_valueofandfromstring_web");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0017().run(args);
    }

    /* Run test */
    /*
     * @testName: enumHeaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an enum in which case fromString MUST be used.
     */
    @Test
    public void enumHeaderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "param:" + DATA);
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/enumheader"));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.FROMSTRING.name());
        invoke();
    }

    /*
     * @testName: enumCookieTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an enum in which case fromString MUST be used.
     */
    @Test
    public void enumCookieTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Cookie: param=" + DATA);
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/enumcookie"));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.FROMSTRING.name());
        invoke();
    }

    /*
     * @testName: enumMaxtrixTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an enum in which case fromString MUST be used.
     */
    @Test
    public void enumMaxtrixTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/enummatrix;param=" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.FROMSTRING.name());
        invoke();
    }

    /*
     * @testName: enumQueryTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an enum in which case fromString MUST be used.
     */
    @Test
    public void enumQueryTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/enumquery?param=" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.FROMSTRING.name());
        invoke();
    }

    /*
     * @testName: enumPathTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an enum in which case fromString MUST be used.
     */
    @Test
    public void enumPathTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/enumpath/" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.FROMSTRING.name());
        invoke();
    }

    /*
     * @testName: entityHeaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an entity in which case fromString MUST be used.
     */
    @Test
    public void entityHeaderTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "param:" + DATA);
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/entityheader"));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.VALUEOF.name());
        invoke();
    }

    /*
     * @testName: entityCookieTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an entity in which case fromString MUST be used.
     */
    @Test
    public void entityCookieTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, "Cookie: param=" + DATA);
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/entitycookie"));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.VALUEOF.name());
        invoke();
    }

    /*
     * @testName: entityMaxtrixTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an entity in which case fromString MUST be used.
     */
    @Test
    public void entityMaxtrixTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/entitymatrix;param=" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.VALUEOF.name());
        invoke();
    }

    /*
     * @testName: entityQueryTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an entity in which case fromString MUST be used.
     */
    @Test
    public void entityQueryTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/entityquery?param=" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.VALUEOF.name());
        invoke();
    }

    /*
     * @testName: entityPathTest
     * 
     * @assertion_ids: JAXRS:SPEC:5; JAXRS:SPEC:5.5;
     * 
     * @test_Strategy: If both methods are present then valueOf MUST be used
     * unless the type is an entity in which case fromString MUST be used.
     */
    @Test
    public void entityPathTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "resource/entitypath/" + DATA));
        setProperty(Property.SEARCH_STRING,
                EnumWithFromStringAndValueOf.VALUEOF.name());
        invoke();
    }
}
