package it.nrsoft.nrlib.concurrent;

/**
 * 
 * @author Doug Lea
 *
 * @param <A>
 * @param <V>
 */
public interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}