import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] test = {{1,3,4},{1},{5,6,7,8},{7,9}};
        assertEquals(9, MultiArr.maxValue(test));
        test = new int[][] {{1, 2, 3}, {1, 12, 123, 1234}, {5, 6, 7, 8}, {7, 9}};
        assertEquals(1234, MultiArr.maxValue(test));

    }

    @Test
    public void testAllRowSums() {
        int[][] test = {{1,3,4},{1},{5,6,7,8},{7,9}};
        int[] ans = {8, 1, 26, 16};
        assertArrayEquals(ans, MultiArr.allRowSums(test));
        test = new int[][]{{1, 2, 3}, {1, 12, 123, 1234}, {5, 6, 7, 8}, {7, 9}};
        ans = new int[] {6, 1370, 26, 16};
        assertArrayEquals(ans, MultiArr.allRowSums(test));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
