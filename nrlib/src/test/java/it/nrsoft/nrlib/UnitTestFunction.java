package it.nrsoft.nrlib;

import org.junit.Assert;
import org.junit.Test;

import it.nrsoft.nrlib.util.Functions;


public class UnitTestFunction {

	@Test
	public void test0() throws Exception {

			Assert.assertEquals(new Integer(4), Functions.Max( Functions.integerComparator , 1,2,3,4));
			
			Assert.assertEquals(new Integer(1), Functions.Min( Functions.integerComparator , 1,2,3,4));
			
			Assert.assertEquals(new Integer(1), Functions.Coalesce(null,1));

	}

}
