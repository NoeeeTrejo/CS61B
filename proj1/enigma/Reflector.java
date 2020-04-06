package enigma;

import static enigma.EnigmaException.error;

/**
 * Class that represents a reflector in the enigma.
 *
 * @author Noe Trejo-Cruz :)
 */
class Reflector extends FixedRotor {

    /**
     * A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM.
     */
    Reflector(String name, Permutation perm) {
        super(name, perm);
        if (!perm.derangement()) {
            throw new EnigmaException("This permutation maps to itself.");
        }
    }

    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

    @Override
    boolean reflecting() {
        return true;
    }
}
