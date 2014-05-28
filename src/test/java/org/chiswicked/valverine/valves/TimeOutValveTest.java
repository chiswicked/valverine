package org.chiswicked.valverine.valves;

import org.apache.catalina.core.StandardEngine;
import org.junit.After;
import org.junit.Before;

public class TimeOutValveTest extends TamperValveTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected TamperValve getConcrete() {
        TamperValve tamperValve = new TimeOutValve();
        tamperValve.setContainer(new StandardEngine());
        tamperValve.setNext(terminatorValve);

        return tamperValve;
    }
}