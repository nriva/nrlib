package it.nrsoft.nrlib.matrix;

import it.nrsoft.nrlib.math.Matrix;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MatrixUnitTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MatrixUnitTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MatrixUnitTest.class );
    }

    /**
     * 
     */
    public void testApp()
    {
        Matrix a = new Matrix(3,3);
        a.set(new int[][]{{1,2,3}
        					,{4,5,6}
        					,{7,8,9}});
        
        Matrix b1 = new Matrix(3,1);
        b1.set(new int[][]{{1},{4},{7}});
        
        Matrix b2 = new Matrix(3,1);
        b2.set(new int[][]{{2},{5},{8}});
        
        
        assertEquals(0,Matrix.match(a,b1));
        assertEquals(1,Matrix.match(a,b2));
        
        Matrix c = new Matrix(3,1);
        c.set(new int[][]{{1},{4},{9}});
        
        
        assertEquals(-1,Matrix.match(a,c));
    }
}
