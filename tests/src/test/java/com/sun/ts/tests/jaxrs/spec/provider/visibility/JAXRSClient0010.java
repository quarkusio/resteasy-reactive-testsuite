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

package com.sun.ts.tests.jaxrs.spec.provider.visibility;

import java.util.function.Supplier;

import javax.ws.rs.core.MediaType;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.tests.jaxrs.common.JAXRSCommonClient;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */

@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0010 extends JAXRSCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_provider_visibility_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.HolderClass.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.HolderResolver.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.VisibilityException.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.DummyWriter.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.VisibilityExceptionMapper.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.Resource.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.DummyClass.class,
                                    com.sun.ts.tests.jaxrs.spec.provider.visibility.StringReader.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0010() {
        setContextRoot("/jaxrs_spec_provider_visibility_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0010().run(args);
    }

    /* Run test */
    /*
     * @testName: contextResolverTest
     * 
     * @assertion_ids: JAXRS:SPEC:27; JAXRS:SPEC:28;
     * 
     * @test_Strategy: Provider classes are instantiated by the JAX-RS runtime and
     * MUST have a public constructor for which the JAX-RS runtime can provide all
     * parameter values.
     * 
     * If more than one public constructor can be used then an implementation MUST
     * use the one with the most parameters
     */
    @Test
    public void contextResolverTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "contextresolver"));
        setProperty(Property.SEARCH_STRING, HolderClass.OK);
        invoke();
    }

    /*
     * @testName: exceptionMapperTest
     * 
     * @assertion_ids: JAXRS:SPEC:27; JAXRS:SPEC:28;
     * 
     * @test_Strategy: Provider classes are instantiated by the JAX-RS runtime and
     * MUST have a public constructor for which the JAX-RS runtime can provide all
     * parameter values.
     * 
     * If more than one public constructor can be used then an implementation MUST
     * use the one with the most parameters
     */
    @Test
    public void exceptionMapperTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "exceptionmapper"));
        setProperty(Property.SEARCH_STRING, HolderClass.OK);
        invoke();
    }

    /*
     * @testName: bodyWriterTest
     * 
     * @assertion_ids: JAXRS:SPEC:27; JAXRS:SPEC:28;
     * 
     * @test_Strategy: Provider classes are instantiated by the JAX-RS runtime and
     * MUST have a public constructor for which the JAX-RS runtime can provide all
     * parameter values.
     * 
     * If more than one public constructor can be used then an implementation MUST
     * use the one with the most parameters
     */
    @Test
    public void bodyWriterTest() throws Fault {
        setProperty(Property.REQUEST, buildRequest(Request.GET, "bodywriter"));
        setProperty(Property.SEARCH_STRING, HolderClass.OK);
        invoke();
    }

    /*
     * @testName: bodyReaderTest
     * 
     * @assertion_ids: JAXRS:SPEC:27; JAXRS:SPEC:28;
     * 
     * @test_Strategy: Provider classes are instantiated by the JAX-RS runtime and
     * MUST have a public constructor for which the JAX-RS runtime can provide all
     * parameter values.
     * 
     * If more than one public constructor can be used then an implementation MUST
     * use the one with the most parameters
     */
    @Test
    public void bodyReaderTest() throws Fault {
        MediaType type = new MediaType("text", "tck");
        setProperty(Property.REQUEST, buildRequest(Request.POST, "bodyreader"));
        setProperty(Property.CONTENT, "text");
        setProperty(Property.REQUEST_HEADERS, buildContentType(type));
        setProperty(Property.SEARCH_STRING, HolderClass.OK);
        invoke();
    }

}
