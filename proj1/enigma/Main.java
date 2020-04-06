package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.error;

/**
 * Enigma simulator.
 *
 * @author Noe Trejo :)
 */
public final class Main {

    /**
     * Rotors of the machine.
     */
    private int _rotors;
    /**
     * Pawls of the machine.
     */
    private int _pawls;
    /**
     * All of the rotors.
     */
    private Collection<Rotor> _allrotors;
    /**
     * Settings for the machine.
     */
    private String _settings;
    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;
    /**
     * Source of input messages.
     */
    private Scanner _input;
    /**
     * Source of machine configuration.
     */
    private Scanner _config;
    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine xMach = readConfig();
        _config.close();
        while (_input.hasNextLine()) {
            if (_input.hasNext("\\s*[*].*")) {
                String l = "";
                String settings = "";
                boolean s = false;

                while (!s) {
                    l = _input.nextLine();
                    if (l.matches("[*].+")) {
                        s = true;
                    } else {
                        printMessageLine(l);
                    }
                }
                int i = 0;
                Scanner line = new Scanner(l);
                while ((i < (xMach.numRotors() + 2))
                       || line.hasNext("[(].+[)]")) {
                    if (!line.hasNext()) {
                        throw new EnigmaException("missing some rotors");
                    }
                    settings += line.next().replaceAll("[*]", "* ") + " ";
                    i++;
                }
                setUp(xMach, settings.substring(0, settings.length() - 1));
                while (!_input.hasNext("[*]") && _input.hasNextLine()) {
                    String next = _input.nextLine();
                    next = next.replaceAll("\\s+", "");
                    printMessageLine(xMach.convert(next));
                }
            } else {
                throw new EnigmaException("Config missing '*' ");
            }
        }
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    private Machine readConfig() {
        try {
            _allrotors = new ArrayList<>();
            String p = "\\S+";
            if (_config.hasNext(p)) {
                _alphabet = new Alphabet(_config.next());
                if (_config.hasNextInt()) {
                    _rotors = _config.nextInt();
                    if (_config.hasNextInt()) {
                        _pawls = _config.nextInt();
                        p = ".+";
                        while (_config.hasNext(p)) {
                            _allrotors.add(readRotor());
                        }
                    } else {
                        throw new EnigmaException("Number of rotors"
                                                  + "not passed in");
                    }
                } else {
                    throw new EnigmaException("Number of rotors"
                                              + "not passed in");
                }
            }
            return new Machine(_alphabet, _rotors, _pawls, _allrotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }


    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        try {
            String p = "\\s*[(].+[)]\\s*";
            String p2 = "\\s*[(]\\w+[)]\\s*";
            String names = _config.next();
            String next = _config.next();
            String c = "";
            String notches = "";
            if (next.charAt(0) == 'M') {
                notches += next.substring(1);
                while (_config.hasNext(p)) {
                    String nxt = _config.next();
                    String s = nxt.replaceAll("[)][(]", ") (");
                    c += s + " ";
                }
                Permutation perm = new Permutation(c, _alphabet);
                return new MovingRotor(names, perm, notches);
            } else if (next.charAt(0) == 'N') {
                while (_config.hasNext(p)) {
                    c += _config.next() + " ";
                }
                Permutation perm = new Permutation(c, _alphabet);
                return new FixedRotor(names, perm);
            } else if (next.charAt(0) == 'R') {
                while (_config.hasNext(p)) {
                    c += _config.next() + " ";
                }
                Permutation perm = new Permutation(c, _alphabet);
                return new Reflector(names, perm);
            } else {
                throw new EnigmaException("Incorrect Rotor type");
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        String[] rotors = new String[M.numRotors()];
        String plugB = "";
        Scanner reader = new Scanner(settings);
        if (reader.hasNext("[*]")) {
            reader.next();
            for (int i = 0; i < M.numRotors(); i++) {
                rotors[i] = reader.next();
            }
            M.insertRotors(rotors);
            if (reader.hasNext("\\w{" + (M.numRotors() - 1) + "}")) {
                M.setRotors(reader.next());
            }
            while (reader.hasNext("[(]\\w+[)]")) {
                plugB += reader.next() + " ";
            }
            if (plugB.length() > 0) {
                String cycles = plugB.substring(0, plugB.length() - 1);
                Permutation perm = new Permutation(cycles, _alphabet);
                M.setPlugboard(perm);
            }
        }
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        String m = msg;
        if (msg.length() == 0) {
            _output.println();
        } else {
            while (m.length() > 0) {
                if (m.length() <= 5) {
                    String temp = m;
                    m = "";
                    _output.println(temp);
                } else {
                    _output.print(m.substring(0, 5) + " ");
                    m = m.substring(5);
                }
            }
        }
    }
}
