package it.nrsoft.nrlib.concurrent;

import java.util.concurrent.*;

/**
 * BackgroundTask
 * <p/>
 * Background task class supporting cancellation, completion notification, and progress notification
 *
 * @author Brian Goetz and Tim Peierls
 */

public abstract class BackgroundTask <V> implements Runnable, Future<V> {
    private final FutureTask<V> computation = new Computation();

    private class Computation extends FutureTask<V> {
        public Computation() {
            super(new Callable<V>() {
                public V call() throws Exception {
                    return BackgroundTask.this.compute();
                }
            });
        }

        /**
         * Notifica finale all'interfaccia grafica
         */
        @Override
        protected final void done() {
            SWTGuiExecutor.instance().execute(new Runnable() {
                public void run() {
                    V value = null;
                    Throwable thrown = null;
                    boolean cancelled = false;
                    try {
                        value = get();
                    } catch (ExecutionException e) {
                        thrown = e.getCause();
                    } catch (CancellationException e) {
                        cancelled = true;
                    } catch (InterruptedException consumed) {
                    } finally {
                        onCompletion(value, thrown, cancelled);
                    }
                };
            });
        }
    }

    protected void setProgress(final int current, final int max) {
    	SWTGuiExecutor.instance().execute(new Runnable() {
            public void run() {
                onProgress(current, max);
            }
        });
    }

    // Called in the background thread
    protected abstract V compute() throws Exception;

    // Called in the event thread
    protected void onCompletion(V result, Throwable exception,
                                boolean cancelled) {
    }

    // Called in the event thread
    protected void onProgress(int current, int max) {
    }

    // Other Future methods just forwarded to computation
    public boolean cancel(boolean mayInterruptIfRunning) {
    	boolean b = computation.cancel(mayInterruptIfRunning);
        return b;
    }

    public V get() throws InterruptedException, ExecutionException {
        return computation.get();
    }

    public V get(long timeout, TimeUnit unit)
            throws InterruptedException,
            ExecutionException,
            TimeoutException {
        return computation.get(timeout, unit);
    }

    public boolean isCancelled() {
        boolean b = computation.isCancelled();
        return b;
    }

    public boolean isDone() {
        return computation.isDone();
    }

    public void run() {
        computation.run();
    }
}