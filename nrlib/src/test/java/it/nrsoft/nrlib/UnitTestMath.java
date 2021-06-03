package it.nrsoft.nrlib;

import static org.junit.Assert.*;

import org.junit.Test;

import it.nrsoft.nrlib.math.RomanNotation;
import it.nrsoft.nrlib.math.RomanNumeral;

public class UnitTestMath {
	
	@Test
	public void test1()
	{	 
		assertEquals("XCIX", (new RomanNumeral(99)).toString());
		assertEquals("MCMXCIX", RomanNotation.intToRoman(1999));
		assertEquals("MCMXC", RomanNotation.intToRoman(1990));
		
		assertEquals(199, (new RomanNumeral("CIC")).toInt());
	}

}
