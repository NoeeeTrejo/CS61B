package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author
 */
class HWLists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        if (L == null) {
            return null;
        }
        if (L.tail == null){
            IntList head = new IntList(L.head, null);
            return new IntListList(head, null);
        }

        IntList ref = L;
        for(IntList pointer = L.tail;
                pointer != null;
                pointer = pointer.tail,
                ref = ref.tail){

            if (ref.head >= pointer.head){
                ref.tail = null;
                return new IntListList(L, HWLists.naturalRuns(pointer));
            }
        }
        return new IntListList(L, null);
    }


}
