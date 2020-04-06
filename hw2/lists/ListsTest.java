package lists;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {
    @Test
    public void testNaturalRuns(){

        int[][] ansL = {{1, 2, 3}, {3, 4, 5}, {4, 5, 6}};
        int[] Llist = {1, 2, 3, 3, 4, 5, 4, 5, 6};
        IntList L = IntList.list(Llist);
        IntListList ans = IntListList.list(ansL);
        IntListList myAns = HWLists.naturalRuns(L);
        assertTrue(myAns.equals(ans));
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
