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

package com.sun.ts.tests.jaxrs.spec.filter.lastvalue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
 * Test the interceptor is called when any entity provider is called
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0020 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_spec_filter_lastvalue_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.FirstWriterInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.SecondWriterInterceptor.class,
                                    com.sun.ts.tests.jaxrs.common.util.JaxrsUtil.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.LinkedListEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.FirstReaderInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.ArrayListEntityProvider.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.SecondReaderInterceptor.class,
                                    com.sun.ts.tests.jaxrs.spec.filter.lastvalue.Resource.class);
                }
            });

    private static final long serialVersionUID = 1405911734696409993L;

    public static final String plaincontent = JAXRSClient0020.class.getName();

    public JAXRSClient0020() {
        setContextRoot("/jaxrs_spec_filter_lastvalue_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0020().run(args);
    }

    /* Run test */

    /*
     * @testName: readerContextOnContainerTest
     * 
     * @assertion_ids: JAXRS:SPEC:86;
     * 
     * @test_Strategy: JAX-RS implementations MUST use the last parameter values
     * set in the context object when calling the wrapped methods
     * MessageBodyReader.readFrom and MessageBodyWrite.writeTo.
     */
    @Test
    public void readerContextOnContainerTest() throws Fault {
        addInterceptors(FirstReaderInterceptor.class);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "postlist"));
        setRequestContentEntity(plaincontent);
        setProperty(Property.SEARCH_STRING,
                SecondReaderInterceptor.class.getName());
        setProperty(Property.SEARCH_STRING,
                SecondReaderInterceptor.class.getAnnotations()[0].annotationType()
                        .getName());
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
        logMsg("Last values set in contexts were used as expected");
    }

    /*
     * @testName: readerContextOnClientTest
     * 
     * @assertion_ids: JAXRS:SPEC:86;
     * 
     * @test_Strategy: JAX-RS implementations MUST use the last parameter values
     * set in the context object when calling the wrapped methods
     * MessageBodyReader.readFrom and MessageBodyWrite.writeTo.
     */
    @Test
    public void readerContextOnClientTest() throws Fault {
        addProvider(FirstReaderInterceptor.class);
        addProvider(SecondReaderInterceptor.class);
        addProvider(ArrayListEntityProvider.class);
        addProvider(LinkedListEntityProvider.class);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "poststring"));
        setRequestContentEntity(plaincontent);
        invoke();
        Response response = getResponse();
        response.getHeaders().add(Resource.HEADERNAME,
                FirstReaderInterceptor.class.getName());
        @SuppressWarnings("unchecked")
        List<String> list = response.readEntity(List.class);
        assertTrue(ArrayList.class.isInstance(list),
                "Entity is not instanceof ArrayList");
        String entity = list.get(0);
        assertContains(entity, SecondReaderInterceptor.class.getName(),
                "Second value in reader interceptor is unexpectedly not used");
        assertContains(entity,
                SecondReaderInterceptor.class.getAnnotations()[0].annotationType()
                        .getName(),
                "Second value in reader interceptor is unexpectedly not used");
        assertContains(entity, MediaType.TEXT_PLAIN,
                "Second value in reader interceptor is unexpectedly not used");
        logMsg("Last values set in contexts were used as expected");
    }

    /*
     * @testName: writerContextOnContainerTest
     * 
     * @assertion_ids: JAXRS:SPEC:86;
     * 
     * @test_Strategy: JAX-RS implementations MUST use the last parameter values
     * set in the context object when calling the wrapped methods
     * MessageBodyReader.readFrom and MessageBodyWrite.writeTo.
     */
    @Test
    public void writerContextOnContainerTest() throws Fault {
        addInterceptors(FirstWriterInterceptor.class);
        setProperty(Property.REQUEST, buildRequest(Request.GET, "getlist"));
        setProperty(Property.SEARCH_STRING,
                SecondWriterInterceptor.class.getName());
        setProperty(Property.SEARCH_STRING,
                SecondWriterInterceptor.class.getAnnotations()[0].annotationType()
                        .getName());
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
        logMsg("Last values set in contexts were used as expected");
    }

    /*
     * @testName: writerContextOnClientTest
     * 
     * @assertion_ids: JAXRS:SPEC:86;
     * 
     * @test_Strategy: JAX-RS implementations MUST use the last parameter values
     * set in the context object when calling the wrapped methods
     * MessageBodyReader.readFrom and MessageBodyWrite.writeTo.
     */
    @Test
    public void writerContextOnClientTest() throws Fault {
        addProvider(FirstReaderInterceptor.class);
        addProvider(SecondReaderInterceptor.class);
        addProvider(ArrayListEntityProvider.class);
        addProvider(LinkedListEntityProvider.class);
        ArrayList<String> list = new ArrayList<String>();
        list.add(plaincontent);
        setRequestContentEntity(list);
        addInterceptors(FirstWriterInterceptor.class);
        setProperty(Property.REQUEST, buildRequest(Request.POST, "poststring"));
        setProperty(Property.SEARCH_STRING,
                SecondWriterInterceptor.class.getName());
        setProperty(Property.SEARCH_STRING,
                SecondWriterInterceptor.class.getAnnotations()[0].annotationType()
                        .getName());
        setProperty(Property.SEARCH_STRING, MediaType.TEXT_PLAIN);
        invoke();
        logMsg("Last values set in contexts were used as expected");
    }

    // //////////////////////////////////////////////////////////////////////
    private void addInterceptors(Class<?> clazz) {
        setProperty(Property.REQUEST_HEADERS,
                Resource.HEADERNAME + ":" + clazz.getName());
    }
}
