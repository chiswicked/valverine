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
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EmbeddedTomcatTest extends BaseEmbeddedTomcatTest {

    @Test
    public void testGet() throws Exception {
        HttpResponse res = getInstance().sendGet();
        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "GET");
    }

    @Test
    public void testPost() throws Exception {
        HttpResponse res = getInstance().sendPost();
        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "POST");
    }

    @Test
    public void testHead() throws Exception {
        HttpResponse res = getInstance().sendHead();
        assertEquals(res.getBody(), ""); // HEAD sends an empty body
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "HEAD");
    }

    @Test
    public void testOptions() throws Exception {
        HttpResponse res = getInstance().sendOptions();
        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "OPTIONS");
    }

    @Test
    public void testPut() throws Exception {
        HttpResponse res = getInstance().sendPut();
        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "PUT");
    }

    @Test
    public void testDelete() throws Exception {
        HttpResponse res = getInstance().sendDelete();
        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertEquals(res.getHeader().get(Valverine.HTTP_HEADER_METHOD), "DELETE");
    }

    @Test(expected = IOException.class)
    public void testTrace() throws Exception {
        getInstance().sendTrace();
    }
}