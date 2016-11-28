package it.nrsoft.nrlib.combinatorial;

import java.util.ArrayList;

import it.nrsoft.nrlib.pattern.Observer;

public final class WordList extends ArrayList<int[]> implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5643241691469772017L;

	public void Update(Object subject) {
		add(((int[])subject).clone());

	}

}
