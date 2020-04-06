import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 *
 * @author Noe Trejo
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /**
     * Creates a new empty set.
     */
    public BSTStringSet() {
        _root = null;
    }

    private Node putHelper(String s) {
        if (_root == null || s == null) {
            return null;
        }
        Node pointer = _root;
        while (true) {
            int c = s.compareTo(pointer.s);
            Node next;
            if (c < 0) {
                next = pointer.left;
            } else if (c > 0) {
                next = pointer.right;
            } else {
                return pointer;
            }
            if (next == null) {
                return pointer;
            } else {
                pointer = next;
            }
        }
    }

    @Override
    public void put(String s) {
        Node n = putHelper(s);
        if (n == null) {
            _root = new Node(s);
        } else {
            int c = s.compareTo(n.s);
            if (c > 0) {
                n.right = new Node(s);
            } else if (c < 0) {
                n.left = new Node(s);
            }
        }
    }

    @Override
    public boolean contains(String s) {
        Node n = putHelper(s);
        return n != null && s.equals(n.s);
    }

    @Override
    public List<String> asList() {
        ArrayList<String> l = new ArrayList<>();
        for (String s : this) {
            l.add(s);
        }
        return l;
    }


    /**
     * Represents a single Node of the tree.
     */
    private static class Node {
        /**
         * String stored in this Node.
         */
        private String s;
        /**
         * Left child of this Node.
         */
        private Node left;
        /**
         * Right child of this Node.
         */
        private Node right;

        /**
         * Creates a Node containing SP.
         */
        Node(String sp) {
            s = sp;
        }
    }

    /**
     * An iterator over BSTs.
     */
    private static class BSTIterator implements Iterator<String> {
        /**
         * Stack of nodes to be delivered.  The values to be delivered
         * are (a) the label of the top of the stack, then (b)
         * the labels of the right child of the top of the stack inorder,
         * then (c) the nodes in the rest of the stack (i.e., the result
         * of recursively applying this rule to the result of popping
         * the stack.
         */
        private Stack<Node> _toDo = new Stack<>();

        /**
         * A new iterator over the labels in NODE.
         */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Add the relevant subtrees of the tree rooted at NODE.
         */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    @Override
    public Iterator<String> iterator(String low, String high) {
        return new Helper(_root, low, high);
    }

    private class Helper implements Iterator<String> {
        public Helper(Node n, String low, String high) {
            constructorHelper(n, low, high);
        }
        private void constructorHelper(Node n, String low, String high){
          if (n != null){
            int left = low.compareTo(n.s);
            int right = high.compareTo(n.s);
            if (left < 0){
              constructorHelper(n.left, low, high);
            }
            if (left <= 0 && right > 0){
              lst.add(n.s);
            }
            if (right > 0){
              constructorHelper(n.right, low, high);
            }
          }
        }

        private String _low;
        private String _high;
        private Stack<Node> _toDo = new Stack<>();
        private ArrayList<String> lst = new ArrayList<>();
        private Node _current;

        @Override
        public boolean hasNext() {
            return lst.size() > 0;
//            return !_toDo.isEmpty()
//                    || (_current != null
//                    && _current.s.compareTo(_high) >= 0);
        }

        @Override
        public String next() {
            return lst.remove(0);
//            for (; _current != null
//                    && _current.s.compareTo(_low) >= 0;
//                 _current = _current.left) {
//                _toDo.push(_current);
//            }
//            Node r = _toDo.pop();
//            _current = r.right;
//            return r.s;
        }
    }


    /**
     * Root node of the tree.
     */
    private Node _root;
}
