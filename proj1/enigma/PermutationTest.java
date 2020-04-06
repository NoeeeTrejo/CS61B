package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static enigma.TestUtils.*;
import static org.junit.Assert.*;

/**
 * The suite of all JUnit tests for the Permutation class.
 *
 * @author
 */
public class PermutationTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);
    private Permutation perm;
    private String alpha = UPPER_STRING;

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     *
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    Permutation getNewPermutation(String cycles, Alphabet alphabet) {
        return new Permutation(cycles, alphabet);
    }
    /* ***** TESTING UTILITIES ***** */

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     *
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet(String chars) {
        return new Alphabet(chars);
    }

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     *
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    Alphabet getNewAlphabet() {
        return new Alphabet();
    }

    /**
     * Check that perm has an alphabet whose size is that of
     * FROMALPHA and TOALPHA and that maps each character of
     * FROMALPHA to the corresponding character of FROMALPHA, and
     * vice-versa. TESTID is used in error messages.
     */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void labTests() {
        alpha = "NOE";
        perm = getNewPermutation("(NO) (E)", getNewAlphabet(alpha));
        checkPerm("My name!", "NOE", "ONE");
        assertFalse(perm.derangement());


        alpha = "TREJO";
        perm = getNewPermutation("(RE) (TJ) (O)", getNewAlphabet(alpha));
        checkPerm("My last name!", "TREJO", "JERTO");
        assertFalse(perm.derangement());


        alpha = "HIWORLD";
        perm = getNewPermutation("(HIWORLD)", getNewAlphabet(alpha));
        checkPerm("Alphabet = permutation", "HIWORLD", "IWORLDH");
        assertTrue(perm.derangement());

    }

}
