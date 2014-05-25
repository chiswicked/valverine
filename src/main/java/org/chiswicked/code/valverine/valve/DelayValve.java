package org.chiswicked.code.valverine.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by chiswicked on 25/05/2014.
 */

public class DelayValve extends ValveBase {


    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
