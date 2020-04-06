/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        tb = input;
        toMatch = match;
        colIndex = input.colNameToIndex(colName);
    }

    @Override
    protected boolean keep() {
        if (toMatch.equals(_next.getValue(colIndex))){
            return true;
        }
        return false;
    }
    Table tb;
    int colIndex;
    String toMatch;
}
