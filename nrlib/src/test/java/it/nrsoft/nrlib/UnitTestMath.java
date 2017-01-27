package it.nrsoft.nrlib;

import static org.junit.Assert.*;

import org.junit.Test;

import it.nrsoft.nrlib.math.RomanNotation;
import it.nrsoft.nrlib.math.RomanNumeral;

public class UnitTestMath {
	
	@Test
	public void test1()
	{	// CXCIX
		assertEquals("CIC", (new RomanNumeral(99)).toString());
//		assertEquals("MIM", RomanNotation.intToRoman(1999));
//		assertEquals("MXM", RomanNotation.intToRoman(1990));
		
		assertEquals(199, (new RomanNumeral("CIC")).toInt());
	}

}
