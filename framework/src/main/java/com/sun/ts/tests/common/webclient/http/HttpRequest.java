/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.common.webclient.Util;

/**
 * Represents an HTTP client Request
 */

public class HttpRequest {

    static {
        if (TestUtil.traceflag) {
            System.setProperty("org.apache.commons.logging.Log",
                    "com.sun.ts.tests.common.webclient.log.WebLog");
            System.setProperty(
                    "org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
        }
    }

    /**
     * Default HTTP port.
     */
    public static int DEFAULT_HTTP_PORT = 80;

    /**
     * Default HTTP SSL port.
     */
    public static final int DEFAULT_SSL_PORT = 443;

    /**
     * No authentication
     */
    public static final int NO_AUTHENTICATION = 0;

    /**
     * Basic authentication
     */
    public static final int BASIC_AUTHENTICATION = 1;

    /**
     * Digest authenctication
     */
    public static final int DIGEST_AUTHENTICATION = 2;

    /**
     * Method representation of request.
     */
    private HttpRequestBase _method = null;

    /**
     * Target web container host
     */
    private String _host = null;

    /**
     * Target web container port
     */
    private int _port = DEFAULT_HTTP_PORT;

    /**
     * Is the request going over SSL
     */
    private boolean _isSecure = false;

    /**
     * HTTP state
     */
    private HttpClientContext _state = null;

    /**
     * Original request line for this request.
     */
    private String _requestLine = null;

    /**
     * Authentication type for current request
     */
    private int _authType = NO_AUTHENTICATION;

    /**
     * Flag to determine if session tracking will be used or not.
     */
    private boolean _useCookies = false;

    /**
     * Content length of request body.
     */
    private int _contentLength = 0;

    /**
     * FollowRedirects
     */
    private boolean _redirect = false;

    Header[] _headers = null;

    protected HttpClient client = null;

    /**
     * Creates new HttpRequest based of the passed request line. The request line
     * provied must be in the form of:<br>
     * 
     * <pre>
     *     METHOD PATH HTTP-VERSION
     *     Ex.  GET /index.html HTTP/1.0
     * </pre>
     */
    public HttpRequest(String requestLine, String host, int port) {
        client = HttpClients.createDefault();
        _method = MethodFactory.getInstance(requestLine);
        _method.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build());
        _host = host;
        _port = port;

        if (port == DEFAULT_SSL_PORT) {
            _isSecure = true;
        }

        // If we got this far, the request line is in the proper
        // format
        _requestLine = requestLine;
    }

    /*
     * public methods
     * ========================================================================
     */

    /**
     * <code>getRequestPath</code> returns the request path for this particular
     * request.
     *
     * @return String request path
     */
    public String getRequestPath() {
        return _method.getURI().getPath();
    }

    /**
     * <code>getRequestMethod</code> returns the request type, i.e., GET, POST,
     * etc.
     *
     * @return String request type
     */
    public String getRequestMethod() {
        return _method.getMethod();
    }

    /**
     * <code>isSecureConnection()</code> indicates if the Request is secure or
     * not.
     *
     * @return boolean whether Request is using SSL or not.
     */
    public boolean isSecureRequest() {
        return _isSecure;
    }

    /**
     * <code>setSecureRequest</code> configures this request to use SSL.
     *
     * @param secure
     *        - whether the Request uses SSL or not.
     */
    public void setSecureRequest(boolean secure) {
        _isSecure = secure;
    }

    /**
     * <code>setContent</code> will set the body for this request. Note, this is
     * only valid for POST and PUT operations, however, if called and the request
     * represents some other HTTP method, it will be no-op'd.
     *
     * @param content
     *        request content
     */
    public void setContent(String content) {
        if (_method instanceof HttpEntityEnclosingRequest) {
            ((HttpEntityEnclosingRequest) _method)
                    .setEntity(new StringEntity(content, (ContentType) null));
        }
        _contentLength = content.length();
    }

    /**
     * <code>setAuthenticationCredentials configures the request to
     * perform authentication.
     *
     * <p><code>username</code> and <code>password</code> cannot be null.
     * </p>
     *
     * <p>
     * It is legal for <code>realm</code> to be null.
     * </p>
     *
     * @param username
     *        the user
     * @param password
     *        the user's password
     * @param authType
     *        authentication type
     * @param realm
     *        authentication realm
     */
    public void setAuthenticationCredentials(String username, String password,
            int authType, String realm) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        UsernamePasswordCredentials cred = new UsernamePasswordCredentials(username,
                password);
        AuthScope scope = new AuthScope(_host, _port, realm);
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(scope, cred);
        getState().setCredentialsProvider(credentialsProvider);
        TestUtil.logTrace("[HttpRequest] Added credentials for '" + username
                + "' with password '" + password + "' in realm '" + realm + "'");

        _authType = authType;
    }

    /**
     * <code>addRequestHeader</code> adds a request header to this request. If a
     * request header of the same name already exists, the new value, will be
     * added to the set of already existing values.
     *
     * <strong>NOTE:</strong> that header names are not case-sensitive.
     *
     * @param headerName
     *        request header name
     * @param headerValue
     *        request header value
     */
    public void addRequestHeader(String headerName, String headerValue) {
        _method.addHeader(headerName, headerValue);
        TestUtil.logTrace("[HttpRequest] Added request header: "
                + _method.getFirstHeader(headerName));
    }

    public void addRequestHeader(String header) {
        StringTokenizer st = new StringTokenizer(header, "|");
        while (st.hasMoreTokens()) {
            String h = st.nextToken();
            if (h.toLowerCase().startsWith("cookie")) {
                createCookie(h);
                continue;
            }
            int col = h.indexOf(':');
            addRequestHeader(h.substring(0, col).trim(), h.substring(col + 1).trim());
        }
    }

    /**
     * <code>setRequestHeader</code> sets a request header for this request
     * overwritting any previously existing header/values with the same name.
     *
     * <strong>NOTE:</strong> Header names are not case-sensitive.
     *
     * @param headerName
     *        request header name
     * @param headerValue
     *        request header value
     */
    public void setRequestHeader(String headerName, String headerValue) {
        _method.setHeader(headerName, headerValue);
        TestUtil.logTrace("[HttpRequest] Set request header: "
                + _method.getFirstHeader(headerName));

    }

    /**
     * <code>setFollowRedirects</code> indicates whether HTTP redirects are
     * followed. By default, redirects are not followed.
     */
    public void setFollowRedirects(boolean followRedirects) {
        _method.setConfig(RequestConfig.custom().setRedirectsEnabled(followRedirects).build());
    }

    /**
     * <code>getFollowRedirects</code> indicates whether HTTP redirects are
     * followed.
     */
    public boolean getFollowRedirects() {
        return _method.getConfig().isRedirectsEnabled();
    }

    /**
     * <code>setState</code> will set the HTTP state for the current request (i.e.
     * session tracking). This has the side affect
     */
    public void setState(HttpClientContext state) {
        _state = state;
        _useCookies = true;
    }

    /**
     * <code>execute</code> will dispatch the current request to the target
     * server.
     *
     * @return HttpResponse the server's response.
     * @throws IOException
     *         if an I/O error occurs during dispatch.
     */
    public HttpResponse execute() throws IOException, HttpException {
        String method;
        int defaultPort;
        //    ProtocolSocketFactory factory;

        if (_method.getConfig().isRedirectsEnabled()) {
            client = HttpClients.createDefault();

            if (_isSecure) {
                method = "https";
                defaultPort = DEFAULT_SSL_PORT;
                //        factory = new SSLProtocolSocketFactory();
            } else {
                method = "http";
                defaultPort = DEFAULT_HTTP_PORT;
                //        factory = new DefaultProtocolSocketFactory();
            }

            //      Protocol protocol = new Protocol(method, factory, defaultPort);
            //      HttpConnection conn = new HttpConnection(_host, _port, protocol);
            //
            //      if (conn.isOpen()) {
            //        throw new IllegalStateException("Connection incorrectly opened");
            //      }
            //
            //      conn.open();

            TestUtil.logMsg("[HttpRequest] Dispatching request: '" + _requestLine
                    + "' to target server at '" + _host + ":" + _port + "'");

            addSupportHeaders();
            _headers = _method.getAllHeaders();

            TestUtil.logTrace(
                    "########## The real value set: " + _method.getConfig().isRedirectsEnabled());

            org.apache.http.HttpResponse httpResponse = client.execute(new HttpHost(_host, _port, method), _method, getState());

            return new HttpResponse(_host, _port, _isSecure, _method, getState(), httpResponse);
        } else {
            if (_isSecure) {
                method = "https";
                defaultPort = DEFAULT_SSL_PORT;
                //        factory = new SSLProtocolSocketFactory();
            } else {
                method = "http";
                defaultPort = DEFAULT_HTTP_PORT;
                //        factory = new DefaultProtocolSocketFactory();
            }

            //      Protocol protocol = new Protocol(method, factory, defaultPort);
            //      HttpConnection conn = new HttpConnection(_host, _port, protocol);
            //
            //      if (conn.isOpen()) {
            //        throw new IllegalStateException("Connection incorrectly opened");
            //      }
            //
            //      conn.open();

            TestUtil.logMsg("[HttpRequest] Dispatching request: '" + _requestLine
                    + "' to target server at '" + _host + ":" + _port + "'");

            addSupportHeaders();
            _headers = _method.getAllHeaders();

            TestUtil.logTrace(
                    "########## The real value set: " + _method.getConfig().isRedirectsEnabled());

            org.apache.http.HttpResponse httpResponse = client.execute(new HttpHost(_host, _port, method), _method, getState());

            return new HttpResponse(_host, _port, _isSecure, _method, getState(), httpResponse);
        }
    }

    /**
     * Returns the current state for this request.
     *
     * @return HttpState current state
     */
    public HttpClientContext getState() {
        if (_state == null) {
            _state = HttpClientContext.create();
            _state.setCookieStore(new BasicCookieStore());
        }
        return _state;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(255);
        sb.append("[REQUEST LINE] -> ").append(_requestLine).append('\n');

        if (_headers != null && _headers.length != 0) {

            for (Header _header : _headers) {
                sb.append("       [REQUEST HEADER] -> ");
                sb.append(_header).append('\n');
            }
        }

        if (_contentLength != 0) {
            sb.append("       [REQUEST BODY LENGTH] -> ").append(_contentLength);
            sb.append('\n');
        }

        return sb.toString();

    }

    /*
     * private methods
     * ========================================================================
     */

    private void createCookie(String cookieHeader) {
        String cookieLine = cookieHeader.substring(cookieHeader.indexOf(':') + 1)
                .trim();
        StringTokenizer st = new StringTokenizer(cookieLine, " ;");
        BasicClientCookie cookie = null;

        getState();

        if (cookieLine.indexOf("$Version") == -1) {
            cookie.setVersion(0);
            // FIXME: not sure how to port this
            //      _method.getParams().setCookiePolicy(CookiePolicy.NETSCAPE);
        }

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (token.charAt(0) != '$' && !token.startsWith("Domain")
                    && !token.startsWith("Path")) {
                String name = token.substring(0, token.indexOf('='));
                String value = token.substring(token.indexOf('=') + 1);
                cookie = new BasicClientCookie(name, value);
                cookie.setVersion(1);
            } else if (token.indexOf("Domain") > -1) {
                cookie.setDomain(token.substring(token.indexOf('=') + 1));
            } else if (token.indexOf("Path") > -1) {
                cookie.setPath(token.substring(token.indexOf('=') + 1));
            }
        }
        _state.getCookieStore().addCookie(cookie);

    }

    /**
     * Adds any support request headers necessary for this request. These headers
     * will be added based on the state of the request.
     */
    private void addSupportHeaders() {

        // Authentication headers
        // NOTE: Possibly move logic to generic method
        switch (_authType) {
            case NO_AUTHENTICATION:
                break;
            case BASIC_AUTHENTICATION:
                setBasicAuthorizationHeader();
                break;
            case DIGEST_AUTHENTICATION:
                throw new UnsupportedOperationException(
                        "Digest Authentication is not currently " + "supported");
        }

        // A Host header will be added to each request to handle
        // cases where virtual hosts are used, or there is no DNS
        // available on the system where the container is running.
        setHostHeader();

        // Content length header
        setContentLengthHeader();

        // Cookies
        setCookieHeader();
    }

    /**
     * Sets a basic authentication header in the request is Request is configured
     * to use basic authentication
     */
    private void setBasicAuthorizationHeader() {
        UsernamePasswordCredentials cred = (UsernamePasswordCredentials) getState().getCredentialsProvider()
                .getCredentials(new AuthScope(_host, _port, null));
        String authString = null;
        if (cred != null) {
            authString = "Basic " + Util.getBase64EncodedString(
                    cred.getUserName() + ":" + cred.getPassword());
        } else {
            TestUtil.logTrace("[HttpRequest] NULL CREDENTIALS");
        }
        _method.setHeader("Authorization", authString);
    }

    /**
     * Sets a Content-Length header in the request if content is present
     */
    private void setContentLengthHeader() {
        // automatic now
    }

    /**
     * Sets a host header in the request. If the configured host value is an IP
     * address, the Host header will be sent, but without any value.
     *
     * If we adhered to the HTTP/1.1 spec, the Host header must be empty of the
     * target server is identified via IP address. However, no user agents I've
     * tested follow this. And if a custom client library does this, it may not
     * work properly with the target server. For now, the Host request-header will
     * always have a value.
     */
    private void setHostHeader() {
        if (_port == DEFAULT_HTTP_PORT || _port == DEFAULT_SSL_PORT) {
            _method.setHeader("Host", _host);
        } else {
            _method.setHeader("Host", _host + ":" + _port);
        }
    }

    /**
     * Sets a Cookie header if this request is using cookies.
     */
    private void setCookieHeader() {
        // automatic now
    }
}
