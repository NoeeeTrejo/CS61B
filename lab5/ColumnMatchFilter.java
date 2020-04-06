/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        tb = input;
        colNum1 = input.colNameToIndex(colName1);
        colNum2 = input.colNameToIndex(colName2);
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        if (_next.getValue(colNum1).equals(_next.getValue(colNum2))){
            return true;
        }
        return false;
    }
    Table tb;
    int colNum1;
    int colNum2;
}
