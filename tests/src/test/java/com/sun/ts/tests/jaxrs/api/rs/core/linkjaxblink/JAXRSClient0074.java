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

package com.sun.ts.tests.jaxrs.api.rs.core.linkjaxblink;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import jakarta.ws.rs.core.Link;
import javax.xml.namespace.QName;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0074 extends JAXRSCommonClient {

    private static final long serialVersionUID = -6053007016837644641L;

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0074().run(args);
    }

    /*
     * @testName: defaultConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:822; JAXRS:JAVADOC:820; JAXRS:JAVADOC:821;
     * 
     * @test_Strategy: Default constructor needed during unmarshalling.
     * JaxbLink.getParams; JaxbLink.getUri
     */
    @org.junit.jupiter.api.Test
    public void defaultConstructorTest() throws Fault {
        Link.JaxbLink jaxbLink = new Link.JaxbLink();
        boolean getUri = jaxbLink.getUri() == null
                || jaxbLink.getUri().toASCIIString() == null
                || jaxbLink.getUri().toASCIIString().isEmpty();
        assertTrue(getUri, "JaxbLink.getUri() is unexpectedly preset to",
                jaxbLink.getUri());
        logMsg("Link.JaxbLink.getUri() is empty as expected");
        boolean params = jaxbLink.getParams() == null
                || jaxbLink.getParams().isEmpty();
        assertTrue(params, "JaxbLink.getParams() is unexpectedly preset to",
                jaxbLink.getParams());
        logMsg("Link.JaxbLink.getParams() is empty as expected");
    }

    /*
     * @testName: uriConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:823; JAXRS:JAVADOC:820; JAXRS:JAVADOC:821;
     * 
     * @test_Strategy: Construct an instance from a URI and no parameters.
     * JaxbLink.getParams; JaxbLink.getUri
     */
    @org.junit.jupiter.api.Test
    public void uriConstructorTest() throws Fault {
        String uri = "protocol://domain2.domain1:port";
        URI fromString = uriFromString(uri);
        Link.JaxbLink jaxbLink = new Link.JaxbLink(fromString);

        boolean getUri = jaxbLink.getUri().equals(fromString);
        assertTrue(getUri, "JaxbLink.getUri() is unexpectedly preset to",
                jaxbLink.getUri());
        logMsg("Link.JaxbLink.getUri() is", uri, "as expected");

        boolean params = jaxbLink.getParams() == null
                || jaxbLink.getParams().isEmpty();
        assertTrue(params, "JaxbLink.getParams() is unexpectedly preset to",
                jaxbLink.getParams());
        logMsg("Link.JaxbLink.getParams() is empty as expected");
    }

    /*
     * @testName: uriParamsConstructorTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:824; JAXRS:JAVADOC:820; JAXRS:JAVADOC:821;
     * 
     * @test_Strategy: Construct an instance from a URI and some parameters.
     * JaxbLink.getParams; JaxbLink.getUri
     */
    @org.junit.jupiter.api.Test
    public void uriParamsConstructorTest() throws Fault {
        String uri = "protocol://domain2.domain1:port";
        String q = "qName";
        QName qName = new QName(q);
        URI fromString = uriFromString(uri);
        java.util.Map<QName, Object> map;
        map = new HashMap<QName, Object>();
        map.put(qName, q);
        Link.JaxbLink jaxbLink = new Link.JaxbLink(fromString, map);

        boolean getUri = jaxbLink.getUri().equals(fromString);
        assertTrue(getUri, "JaxbLink.getUri() is unexpectedly preset to",
                jaxbLink.getUri());
        logMsg("Link.JaxbLink.getUri() is", uri, "as expected");

        boolean params = jaxbLink.getParams().containsKey(qName)
                && jaxbLink.getParams().get(qName).equals(q);
        assertTrue(params, "JaxbLink.getParams() is unexpectedly set to",
                jaxbLink.getParams());
        logMsg("Link.JaxbLink.getParams() contains", q, "as expected");
    }

    // //////////////////////////////////////////////////////////////////////
    private static URI uriFromString(String uri) throws Fault {
        URI fromString = null;
        try {
            fromString = new URI(uri);
        } catch (URISyntaxException e) {
            throw new Fault(e);
        }
        return fromString;
    }
}
