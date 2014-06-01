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


package org.chiswicked.valverine.valves;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Interrupts request processing and throws a specified HTTP error back to the client
 *
 * @author Norbert Metz
 */
public class HTTPErrorValve extends TamperValve {

    private int httpStatus = HttpServletResponse.SC_NOT_FOUND;

    /**
     * Interrupts <code>request</code> processing and throws HTTP 404 Error
     *
     * @param request  The servlet request to be processed
     * @param response The servlet response to be created
     * @throws IOException      if an input/output error occurs, or is thrown by a subsequently invoked Valve, Filter, or Servlet
     * @throws ServletException if a servlet error occurs, or is thrown by a subsequently invoked Valve, Filter, or Servlet
     */
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.getContainer().getLogger().debug("Forcing HTTP status " + httpStatus);
        response.sendError(httpStatus);
    }


    /**
     * <p>Set the HTTP status code to return. Defaults to HTTP error 404 if not specified</p>
     *
     * @param httpStatus HTTP status code
     * @link http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     * @see javax.servlet.http.HttpServletResponse
     */
    public synchronized void setHttpStatus(int httpStatus) {
        Response res = new Response();
        try {
            res.setStatus(httpStatus);
        } catch (Exception e) {
            this.warn("Illegal HTTP status code: " + httpStatus);
        } finally {
            if (httpStatus >= 100 && httpStatus < 600) {
                this.httpStatus = httpStatus;
            }
        }
    }
}
