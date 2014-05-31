package org.chiswicked.tomcat.tools;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmbeddedTomcatTest {

    private static EmbeddedTomcat tomcat;

    @Before
    public void setUp() throws Exception {
        tomcat = new EmbeddedTomcat();
        tomcat.start();
    }

    @Test
    public void testGet() throws Exception {
        Assert.assertEquals(tomcat.sendGet(1000), "GET request received from 127.0.0.1");
    }

    @Test
    public void testPost() throws Exception {
        Assert.assertEquals(tomcat.sendPost(1000), "POST request received from 127.0.0.1");
    }

    @After
    public void tearDown() throws Exception {
        tomcat.stop();
    }

}