package enigma;

/**
 * Superclass that represents a rotor in the enigma machine.
 *
 * @author Noe Trejo-Cruz :)
 */
class Rotor {

    /**
     * My name.
     */
    private final String _name;
    /**
     * The permutation implemented by this rotor in its 0 position.
     */
    private Permutation _permutation;
    /**
     * the settings of the rotor.
     */
    private int setting;

    /**
     * A rotor named NAME whose permutation is given by PERM.
     */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
    }

    /**
     * Return my name.
     */
    String name() {
        return _name;
    }

    /**
     * Return my alphabet.
     */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /**
     * Return my permutation.
     */
    Permutation permutation() {
        return _permutation;
    }

    /**
     * Return the size of my alphabet.
     */
    int size() {
        return _permutation.size();
    }

    /**
     * Return true iff I have a ratchet and can move.
     */
    boolean rotates() {
        return false;
    }

    /**
     * Return true iff I reflect.
     */
    boolean reflecting() {
        return false;
    }

    /**
     * Return my current setting.
     */
    int setting() {
        return setting;
    }

    /**
     * Set setting() to POSN.
     */
    void set(int posn) {
        setting = posn;
    }

    /**
     * Set setting() to character CPOSN.
     */
    void set(char cposn) {
        alphabet().toInt(cposn);
        setting = cposn;
    }

    /**
     * Return the conversion of P (an integer in the range 0..size()-1)
     * according to my permutation.
     */
    int convertForward(int p) {
        int conv = permutation().permute(permutation().wrap(p + setting));
        return permutation().wrap(conv - setting);
    }

    /**
     * Return the conversion of E (an integer in the range 0..size()-1)
     * according to the inverse of my permutation.
     */
    int convertBackward(int e) {
        int conv = permutation().invert(permutation().wrap(e + setting));
        return permutation().wrap(conv - setting);
    }

    /**
     * Returns true iff I am positioned to allow the rotor to my left
     * to advance.
     */
    boolean atNotch() {
        return false;
    }

    /**
     * Advance me one position, if possible. By default, does nothing.
     */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

}
