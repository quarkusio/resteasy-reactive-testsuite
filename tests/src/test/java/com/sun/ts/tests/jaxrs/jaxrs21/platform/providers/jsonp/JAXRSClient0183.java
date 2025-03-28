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

package com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonp;

import java.util.function.Supplier;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
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
 */
/**
 * @since 2.1
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0183 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_platform_providers_jsonp_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonp.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.platform.providers.jsonp.Resource.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0183() {
        setContextRoot("/jaxrs_jaxrs21_platform_providers_jsonp_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0183().run(args);
    }

    /*
     * @testName: serverJsonStringReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:129;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonStringReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tostring"));
        setProperty(Property.SEARCH_STRING, Resource.MESSAGE);
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        System.out.println(TEST_PROPS.get(Property.REQUEST_HEADERS));
        invoke();
    }

    @Override
    protected void run(String[] args) {
        if (args.length == 0)
            args = new String[] { "-p", "install/jaxrs/bin/ts.jte", "-t",
                    "serverJsonStringReturnTest", "-vehicle", "standalone" };
        super.run(args);
    }

    /*
     * @testName: serverJsonNumberReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:129;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonNumberReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tonumber"));
        setProperty(Property.SEARCH_STRING, String.valueOf(Long.MIN_VALUE));
        setProperty(Property.REQUEST_HEADERS, buildAccept(getJsonType()));
        invoke();
    }

    /*
     * @testName: serverJsonStringArgumentTest
     * 
     * @assertion_ids: JAXRS:SPEC:129;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonStringArgumentTest() throws Fault {
        String entity = getClass().getName();
        JsonString json = Json.createValue(entity);
        setRequestContentEntity(json);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "fromstring"));
        setProperty(Property.REQUEST_HEADERS, buildContentType(getJsonType()));
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    /*
     * @testName: serverJsonNumberArgumentTest
     * 
     * @assertion_ids: JAXRS:SPEC:129;
     * 
     * @test_Strategy:
     */
    @Test
    public void serverJsonNumberArgumentTest() throws Fault {
        JsonNumber number = Json.createValue(Long.MIN_VALUE);
        setRequestContentEntity(number);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "fromnumber"));
        setProperty(Property.REQUEST_HEADERS, buildContentType(getJsonType()));
        setProperty(Property.SEARCH_STRING, String.valueOf(Long.MIN_VALUE));
        invoke();
    }

    // ////////////////////////////////////////////////////////////////////////
    private static MediaType getJsonType() {
        return MediaType.APPLICATION_JSON_TYPE;
    }

}
