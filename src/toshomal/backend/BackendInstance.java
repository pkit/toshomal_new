package toshomal.backend;

import toshomal.common.ToshoReader;

import java.io.IOException;
import java.util.Date;

import jaxcentServer.ServerMain;

import static toshomal.common.ToshoReader.getToshoContent;

public class BackendInstance extends Thread {

    String logline;
    boolean bRun;
    ToshomalMessage beMsg;
    ToshomalMessage feMsg;
    ToshoReader reader;
    long sleepTime;
    //Date lastUpdate;

    public BackendInstance()
    {
        this.logline =  "Backend running...";
        this.reader = new ToshoReader();
        this.sleepTime = 60 * 1000;
        //this.lastUpdate = new Date(0);
    }
    
    public void run() {
        try {
            bRun = true;
            long sleep = sleepTime;
            long maxSleep = sleepTime << 6;
            while(bRun)
            {
                if(getToshoContent())
                    sleep = sleepTime;
                System.out.println(String.format("Sleeping %d seconds...", sleep / 1000));
                sleep(sleep);
                if (sleep < maxSleep)
                    sleep <<= 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void terminate() throws IOException
    {
        bRun = false;
    }
    
    public static void main( String[] args )
    {
        // Retrieve args, start server main
        if ( args.length < 3 || args.length > 4 ) {
            usage();
        }
        int port = 80;
        try {
            port = Integer.parseInt( args[0] );
        } catch (Exception ex) {
            usage();
        }

        String reloadableClasspath = null;
        if ( args.length == 4 )
            reloadableClasspath = args[3];
        try {
        	new BackendInstance().start();
            new ServerMain( port, args[1], args[2], reloadableClasspath ).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static void usage()
    {
        System.out.println( "Usage: " );
        System.out.println( "  java -jar toshomal.jar <port-number> <html-dir> <config-file>" );
        System.out.println( "  java -jar toshomal.jar <port-number> <html-dir> <config-file> <reloadable-classpath>" );
        System.out.println();
        System.out.println( "e.g." );
        System.out.println();
        System.out.println( "  java -jar toshomal.jar 80 c:\\MyHtmlFiles c:\\MyConfigFile.xml" );
        System.exit( 1 );
    }
}
