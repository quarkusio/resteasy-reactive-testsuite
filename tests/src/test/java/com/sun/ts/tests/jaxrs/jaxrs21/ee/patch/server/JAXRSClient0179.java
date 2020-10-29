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

package com.sun.ts.tests.jaxrs.jaxrs21.ee.patch.server;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.methods.HttpPatch;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.common.webclient.http.HttpRequest;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

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
public class JAXRSClient0179 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_ee_patch_server_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.patch.server.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.patch.Resource.class, AdaptiveHttpRequest.class,
                                    AdaptiveMethodFactory.class, HttpPatch.class);
                }
            });

    private static final long serialVersionUID = 21L;

    public JAXRSClient0179() {
        setContextRoot("/jaxrs_jaxrs21_ee_patch_server_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0179().run(args);
    }

    /*
     * @testName: patchTest
     * 
     * @assertion_ids: JAXRS:SPEC:124;
     * 
     */
    @Test
    public void patchTest() throws Fault {
        AdaptiveMethodFactory.getMethodMap().put("PATCH", HttpPatch.class);
        setProperty(Property.REQUEST, buildRequest("PATCH", "patch"));
        setProperty(Property.CONTENT, "patch");
        setProperty(Property.SEARCH_STRING, "patch");
        setProperty(Property.REQUEST_HEADERS,
                buildAccept(MediaType.TEXT_PLAIN_TYPE));
        invoke();
    }

    @Override
    protected HttpRequest createHttpRequest(String requestLine, String host,
            int port) {
        return new AdaptiveHttpRequest(requestLine, host, port);
    };
}
