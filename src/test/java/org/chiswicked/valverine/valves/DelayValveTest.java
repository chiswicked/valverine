package org.chiswicked.valverine.valves;

import org.apache.catalina.core.StandardEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DelayValveTest extends TamperValveTest {

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

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), -1000);

        assertTrue("<Valve delay=\"-1000\" /> should be delayed by 0; processing took " + elapsedTime, elapsedTime >= 0 && elapsedTime < 150);
    }

    @Test
    public void testInvokeBelowMinDelay1() throws Exception {

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), -1);

        assertTrue("<Valve delay=\"-1\" /> should be delayed by 0; processing took " + elapsedTime, elapsedTime >= 0 && elapsedTime < 150);
    }

    @Test
    public void testInvokeWithinRange0() throws Exception {

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), 0);

        assertTrue("<Valve delay=\"0\" /> should be delayed by 0; processing took " + elapsedTime, elapsedTime >= 0 && elapsedTime < 150);
    }

    @Test
    public void testInvokeWithinRange() throws Exception {

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), 0);

        assertTrue("<Valve /> should be delayed by 0; processing took " + elapsedTime, elapsedTime >= 0 && elapsedTime < 150);
    }

    @Test
    public void testInvokeWithinRange100() throws Exception {

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), 100);

        assertTrue("<Valve delay=\"100\" /> should be delayed by 100; processing took " + elapsedTime, elapsedTime >= 75 && elapsedTime < 250);
    }

    @Test
    public void testInvokeWithinRange1000() throws Exception {

        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), 1000);

        assertTrue("<Valve delay=\"1000\" /> should be delayed by 1000; processing took " + elapsedTime, elapsedTime >= 975 && elapsedTime < 1150);
    }

    @Test
    public void testInvokeAboveMaxDelay31000() throws Exception {
        int elapsedTime = doDelayedRequest((DelayValve) getConcrete(), 31000);

        assertTrue("<Valve delay=\"31000\" /> should be delayed by 30000; processing took " + elapsedTime, elapsedTime >= 29975 && elapsedTime < 30150);
    }


    @Override
    protected TamperValve getConcrete() {
        TamperValve tamperValve = new DelayValve();
        tamperValve.setContainer(new StandardEngine());
        tamperValve.setNext(terminatorValve);

        return tamperValve;
    }

    private int doDelayedRequest(DelayValve valveToTestWith) throws Exception {
        long startTime = System.currentTimeMillis();

        valveToTestWith.invoke(requestMock, responseMock);

        long endTime = System.currentTimeMillis();

        return (int) (endTime - startTime);
    }

    private int doDelayedRequest(DelayValve valveToTestWith, int delay) throws Exception {
        valveToTestWith.setDelay(delay);

        return doDelayedRequest(valveToTestWith);
    }
}