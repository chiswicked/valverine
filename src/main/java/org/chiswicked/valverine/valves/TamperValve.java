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

import org.apache.catalina.valves.ValveBase;


/**
 * Provides frequently used logging and request tampering functionalities for extending Valves
 *
 * @author Norbert Metz
 */
public abstract class TamperValve extends ValveBase {

    /**
     * Delay request processing
     *
     * @param time Time of delay in milliseconds
     */
    protected void delayProcessing(int time) {
        try {
            Thread.sleep(time);
            this.debug(String.format("Request processing delayed by %s milliseconds", time));
        } catch (InterruptedException ie) {
            this.error("Interrupted while sleeping for " + time + " milliseconds");
        }
    }


    /*
    When a Valve is not assigned to a container, it cannot log as the logger is accessed via the container
    TODO Silently dropping logging may be dangerous
    */


    /**
     * Create <b>TRACE</> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void trace(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isTraceEnabled()) {
            this.getContainer().getLogger().trace(msg);
        }
    }


    /**
     * Create <i>DEBUG</i> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void debug(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isDebugEnabled()) {
            this.getContainer().getLogger().debug(msg);
        }
    }


    /**
     * Create <i>INFO</i> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void info(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isInfoEnabled()) {
            this.getContainer().getLogger().info(msg);
        }
    }


    /**
     * Create <i>WARN</i> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void warn(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isWarnEnabled()) {
            this.getContainer().getLogger().warn(msg);
        }
    }


    /**
     * Create <i>ERROR</i> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void error(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isErrorEnabled()) {
            this.getContainer().getLogger().error(msg);
        }
    }


    /**
     * Create <i>FATAL</i> level log entry, if enabled by container
     *
     * @param msg Message to be logged
     */
    @SuppressWarnings("unused")
    protected void fatal(String msg) {
        if (this.getContainer() != null && this.getContainer().getLogger().isFatalEnabled()) {
            this.getContainer().getLogger().fatal(msg);
        }
    }
}