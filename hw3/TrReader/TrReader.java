import java.io.IOException;
import java.io.Reader;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Noe Trejo
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        this.from = from;
        this.to = to;
        this.reader = str;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (off + len > cbuf.length){
            throw new IOException();
        }
        int j = reader.read(cbuf, off, len);
        for (int i = off; i < off + len; i++){
            int indexOf = from.indexOf(cbuf[i]);
            if (indexOf != -1){
                cbuf[i] =  to.charAt(indexOf);
            }
        }
        return j;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
    char[] fromArr;
    String to;
    String from;
    Reader reader;

}
