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

package com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.ssebroadcaster;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.InboundSseEvent;
import jakarta.ws.rs.sse.SseEventSource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.lib.util.TestUtil;
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
public class JAXRSClient0174 extends SSEJAXRSClient0177 {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_jaxrs21_ee_sse_ssebroadcaster_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.ssebroadcaster.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.util.Holder.class,
                                    com.sun.ts.tests.jaxrs.common.util.LinkedHolder.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.SSEMessage.class,
                                    com.sun.ts.tests.jaxrs.jaxrs21.ee.sse.ssebroadcaster.BroadcastResource.class);
                }
            });

    private static final long serialVersionUID = 21L;

    private static final int CLIENTS = 5;

    public JAXRSClient0174() {
        setContextRoot("/jaxrs_jaxrs21_ee_sse_ssebroadcaster_web");
    }

    // QUARKUS: make this a JUnit method
    @BeforeEach
    //  @Override
    //  public void setup(String[] args, Properties p) throws Fault {
    public void setup() throws Fault {
        super.setup(new String[0], new Properties());
        target = ClientBuilder.newClient()
                .target(getAbsoluteUrl("broadcast/register"));
        clients = new BroadCasterClient[CLIENTS];
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0174().run(args);
    }

    @Override
    public void cleanup() throws Fault {
        super.cleanup();
        try {
            for (int i = 0; i != clients.length; i++) {
                System.out.println("cleanup" + i);
                clients[i].close();
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    private BroadCasterClient[] clients;

    /* Run test */
    ///////////////////////////////////////////////////////////////////////////////////////////

    /*
     * @testName: sseBroadcastTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:1216; JAXRS:JAVADOC:1220; JAXRS:JAVADOC:1221;
     * JAXRS:JAVADOC:1222; JAXRS:JAVADOC:1224;
     * 
     * @test_Strategy:
     */
    @Test
    public void sseBroadcastTest() throws Fault {
        int MSG_MAX = 7;
        int wait = 25;

        setProperty(Property.REQUEST, buildRequest(Request.GET, "broadcast/clear"));
        setProperty(Property.SEARCH_STRING, "CLEAR");
        invoke();

        clients = new BroadCasterClient[CLIENTS];
        for (int i = 0; i != CLIENTS; i++) {
            clients[i] = new BroadCasterClient();
            Thread t = new Thread(clients[i]);
            t.start();
        }

        for (int i = 0; i != CLIENTS; i++) {
            while (clients[i].getEvents().size() == 0 && wait-- > 0)
                TestUtil.sleep(100);
        }

        for (int i = 0; i != MSG_MAX; i++) {
            setProperty(Property.CONTENT, SSEMessage.MESSAGE + i);
            setProperty(Property.REQUEST,
                    buildRequest(Request.POST, "broadcast/broadcast"));
            setProperty(Property.SEARCH_STRING, TEST_PROPS.get(Property.CONTENT));
            invoke();
        }

        wait = 25;
        while (clients[0].holder.size() <= MSG_MAX && wait > 0) {
            TestUtil.sleep(200);
            wait--;
        }

        setProperty(Property.REQUEST, buildRequest(Request.GET, "broadcast/close"));
        setProperty(Property.SEARCH_STRING, "CLOSE");
        invoke();

        for (int i = 0; i != CLIENTS; i++) {
            List<String> events = clients[i].getEvents();
            for (String e : events) {
                logMsg("Client", i, "Received message", e);
            }
        }

        for (int i = 0; i != CLIENTS; i++) {
            List<String> events = clients[i].getEvents();
            assertEquals(events.size(), MSG_MAX + 1,
                    "Received unexpected number of events", events.size());
            assertTrue(events.get(0).contains("WELCOME"),
                    "Received unexpected message", events.get(0));
            for (int j = 0; j != MSG_MAX; j++)
                assertEquals(events.get(j + 1), SSEMessage.MESSAGE + j,
                        "Received unexpected message", events.get(j + 1));
        }

        setProperty(Property.REQUEST, buildRequest(Request.GET, "broadcast/check"));
        invoke();
        String response = getResponseBody();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i != CLIENTS; i++) {
            sb.append("SseEventSink number ").append(i).append(" is closed:true");
        }
        sb.append("OnCloseSink has been called:true");
        assertEquals(response, sb.toString(), "Unexpected check message received",
                response);
    }

    private WebTarget target;

    class BroadCasterClient implements Runnable, AutoCloseable {
        MsgHolder holder = new MsgHolder();

        volatile boolean isClosed = false;

        @Override
        public void run() {
            try (SseEventSource source = SseEventSource.target(target).build()) {
                // Quarkus: make sure we stop reconnecting on completion
                source.register(holder::add, this::collectError, this::collectCompletion);
                source.open();
                while (!isClosed) {
                    sleepUntilHolderGetsFilled(holder);
                    System.out.append("WAITING:").println(toString());
                }
            }
        }
        
        private void collectError(Throwable t) {
            t.printStackTrace();
        }

        private void collectCompletion() {
            isClosed = true;
        }
        
        @Override
        public void close() throws Exception {
            isClosed = true;
        }

        public List<String> getEvents() {
            return holder.asList();
        }
    }

    static class MsgHolder extends LinkedHolder<String> {
        public void add(InboundSseEvent value) {
            String data = value.readData();
            super.add(data);
            System.out.println("Received" + data);
        }
    }
}
