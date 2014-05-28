package org.chiswicked.code.valverine.valve;

import org.apache.catalina.core.StandardEngine;
import org.chiswicked.valverine.valves.HTTPErrorValve;
import org.chiswicked.valverine.valves.TamperValve;
import org.junit.After;
import org.junit.Before;

public class HTTPErrorValveTest extends TamperValveTest {

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
        TamperValve tamperValve = new HTTPErrorValve();
        tamperValve.setContainer(new StandardEngine());
        tamperValve.setNext(terminatorValve);

        return tamperValve;
    }
}