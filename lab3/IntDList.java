import java.awt.dnd.DnDConstants;

/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Vivant Sakore (1/29/2020)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        DNode traveler = _front;
        int size = 0;
        if (traveler == null) {
            return 0;
        }
        if (_front == _back) {
            return 1;
        }
        for (; traveler != null; traveler = traveler._next) {
            size++;
        }
        return size;


    }

    /**
     * @param i index of element to return,
     *          where i = 0 returns the first element,
     *          i = 1 returns the second element,
     *          i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size for positive indices
     *          and -size <= i <= -1 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        DNode traverser;

        if (i < 0) {
            traverser = _back;
            for (int counter = 0; counter < Math.abs(i) - 1; counter++) {
                traverser = traverser._prev;
            }
            return traverser._val;

        } else if (i == 0) {
            return getFront();
        } else {
            traverser = _front;
            for (int counter = 0; counter < Math.abs(i); counter++) {
                traverser = traverser._next;
            }
            return traverser._val;
        }
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        DNode insert = new DNode(null, d, null);
        if ((_front == null) && (_back == null)) {
            insertBack(d);
            return;
        }
        insert._next = _front;
        _front._prev = insert;
        _front = insert;
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        DNode insert = new DNode(null, d, null);
        if (_back == null && _front == null) {
            _front = _back = insert;
            return;
        }
        insert._prev = _back;
        _back._next = insert;
        _back = insert;

    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position, and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices (including insertions at front and back)
     *              and -(size+1) <= index <= -1 for negative indices (including insertions at front and back).
     */

    public DNode findIndex(int index, String dir){
        DNode traveler = new DNode(0);
        if (dir == "b"){
            traveler = _back;
            for (int counter = 0; counter < Math.abs(index) - 1; counter++){
                traveler = traveler._prev;
            }
            return traveler;
        }
        if (dir == "f") {
            traveler = _front;
            for (int counter = 0; counter < index; counter++){
                traveler = traveler._next;
            }
            return traveler;
        }
        return traveler;
    }

    public void insertAtIndex(int d, int index) {
        DNode insert = new DNode(null, d, null);
        DNode traveller;

        if (Math.abs(index) > this.size()){
            if (index < 0){
                index = Math.abs(index) - (this.size() + 1);
            }
            else {
                index = index - (this.size() + 1);
            }}
        if ((index == 0 || (_front == null && _back == null)) || index == -(size())) {
            insertFront(d);
            return;
        } else if ((index == -1) || (index == this.size() )) {
            insertBack(d);
            return;
        }
        DNode inFront;
        DNode behind;
        if (index < 0){
            traveller = findIndex(index, "b");
            behind = traveller;
            inFront = behind._next;
        }
        else{
            traveller = findIndex(index, "f");
            behind = traveller._prev;
            inFront = traveller;
        }
        behind._next = insert;
        inFront._prev = insert;
        insert._prev = behind;
        insert._next = inFront;
        return;
        }

    /**
     * Removes the first item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        int val = _front._val;
        if (this.size() == 1){
            _front = _back = null;
            return val;
        }
        _front._next._prev = null;
        _front = _front._next;
        return val;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        int val = _back._val;
        if (size() == 1){
            _front = _back = null;
            return val;
        }
        _back._prev._next = null;
        _back = _back._prev;
        return val;
    }

    /**
     * @param index index of element to be deleted,
     *              where index = 0 returns the first element,
     *              index = 1 will delete the second element,
     *              index = -1 will delete the last element,
     *              index = -2 will delete the second to last element, and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices (including deletions at front and back)
     *              and -size <= index <= -1 for negative indices (including deletions at front and back).
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        DNode inFront;
        DNode Behind;
        DNode traverser;
        if (index == 0) {
            return deleteFront();
        } else if (index == -1) {
            return deleteBack();
        } else {
            if ( index < 0){
                traverser = findIndex(index, "b");
                inFront = traverser._next;
                Behind = traverser._prev;
            }
            else{
                traverser = findIndex(index, "f");
            }
            inFront = traverser._next;
            Behind = traverser._prev;
            Behind._next = inFront;
            inFront._prev = Behind;
            return traverser._val;
        }
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        String str = "[";
        DNode curr = _front;
        for (; curr._next != null; curr = curr._next) {
            str += curr._val + ", ";
        }
        str += curr._val +"]";
        return str;
    }
    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
