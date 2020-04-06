package arrays;

import org.junit.Test;
import static org.junit.Assert.*;


/** FIXME
 *  @author FIXME
 */

public class ArraysTest {

    @Test
    public void testCatenate() {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {5, 6, 7, 8};
        int[] ans = {1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(ans, Arrays.catenate(arr1, arr2));
        ans = new int[]{5, 6, 7, 8, 1, 2, 3, 4};
        assertArrayEquals(ans, Arrays.catenate(arr2, arr1));

        ans = new int[]{};
        assertArrayEquals(ans, Arrays.catenate(new int[]{}, new int[]{}));

        ans = new int[]{1, 2, 3};
        assertArrayEquals(ans, Arrays.catenate(new int[]{}, ans));
    }

    @Test
    public void testRemove(){
        int[] test = {1, 2, 3, 4, 5};
        int[] ans = {1, 2, 5};
        assertArrayEquals(ans, Arrays.remove(test, 2, 2));

        ans = new int[0];
        assertArrayEquals(ans, Arrays.remove(test, 0, 5));
    }

    @Test
    public void testNaturalRuns(){
        int[][] ansL = {{1, 2, 3}, {3, 4, 5}, {4, 5, 6}};
        int[] Llist = {1, 2, 3, 3, 4, 5, 4, 5, 6};
        assertArrayEquals(ansL, Arrays.naturalRuns(Llist));

        ansL = new int[][]{{}};
        Llist = new int[]{};
        assertArrayEquals(ansL, Arrays.naturalRuns(Llist));

        Llist = new int[]{1};
        ansL = new int[][]{{1}};
        assertArrayEquals(ansL, Arrays.naturalRuns(Llist));
    }



    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
