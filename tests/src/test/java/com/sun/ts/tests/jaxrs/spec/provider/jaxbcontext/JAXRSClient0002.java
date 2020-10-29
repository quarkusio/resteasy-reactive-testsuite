/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
@Disabled(QuarkusRest.Unsupported_Xml)
public class JAXRSClient0002 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_provider_jaxbcontext_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.SomeJaxbContext.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.TckJaxbProvider.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.SomeUnmarshaller.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.JaxbContextProvider.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.jaxbcontext.SomeMarshaller.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0002() {
        setContextRoot("/jaxrs_spec_provider_jaxbcontext_web/resource");
    }

    private void setPropertyAndInvoke(String resourceMethod) throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
        setProperty(Property.REQUEST_HEADERS,
                buildContentType(MediaType.APPLICATION_XML_TYPE));
        setProperty(Property.SEARCH_STRING, SomeUnmarshaller.class.getSimpleName());
        setProperty(Property.SEARCH_STRING, SomeMarshaller.class.getSimpleName());
        setProperty(Property.CONTENT, "anything");
        invoke();
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0002().run(args);
    }

    /* Run test */
    /*
     * @testName: readWriteProviderTest
     * 
     * @assertion_ids: JAXRS:SPEC:34
     * 
     * @test_Strategy: The implementation-supplied entity provider(s) for
     * javax.xml.bind.JAXBElement and application supplied JAXB classes MUST use
     * JAXBContext instances provided by application-supplied context resolvers,
     * see Section 4.3.
     */
    @Test
    public void readWriteProviderTest() throws Fault {
        setPropertyAndInvoke("jaxb");
    }

}
