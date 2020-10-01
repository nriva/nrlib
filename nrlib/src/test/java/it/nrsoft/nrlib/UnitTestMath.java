package it.nrsoft.nrlib;

import static org.junit.Assert.*;

import org.junit.Test;

import it.nrsoft.nrlib.math.RomanNotation;

public class UnitTestMath {
	
	// @Test
	public void test1()
	{
		
		assertEquals("CIC", RomanNotation.intToRoman(199));
		assertEquals("MIM", RomanNotation.intToRoman(1999));
		assertEquals("MXM", RomanNotation.intToRoman(1990));
	}

}
