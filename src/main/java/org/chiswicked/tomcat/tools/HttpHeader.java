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

import org.chiswicked.valverine.Valverine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Light-weight wrapper object for HTTP headers
 */
public class HttpHeader {
    private static String STATUS_LINE_PATTERN = "^([A-Z]*\\/\\d\\.\\d) (\\d{3}) (.*)$";

    private Map<String, List<String>> header;

    public HttpHeader(Map<String, List<String>> header) {
        // TODO Implement proper parsing of status line
        this.header = new HashMap<String, List<String>>(header);
        this.header.put(Valverine.HTTP_HEADER_STATUS_LINE, Arrays.asList("HTTP/1.1 200 OK"));
        this.header.put(Valverine.HTTP_HEADER_HTTP_VERSION, Arrays.asList("HTTP/1.1"));
        this.header.put(Valverine.HTTP_HEADER_STATUS_CODE, Arrays.asList("200"));
        this.header.put(Valverine.HTTP_HEADER_REASON_PHRASE, Arrays.asList("OK"));
    }

    /**
     * Returns to value of a specified HTTP header element
     *
     * @param key Identifier of the HTTP header element (e.g. <i>Content-Type</i>)
     * @return The value of HTTP header element (e.g. <i>text/html; charset=UTF-8</i>)
     */
    public String get(String key) {
        // TODO Implement proper String parsing
        return Valverine.clean(String.valueOf(header.get(key)));
    }

    /**
     * Returns the HTTP Status-Code
     *
     * @return HTTP Status-Code
     * @link http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     */
    public int getStatus() {
        return Integer.parseInt(get(Valverine.HTTP_HEADER_STATUS_CODE));
    }


    /**
     * Outputs the content of the HTTP header to stderr
     */
    public void dump() {
        for (Map.Entry<String, List<String>> entry : header.entrySet()) {
            System.err.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
