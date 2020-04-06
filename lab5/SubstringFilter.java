/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        // FIXME: Add your code here.
        sub = subStr;
        tb = input;
        colIndex = input.colNameToIndex(colName);
    }

    @Override
    protected boolean keep() {
        if (_next.getValue(colIndex).contains(sub)){
            return true;
        }

        return false;
    }
    Table tb;
    int colIndex;
    String sub;
}
