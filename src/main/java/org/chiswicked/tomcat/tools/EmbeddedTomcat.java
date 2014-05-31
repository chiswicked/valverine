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
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

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
import java.net.URL;


public final class EmbeddedTomcat {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private Tomcat tomcat;
    private String urlString;
    private URL url;
    private boolean isServletSet;


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
     * <p>Initialises server instance with given servlet</p>
     *
     * @param servlet Servlet to initialise tomcat with
     * @throws Exception
     */
    public EmbeddedTomcat(Servlet servlet)
            throws Exception {
        initialize(servlet);
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
            System.err.println("Embedded Tomcat instance failed to load. Invalid URL: " + urlString);
            e.printStackTrace();
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
     * Sends a GET request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public String sendGet(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod("GET", timeout);
    }


    /**
     * Sends a POST request to the server
     *
     * @param timeout Request timeout
     * @return Server response
     * @throws Exception
     */
    public String sendPost(int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod("POST", timeout);
    }


    /**
     * Sends an HTTP request to the server
     *
     * @param method HTTP method to use
     * @return Server response
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String sendMethod(String method)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism
        return this.sendMethod(method, 0);
    }


    private String sendMethod(String method, int timeout)
            throws Exception {
        // TODO Implement more user-friendly error handling mechanism

        // Set up connection to tomcat test instance
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setUseCaches(false);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod(method);

        long startTime = System.currentTimeMillis();

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            response.append(inputLine);
        }
        in.close();

        long endTime = System.currentTimeMillis();

        // Log response time
        tomcat.getEngine().getLogger().info("Millis: " + (int) (endTime - startTime));

        return response.toString();
    }


    private void initialize(Servlet servlet)
            throws Exception {
        // TODO Implement more user-friendly exception mechanism
        tomcat = new Tomcat();
        tomcat.setPort(0);
        tomcat.setBaseDir(TEMP_DIR);
        tomcat.getHost().setAppBase(TEMP_DIR);

        if (!isServletSet) {
            File docBase = new File(TEMP_DIR);
            Context ctx = tomcat.addContext("", docBase.getAbsolutePath());
            Tomcat.addServlet(ctx, "test", servlet);
            ctx.addServletMapping("/*", "test");
            isServletSet = true;
        } else {
            // TODO Implement more user-friendly exception mechanism
            throw new Exception("Servlet is already set");
        }
    }


    /**
     * Returns the TCP port allocated to the server upon initialisation
     *
     * @return Port allocated to tomcat instance upon initialisation
     */
    private int getPort() {
        // TODO Implement more user-friendly error handling mechanism
        int port = tomcat.getConnector().getLocalPort();
        if (port <= 0) System.err.println("Tomcat server must be started before retrieving allocated TCP port");

        return port;
    }

    /**
     * Embedded servlet for default setup
     */
    private static class EmbeddedTestServlet extends DefaultServlet {


        /**
         * <p>Default Servlet to initialise EmbeddedTomcat with if no constructor argument is specified</p>
         * <p>In the HTTP response, it returns the request method and the requestor's remote address in {@code plain/text}</p>
         *
         * @param req HTTP request
         * @param res HTTP response
         * @throws ServletException
         * @throws IOException
         */
        private void doMethod(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            ServletOutputStream out = res.getOutputStream();

            res.setContentType("plain/text");

            out.write((req.getMethod() + " request received from " + req.getRemoteAddr() + "\n").getBytes());
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