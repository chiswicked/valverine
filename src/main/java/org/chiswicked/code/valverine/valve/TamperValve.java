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

import org.apache.catalina.valves.ValveBase;

/**
 * <b>TamperValve</b> provides frequently used logging and request tampering functionalities for extending Valves
 *
 * @author Norbert Metz
 */

public abstract class TamperValve extends ValveBase {

    /**
     * Delay request processing
     *
     * @param time Time of delay in milliseconds
     */
    protected void sleep(int time) {
        try {
            Thread.sleep(time);
            this.info(String.format("Request processing delayed by %s milliseconds", time));
        } catch (InterruptedException ie) {
            this.error("Interrupted while sleeping for " + time + " milliseconds");
        }
    }

    /**
     * Create <b>trace</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void trace(String msg) {
        if (this.containerLog.isTraceEnabled()) {
            this.containerLog.trace(msg);
        }
    }

    /**
     * Create <b>debug</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void debug(String msg) {
        if (this.containerLog.isDebugEnabled()) {
            this.containerLog.debug(msg);
        }
    }

    /**
     * Create <b>info</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void info(String msg) {
        if (this.containerLog.isInfoEnabled()) {
            this.containerLog.info(msg);
        }
    }

    /**
     * Create <b>warn</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void warn(String msg) {
        if (this.containerLog.isWarnEnabled()) {
            this.containerLog.warn(msg);
        }
    }

    /**
     * Create <b>erro</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void error(String msg) {
        if (this.containerLog.isErrorEnabled()) {
            this.containerLog.error(msg);
        }
    }

    /**
     * Create <b>fatal</b> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    protected void fatal(String msg) {
        if (this.containerLog.isFatalEnabled()) {
            this.containerLog.fatal(msg);
        }
    }
}
