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

package com.sun.ts.tests.jaxrs.ee.rs.core.responsebuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

import jakarta.ws.rs.core.GenericType;
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
@org.junit.jupiter.api.extension.ExtendWith(com.sun.ts.tests.TckExtention.class)
public class JAXRSClient0130 extends JaxrsCommonClient {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest().setFlatClassPath(true)
            .overrideConfigKey("quarkus.rest.single-default-produces", "false")
            .overrideConfigKey("quarkus.rest.fail-on-duplicate", "false")
            .overrideConfigKey("quarkus.rest.default-produces", "false")
            .overrideConfigKey("quarkus.http.root-path", "/jaxrs_ee_core_responsebuilder_web")
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(
                                    com.sun.ts.tests.jaxrs.ee.rs.core.responsebuilder.TSAppConfig.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.responsebuilder.AnnotatedClass.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.responsebuilder.DateContainerReaderWriter.class,
                                    com.sun.ts.tests.jaxrs.ee.rs.core.responsebuilder.Resource.class,
                                    DateClientReaderWriter.class);
                }
            });

    private static final long serialVersionUID = 1L;

    public JAXRSClient0130() {
        setContextRoot("/jaxrs_ee_core_responsebuilder_web/resource");
    }

    /**
     * Entry point for different-VM execution. It should delegate to method
     * run(String[], PrintWriter, PrintWriter), and this method should not contain
     * any test configuration.
     */
    public static void main(String[] args) {
        new JAXRSClient0130().run(args);
    }

    /* Run test */

    /*
     * @testName: entityObjectTest
     * 
     * @assertion_ids: JAXRS:JAVADOC:879;
     * 
     * @test_Strategy: Set the message entity content encoding.
     */
    @Test
    public void entityObjectTest() throws Fault {
        Date date = Calendar.getInstance().getTime();
        String entity = DateContainerReaderWriter.dateToString(date);
        StringBuilder sb = new StringBuilder();
        DateClientReaderWriter rw = new DateClientReaderWriter(sb);
        addProvider(rw);

        setProperty(Property.REQUEST, buildRequest(Request.POST, "entity"));
        setProperty(Property.CONTENT, entity);
        invoke();

        Response response = getResponse();
        Date responseDate = response.readEntity(Date.class);
        assertFault(date.equals(responseDate), "entity date", date,
                "differs from acquired", responseDate);

        Annotation[] annotations = AnnotatedClass.class.getAnnotations();
        for (Annotation annotation : annotations) {
            String name = annotation.annotationType().getName();
            assertFault(sb.toString().contains(name), sb, "does not contain", name,
                    ", annotations not passed to MessageBodyWriter?");
        }
    }

    // ////////////////////////////////////////////////////////////////////
    protected <T> GenericType<T> generic(Class<T> clazz) {
        return new GenericType<T>(clazz);
    }

    protected String readLine(Reader reader) throws Fault {
        String line = null;
        BufferedReader buffered = new BufferedReader(reader);
        try {
            line = buffered.readLine();
        } catch (IOException e) {
            try {
                buffered.close();
            } catch (IOException ie) {
            }
            throw new Fault(e);
        }
        return line;
    }
}
