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
}