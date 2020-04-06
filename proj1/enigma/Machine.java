package enigma;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Noe Trejo-Cruz :)
 */
class Machine {

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;
    /**
     * Starting settings of the rotor.
     */
    private Permutation plugBoard;
    /**
     * An array of all the Rotors.
     */
    private Rotor[] _allRotors;
    /**
     * numbers of rotors.
     */
    private int _numRotors;
    /**
     * Common rotor pawls.
     */
    private int _pawls;
    /**
     * An ArrayList that acts like a
     * Python 3 List to access rotors
     * and add rotors.
     */
    private ArrayList<Rotor> rotor;

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        this._alphabet = alpha;
        this._pawls = pawls;
        this._numRotors = numRotors;
        this._allRotors = new Rotor[allRotors.size()];
        int i = 0;
        for (Rotor rot : allRotors) {
            this._allRotors[i] = rot;
            i++;
        }
    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return this._numRotors;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return this._pawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        ArrayList<String> rNames = new ArrayList<>();
        this.rotor = new ArrayList<>();
        for (String name : rotors) {
            for (Rotor rot : this._allRotors) {
                if (name.equals(rot.name())) {
                    rot.set(0);
                    this.rotor.add(rot);
                    if (rNames.contains(name)) {
                        throw new EnigmaException("A rotor has the same"
                                                  + " name as another one ");
                    } else {
                        rNames.add(name);
                    }
                }
            }
        }
        if (this.rotor.size() != rotors.length) {
            throw new EnigmaException("bad rotor name");
        }
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 characters in my alphabet. The first letter refers
     * to the leftmost rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {
        if (setting.length() != (numRotors() - 1)) {
            throw new EnigmaException("The wheel setting is too short");
        }
        if (!rotor.get(0).reflecting()) {
            throw new EnigmaException("You're MISSING the reflector!!!");
        } else {
            if (setting.length() == (numRotors() - 1)) {
                for (int i = 1; i < rotor.size(); i++) {
                    Rotor r = rotor.get(i);
                    if (i < numRotors() - numPawls()) {
                        if (!r.reflecting()
                            && !r.rotates()) {
                            r.set(_alphabet.toInt
                                    (setting.charAt(i - 1)));
                        } else {
                            throw new EnigmaException("Non-moving rotor"
                                                      + " slots mismatched.");
                        }
                    } else if (i >= numRotors() - numPawls()) {
                        if (r.rotates()) {
                            r.set(_alphabet.toInt
                                    (setting.charAt(i - 1)));
                        } else {
                            throw new EnigmaException("SLOT MISMATCH: "
                                                      + "Non moving rotor");
                        }
                    }
                }
            } else {
                throw new EnigmaException("Wrong length of settings.");
            }
        }
    }


    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugboard) {
        this.plugBoard = plugboard;
    }

    /**
     * A function that advances all moving rotors
     * while taking in account double stepping.
     */
    void moveRotors() {
        ArrayList<Rotor> mRotors = new ArrayList<>();
        for (int i = numRotors() - numPawls(); i < numRotors(); i++) {
            Rotor r = rotor.get(i);
            if (i == numRotors() - 1) {
                mRotors.add(r);
            } else {
                Rotor succ = rotor.get(i + 1);
                Rotor prev = rotor.get(i - 1);
                if (mRotors.contains(prev) || succ.atNotch()) {
                    if (!mRotors.contains(r)) {
                        mRotors.add(r);
                    }
                    if (r.atNotch()
                        && !mRotors.contains(prev)) {
                        mRotors.add(prev);
                    }
                }
            }
        }
        for (Rotor r : mRotors) {
            r.advance();
        }
    }

    /**
     * Just a helper function to
     * help me make the convert function a little
     * shorter and easier to understand for
     * my own sake + I don't feel like writing
     * the same code twice. :)
     *
     * @param start start of the for loop.
     * @param end   is the end of the for loop.
     * @param cond  the condition for the for loop.
     * @param dir   whether to increase using ++ or --.
     * @param c     the c being passed in
     * @return the updated value of c
     */
    int cConverter(int start, int end, String cond, String dir, int c) {
        if (cond.equals("")) {
            throw new EnigmaException("Uhh lol you"
                                      + "didn't put anything"
                                      + "into the helper func");
        }
        if (cond.equals(">=") && dir.equals("--")) {
            for (int i = start; i >= end; i--) {
                Rotor r = rotor.get(i);
                c = r.convertForward(c);
            }
        }
        if (cond.equals("<") && dir.equals("++")) {
            for (int i = start; i < rotor.size(); i++) {
                Rotor r = rotor.get(i);
                c = r.convertBackward(c);
            }
        }
        return c;
    }

    /** Simple function so that I stick to
     * general code conduct! Don't wanna rewrite the same
     * code
     * @param c is the c being passed in
     * @return is the altered c*/
    int plugboardNullCheck(int c) {
        if (plugBoard != null) {
            c = plugBoard.permute(c);
        }
        return c;
    }


    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * the machine. That consists of helper functions because I thought
     * it would be fun :)
     */
    int convert(int c) {
        moveRotors();
        c = c % _alphabet.size();
        c = plugboardNullCheck(c);
        c = cConverter(rotor.size() - 1, 0, ">=", "--", c);
        c = cConverter(1, rotor.size(), "<", "++", c);
        c = plugboardNullCheck(c);
        return c;
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     * NOTE/CITATION: I didn't know how to convert Strings into
     * chars so I searched it up:
     * https://www.techiedelight.com/convert-string-list-characters-java/.
     */
    String convert(String msg) {
        String rS = "";
        for (int i = 0; i < msg.length(); i++) {
            int s = _alphabet.toInt(msg.charAt(i));
            rS += _alphabet.toChar(convert(s));
        }

        return rS;
    }
}
