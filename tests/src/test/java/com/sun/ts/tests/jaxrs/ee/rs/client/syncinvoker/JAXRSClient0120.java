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

package com.sun.ts.tests.jaxrs.ee.rs.client.syncinvoker;

import java.util.function.Supplier;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.SyncInvoker;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jaxrs.common.client.JaxrsCommonClient;
import com.sun.ts.tests.jaxrs.common.client.JdkLoggingFilter;

import io.quarkus.test.QuarkusUnitTest;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0120 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_rs_client_syncinvoker_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.client.syncinvoker.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.common.impl.TRACE.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.client.syncinvoker.Resource.class);
                }
            });

    private static final long serialVersionUID = 4942772066511819511L;

    protected long millis;

    public JAXRSClient0120() {
        setContextRoot("/jaxrs_ee_rs_client_syncinvoker_web/resource");
    }

    public static void main(String[] args) {
        new JAXRSClient0120().run(args);
    }

    static final String[] METHODS = { "DELETE", "GET", "OPTIONS" };

    static final String[] ENTITY_METHODS = { "PUT", "POST" };

    /* Run test */
    // --------------------------------------------------------------------
    // ---------------------- DELETE --------------------------------------
    // --------------------------------------------------------------------
    /*
     * @testName: deleteTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:541;
     * 
     * @test_Strategy: Invoke HTTP DELETE method for the current request
     * synchronously.
     */
    public Response deleteTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("delete");
        Response response = sync.delete();
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: deleteThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:541;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void deleteThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.delete();
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: deleteWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:543;
     * 
     * @test_Strategy: Invoke HTTP DELETE method for the current request
     * synchronously.
     */
    public String deleteWithStringClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("delete");
        String response = sync.delete(String.class);
        assertResponseString(response, "delete");
        return response;
    }

    /*
     * @testName: deleteWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:543;
     * 
     * @test_Strategy: Invoke HTTP DELETE method for the current request
     * synchronously.
     */
    public Response deleteWithResponseClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("delete");
        Response response = sync.delete(Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: deleteWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:543;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void deleteWithStringClassThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.delete(String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: deleteWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:543;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void deleteWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("deletenotok");
                sync.delete(String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: deleteWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:543;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void deleteWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("deletenotok");
        Response response = sync.delete(Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: deleteWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:546;
     * 
     * @test_Strategy: Invoke HTTP DELETE method for the current request
     * synchronously.
     */
    public String deleteWithGenericTypeStringTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("delete");
        GenericType<String> generic = createGeneric(String.class);
        String response = sync.delete(generic);
        assertResponseString(response, "delete");
        return response;
    }

    /*
     * @testName: deleteWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:546;
     * 
     * @test_Strategy: Invoke HTTP DELETE method for the current request
     * synchronously.
     */
    public Response deleteWithGenericTypeResponseTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("delete");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.delete(generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: deleteWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:546;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void deleteWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                GenericType<String> generic = createGeneric(String.class);
                sync.delete(generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: deleteWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:546;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void deleteWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("deletenotok");
                GenericType<String> generic = createGeneric(String.class);
                sync.delete(generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: deleteWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:546;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.delete( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void deleteWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("deletenotok");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.delete(generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ------------------------------------------------------------------
    // ---------------------------GET------------------------------------
    // ------------------------------------------------------------------
    /*
     * @testName: getTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:549;
     * 
     * @test_Strategy: Invoke HTTP GET method for the current request
     * synchronously.
     */
    public Response getTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("get");
        Response response = sync.get();
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: getThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:549;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void getThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.get();
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: getWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:551;
     * 
     * @test_Strategy: Invoke HTTP GET method for the current request
     * synchronously.
     */
    public String getWithStringClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("get");
        String response = sync.get(String.class);
        assertResponseString(response, "get");
        return response;
    }

    /*
     * @testName: getWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:551;
     * 
     * @test_Strategy: Invoke HTTP GET method for the current request
     * synchronously.
     */
    public Response getWithResponseClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("get");
        Response response = sync.get(Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: getWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:551;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void getWithStringClassThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.get(String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: getWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:551;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void getWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("getnotok");
                sync.get(String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: getWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:551;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void getWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("getnotok");
        Response response = sync.get(Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: getWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:554;
     * 
     * @test_Strategy: Invoke HTTP GET method for the current request
     * synchronously.
     */
    public String getWithGenericTypeStringTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("get");
        GenericType<String> generic = createGeneric(String.class);
        String response = sync.get(generic);
        assertResponseString(response, "get");
        return response;
    }

    /*
     * @testName: getWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:554;
     * 
     * @test_Strategy: Invoke HTTP GET method for the current request
     * synchronously.
     */
    public Response getWithGenericTypeResponseTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("get");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.get(generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: getWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:554;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( GenericType ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void getWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                GenericType<String> generic = createGeneric(String.class);
                sync.get(generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: getWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:554;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( GenericType ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void getWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("getnotok");
                GenericType<String> generic = createGeneric(String.class);
                sync.get(generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: getWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:554;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.get( GenericType ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void getWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("getnotok");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.get(generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ------------------------------------------------------------------
    // ---------------------------HEAD-----------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: headTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:557;
     * 
     * @test_Strategy: Invoke HTTP HEAD method for the current request
     * synchronously.
     */
    public Response headTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("head");
        Response response = sync.head();
        Status status = Status.fromStatusCode(response.getStatus());
        assertFault(status == Status.OK || status == Status.NO_CONTENT,
                "Incorrect status for head received");
        return response;
    }

    /*
     * @testName: headThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:557;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.head throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void headThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.head();
            }
        };
        assertProcessingException(run);
    }

    // ------------------------------------------------------------------
    // ---------------------------METHOD-----------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: methodTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:559;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodTest() throws Fault {
        Response response = null;
        for (String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            response = sync.method(method);
            assertResponseOk(response);
        }
    }

    /*
     * @testName: methodThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:559;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void methodThrowsProcessingExceptionTest() throws Fault {
        for (final String method : METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    sync.method(method);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName: methodWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:561;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithStringClassTest() throws Fault {
        String response = null;
        for (String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            response = sync.method(method, String.class);
            assertResponseString(response, method.toLowerCase());
        }
    }

    /*
     * @testName: methodWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:561;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithResponseClassTest() throws Fault {
        Response response = null;
        for (String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            response = sync.method(method, Response.class);
            assertResponseOk(response);
        }
    }

    /*
     * @testName: methodWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:561;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void methodWithStringClassThrowsProcessingExceptionTest()
            throws Fault {
        for (final String method : METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    sync.method(method, String.class);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName: methodWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:561;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void methodWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        for (final String method : METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerForMethod(
                            method.toLowerCase() + "notok");
                    sync.method(method, String.class);
                }
            };
            assertWebApplicationException(run);
        }
    }

    /*
     * @testName: methodWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:561;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void methodWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        for (final String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(
                    method.toLowerCase() + "notok");
            Response response = sync.method(method, Response.class);
            assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
        }
    }

    /*
     * @testName: methodWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:564;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithGenericTypeStringTest() throws Fault {
        GenericType<String> generic = createGeneric(String.class);
        String response = null;
        for (String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            response = sync.method(method, generic);
            assertResponseString(response, method.toLowerCase());
        }
    }

    /*
     * @testName: methodWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:564;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithGenericTypeResponseTest() throws Fault {
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = null;
        for (String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            response = sync.method(method, generic);
            assertResponseOk(response);
        }
    }

    /*
     * @testName: methodWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:564;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void methodWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        final GenericType<String> generic = createGeneric(String.class);
        for (final String method : METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    sync.method(method, generic);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName: methodWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:564;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(GenericType<String>)
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void methodWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        final GenericType<String> generic = createGeneric(String.class);
        for (final String method : METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerForMethod(
                            method.toLowerCase() + "notok");
                    sync.method(method, generic);
                }
            };
            assertWebApplicationException(run);
        }
    }

    /*
     * @testName: methodWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:564;
     * 
     * @test_Strategy:
     * jakarta.ws.rs.client.SyncInvoker.method(GenericType<Response>) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void methodWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = null;
        for (final String method : METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(
                    method.toLowerCase() + "notok");
            response = sync.method(method, generic);
            assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
        }
    }

    /*
     * @testName: methodWithEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:567;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    public Response methodWithEntityTest() throws Fault {
        Response response = null;
        for (String method : ENTITY_METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            Entity<String> entity = createEntity(method.toLowerCase());
            response = sync.method(method, entity);
            assertResponseOk(response);
        }
        return response;
    }

    /*
     * @testName: methodWithEntityThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:567;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity)
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void methodWithEntityThrowsProcessingExceptionTest() throws Fault {
        final Entity<String> entity = createEntity("entity");
        for (final String method : ENTITY_METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    sync.method(method, entity);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName: methodWithStringClassWithEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:569;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    public String methodWithStringClassWithEntityTest() throws Fault {
        String response = null;
        for (String method : ENTITY_METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            Entity<String> entity = createEntity(method.toLowerCase());
            response = sync.method(method, entity, String.class);
            assertResponseString(response, method.toLowerCase());
        }
        return response;
    }

    /*
     * @testName: methodWithResponseClassWithEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:569;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    public String methodWithResponseClassWithEntityTest() throws Fault {
        String response = null;
        for (String method : ENTITY_METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            Entity<String> entity = createEntity(method.toLowerCase());
            response = sync.method(method, entity, String.class);
            assertResponseString(response, method.toLowerCase());
        }
        return response;
    }

    /*
     * @testName: methodWithStringClassWithEntityThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:569;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * Class) throws ProcessingException in case the invocation failed.
     */
    @Test
    public void methodWithStringClassWithEntityThrowsProcessingExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    Entity<String> entity = createEntity(method.toLowerCase());
                    sync.method(method, entity, String.class);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName: methodWithStringClassWithEntityThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:569;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * Class) throws WebApplicationException - in case the response status code of
     * the response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void methodWithStringClassWithEntityThrowsWebApplicationExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SyncInvoker sync = createSyncInvokerForMethod(
                            method.toLowerCase() + "notok");
                    Entity<String> entity = createEntity(method.toLowerCase());
                    sync.method(method, entity, String.class);
                }
            };
            assertWebApplicationException(run);
        }
    }

    /*
     * @testName:
     * methodWithResponseClassWithEntityThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:569;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * Class) throws WebApplicationException - in case the response status code of
     * the response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void methodWithResponseClassWithEntityThrowsNoWebApplicationExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            SyncInvoker sync = createSyncInvokerForMethod(
                    method.toLowerCase() + "notok");
            Entity<String> entity = createEntity(method.toLowerCase());
            Response response = sync.method(method, entity, Response.class);
            assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
        }
    }

    /*
     * @testName: methodWithGenericTypeStringWithEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:572;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithGenericTypeStringWithEntityTest() throws Fault {
        String response = null;
        for (String method : ENTITY_METHODS) {
            GenericType<String> generic = createGeneric(String.class);
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            Entity<String> entity = createEntity(method.toLowerCase());
            response = sync.method(method, entity, generic);
            assertResponseString(response, method.toLowerCase());
        }
    }

    /*
     * @testName: methodWithGenericTypeResponseWithEntityTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:572;
     * 
     * @test_Strategy: Invoke an arbitrary method for the current request
     * synchronously.
     */
    @Test
    public void methodWithGenericTypeResponseWithEntityTest() throws Fault {
        Response response = null;
        for (String method : ENTITY_METHODS) {
            GenericType<Response> generic = createGeneric(Response.class);
            SyncInvoker sync = createSyncInvokerForMethod(method.toLowerCase());
            Entity<String> entity = createEntity(method.toLowerCase());
            response = sync.method(method, entity, generic);
            assertResponseOk(response);
        }
    }

    /*
     * @testName:
     * methodWithGenericTypeStringWithEntityThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:572;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * GenericType) throws ProcessingException in case the invocation failed.
     */
    @Test
    public void methodWithGenericTypeStringWithEntityThrowsProcessingExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    GenericType<String> generic = createGeneric(String.class);
                    SyncInvoker sync = createSyncInvokerWrongUrl();
                    Entity<String> entity = createEntity(method);
                    sync.method(method, entity, generic);
                }
            };
            assertProcessingException(run);
        }
    }

    /*
     * @testName:
     * methodWithGenericTypeStringWithEntityThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:572;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * GenericType) throws WebApplicationException - in case the response status
     * code of the response returned by the server is not successful and the
     * specified response type is not Response.
     */
    @Test
    public void methodWithGenericTypeStringWithEntityThrowsWebApplicationExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    GenericType<String> generic = createGeneric(String.class);
                    SyncInvoker sync = createSyncInvokerForMethod(
                            method.toLowerCase() + "notok");
                    Entity<String> entity = createEntity(method);
                    sync.method(method, entity, generic);
                }
            };
            assertWebApplicationException(run);
        }
    }

    /*
     * @testName:
     * methodWithGenericTypeResponseWithEntityThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:572;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.method(String, Entity,
     * GenericType) throws WebApplicationException - in case the response status
     * code of the response returned by the server is not successful and the
     * specified response type is not Response.
     */
    @Test
    public void methodWithGenericTypeResponseWithEntityThrowsNoWebApplicationExceptionTest()
            throws Fault {
        for (final String method : ENTITY_METHODS) {
            GenericType<Response> generic = createGeneric(Response.class);
            SyncInvoker sync = createSyncInvokerForMethod(
                    method.toLowerCase() + "notok");
            Entity<String> entity = createEntity(method);
            Response response = sync.method(method, entity, generic);
            assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
        }
    }

    // ------------------------------------------------------------------
    // ---------------------------OPTIONS--------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: optionsTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:575;
     * 
     * @test_Strategy: Invoke HTTP options method for the current request
     * synchronously.
     */
    public Response optionsTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("options");
        Response response = sync.options();
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: optionsThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:575;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void optionsThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.options();
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: optionsWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:577;
     * 
     * @test_Strategy: Invoke HTTP options method for the current request
     * synchronously.
     */
    public String optionsWithStringClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("options");
        String response = sync.options(String.class);
        assertResponseString(response, "options");
        return response;
    }

    /*
     * @testName: optionsWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:577;
     * 
     * @test_Strategy: Invoke HTTP options method for the current request
     * synchronously.
     */
    public Response optionsWithResponseClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("options");
        Response response = sync.options(Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: optionsWithStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:577;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void optionsWithStringThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.options(String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: optionsWithStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:577;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void optionsWithStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("optionsnotok");
                sync.options(String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: optionsWithResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:577;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void optionsWithResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("optionsnotok");
        Response response = sync.options(Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: optionsWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:580;
     * 
     * @test_Strategy: Invoke HTTP options method for the current request
     * synchronously.
     */
    public String optionsWithGenericTypeStringTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("options");
        GenericType<String> generic = createGeneric(String.class);
        String response = sync.options(generic);
        assertResponseString(response, "options");
        return response;
    }

    /*
     * @testName: optionsWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:580;
     * 
     * @test_Strategy: Invoke HTTP options method for the current request
     * synchronously.
     */
    public Response optionsWithGenericTypeResponseTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("options");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.options(generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: optionsWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:580;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( GenericType )
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void optionsWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                GenericType<String> generic = createGeneric(String.class);
                sync.options(generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: optionsWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:580;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void optionsWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("optionsnotok");
                GenericType<String> generic = createGeneric(String.class);
                sync.options(generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName:
     * optionsWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:580;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.options( GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void optionsWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("optionsnotok");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.options(generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ------------------------------------------------------------------
    // ---------------------------POST-----------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: postTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:583;
     * 
     * @test_Strategy: Invoke HTTP post method for the current request
     * synchronously.
     */
    public Response postTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("post");
        Entity<String> entity = createEntity("post");
        Response response = sync.post(entity);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: postThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:583;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post(Entity) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void postThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("post");
                sync.post(entity);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: postWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:585;
     * 
     * @test_Strategy: Invoke HTTP post method for the current request
     * synchronously.
     */
    public String postWithStringClassTest() throws Fault {
        Entity<String> entity = createEntity("post");
        SyncInvoker sync = createSyncInvokerForMethod("post");
        String response = sync.post(entity, String.class);
        assertResponseString(response, "post");
        return response;
    }

    /*
     * @testName: postWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:585;
     * 
     * @test_Strategy: Invoke HTTP post method for the current request
     * synchronously.
     */
    public Response postWithResponseClassTest() throws Fault {
        Entity<String> entity = createEntity("post");
        SyncInvoker sync = createSyncInvokerForMethod("post");
        Response response = sync.post(entity, Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: postWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:585;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void postWithStringClassThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("post");
                sync.post(entity, String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: postWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:585;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void postWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("postnotok");
                Entity<String> entity = createEntity("post");
                sync.post(entity, String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: postWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:585;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void postWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("postnotok");
        Entity<String> entity = createEntity("post");
        Response response = sync.post(entity, Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: postWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:588;
     * 
     * @test_Strategy: Invoke HTTP post method for the current request
     * synchronously.
     */
    public String postWithGenericTypeStringTest() throws Fault {
        GenericType<String> generic = createGeneric(String.class);
        Entity<String> entity = createEntity("post");
        SyncInvoker sync = createSyncInvokerForMethod("post");
        String response = sync.post(entity, generic);
        assertResponseString(response, "post");
        return response;
    }

    /*
     * @testName: postWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:588;
     * 
     * @test_Strategy: Invoke HTTP post method for the current request
     * synchronously.
     */
    public Response postWithGenericTypeResponseTest() throws Fault {
        GenericType<Response> generic = createGeneric(Response.class);
        Entity<String> entity = createEntity("post");
        SyncInvoker sync = createSyncInvokerForMethod("post");
        Response response = sync.post(entity, generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: postWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:588;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, GenericType )
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void postWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("post");
                GenericType<String> generic = createGeneric(String.class);
                sync.post(entity, generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: postWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:588;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void postWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("postnotok");
                Entity<String> entity = createEntity("post");
                GenericType<String> generic = createGeneric(String.class);
                sync.post(entity, generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: postWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:588;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.post( Entity, GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void postWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("postnotok");
        Entity<String> entity = createEntity("post");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.post(entity, generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ------------------------------------------------------------------
    // ---------------------------PUT -----------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: putTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:591;
     * 
     * @test_Strategy: Invoke HTTP PUT method for the current request
     * synchronously.
     */
    public Response putTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("put");
        Entity<String> entity = createEntity("put");
        Response response = sync.put(entity);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: putThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:591;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put(Entity) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void putThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("put");
                sync.put(entity);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: putWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:593;
     * 
     * @test_Strategy: Invoke HTTP put method for the current request
     * synchronously.
     */
    public String putWithStringClassTest() throws Fault {
        Entity<String> entity = createEntity("put");
        SyncInvoker sync = createSyncInvokerForMethod("put");
        String response = sync.put(entity, String.class);
        assertResponseString(response, "put");
        return response;
    }

    /*
     * @testName: putWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:593;
     * 
     * @test_Strategy: Invoke HTTP put method for the current request
     * synchronously.
     */
    public Response putWithResponseClassTest() throws Fault {
        Entity<String> entity = createEntity("put");
        SyncInvoker sync = createSyncInvokerForMethod("put");
        Response response = sync.put(entity, Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: putWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:593;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put( Entity, Class ) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void putWithStringClassThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("put");
                sync.put(entity, String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: putWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:593;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put( Entity, Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void putWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("putnotok");
                Entity<String> entity = createEntity("put");
                sync.put(entity, String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: putWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:593;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put( Entity, Class ) throws
     * WebApplicationException - in case the response status code of the response
     * returned by the server is not successful and the specified response type is
     * not Response.
     */
    @Test
    public void putWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("putnotok");
        Entity<String> entity = createEntity("put");
        Response response = sync.put(entity, Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: putWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:596;
     * 
     * @test_Strategy: Invoke HTTP put method for the current request
     * synchronously.
     */
    public String putWithGenericTypeStringTest() throws Fault {
        GenericType<String> generic = createGeneric(String.class);
        Entity<String> entity = createEntity("put");
        SyncInvoker sync = createSyncInvokerForMethod("put");
        String response = sync.put(entity, generic);
        assertResponseString(response, "put");
        return response;
    }

    /*
     * @testName: putWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:596;
     * 
     * @test_Strategy: Invoke HTTP put method for the current request
     * synchronously.
     */
    public Response putWithGenericTypeResponseTest() throws Fault {
        GenericType<Response> generic = createGeneric(Response.class);
        Entity<String> entity = createEntity("put");
        SyncInvoker sync = createSyncInvokerForMethod("put");
        Response response = sync.put(entity, generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: putWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:596;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put( Entity, GenericType )
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void putWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                Entity<String> entity = createEntity("put");
                GenericType<String> generic = createGeneric(String.class);
                sync.put(entity, generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: putWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:596;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.put( Entity, GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void putWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("putnotok");
                Entity<String> entity = createEntity("put");
                GenericType<String> generic = createGeneric(String.class);
                sync.put(entity, generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: putWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:596;
     * 
     * @test_Strategy: throws WebApplicationException - in case the response
     * status code of the response returned by the server is not successful and
     * the specified response type is not Response.
     */
    @Test
    public void putWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("putnotok");
        Entity<String> entity = createEntity("put");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.put(entity, generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ------------------------------------------------------------------
    // ---------------------------TRACE -----------------------------------
    // ------------------------------------------------------------------

    /*
     * @testName: traceTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:599;
     * 
     * @test_Strategy: Invoke HTTP trace method for the current request
     * synchronously.
     */
    public Response traceTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("trace");
        Response response = sync.trace();
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: traceThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:599;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace(Entity) throws
     * ProcessingException in case the invocation failed.
     */
    @Test
    public void traceThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.trace();
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: traceWithStringClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:601;
     * 
     * @test_Strategy: Invoke HTTP trace method for the current request
     * synchronously.
     */
    public String traceWithStringClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("trace");
        String response = sync.trace(String.class);
        assertResponseString(response, "trace");
        return response;
    }

    /*
     * @testName: traceWithResponseClassTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:601;
     * 
     * @test_Strategy: Invoke HTTP trace method for the current request
     * synchronously.
     */
    public Response traceWithResponseClassTest() throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("trace");
        Response response = sync.trace(Response.class);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: traceWithStringClassThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:601;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, Class )
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void traceWithStringClassThrowsProcessingExceptionTest() throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                sync.trace(String.class);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: traceWithStringClassThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:601;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, Class )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void traceWithStringClassThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("tracenotok");
                sync.trace(String.class);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: traceWithResponseClassThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:601;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, Class )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void traceWithResponseClassThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("tracenotok");
        Response response = sync.trace(Response.class);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    /*
     * @testName: traceWithGenericTypeStringTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:604;
     * 
     * @test_Strategy: Invoke HTTP trace method for the current request
     * synchronously.
     */
    public String traceWithGenericTypeStringTest() throws Fault {
        GenericType<String> generic = createGeneric(String.class);
        SyncInvoker sync = createSyncInvokerForMethod("trace");
        String response = sync.trace(generic);
        assertResponseString(response, "trace");
        return response;
    }

    /*
     * @testName: traceWithGenericTypeResponseTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:604;
     * 
     * @test_Strategy: Invoke HTTP trace method for the current request
     * synchronously.
     */
    public Response traceWithGenericTypeResponseTest() throws Fault {
        GenericType<Response> generic = createGeneric(Response.class);
        SyncInvoker sync = createSyncInvokerForMethod("trace");
        Response response = sync.trace(generic);
        assertResponseOk(response);
        return response;
    }

    /*
     * @testName: traceWithGenericTypeStringThrowsProcessingExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:604;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, GenericType )
     * throws ProcessingException in case the invocation failed.
     */
    @Test
    public void traceWithGenericTypeStringThrowsProcessingExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerWrongUrl();
                GenericType<String> generic = createGeneric(String.class);
                sync.trace(generic);
            }
        };
        assertProcessingException(run);
    }

    /*
     * @testName: traceWithGenericTypeStringThrowsWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:604;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void traceWithGenericTypeStringThrowsWebApplicationExceptionTest()
            throws Fault {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                SyncInvoker sync = createSyncInvokerForMethod("tracenotok");
                GenericType<String> generic = createGeneric(String.class);
                sync.trace(generic);
            }
        };
        assertWebApplicationException(run);
    }

    /*
     * @testName: traceWithGenericTypeResponseThrowsNoWebApplicationExceptionTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:604;
     * 
     * @test_Strategy: jakarta.ws.rs.client.SyncInvoker.trace( Entity, GenericType )
     * throws WebApplicationException - in case the response status code of the
     * response returned by the server is not successful and the specified
     * response type is not Response.
     */
    @Test
    public void traceWithGenericTypeResponseThrowsNoWebApplicationExceptionTest()
            throws Fault {
        SyncInvoker sync = createSyncInvokerForMethod("tracenotok");
        GenericType<Response> generic = createGeneric(Response.class);
        Response response = sync.trace(generic);
        assertStatusAndLog(response, Status.NOT_ACCEPTABLE);
    }

    // ///////////////////////////////////////////////////////////////////////
    // utility methods

    protected String getUrl(String method) {
        StringBuilder url = new StringBuilder();
        url.append("http://").append(_hostname).append(":").append(_port);
        url.append(getContextRoot()).append("/").append(method);
        return url.toString();
    }

    /**
     * Create SyncInvoker for given resource method and start time
     */
    protected SyncInvoker createSyncInvokerForMethod(String methodName) {
        Client client = ClientBuilder.newClient();
        client.register(new JdkLoggingFilter(false));
        WebTarget target = client.target(getUrl(methodName));
        SyncInvoker sync = target.request();
        return sync;
    }

    protected SyncInvoker createSyncInvokerWrongUrl() {
        _hostname = "tck.cts";
        _port = 888;
        return createSyncInvokerForMethod("wrongurl");
    }

    protected static void assertStatusAndLog(Response response, Status status)
            throws Fault {
        assertFault(response.getStatus() == status.getStatusCode(),
                "Returned unexpected status", response.getStatus());
        String msg = new StringBuilder().append("Returned status ")
                .append(status.getStatusCode()).append(" (").append(status.name())
                .append(")").toString();
        TestUtil.logMsg(msg);
    }

    protected static void assertResponseOk(Response response) throws Fault {
        assertStatusAndLog(response, Status.OK);
    }

    protected static void assertResponseString(String response,
            String expectedValue) throws Fault {
        assertFault(expectedValue.equals(response), "expected value", expectedValue,
                "differes from acquired value", response);
    }

    protected static <T> Entity<T> createEntity(T entity) {
        return Entity.entity(entity, MediaType.WILDCARD_TYPE);
    }

    protected static <T> GenericType<T> createGeneric(Class<T> clazz) {
        return new GenericType<T>(clazz);
    }

    protected static void assertProcessingException(Runnable runnable)
            throws Fault {
        assertException(runnable, ProcessingException.class);
    }

    protected static//
    void assertWebApplicationException(Runnable runnable) throws Fault {
        assertException(runnable, WebApplicationException.class);
    }

    protected static <T extends Exception> void assertException(Runnable runnable,
            Class<T> exception) throws Fault {
        try {
            runnable.run();
        } catch (Exception e) {
            if (exception != null && exception.isInstance(e)) {
                return;
            }
            throw new Fault("unexpected exception", e);
        }
        fault("ProcessingException has not been thrown");
    }

}
