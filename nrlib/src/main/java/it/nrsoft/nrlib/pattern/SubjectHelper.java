package it.nrsoft.nrlib.pattern;

import java.util.*;

public class SubjectHelper implements Subject {
	
	private List<Observer> observers = new ArrayList<Observer>(5);

	@Override
	public void notify(Object obj) {
		for(Observer obs : observers)
			obs.Update(obj);

	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);

	}

}
