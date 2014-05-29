package org.chiswicked.tomcat.tools;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.Valve;
import org.apache.catalina.startup.Tomcat;
import org.chiswicked.valverine.valves.DelayValve;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmbeddedTomcatTest {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static Tomcat tomcat;

    /**
     * Make Tomcat instance accessible to sub-classes.
     */
    protected Tomcat getTomcatInstance() {
        return tomcat;
    }


    /**
     * Sub-classes need to know port so they can connect
     */
    protected int getPort() {
        return tomcat.getConnector().getLocalPort();
    }


    @Before
    public void setUp() throws Exception {
        tomcat = new Tomcat();
        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.setPort(0);
        tomcat.getHost().setAppBase(System.getProperty("java.io.tmpdir"));
        //    tomcat.getHost().setAutoDeploy(true);
        //    tomcat.getHost().setDeployOnStartup(true);

        tomcat.init();
        tomcat.start();

        /* tomcat.getConnector().

       URL url = new URL("http://localhost:" + getPort() + "/");

        URLConnection conn = null;

        try {
            conn = url.openConnection();
        }   catch (Exception e) {

        }

        try {
            InputStream is = conn.getInputStream();
        }   catch (Exception e) {

        }

        //  Response response = tomcat.getConnector().createResponse();
       // response.setStatus(404);

      //  Assert.assertEquals(response.getStatus(), 404);*/
    }

    @Test
    public void testValve() {
        DelayValve dv = new DelayValve();
        dv.setDelay(1000);


        tomcat.getHost().getPipeline().addValve(dv);

        Valve[] valves = tomcat.getHost().getPipeline().getValves();

        for (Valve v : valves) {

            tomcat.getEngine().getLogger().info("woooo - " + v.toString());
        }

        tomcat.getEngine().getLogger().info("================================");

        //Assert.assertNotNull(tomcat.getHost().getPipeline().getV);
    }

    @After
    public void tearDown() throws Exception {

        if (tomcat.getServer() != null
                && tomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (tomcat.getServer().getState() != LifecycleState.STOPPED) {
                tomcat.stop();
            }
            tomcat.destroy();
        }
    }

}