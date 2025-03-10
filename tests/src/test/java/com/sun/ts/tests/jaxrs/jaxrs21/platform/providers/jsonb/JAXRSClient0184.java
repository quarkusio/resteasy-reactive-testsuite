/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Supplier;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.MediaType;

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
 * 
 */
/**
 * @since 2.1
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0184 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_platform_providers_jsonb_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonb.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonb.Resource.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0184() {
        setContextRoot("/jaxrs_jaxrs21_platform_providers_jsonb_web/resource");
    }

    /*
     * @testName: serverJsonBStringReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy: String is defined in 4.2.4 to have a MBW<String>, whereas
     * JSONB has MBW<Object> for which it fails to be chosen and String MBW is
     * used. This is intentional for JAXRS spec not to allow other MBW to send
     * Strings
     */
    @Test
    public void serverJsonBStringReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tostring"));
        setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBCharReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy: For CHAR and other than String data types, JSONB MBW is
     * used, since unlike String, they have text/plain MBW in 4.2.4.
     */
    @Test
    public void serverJsonBCharReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tochar"));
        setProperty(Property.SEARCH_STRING,
                jsonb.toJson(Resource.MESSAGE.charAt(0)));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBByteReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBByteReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tobyte"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(Byte.MAX_VALUE));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBShortReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBShortReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "toshort"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(Short.MAX_VALUE));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBIntReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBIntReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "toint"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(Integer.MAX_VALUE));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBLongReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBLongReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tolong"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(Long.MAX_VALUE));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBNumberReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBNumberReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tonumber"));
        setProperty(Property.SEARCH_STRING,
                jsonb.toJson(BigDecimal.valueOf(Long.MAX_VALUE)));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBBigIntegerReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBBigIntegerReturnTest() throws Fault {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tobiginteger"));
        setProperty(Property.SEARCH_STRING,
                jsonb.toJson(BigInteger.valueOf(Long.MAX_VALUE)));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBURIReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBURIReturnTest()
            throws Fault, JsonbException, URISyntaxException {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "touri"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(new URI(Resource.URL)));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonBURLReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:130;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonBURLReturnTest()
            throws Fault, JsonbException, MalformedURLException {
        Jsonb jsonb = JsonbBuilder.create();
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tourl"));
        setProperty(Property.SEARCH_STRING, jsonb.toJson(new URL(Resource.URL)));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    // ////////////////////////////////////////////////////////////////////////
    private static MediaType getJsonType() {
        return MediaType.APPLICATION_JSON_TYPE;
    }

}
