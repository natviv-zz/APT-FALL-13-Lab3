import junit.framework.TestCase;

public class RationalTest extends TestCase {

    protected Rational HALF;
    int max= Integer.MAX_VALUE;
    int min=Integer.MIN_VALUE;

    protected void setUp() {
      HALF = new Rational( 1, 2 );
    }

    // Create new test
    public RationalTest (String name) {
        super(name);
    }
    // Test equals function
    public void testEquality() {
        assertEquals(new Rational(1,3), new Rational(1,3));
        assertEquals(new Rational(1,3), new Rational(2,6));
        assertEquals(new Rational(max,max), new Rational(min,min));
        assertEquals(new Rational(1,2), new Rational(-1,-2));
        assertEquals(new Rational(0,2), new Rational(0,-2));
        assertEquals(new Rational(0,max), new Rational(0,min));
    }
    // Test tolerance methods
    public void testtolerance(){
    	Rational tol= new Rational(0,1000);
    	Rational.setTolerance(tol);
    	assertEquals("Tolerance passed",Rational.getTolerance(),tol);
    	
    	
    }
    //Test for absolute
    public void testAbs(){
    	assertEquals(new Rational(1,2), new Rational(-1,2).abs());
    	assertEquals(new Rational(0,2), new Rational(0,-2).abs());
    }
    
    // Test for nonequality
    public void testNonEquality() {
        assertFalse(new Rational(2,3).equals(
            new Rational(1,3)));
    }

    public void testAccessors() {
    	assertEquals(new Rational(2,3).numerator(), 2);
    	assertEquals(new Rational(2,3).denominator(), 3);
    }
    //test equals function
    public void testEquals() {
    	
    	assertTrue(new Rational(2,3).equals(new Rational(2,3)));
    	assertTrue(new Rational(-2,-3).equals(new Rational(-2,-3)));
    	assertTrue(new Rational(0,3).equals(new Rational(0,1)));
    	assertTrue(new Rational(2,-3).equals(new Rational(-2,3)));
    	assertFalse(new Rational(2,-3).equals(null));
    	assertFalse(new Rational(2,-3).equals(max));
    	
    	
    }
    
    //Test plus
    public void testPlus(){
    	assertEquals(new Rational(0,1), new Rational(1,1).plus(new Rational(3,-3)));
    	assertEquals(new Rational(0,1), new Rational(3,-3).plus(new Rational(1,1)));
    	assertEquals(new Rational(-2,1),new Rational(3,-3).plus(new Rational(1,-1)));
        assertEquals(new Rational(0,1), new Rational(-2*max,2*max).plus(new Rational(max,max)));
    }
    
    //test times
    public void testTimes(){
    	assertEquals(new Rational(-1,1),new Rational(1,-min).times(new Rational(min,1)));
    }
    
    public void testDivide(){
    	assertEquals(new Rational(1,1), new Rational(min,1).divides(new Rational(min,1)));
    }
    // test for illegal arguments for divide (divide by zero)
    public void testDividebyzero(){
    	Rational s = new Rational( 1, max );
        Rational sdiv;
        try {
            sdiv = s.divides(new Rational(1,0));
            fail("Exception not caught");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 	
     }
    // test for is less than negative does not work for negative numbers
    public void testLessthan(){
    	assertTrue(new Rational(1,-2).isLessThan(new Rational(-1,4)));
    }
    
    //test root less than
    public void testRootinequality(){
    	Rational s1 = new Rational( 1, 4 );
    	Rational s2 = new Rational( 1, 2 );
        
    	Rational sRoot1 = null;
    	Rational sRoot2 = null;
        try {
            sRoot1 = s1.root();
            sRoot2 = s2.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        
        assertTrue( sRoot1.isLessThan(sRoot2) );
        //assertTrue( sRoot1.isLessThan(new Rational(1,1)) );
    }
    
    public void testRootlessthanone(){
    	Rational s1 = new Rational( 1, 4 );
    	Rational s2 = new Rational( 1, 2 );
        
    	Rational sRoot1 = null;
    	Rational sRoot2 = null;
        try {
            sRoot1 = s1.root();
            sRoot2 = s2.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        
        //assertTrue( sRoot1.isLessThan(sRoot2) );
        assertTrue( sRoot2.isLessThan(new Rational(1,1)) );
    }
    
    
    //test for illegal arguments for plus
    public void testplusinf(){
    	Rational s = new Rational( 1, 2 );
        Rational sp;
        try {
            sp = s.plus(new Rational(1,0));
            fail("Exception not caught");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 	
     }
    
    //test for illegal arguments for minus
    public void testMinusinf(){
    	Rational s = new Rational( 1, 2 );
        Rational sm;
        try {
            sm = s.minus(new Rational(1,0));
            fail("Exception not caught");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 	
     }
    //test for illegal arguments for times
    public void testTimesinf(){
    	Rational s = new Rational( 1, 2 );
        Rational st;
        try {
            st = s.times(new Rational(1,0));
            fail("Exception not caught");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 	
     }
    
    
    
    
    
    
    
    
    public void testMain(){
    	String[] args= new String[1];
    	args[0]="";
		try{
			Rational.main(args);
		} 
		finally{
		assertTrue(true);
		}
    }
        //Test Root
   
    
    
    public void testRootaftertolerance() {
        Rational.setTolerance(new Rational(1,100));
    	Rational s = new Rational( 1, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
      
    
    
    
    
    
    
    
    public void testRoot() {
        Rational s = new Rational( 1, 2 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
           // System.out.println("Testing half root "+ sRoot);
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
    
    //test root illegal argument
    public void testRoot2(){
    	Rational s = new Rational( 1, max );
        Rational sRoot;
        try {
            sRoot = s.root();
            fail("Exception not caught");
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        } 	
    }
   
    public void testRootzero(){
    	try{
    		
    	assertEquals(new Rational(0,1),new Rational(0,1).root());
    	} catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        } 	
    	
    }
    
    
    
    
    
    
    
    

    public static void main(String args[]) {
        String[] testCaseName = 
            { RationalTest.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
    }
}
