package it.nrsoft.nrlib.concurrent;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.widgets.Display;


public class SWTGuiExecutor extends AbstractExecutorService {

	// Singletons have a private constructor and a public factory
	private static final SWTGuiExecutor instance = new SWTGuiExecutor();

	private SWTGuiExecutor() { }

	public static SWTGuiExecutor instance() { return instance; }

	public void execute(Runnable r) {

		Display display = Display.getCurrent();
		if(display==null)
			display = Display.getDefault();
		if(display!=null)		
			display.asyncExec(r);
	
	}

	// Plus trivial implementations of lifecycle methods

	public void shutdown() {
		// TODO Auto-generated method stub
	}

	public List<Runnable> shutdownNow() {
	// TODO Auto-generated method stub
	
		return null;

	}

	public boolean isShutdown() {
	
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit)
		throws InterruptedException {
		// TODO Auto-generated method stub
		return false;

	}
}

