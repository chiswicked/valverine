package org.chiswicked.valverine.valves;

import org.chiswicked.tomcat.tools.BaseEmbeddedTomcatTest;
import org.chiswicked.tomcat.tools.HttpResponse;
import org.junit.Test;

import java.io.IOException;

public class TimeOutValveTest extends BaseEmbeddedTomcatTest {

    @Test(expected = IOException.class)
    public void test1000ServerTimesOut() throws Exception {
        TimeOutValve valve = new TimeOutValve();
        valve.setDelay(1000);
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        // TODO Test time?
    }

    @Test(expected = IOException.class)
    public void testDefaultServerTimesOut() throws Exception {
        TimeOutValve valve = new TimeOutValve();
        getInstance().addValvetoEngine(valve);
        HttpResponse res = getInstance().sendGet();
        // TODO Test time?
    }
}