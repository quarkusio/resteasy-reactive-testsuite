/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.platform.provider.jsonp;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;

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
public class JAXRSClient0162 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_platform_provider_jsonp_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.platform.provider.jsonp.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.platform.provider.jsonp.Resource.class,
                                    com.sun.ts.tests.jaxrs.common.provider.PrintingErrorHandler.class);
                }
            });

    private static final long serialVersionUID = 7441792527287072853L;

    public JAXRSClient0162() {
        setContextRoot("/jaxrs_platform_provider_jsonp_web/resource");
    }

    /*
     * @testName: serverJsonArrayReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:107;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonArray
     */
    @Test
    public void serverJsonArrayReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "toarray"));
        bufferEntity(true);
        invoke();
        assertResponseContainsRow(0);
        assertResponseContainsRow(1);
    }

    /*
     * @testName: serverJsonStructureReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:33; JAXRS:SPEC:33.1; JAXRS:SPEC:75;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonStructure
     */
    @Test
    public void serverJsonStructureReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "tostructure"));
        bufferEntity(true);
        invoke();
        assertResponseContainsRow(0);
        assertResponseContainsRow(1);
    }

    /*
     * @testName: serverJsonObjectReturnTest
     * 
     * @assertion_ids: JAXRS:SPEC:107;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonObject
     */
    @Test
    public void serverJsonObjectReturnTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "toobject"));
        bufferEntity(true);
        invoke();
        assertResponseContainsRow(0);
    }

    /*
     * @testName: serverJsonArrayArgumentTest
     * 
     * @assertion_ids: JAXRS:SPEC:107;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonArray
     */
    @Test
    public void serverJsonArrayArgumentTest() throws Fault {
        String entity = Resource.createArray().toString();
        setProperty(Property.REQUEST, buildRequest(Request.POST, "fromarray"));
        setProperty(Property.REQUEST_HEADERS, buildContentType(getJsonType()));
        setProperty(Property.CONTENT, entity);
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    /*
     * @testName: serverJsonStructureArgumentTest
     * 
     * @assertion_ids: JAXRS:SPEC:107;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonStructure
     */
    @Test
    public void serverJsonStructureArgumentTest() throws Fault {
        String entity = Resource.createArray().toString();
        setProperty(Property.REQUEST, buildRequest(Request.POST, "fromstructure"));
        setProperty(Property.REQUEST_HEADERS, buildContentType(getJsonType()));
        setProperty(Property.CONTENT, entity);
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    /*
     * @testName: serverJsonObjectArgumentTest
     * 
     * @assertion_ids: JAXRS:SPEC:107;
     * 
     * @test_Strategy: implementations MUST support entity providers for the
     * following types: JsonObject
     */
    @Test
    public void serverJsonObjectArgumentTest() throws Fault {
        String entity = Resource.createObject(1).toString();
        setProperty(Property.REQUEST, buildRequest(Request.POST, "fromobject"));
        setProperty(Property.REQUEST_HEADERS, buildContentType(getJsonType()));
        setProperty(Property.CONTENT, entity);
        setProperty(Property.SEARCH_STRING, entity);
        invoke();
    }

    // ////////////////////////////////////////////////////////////////////////
    private void assertResponseContainsRow(int id) throws Fault {
        String content = getResponseBody();
        assertContains(content, Resource.LABEL[0]);
        assertContains(content, Resource.TYPE[id]);
        assertContains(content, Resource.LABEL[1]);
        assertContains(content, Resource.PHONE[id]);
        logMsg("Found", Resource.createObject(id).toString(), "as expected");
    }

    private static void assertContains(String where, String what) throws Fault {
        assertContains(where, what, what, "has not been found in", where);
    }

    private static MediaType getJsonType() {
        return new MediaType("application", "json");
    }

}
