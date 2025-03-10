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

package com.sun.ts.tests.jaxrs.spec.returntype;

import java.util.UUID;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

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
public class JAXRSClient0018 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_returntype_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.returntype.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.returntype.UUIDWriter.class,
                                    com.sun.ts.tests.jaxrs.common.AbstractMessageBodyRW.class,
                                    com.sun.ts.tests.jaxrs.spec.returntype.ReturnTypeTest.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityMessageWriter.class);
                }
            });

    private static final long serialVersionUID = ReturnTypeTest.serialVersionUID;

    public JAXRSClient0018() {
        setContextRoot("/jaxrs_spec_returntype_web/ReturnTypeTest");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0018().run(args);
    }

    /* Run test */
    /*
     * @testName: voidTest
     * 
     * @assertion_ids: JAXRS:SPEC:14.1; JAXRS:SPEC:14.8; JAXRS:SPEC:14;
     * JAXRS:SPEC:57; JAXRS:SPEC:60;
     * 
     * @test_Strategy: Client sends a request on a resource at
     * /ReturnTypeTest/void, Verify that 204 status returned.
     */
    @Test
    public void voidTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "void"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: nullEntityResponseTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.4; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: Response: 204 status code is used if the entity property is
     * null.
     */
    @Test
    public void nullEntityResponseTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "nullEntityResponse"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: nullResponseTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.3; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: Response: A null return value results in a 204 status code.
     */
    @Test
    public void nullResponseTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "nullResponse"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: nullGenericEntityTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.6; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: Client sends a request on a resource at
     * /ReturnTypeTest/get, Verify that 204 status returned.
     */
    @Test
    public void nullGenericEntityTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "nullGenericEntityTest"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: genericEntityTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.5; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: Results in an entity body mapped from the Entity property
     * of the GenericEntity. If the return value is not null a 200 status code is
     * used
     */
    @Test
    public void genericEntityTest() throws Fault {
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "genericEntityTest"));
        UUID uuid = new UUID(serialVersionUID, serialVersionUID >> 1);
        setProperty(Property.SEARCH_STRING, uuid.toString());
        invoke();
    }

    /*
     * @testName: nullEntityTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.8; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: a null return value results in a 204 status code.
     */
    @Test
    public void nullEntityTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "nullEntityTest"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NO_CONTENT));
        invoke();
    }

    /*
     * @testName: defaultStatusTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.8; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: a null return value results in a 204 status code.
     */
    @Test
    public void defaultStatusTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "default"));
        setProperty(Property.SEARCH_STRING, "I am OK");
        invoke();
    }

    /*
     * @testName: entityBodyTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.7; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy: Other: Results in an entity body mapped from the class of
     * the returned instance. If the return value is not null a 200 status code is
     * used
     */
    @Test
    public void entityBodyTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.GET, "entitybodytest"));
        setProperty(Property.SEARCH_STRING, String.valueOf(serialVersionUID));
        invoke();
    }

    /*
     * @testName: entityResponseTest
     * 
     * @assertion_ids: JAXRS:SPEC:14; JAXRS:SPEC:14.4; JAXRS:SPEC:57;
     * JAXRS:SPEC:60;
     * 
     * @test_Strategy:If the status property of the Response is not set: a 200
     * status code is used for a non-null entity property
     */
    @Test
    public void entityResponseTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS, buildAccept(MediaType.TEXT_XML_TYPE));
        setProperty(Property.REQUEST,
                buildRequest(Request.GET, "entitybodyresponsetest"));
        setProperty(Property.SEARCH_STRING, String.valueOf(serialVersionUID));
        invoke();
    }

    /*
     * @testName: notAcceptableTest
     * 
     * @assertion_ids: JAXRS:SPEC:25.6; JAXRS:SPEC:57; JAXRS:SPEC:60;
     * 
     * @test_Strategy: Client sends a request on a resource at
     * /ReturnTypeTest/notAcceptable, Verify that 406 status returned.
     */
    @Test
    public void notAcceptableTest() throws Fault {
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_HTML_TYPE));
        setProperty(Property.REQUEST, buildRequest(Request.GET, "notAcceptable"));
        setProperty(Property.STATUS_CODE, getStatusCode(Status.NOT_ACCEPTABLE));
        invoke();
    }
}
