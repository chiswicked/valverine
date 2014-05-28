package org.chiswicked.valverine.valves;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.mock;

public abstract class TamperValveTest {
    protected static TerminatorValve terminatorValve;
    protected Request requestMock;
    protected Response responseMock;

    @Before
    public void setUp() throws Exception {
        terminatorValve = mock(TerminatorValve.class);
        requestMock = mock(Request.class);
        responseMock = mock(Response.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSleep() throws Exception {

    }

    @Test
    public void testTrace() throws Exception {

    }

    @Test
    public void testDebug() throws Exception {

    }

    @Test
    public void testInfo() throws Exception {

    }

    @Test
    public void testWarn() throws Exception {

    }

    @Test
    public void testError() throws Exception {

    }

    @Test
    public void testFatal() throws Exception {

    }

    @SuppressWarnings("unused")
    protected abstract TamperValve getConcrete();

    private class TerminatorValve extends ValveBase {

        @Override
        public void invoke(Request request, Response response) throws IOException, ServletException {
            // Do nothing, that way terminate processing
        }
    }
}