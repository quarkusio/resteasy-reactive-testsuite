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

package com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.sseeventsink;

import java.util.function.Supplier;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jaxrs.QuarkusRest;
import com.sun.ts.tests.jaxrs.common.util.Holder;
import com.sun.ts.tests.jaxrs.common.util.LinkedHolder;
import com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.SSEJAXRSClient0177;
import com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.SSEMessage;

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
public class JAXRSClient0176 extends SSEJAXRSClient0177 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_ee_sse_sseeventsink_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.sseeventsink.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.SSEMessage.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.sseeventsink.StageCheckerResource.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.sseeventsink.CloseResource.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.sseeventsink.MBWCheckResource.class);
                }
            });

    private static final long serialVersionUID = 21L;

    protected long millis = 550L;

    protected int callbackResult = 0;

    protected Throwable callbackException = null;

    protected int sleep = -1;

    public JAXRSClient0176() {
        setContextRoot("/jaxrs_jaxrs21_ee_sse_sseeventsink_web");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0176().run(args);
    }

    /* Run test */
    ///////////////////////////////////////////////////////////////////////////////////////////
    // Default MBW

    /*
     * @testName: stringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void stringTest() throws Fault {
        querySSEEndpointAndAssert("mbw/string");
    }

    /*
     * @testName: charTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void charTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/char");
        assertEquals(String.valueOf(SSEMessage.MESSAGE.charAt(0)),
                holder.get().readData(), "Unexpected message received",
                holder.get().readData());
    }

    /*
     * @testName: intTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void intTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/int");
        assertEquals(Integer.MIN_VALUE, holder.get().readData(Integer.class),
                "Unexpected message received", holder.get().readData());
    }

    /*
     * @testName: doubleTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void doubleTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/double");
        assertEquals(Double.MAX_VALUE, holder.get().readData(Double.class),
                "Unexpected message received", holder.get().readData());
    }

    /*
     * @testName: bytearrayTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void bytearrayTest() throws Fault {
        querySSEEndpointAndAssert("mbw/bytearray");
    }

    /*
     * @testName: readerTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void readerTest() throws Fault {
        querySSEEndpointAndAssert("mbw/reader");
    }

    /*
     * @testName: inputstreamTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void inputstreamTest() throws Fault {
        querySSEEndpointAndAssert("mbw/inputstream");
    }

    /*
     * @testName: fileTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void fileTest() throws Fault {
        querySSEEndpointAndAssert("mbw/file");
    }

    /*
     * @testName: datasourceTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Disabled(QuarkusRest.Unsupported_DataSource)
    @Test
    public void datasourceTest() throws Fault {
        querySSEEndpointAndAssert("mbw/datasource");
    }

    /*
     * @testName: transformSourceTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void transformSourceTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/transformsource");
        logTrace("Received", holder.get());
        assertTrue(holder.get().readData().contains(SSEMessage.MESSAGE),
                "Unexpected message received", holder.get().readData());
    }

    /*
     * @testName: jaxbElementTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Disabled(QuarkusRest.Unsupported_Xml)
    @Test
    public void jaxbElementTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/jaxbelement");
        logTrace("Received", holder.get());
        assertTrue(holder.get().readData().contains(SSEMessage.MESSAGE),
                "Unexpected message received", holder.get().readData());
    }

    /*
     * @testName: multivaluedMapTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Test
    public void multivaluedMapTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("mbw/multivaluedmap");
        logTrace("Received", holder.get());
        assertTrue(holder.get().readData().contains(SSEMessage.MESSAGE),
                "Unexpected message received", holder.get().readData());
    }

    /*
     * @testName: streamingOutputTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1207; JAXRS:JAVADOC:1211; JAXRS:JAVADOC:1207;
     * JAXRS:JAVADOC:1235; JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * JAXRS:JAVADOC:1219; JAXRS:JAVADOC:1213; JAXRS:JAVADOC:1211;
     * JAXRS:JAVADOC:1207;
     * 
     * @test_Strategy:
     */
    @Disabled(QuarkusRest.Unsupported_Streaming_Output)
    @Test
    public void streamingOutputTest() throws Fault {
        querySSEEndpointAndAssert("mbw/streamingoutput");
    }

    /*
     * @testName: closeTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1230; JAXRS:JAVADOC:1231; JAXRS:JAVADOC:1235;
     * JAXRS:JAVADOC:1236; JAXRS:JAVADOC:1232;
     * 
     * @test_Strategy: Subsequent calls have no effect and are ignored. Once the
     * SseEventSink is closed, invoking any method other than this one and
     * isClosed() would result in an IllegalStateException being thrown.
     */
    @Test
    public void closeTest() throws Fault {
        Holder<InboundSseEvent> holder = querySSEEndpoint("close/reset");
        assertEquals(holder.get().readData(), "RESET", "Reset unsuccessful");

        querySSEEndpointAndAssert("close/send");

        boolean closed = false;
        int cnt = 0;
        while (!closed && cnt < 20) {
            setProperty(Property.REQUEST, buildRequest(Request.GET, "close/closed"));
            setProperty(Property.REQUEST_HEADERS,
                    buildAccept(MediaType.TEXT_PLAIN_TYPE));
            setProperty(Property.EXPECTED_HEADERS,
                    buildContentType(MediaType.TEXT_PLAIN_TYPE));
            invoke();
            closed = getResponse().readEntity(Boolean.class);
            TestUtil.sleepMsec(200);
            cnt++;
        }

        holder = querySSEEndpoint("close/check");
        String msg = holder.get().readData();
        assertEquals(msg, "CHECK", "check unsuccessful:", msg);
    }

    /*
     * @testName: sseStageCheckTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1232; JAXRS:JAVADOC:1217;
     * 
     * @test_Strategy: check the stage is ever done
     */
    @Test
    public void sseStageCheckTest() throws Fault {
        LinkedHolder<InboundSseEvent> holder = new LinkedHolder<>();
        WebTarget target = ClientBuilder.newClient()
                .target(getAbsoluteUrl("stage"));

        try (SseEventSource source = SseEventSource.target(target).build()) {
            source.register(holder::add);
            source.open();
            sleepUntilHolderGetsFilled(holder);
            assertNotNull(holder.get(), "No message received");
            assertTrue(holder.get(0).readData().contains(SSEJAXRSClient0177.MESSAGE),
                    holder.get(0), "does not contain expected", SSEJAXRSClient0177.MESSAGE);
            if (!holder.get(1).readData().contains(StageCheckerResource.DONE)) {
                sleepUntilHolderGetsFilled(holder);
            }
        }
        assertNotNull(holder.get(1), "No message received");
        assertEquals(holder.get(1).readData(), StageCheckerResource.DONE,
                "The stage has not ever been done, message is",
                holder.get().readData());
    }

}
