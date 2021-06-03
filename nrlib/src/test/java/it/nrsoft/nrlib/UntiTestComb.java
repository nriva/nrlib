package it.nrsoft.nrlib;

import org.junit.Assert;
import org.junit.Test;

import it.nrsoft.nrlib.combinatorial.*;


public class UntiTestComb {
	
	private static final char[] SYMBOLS = new char[] {'A','B','C','D'};


	@Test
	public void test0() {
		Assert.assertEquals(CombinatorialCalc.Fact(10),3628800);
		Assert.assertEquals(CombinatorialCalc.Pow(3, 2),9);
		Assert.assertEquals(CombinatorialCalc.BinomialFactor(4, 1),4);
	}	


	@Test
	public void test1() {
		
		CombinationSubjectList comb1 = new CombinationSubjectList(4, 2);
		comb1.generate();
		Assert.assertEquals(comb1.getCardinality(), comb1.getWordList().size());
//		for(int[] word : comb1.getWordList())
//			System.out.println(Util.wordAsString(word, SYMBOLS));
		
//		System.out.println();
		
		CombinationRepSubjectList comb2 = new CombinationRepSubjectList(4, 2);
		comb2.generate();
		Assert.assertEquals(comb2.getCardinality(), comb2.getWordList().size());
//		for(int[] word : comb2.getWordList())
//			System.out.println(Util.wordAsString(word, SYMBOLS));
		
//		System.out.println();
		
		DispositionSubjectList disp1 = new DispositionSubjectList(4, 2);
		disp1.generate();
		Assert.assertEquals(disp1.getCardinality(), disp1.getWordList().size());
//		for(int[] word : disp1.getWordList())
//			System.out.println(Util.wordAsString(word, SYMBOLS));
		
//		System.out.println();
		
		PermutationSubjectList perm1 = new PermutationSubjectList(4);
		perm1.generate();
		Assert.assertEquals(perm1.getCardinality(), perm1.getWordList().size());
//		for(String word : Util.wordListAsString(perm1.getWordList(),SYMBOLS))
//			System.out.println(word);
	}



}
