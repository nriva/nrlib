package it.nrsoft.nrlib.daemon;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.daemon.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


class EchoTask extends TimerTask {
	
	private Logger logger = LogManager.getLogger(TestDaemon.class);
	
    @Override
    public void run() {
        logger.info(new Date() + " running ...");
    }
}

public class TestDaemon implements Daemon {

    private static Timer timer = null;
    
    private static Logger logger = LogManager.getLogger(TestDaemon.class);

    public static void main(String[] args) throws FileNotFoundException {
		FileInputStream str = new FileInputStream("daemon.properties");
		//PropertyConfigurator.configure(str);
        timer = new Timer();
        timer.schedule(new EchoTask(), 0, 1000);
    }

    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        logger.info("initializing ...");
    }

    public void start() throws Exception {
        logger.info("starting ...");
        main(null);
    }

    public void stop() throws Exception {
    	logger.info("stopping ...");
        if (timer != null) {
            timer.cancel();
        }
    }

    public void destroy() {
    	logger.info("done.");
    }

 }