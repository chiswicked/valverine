package org.chiswicked.code.valverine.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.valves.ValveBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DelayValveTest {
    private static TerminatorValve terminatorValve;
    private Request requestMock;
    private Response responseMock;

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
    public void testInvokeBelowMinDelay5000() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), -5000);

        assertTrue(elapsedTime >= 0 && elapsedTime < 100);
    }

    @Test
    public void testInvokeBelowMinDelay1000() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), -1000);

        assertTrue(elapsedTime >= 0 && elapsedTime < 100);
    }

    @Test
    public void testInvokeBelowMinDelay1() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), -1);

        assertTrue(elapsedTime >= 0 && elapsedTime < 100);
    }

    @Test
    public void testInvokeWithinRange0() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), 0);

        assertTrue(elapsedTime >= 0 && elapsedTime < 100);
    }

    @Test
    public void testInvokeWithinRange100() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), 100);

        assertTrue(elapsedTime >= 50 && elapsedTime < 150);
    }

    @Test
    public void testInvokeWithinRange1000() throws Exception {

        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), 1000);

        assertTrue(elapsedTime >= 950 && elapsedTime < 1050);
    }

    @Test
    public void testInvokeAboveMaxDelay31000() throws Exception {
        int elapsedTime = doDelayedRequest(DelayValveFactory.getDelayValve(), 31000);

        assertTrue(elapsedTime >= 29950 && elapsedTime < 30050);
    }


    private int doDelayedRequest(DelayValve valveToTestWith, int delay) throws Exception {
        valveToTestWith.setDelay(delay);

        long startTime = System.currentTimeMillis();

        valveToTestWith.invoke(requestMock, responseMock);

        long endTime = System.currentTimeMillis();

        return (int) (endTime - startTime);
    }

    private static class DelayValveFactory {
        public static DelayValve getDelayValve() {
            DelayValve delayValve = new DelayValve();
            delayValve.setContainer(new StandardEngine());
            delayValve.setNext(terminatorValve);
            return delayValve;
        }
    }

    private class TerminatorValve extends ValveBase {

        @Override
        public void invoke(Request request, Response response) throws IOException, ServletException {
            // Do nothing, that way terminate processing
        }
    }
}