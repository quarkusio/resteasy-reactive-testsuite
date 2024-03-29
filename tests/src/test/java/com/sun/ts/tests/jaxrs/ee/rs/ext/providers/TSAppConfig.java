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

package com.sun.ts.tests.jaxrs.ee.rs.ext.providers;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.core.Application;

import com.sun.ts.tests.jaxrs.ee.rs.core.application.ApplicationHolderSingleton;
import com.sun.ts.tests.jaxrs.ee.rs.core.application.ApplicationServlet;
import com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumContextResolver;
import com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.EnumProvider;
import com.sun.ts.tests.jaxrs.ee.rs.ext.contextresolver.TextPlainEnumContextResolver;
import com.sun.ts.tests.jaxrs.ee.rs.ext.exceptionmapper.AnyExceptionExceptionMapper;
import com.sun.ts.tests.jaxrs.ee.rs.ext.exceptionmapper.IOExceptionExceptionMapper;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityMessageReader;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.EntityMessageWriter;
import com.sun.ts.tests.jaxrs.ee.rs.ext.messagebodyreaderwriter.ReadableWritableEntity;

public class TSAppConfig extends Application {

    @Override
    public java.util.Set<java.lang.Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(ProvidersServlet.class);
        resources.add(EnumContextResolver.class);
        resources.add(ApplicationServlet.class);
        resources.add(TextPlainEnumContextResolver.class);
        resources.add(AnyExceptionExceptionMapper.class);
        resources.add(IOExceptionExceptionMapper.class);
        resources.add(EntityMessageWriter.class);
        resources.add(EntityMessageReader.class);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<Object>();
        singletons.add(EnumProvider.CTS);
        singletons.add(new ReadableWritableEntity(""));
        singletons.add(new ApplicationHolderSingleton(this));
        return singletons;
    }

}
