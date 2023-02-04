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

package com.sun.ts.tests.jaxrs.spec.resource.responsemediatype;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.TEXT_HTML)
@Path("resource")
public class MediaResource {

    @GET
    @Path("responseoverride")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response override() {
        return Response.ok(" ").type(MediaType.APPLICATION_XML_TYPE).build();
    }

    @GET
    @Path("method")
    @Produces(MediaType.APPLICATION_ATOM_XML)
    public Response method() {
        return Response.ok(" ").build();
    }

    @GET
    @Path("class")
    public Response clazz() {
        return Response.ok(" ").build();
    }

    @POST
    @Produces("text/plain")
    public String plain() {
        return MediaType.TEXT_PLAIN;
    }

    @POST
    public String html(@Context Request req) {
        return MediaType.TEXT_HTML;
    }

    @POST
    @Produces("text/xml")
    public String xml() {
        return MediaType.TEXT_XML;
    }

    @POST
    @Produces("application/*")
    public String app() {
        return MediaType.WILDCARD;
    }

    @POST
    @Produces("application/xml")
    public String appxml() {
        return MediaType.APPLICATION_XML;
    }

    @POST
    @Produces("image/png")
    public String png() {
        return "image/png";
    }

    @POST
    @Produces("image/*")
    public String image() {
        return "image/any";
    }

}
