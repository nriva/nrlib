package it.nrsoft.nrlib.tuples;

public class Pair<T1, T2> {
	
	private T1 member1;
	
	private T2 member2;

	public T1 getMember1() {
		return member1;
	}

	public T2 getMember2() {
		return member2;
	}

	public Pair(T1 member1, T2 member2) {
		super();
		this.member1 = member1;
		this.member2 = member2;
	}
	
	

}
