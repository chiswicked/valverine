package org.chiswicked.tomcat.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmbeddedTomcatTest {

    private static final String VALID_HTTP_METHODS = "GET|POST|HEAD|OPTIONS|PUT|DELETE|TRACE";
    private static final String RESPONSE_PATTERN = "^Processed (" + VALID_HTTP_METHODS + ") request from \\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3} in [1-9][0-9]*ms";

    private EmbeddedTomcat tomcat;

    @Before
    public void setUp() throws Exception {
        tomcat = new EmbeddedTomcat();
        tomcat.start();
    }

    @Test
    public void testGet() throws Exception {
        String res = tomcat.sendGet(1000);
        assertTrue(res.matches(RESPONSE_PATTERN));
        assertTrue(res.indexOf("GET") != -1);
    }

    @Test
    public void testPost() throws Exception {
        String res = tomcat.sendPost(1000);
        assertTrue(res.matches(RESPONSE_PATTERN));
        assertTrue(res.indexOf("POST") != -1);
    }

    @Test
    public void testHead() throws Exception {
        String res = tomcat.sendHead(1000);
        assertTrue(res.matches("^Processed  in [1-9][0-9]*ms"));
    }

    @Test
    public void testOptions() throws Exception {
        String res = tomcat.sendOptions(1000);
        assertTrue(res.matches(RESPONSE_PATTERN));
        assertTrue(res.indexOf("OPTIONS") != -1);
    }

    @Test
    public void testPut() throws Exception {
        String res = tomcat.sendPut(1000);
        assertTrue(res.matches(RESPONSE_PATTERN));
        assertTrue(res.indexOf("PUT") != -1);
    }

    @Test
    public void testDelete() throws Exception {
        String res = tomcat.sendDelete(1000);
        assertTrue(res.matches(RESPONSE_PATTERN));
        assertTrue(res.indexOf("DELETE") != -1);
    }

    @Test
    public void testTrace() throws Exception {
        String res = tomcat.sendTrace(1000);
        assertTrue(res.matches("405 error"));
    }

    @After
    public void tearDown() throws Exception {
        tomcat.stop();
    }

}