package enigma;

import java.util.HashMap;

/**
 * An alphabet of encodable characters.  Provides a mapping from characters
 * to and from indices into the alphabet.
 *
 * @author Noe Trejo :)
 */
class Alphabet {

    /**
     * A new alphabet containing CHARS.  Character number #k has index
     * K (numbering from 0). No character may be duplicated.
     */
    Alphabet(String chars) {
        this._chars = chars;
        if (this._chars.length() == 0) {
            throw new EnigmaException("Alphabet inputs are null");
        }
        HashMap<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < this._chars.length(); i++) {
            char ch = chars.charAt(i);
            if (ch != '('
                && ch != ')'
                && ch != '*'
                && ch != ' '
                && !m.containsKey(ch)) {
                m.put(ch, i);
            } else {
                throw new EnigmaException("Alphabet has characters that "
                                          + "shouldn't be in there");
            }
        }
        this.indexes = m;
        this.characters = new HashMap<>();
        for (char ch : indexes.keySet()) {
            characters.put(indexes.get(ch), ch);
        }
    }

    /**
     * A default alphabet of all upper-case characters.
     */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * Returns the size of the alphabet.
     */
    int size() {
        return characters.size();
    }

    /**
     * Returns true if CH is in this alphabet.
     */
    boolean contains(char ch) {
        return characters.containsValue(ch);
    }

    /**
     * Returns character number INDEX in the alphabet, where
     * 0 <= INDEX < size().
     */
    char toChar(int index) {
        if (index >= 0 && index < this.size()) {
            return characters.get(index);
        } else {
            throw new EnigmaException("Index out of range");
        }
    }

    /**
     * Returns the index of character CH which must be in
     * the alphabet. This is the inverse of toChar().
     */
    int toInt(char ch) {
        if (characters.containsValue(ch)) {
            return indexes.get(ch);
        } else {
            throw new EnigmaException("That character isn't in the Alphabet!");
        }
    }

    /** A Hashmap that acts as a dictionary to
     * store what indexes each character has.*/
    private HashMap<Character, Integer> indexes;

    /** A Hashmap that acts as a dictionary to store
     * the characters and their indexes.*/
    private HashMap<Integer, Character> characters;

    /** The Alphabet of characters that are 'encodable'.*/
    private Alphabet _alphabet;

    /** A String for characters.*/
    private String _chars;


}
