package org.chiswicked.valverine.valves;

import org.chiswicked.tomcat.tools.BaseEmbeddedTomcatTest;
import org.chiswicked.tomcat.tools.HttpResponse;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class HTTPErrorValveTest extends BaseEmbeddedTomcatTest {
    // TODO Test other HTTP statuses?
    @Test(expected = FileNotFoundException.class)
    public void testDefaultResultsIn404() throws Exception {
        HTTPErrorValve valve = new HTTPErrorValve();
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        assertEquals(404, (int) Integer.valueOf(res.getStatus()));
    }

    @Test(expected = FileNotFoundException.class)
    public void testIllegalResultsIn404() throws Exception {
        HTTPErrorValve valve = new HTTPErrorValve();
        valve.setHttpStatus(-99);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        assertEquals(404, (int) Integer.valueOf(res.getStatus()));
    }

    @Test(expected = FileNotFoundException.class)
    public void testBelowRangeResultsIn404() throws Exception {
        HTTPErrorValve valve = new HTTPErrorValve();
        valve.setHttpStatus(99);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        assertEquals(404, (int) Integer.valueOf(res.getStatus()));
    }

    @Test(expected = FileNotFoundException.class)
    public void testAboveRangeResultsIn404() throws Exception {
        HTTPErrorValve valve = new HTTPErrorValve();
        valve.setHttpStatus(999);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        assertEquals(404, (int) Integer.valueOf(res.getStatus()));
    }

    @Test
    public void test200ResultsIn200() throws Exception {
        HTTPErrorValve valve = new HTTPErrorValve();
        valve.setHttpStatus(200);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        assertEquals(200, (int) Integer.valueOf(res.getStatus()));
    }
}