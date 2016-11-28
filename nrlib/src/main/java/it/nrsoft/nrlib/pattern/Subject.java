package it.nrsoft.nrlib.pattern;

public interface Subject
{
	/**
	 * Metodo per la notifica di un evento a tutti gli Observer.
	 * @param obj
	 */
	void notify(Object obj);
	
	/**
	 * Metodo che aggiunge un oggetto Observer a questo Observable.
	 * @param observer
	 */
	void addObserver(Observer observer);
	
	/**
	 * Metodo che toglie un oggetto Observer a questo Observable.
	 * @param observer
	 */
	void removeObserver(Observer observer);
}