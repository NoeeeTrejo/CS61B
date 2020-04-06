package enigma;

import java.util.ArrayList;

/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author Noe Trejo-Cruz :)
 */
class Permutation {
    /**
     * Alphabet of this permutation.
     */
    private int _ring = 0;

    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;
    /**
     * Size of the alphabet being used.
     */
    private int _size;
    /**
     * ArrayList so I can access the sorted list easily.
     */
    private ArrayList<Integer> sortL;

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        sortL = new ArrayList<>();
        for (int i = 0; i < alphabet().size(); i++) {
            sortL.add(i);
        }
        cycles = cycles.replaceAll("[)(]", "");
        String[] arrOfStr = cycles.split(" ");
        for (String s : arrOfStr) {
            addCycle(s);
        }
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {
        for (int i = 0; i < cycle.length(); i++) {
            char ch = cycle.charAt(i);
            int s;
            if (cycle.length() < 1) {
                s = alphabet().toInt(cycle.charAt(i));
            } else if (i != cycle.length() - 1) {
                s = alphabet().toInt(cycle.charAt(i + 1));
            } else {
                s = alphabet().toInt(cycle.charAt(0));
            }
            sortL.set(alphabet().toInt(ch), s);
        }
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return alphabet().size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        return sortL.get(wrap(p));
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        return sortL.indexOf(wrap(c));
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        int index = alphabet().toInt(p);
        return alphabet().toChar(permute(index));
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        int index = alphabet().toInt(c);
        return alphabet().toChar(invert(index));
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        for (int i : sortL) {
            if (sortL.indexOf(i) == i) {
                return false;
            }
        }
        return true;
    }
}
