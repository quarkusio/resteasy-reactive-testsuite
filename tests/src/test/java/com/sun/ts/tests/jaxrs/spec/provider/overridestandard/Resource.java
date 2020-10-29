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

package com.sun.ts.tests.jaxrs.spec.provider.overridestandard;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import javax.activation.DataSource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.xml.transform.Source;

@Path("resource")
public class Resource {
    @Path("bytearray")
    @POST
    public byte[] bytearray(byte[] bytes) {
        return bytes;
    }

    @Path("string")
    @POST
    public String string(String string) {
        return string;
    }

    @Path("inputstream")
    @POST
    public InputStream inputstream(InputStream inputstream) {
        return inputstream;
    }

    @Path("reader")
    @POST
    public Reader reader(Reader reader) {
        return reader;
    }

    @Path("file")
    @POST
    public File file(File file) {
        return file;
    }

    // QUARKUS: disabled as we don't support javax.activation.DataSource
    //  @Path("datasource")
    //  @POST
    public DataSource datasource(DataSource datasource) {
        return datasource;
    }

    // QUARKUS: disabled as we don't support javax.xml.transform.Source
    //  @Path("source")
    //  @POST
    public Source source(Source source) {
        return source;
    }

    // QUARKUS: disable this because we don't support JAXBElement and it causes a build time error
    //  @Path("jaxb")
    //  @POST
    //  public JAXBElement<String> jaxb(JAXBElement<String> jaxb) {
    //    return jaxb;
    //  }

    @Path("map")
    @POST
    public MultivaluedMap<String, String> map(
            MultivaluedMap<String, String> map) {
        return map;
    }

    // QUARKUS: Disable as we don't support javax.ws.rs.core.StreamingOutput
    //  @Path("streamingoutput")
    //  @POST
    //  public StreamingOutput streamingoutput(StreamingOutput streamingoutput) {
    //    return streamingoutput;
    //  }

    @Path("character")
    @POST
    public Character character(Character character) {
        if (character != 'b')
            throw new WebApplicationException(Status.NOT_ACCEPTABLE);
        return character;
    }

    @Path("boolean")
    @POST
    public Boolean bool(Boolean bool) {
        if (!bool)
            throw new WebApplicationException(Status.NOT_ACCEPTABLE);
        return false;
    }

    @Path("number")
    @POST
    public Integer number(Integer i) {
        if (i != 1)
            throw new WebApplicationException(Status.NOT_ACCEPTABLE);
        return i;
    }

}
