package enigma;

/**
 * Class that represents a rotating rotor in the enigma machine.
 *
 * @author Noe Trejo-Cruz :)
 */
class MovingRotor extends Rotor {

    /**
     * A String of notches
     * that acts somewhat like a List.
     */
    private String notchesL;

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        notchesL = notches;
    }

    @Override
    boolean atNotch() {
        Alphabet alph = permutation().alphabet();
        return notchesL.indexOf(alph.toChar(setting())) != -1;
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    void advance() {
        set(permutation().wrap(setting() + 1));
    }

}
