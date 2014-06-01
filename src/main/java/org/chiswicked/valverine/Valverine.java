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

package org.chiswicked.valverine;


import org.apache.catalina.connector.Request;

/**
 * Valverine
 */
public class Valverine {
    public static final String PCKG_NAME = "Valverine";
    public static final String HTTP_HEADER_METHOD = PCKG_NAME + "-Method";
    public static final String HTTP_HEADER_RECEIVED = PCKG_NAME + "-Received";
    public static final String HTTP_HEADER_PROCESSED = PCKG_NAME + "-Processed";
    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 1;
    public static final String FULL_NAME = PCKG_NAME + "/" + MAJOR_VERSION + "." + MINOR_VERSION;
    public static final String HTTP_HEADER_STATUS_LINE = "Status-Line";
    public static final String HTTP_HEADER_HTTP_VERSION = "HTTP-Version";
    public static final String HTTP_HEADER_STATUS_CODE = "Status-Code";
    public static final String HTTP_HEADER_REASON_PHRASE = "Reason-Phrase";

    public static boolean between(long number, long low, long high) {
        return (number >= low && number <= high);
    }

    public static String clean(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        return String.copyValueOf(str.toCharArray(), 1, str.length() - 2);
    }

    public static Request addHeader(Request request, String key, String value) {
        // TODO validation of parameters
        request.getCoyoteRequest().getMimeHeaders().addValue(key).setChars(value.toCharArray(), 0, value.length());
        return request;
    }
}
