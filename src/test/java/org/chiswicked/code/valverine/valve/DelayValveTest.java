package org.chiswicked.code.valverine.valve;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.valves.ValveBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.mock;


public class DelayValveTest {
    private TerminatorValve terminatorValve;

    private DelayValve delayValveBelowMin;
    private DelayValve delayValveWithinRange;
    private DelayValve delayValveAboveMax;

    private Request requestMock;
    private Response responseMock;

    @Before
    public void setUp() throws Exception {
        terminatorValve = mock(TerminatorValve.class);

        delayValveBelowMin = new DelayValve();
        delayValveWithinRange = new DelayValve();
        delayValveAboveMax = new DelayValve();

        delayValveBelowMin.setContainer(new StandardEngine());
        delayValveWithinRange.setContainer(new StandardEngine());
        delayValveAboveMax.setContainer(new StandardEngine());

        delayValveBelowMin.setNext(terminatorValve);

        delayValveBelowMin.setDelay(-1000);
        delayValveWithinRange.setDelay(2000);
        delayValveAboveMax.setDelay(50000);

        requestMock = mock(Request.class);
        responseMock = mock(Response.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInvokeBelowMinDelay() throws Exception {
        doRequest(delayValveBelowMin);
    }
/*

    @Test
    public void testInvokeWithinRange() throws Exception {
        doRequest(delayValveWithinRange);
    }

    @Test
    public void testInvokeAboveMaxDelay() throws Exception {
        doRequest(delayValveAboveMax);
    }
*/

    private void doRequest(Valve valveToTestWith) throws Exception {
        valveToTestWith.invoke(requestMock, responseMock);
    }

    private class TerminatorValve extends ValveBase {
        @Override
        public void invoke(Request request, Response response) throws IOException, ServletException {

        }
    }
}