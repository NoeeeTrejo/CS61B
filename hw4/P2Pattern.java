/** P2Pattern class
 *  @author Josh Hug & Vivant Sakore
 */

public class P2Pattern {
    /* Pattern to match a valid date of the form MM/DD/YYYY. Eg: 9/22/2019 */
    public static String P1 = "([1-9]|0[1-9]|1[012])[- /.]([1-9]|0[1-9]|[1][1-9]|2[1-9]|3[01])[- /.](19|20)\\d\\d";
    /** Pattern to match 61b notation for literal IntLists. */
    public static final String P2 = "\\(((0|[1-9]\\d*),(\\s*))*(0|[1-9]\\d*)\\)";
    /* Pattern to match a valid do  main name. Eg: www.support.facebook-login.com */
    public static String P3 = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$"; //FIXME: Add your regex here

    /* Pattern to match a valid java variable name. Eg: _child13$ */
    public static String P4 = "[a-zA-Z$_][a-zA-Z0-9$_]*";

    /* Pattern to match a valid IPv4 address. Eg: 127.0.0.1 */
    public static String P5 = "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b";

}
