package toshomal.backend;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by IntelliJ IDEA.
 * User: Kit
 * Date: Oct 13, 2010
 * Time: 2:12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ToshomalMessage {

    private String message;
    private boolean empty = true;
    private long timeout;
    private Random random;

    public ToshomalMessage(long to)
    {
        this.timeout = to;
        this.random = new Random();
    }

    public synchronized String take() {
        //Wait until message is available.
        long start = System.currentTimeMillis();
        while (empty) {
            try {
                wait(System.currentTimeMillis() - start + timeout);
                if ( ( System.currentTimeMillis() - start ) >= timeout )
                    return null;
            } catch (InterruptedException e) {}
        }
        //Simulate randomly long delays
        try {
            sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        //Toggle status.
        empty = true;
        //Notify producer that status has changed.
        notifyAll();
        return message;
    }

    public synchronized boolean put(String message) {
        //Wait until message has been retrieved.
        long start = System.currentTimeMillis();
        while (!empty) {
            try {
                wait(System.currentTimeMillis() - start + timeout);
                if ( ( System.currentTimeMillis() - start ) >= timeout )
                    return false;
            } catch (InterruptedException e) {}
        }
        //Simulate randomly long delays
        try {
            sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        //Toggle status.
        empty = false;
        //Store message.
        this.message = message;
        //Notify consumer that status has changed.
        notifyAll();
        return true;
    }
}
