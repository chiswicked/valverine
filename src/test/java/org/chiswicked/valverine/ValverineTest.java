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

import org.junit.Test;

import static org.junit.Assert.*;

public class ValverineTest {

    private String str;

    @Test
    public void testBetween() throws Exception {
        assertTrue(Valverine.between(5, 1, 10));
        assertTrue(Valverine.between(1, 1, 10));
        assertTrue(Valverine.between(10, 1, 10));

        assertFalse(Valverine.between(0, 1, 10));
        assertFalse(Valverine.between(11, 1, 10));

        assertTrue(Valverine.between(-5, -10, -1));
        assertTrue(Valverine.between(-1, -10, -1));
        assertTrue(Valverine.between(-10, -10, -1));

        assertFalse(Valverine.between(0, -10, -1));
        assertFalse(Valverine.between(-11, -10, -1));

        // TODO Mixed negative and positive
        // TODO Illegal range
    }

    @Test
    public void testClean() throws Exception {
        assertNull(Valverine.clean(str));
        assertEquals("", Valverine.clean(""));

        assertEquals("2", Valverine.clean("123"));
        assertEquals("123", Valverine.clean("[123]"));
        assertEquals("[123]", Valverine.clean("[[123]]"));
    }

    @Test
    public void testAddHeader() throws Exception {
        // TODO Implement header testing
        // assertEquals("FC", Valverine.addHeader(mock(Request.class), "Chelsea", "FC").getHeader("Chelsea"));
        // assertEquals("Beka", Valverine.addHeader(mock(Request.class), "Breki", "Beka").getHeader("Breki"));
    }
}