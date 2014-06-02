/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Norbert Metz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.chiswicked.tomcat.tools;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Valve;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.chiswicked.valverine.Valverine;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Embedded Tomcat
 */
public final class EmbeddedTomcat {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private Tomcat tomcat;
    private String urlString;
    private URL url;


    /**
     * <p>Initialises server instance with default servlet</p>
     *
     * @throws Exception
     */
    public EmbeddedTomcat()
            throws Exception {
        this(new EmbeddedTestServlet());
    }


    /**
     * <p>Initialises server instance with given servlet. Default valves are used</p>
     *
     * @param servlet Servlet to initialise tomcat with
     * @throws Exception
     */
    public EmbeddedTomcat(Servlet servlet)
            throws Exception {
        initialize(servlet, null);
    }


    /**
     * <p>Initialises server instance with given valve (chained to default pipeline). Default servlet is used </p>
     *
     * @param valve Valve to initialise tomcat with
     * @throws Exception
     */
    public EmbeddedTomcat(Valve valve)
            throws Exception {
        initialize(new EmbeddedTestServlet(), valve);
    }


    /**
     * <p>Initialises server instance with given servlet and valve</p>
     *
     * @param servlet Servlet to initialise tomcat with
     * @throws Exception
     */
    public EmbeddedTomcat(Servlet servlet, Valve valve)
            throws Exception {
        initialize(servlet, valve);
    }


    /**
     * Start server instance
     *
     * @throws org.apache.catalina.LifecycleException
     */
    public void start()
            throws LifecycleException {
        tomcat.init();
        tomcat.start();

        urlString = "http://localhost:" + getPort() + "/";

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            tomcat.getEngine().getLogger().fatal("Embedded Tomcat instance failed to load. Invalid URL: " + urlString);
        }

        tomcat.getEngine().getLogger().info("Listening on " + urlString);
    }


    /**
     * Shut down server instance
     *
     * @throws LifecycleException
     */
    public void stop()
            throws LifecycleException {
        if (tomcat.getServer() != null
                && tomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
                tomcat.stop();
            }
            tomcat.destroy();
        }
    }

    /**
     * Adds a custom valve to the Engine's pipeline
     *
     * @param valve Valve to add to the pipeline
     */
    public void addValvetoEngine(Valve valve) {
        tomcat.getEngine().getPipeline().addValve(valve);
    }


    /**
     * Sends a GET request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendGet()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.GET, 0);
    }


    /**
     * Sends a GET request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendGet(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.GET, timeout);
    }


    /**
     * Sends a POST request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendPost()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.POST, 0);
    }


    /**
     * Sends a POST request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendPost(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.POST, timeout);
    }


    /**
     * Sends a HEAD request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendHead()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.HEAD, 0);
    }


    /**
     * Sends a HEAD request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendHead(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.HEAD, timeout);
    }


    /**
     * Sends a OPTIONS request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendOptions()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.OPTIONS, 0);
    }


    /**
     * Sends a OPTIONS request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendOptions(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.OPTIONS, timeout);
    }


    /**
     * Sends a PUT request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendPut()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.PUT, 0);
    }


    /**
     * Sends a PUT request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendPut(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.PUT, timeout);
    }


    /**
     * Sends a DELETE request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendDelete()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.DELETE, 0);
    }


    /**
     * Sends a DELETE request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendDelete(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.DELETE, timeout);
    }


    /**
     * Sends a TRACE request to the server
     *
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendTrace()
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.TRACE, 0);
    }

    /**
     * Sends a TRACE request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public HttpResponse sendTrace(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(HttpMethod.TRACE, timeout);
    }


    private HttpResponse sendMethod(String method, int timeout) throws IOException {
        // TODO Implement more user-friendly error handling mechanism

        // Set up connection to tomcat test instance
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setUseCaches(false);
        connection.setReadTimeout(timeout);

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException pe) {
            try {
                connection.setRequestMethod(HttpMethod.GET);
            } catch (ProtocolException peg) {
                tomcat.getEngine().getLogger().error(peg.getMessage());
                // TODO Possibly we want to terminate processing here
            }
        }

        // Send request and read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        HttpHeader header = new HttpHeader(connection.getHeaderFields());

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new HttpResponse(header, response.toString());
    }


    private void initialize(Servlet servlet, Valve valve) {
        // Init tomcat
        tomcat = new Tomcat();
        tomcat.setPort(0);
        tomcat.setBaseDir(TEMP_DIR);
        tomcat.getHost().setAppBase(TEMP_DIR);

        // Init servlet
        File docBase = new File(TEMP_DIR);
        Context ctx = tomcat.addContext("", docBase.getAbsolutePath());
        Tomcat.addServlet(ctx, "test", servlet);
        ctx.addServletMapping("/*", "test");
    }


    /**
     * Returns the TCP port allocated to the server upon initialisation
     *
     * @return Port allocated to tomcat instance upon initialisation
     * @throws org.apache.catalina.LifecycleException
     */
    private int getPort() throws LifecycleException {
        int port = tomcat.getConnector().getLocalPort();
        if (port <= 0) {
            throw new LifecycleException("Cannot retrieve local port before the server is started");
        }

        return port;
    }


    /**
     * Embedded servlet for default setup
     */
    private static class EmbeddedTestServlet extends DefaultServlet {


        /**
         * <p>Default Servlet to initialise #EmbeddedTomcat with if no constructor argument is specified</p>
         * <p>In the HTTP response header, it returns the request method and the requestor's remote address in {@code plain/text}</p>
         * <ul>
         * <li>{@code Valverine-Method: POST}</li>
         * <li>{@code Valverine-Processed: 234}</li>
         * </ul>
         * <p>In the HTTP response body, it returns fully qualified name of Valverine e.g {@code Valverine/1.0}</p>
         *
         * @param req HTTP request
         * @param res HTTP response
         * @throws ServletException
         * @throws IOException
         */
        private void doMethod(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

//          DEBUGGING
/*
            Enumeration<String> reqheaderNames = req.getHeaderNames();

            Collection<String> resheaderNames = res.getHeaderNames();

            System.out.println("REQUEST");
            for (; reqheaderNames.hasMoreElements(); ) {
                Object o = reqheaderNames.nextElement();
                System.out.println(o + ": " + req.getHeader(String.valueOf(o)));
            }

            System.out.println("RESPONSE");
            for (String headerName : resheaderNames) {
                System.out.println(headerName + "> " + res.getHeader(headerName));
            }
*/

            long processingTime = -1;

            //Calculate processing time (time now - when the request was first intercepted by a valve/filter)
            try {
                processingTime = System.currentTimeMillis() - Long.parseLong(req.getHeader(Valverine.HTTP_HEADER_RECEIVED));
            } catch (NumberFormatException nfe) {
                getServletContext().log("Missing or illegal value [" + Valverine.HTTP_HEADER_RECEIVED + ": " + req.getHeader(Valverine.HTTP_HEADER_RECEIVED) + "]");
            }

            ServletOutputStream out = res.getOutputStream();

            res.setHeader(Valverine.HTTP_HEADER_METHOD, req.getMethod());

            if (processingTime > -2) {
                res.setHeader(Valverine.HTTP_HEADER_PROCESSED, String.valueOf(processingTime));
            }

            out.write((Valverine.FULL_NAME + "\n").getBytes());
            out.flush();
            out.close();
        }


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doHead(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doOptions(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }


        @Override
        protected void doTrace(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            doMethod(req, res);
        }
    }
}