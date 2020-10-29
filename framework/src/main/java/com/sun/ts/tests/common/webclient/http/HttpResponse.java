/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package com.sun.ts.tests.common.webclient.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.sun.ts.tests.common.webclient.Util;

/**
 * This class represents an HTTP response from the server.
 */

public class HttpResponse {

    /**
     * Default encoding based on Servlet Specification
     */
    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    /**
     * Content-Type header
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * Wrapped HttpMethod used to pull response info from.
     */
    private HttpRequestBase _method = null;

    /**
     * HttpState obtained after execution of request
     */
    private HttpClientContext _state = null;

    /**
     * Charset encoding returned in the response
     */
    private String _encoding = DEFAULT_ENCODING;

    /**
     * The response body. Initialized after first call to one of the
     * getResponseBody methods and cached for subsequent calls.
     */
    private String _responseBody = null;

    /**
     * Host name used for processing request
     */
    private String _host = null;

    /**
     * Port number used for processing request
     */
    private int _port;

    /**
     * Issecure
     */
    private boolean _isSecure;

    private org.apache.http.HttpResponse _httpResponse;

    /**
     * Creates new HttpResponse
     * 
     * @param httpResponse
     */
    public HttpResponse(String host, int port, boolean isSecure,
            HttpRequestBase method, HttpClientContext state, org.apache.http.HttpResponse httpResponse) {

        _host = host;
        _port = port;
        _isSecure = isSecure;
        _method = method;
        _state = state;
        _httpResponse = httpResponse;
        if (_httpResponse != null) {
            HttpEntity entity = _httpResponse.getEntity();
            if (entity != null) {
                try {
                    _httpResponse.setEntity(new BufferedHttpEntity(entity));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
    }

    /*
     * public methods
     * ========================================================================
     */

    /**
     * Returns the HTTP status code returned by the server
     *
     * @return HTTP status code
     */
    public String getStatusCode() {
        return Integer.toString(_httpResponse.getStatusLine().getStatusCode());
    }

    /**
     * Returns the HTTP reason-phrase returned by the server
     *
     * @return HTTP reason-phrase
     */
    public String getReasonPhrase() {
        return _httpResponse.getStatusLine().getReasonPhrase();
    }

    /**
     * Returns the headers received in the response from the server.
     *
     * @return response headers
     */
    public Header[] getResponseHeaders() {
        return _httpResponse.getAllHeaders();
    }

    /**
     * Returns the headers designated by the name provided.
     *
     * @return response headers
     */
    public Header[] getResponseHeaders(String headerName) {
        return _httpResponse.getHeaders(headerName);
    }

    /**
     * Returns the response header designated by the name provided.
     *
     * @return a specfic response header or null if the specified header doesn't
     *         exist.
     */
    public Header getResponseHeader(String headerName) {
        Header[] headers = getResponseHeaders(headerName);
        if (headers != null) {
            if (headers.length == 1)
                return headers[0];
            StringBuilder sb = new StringBuilder();
            for (Header header : headers) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(header);
            }
            return new BasicHeader(headerName, sb.toString());
        }
        return null;
    }

    /**
     * Returns the response body as a byte array using the charset specified in
     * the server's response.
     *
     * @return response body as an array of bytes.
     */
    public byte[] getResponseBodyAsBytes() throws IOException {
        return getEncodedResponse().getBytes();
    }

    /**
     * Returns the response as bytes (no encoding is performed by client.
     * 
     * @return the raw response bytes
     * @throws IOException
     *         if an error occurs reading from server
     */
    public byte[] getResponseBodyAsRawBytes() throws IOException {
        return EntityUtils.toByteArray(_httpResponse.getEntity());
    }

    /**
     * Returns the response body as a string using the charset specified in the
     * server's response.
     *
     * @return response body as a String
     */
    public String getResponseBodyAsString() throws IOException {
        return getEncodedResponse();
    }

    /**
     * Returns the response body of the server without being encoding by the
     * client.
     * 
     * @return an unecoded String representation of the response
     * @throws IOException
     *         if an error occurs reading from the server
     */
    public String getResponseBodyAsRawString() throws IOException {
        HttpEntity entity = _httpResponse.getEntity();
        return entity != null ? EntityUtils.toString(entity) : null;
    }

    /**
     * Returns the response body as an InputStream using the encoding specified in
     * the server's response.
     *
     * @return response body as an InputStream
     */
    public InputStream getResponseBodyAsStream() throws IOException {
        return new ByteArrayInputStream(getEncodedResponse().getBytes());
    }

    /**
     * Returns the response body as an InputStream without any encoding applied by
     * the client.
     * 
     * @return an InputStream to read the response
     * @throws IOException
     *         if an error occurs reading from the server
     */
    public InputStream getResponseBodyAsRawStream() throws IOException {
        HttpEntity entity = _httpResponse.getEntity();
        return entity != null ? entity.getContent() : null;
    }

    /**
     * Returns the charset encoding for this response.
     *
     * @return charset encoding
     */
    public String getResponseEncoding() {
        Header content = _httpResponse.getFirstHeader(CONTENT_TYPE);
        if (content != null) {
            String headerVal = content.getValue();
            int idx = headerVal.indexOf(";charset=");
            if (idx > -1) {
                // content encoding included in response
                _encoding = headerVal.substring(idx + 9);
            }
        }
        return _encoding;
    }

    /**
     * Returns the post-request state.
     *
     * @return an HttpState object
     */
    public HttpClientContext getState() {
        return _state;
    }

    /**
     * Displays a String representation of the response.
     *
     * @return string representation of response
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(255);

        sb.append("[RESPONSE STATUS LINE] -> ");
        sb.append(_httpResponse.getProtocolVersion());
        sb.append(_httpResponse.getStatusLine().getStatusCode()).append(' ');
        sb.append(_httpResponse.getStatusLine().getReasonPhrase()).append('\n');
        Header[] headers = _httpResponse.getAllHeaders();
        if (headers != null && headers.length != 0) {
            for (int i = 0; i < headers.length; i++) {
                sb.append("       [RESPONSE HEADER] -> ");
                sb.append(headers[i].toString()).append('\n');
            }
        }

        String resBody;
        try {
            resBody = EntityUtils.toString(_httpResponse.getEntity());
        } catch (IOException ioe) {
            resBody = "UNEXECTED EXCEPTION: " + ioe.toString();
        }
        if (resBody != null && resBody.length() != 0) {
            sb.append("------ [RESPONSE BODY] ------\n");
            sb.append(resBody);
            sb.append("\n-----------------------------\n\n");
        }
        return sb.toString();
    }

    /*
     * Eventually they need to come from _method
     */

    public String getHost() {
        return _host;
    }

    public int getPort() {
        return _port;
    }

    public String getProtocol() {
        return _isSecure ? "https" : "http";
    }

    public String getPath() {
        return _method.getURI().getPath();
    }

    /*
     * Private Methods
     * ==========================================================================
     */

    /**
     * Returns the response body using the encoding returned in the response.
     *
     * @return encoded response String.
     */
    private String getEncodedResponse() throws IOException {
        if (_responseBody == null) {
            _responseBody = Util.getEncodedStringFromStream(
                    _httpResponse.getEntity().getContent(), getResponseEncoding());
        }
        return _responseBody;
    }
}
