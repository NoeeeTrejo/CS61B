/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        tb = input;
        this.reff = ref;
        colIndex = input.colNameToIndex(colName);

    }

    @Override
    protected boolean keep() {
        if ((_next.getValue(colIndex)).compareTo(reff) > 0){
            return true;
        }
        return false;
    }
    Table tb;
    int colIndex;
    int colIndex2;
    String reff;
}
