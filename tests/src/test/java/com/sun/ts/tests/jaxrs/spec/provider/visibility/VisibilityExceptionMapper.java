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

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Provider
public class VisibilityExceptionMapper
        implements ExceptionMapper<VisibilityException> {
    private HttpHeaders headers;

    private UriInfo info;

    private Application application;

    private Request request;

    private Providers provider;

    protected VisibilityExceptionMapper(@Context HttpHeaders headers,
            @Context UriInfo info, @Context Application application,
            @Context Request request, @Context Providers provider) {
        super();
        this.headers = headers;
        this.info = info;
        this.application = application;
        this.request = request;
        this.provider = provider;
    }

    @Inject
    public VisibilityExceptionMapper(@Context HttpHeaders headers,
            @Context UriInfo info, @Context Application application,
            @Context Request request) {
        super();
        this.headers = headers;
        this.info = info;
        this.application = application;
        this.request = request;
    }

    public VisibilityExceptionMapper(@Context HttpHeaders headers,
            @Context UriInfo info, @Context Application application) {
        super();
        this.headers = headers;
        this.info = info;
        this.application = application;
    }

    public VisibilityExceptionMapper(@Context HttpHeaders headers,
            @Context UriInfo info) {
        super();
        this.headers = headers;
        this.info = info;
    }

    public VisibilityExceptionMapper(@Context HttpHeaders headers) {
        super();
        this.headers = headers;
    }

    @Override
    public Response toResponse(VisibilityException exception) {
        return new HolderClass(headers, info, application, request, provider)
                .toResponse();
    }

}
