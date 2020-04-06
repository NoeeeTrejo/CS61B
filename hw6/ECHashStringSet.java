import java.util.LinkedList;
import java.util.List;

/** A set of String values.
 *  @author
 */
class ECHashStringSet implements StringSet {
    ECHashStringSet(){
        _size = 0;
        _seen = new LinkedList[(int) (1 / _min)];
    }

    private double l(){
        return ((double)_size)/((double)_seen.length);
    }

    private int hashSeen(int hashCode){
        int last = hashCode & 1;
        int  hash = (hashCode >>> 1) | last;

        return hash % _seen.length;
    }
    private void resize(){
        LinkedList<String>[] o = _seen;
        _seen = new LinkedList[ 2 * o.length];
        _size = 0;
        for(LinkedList<String> L : o)
            if(L != null)
                for(String s : L)
                    this.put(s);

    }

    @Override
    public void put(String s) {
        if (s != null){
            if (l() > _max){
                resize();
            }
            int index = hashSeen(s.hashCode());
            if (_seen[index] == null){
                _seen[index] = new LinkedList<String>();
            }
            _seen[index].add(s);
            _size += 1;
        }
    }

    @Override
    public boolean contains(String s) {
        if (s != null){
            int index = hashSeen(s.hashCode());
            if (_seen[index] != null){
                return _seen[index].contains(s);
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> asList() {
        return new LinkedList<>();
    }
    private static double _min = 0.2;
    private static double _max = 5;
    private int _size;
    private LinkedList<String>[] _seen;
}
