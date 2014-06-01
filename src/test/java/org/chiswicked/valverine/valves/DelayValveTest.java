package org.chiswicked.valverine.valves;

import org.chiswicked.tomcat.tools.BaseEmbeddedTomcatTest;
import org.chiswicked.tomcat.tools.HttpResponse;
import org.chiswicked.valverine.Valverine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DelayValveTest extends BaseEmbeddedTomcatTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testInvokeBelowMinDelay1000() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(-1000);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 0, 500));
    }

    @Test
    public void testInvokeBelowMinDelay1() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(-1);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 0, 500));
    }

    @Test
    public void testInvokeWithinRange0() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(0);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 0, 500));
    }

    @Test
    public void testInvokeDefault() throws Exception {
        DelayValve valve = new DelayValve();
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 2000, 2500));
    }

    @Test
    public void testInvokeWithinRange500() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(500);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 500, 1000));
    }

    @Test
    public void testInvokeWithinRange1000() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(1000);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 1000, 1500));
    }

    @Test
    public void testInvokeAboveMaxDelay21000() throws Exception {
        DelayValve valve = new DelayValve();
        valve.setDelay(21000);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();

        assertEquals(res.getBody(), RESPONSE_PATTERN_SUCCESS);
        assertTrue(Valverine.between(Integer.valueOf(res.getHeader().get(Valverine.HTTP_HEADER_PROCESSED)), 20000, 20500));
    }
}