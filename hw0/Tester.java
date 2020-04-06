import org.junit.Test;
import static org.junit.Assert.*;

import ucb.junit.textui;

/** Tests for hw0. 
 *  @author Noe Trejo-Cruzw
 */
public class Tester {

    /* Feel free to add your own tests.  For now, you can just follow
     * the pattern you see here.  We'll look into the details of JUnit
     * testing later.
     *
     * To actually run the tests, just use
     *      java Tester 
     * (after first compiling your files).
     *
     * DON'T put your HW0 solutions here!  Put them in a separate
     * class and figure out how to call them from here.  You'll have
     * to modify the calls to max, threeSum, and threeSumDistinct to
     * get them to work, but it's all good practice! */

    @Test
    public void maxTest() {
        hw0 test = new hw0();
        // Change call to max to make this call yours.
        assertEquals(14, test.max(new int[] { 0, -5, 2, 14, 10 }));
        assertEquals(2, test.max(new int[]{1, 2}));
    }

    @Test
    public void threeSumTest() {
        hw0 test = new hw0();
        // Change call to threeSum to make this call yours.
        assertTrue(test.threeSum(new int[] { -6, 3, 10, 200 }));
        assertTrue(test.threeSum(new int[]{-6, 2, 4}));
        assertFalse(test.threeSum(new int[]{-6, 2, 5}));
        assertTrue(test.threeSum(new int[]{-6, 3, 10, 200}));
        assertTrue(test.threeSum(new int[]{8, 2, -1, 15}));
        assertTrue(test.threeSum(new int[]{8, 2, -1, -1, 15}));
        assertTrue(test.threeSum(new int[]{5, 1, 0, 3, 6}));
        assertFalse(test.threeSum(new int[]{1, 2}));
    }

    @Test
    public void threeSumDistinctTest() {
        hw0 test = new hw0();
        // Change call to threeSumDistinct to make this call yours.
        assertFalse(test.threeSumDistinct(new int[] { -6, 3, 10, 200 }));
        assertFalse(test.threeSumDistinct(new int[]{-6, 2, 5}));
        assertFalse(test.threeSumDistinct(new int[]{8, 2, -1, 15}));
        assertTrue(test.threeSumDistinct(new int[]{8, 2, -1, -1, 15}));
        assertFalse(test.threeSumDistinct(new int[]{5, 1, 0, 3, 6}));
        assertTrue(test.threeSumDistinct(new int[]{-6, 2, 4}));

    }
    public static void main(String[] unused) {
        textui.runClasses(Tester.class);
    }
}
