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


package org.chiswicked.code.valverine.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * Emulates slow network connection by delaying the request while travelling through the Pipeline
 * <p/>
 * <b>Example:</b><br/>
 * {@code <Valve className="org.chiswicked.code.valverine.valve.DelayValve" delay="1500" />}
 * Add the above line to your {@code $CATALINA_HOME/conf/server.xml}'s
 * {@code <Host>} element to delay every incoming request by 1500 milliseconds
 *
 * @author Norbert Metz
 */
public class DelayValve extends TamperValve {


    // Default length of delay in milliseconds
    private int delay = 2000;


    /**
     * Delay request processing
     *
     * @param request  The servlet request to be processed
     * @param response The servlet response to be created
     * @throws IOException      if an input/output error occurs, or is thrown by a subsequently invoked Valve, Filter, or Servlet
     * @throws ServletException if a servlet error occurs, or is thrown by a subsequently invoked Valve, Filter, or Servlet
     */
    @Override
    public void invoke(Request request, Response response)
            throws IOException, ServletException {
        this.sleep(delay);
        getNext().invoke(request, response);
    }


    /**
     * <p>Set the length of delay. Invoked automatically if {@code delay} attribute is set in the {@code <Valve>} node,
     * otherwise the default 2000 milliseconds is used</p>
     *
     * @param delay Length of delay in milliseconds (min 0, max 30000)
     */
    public synchronized void setDelay(int delay) {
        if (delay > 30000) {
            this.delay = 30000;
        } else if (delay > 0) {
            this.delay = delay;
        } else {
            this.delay = 0;
        }
    }
}
