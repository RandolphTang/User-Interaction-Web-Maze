package disjointsets;

import java.util.ArrayList;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    List<T> contents;
    int size;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        // TODO: replace this with your code
        this.pointers = new ArrayList<>();
        this.contents =  new ArrayList<>();
        this.size = 0;
    }

    @Override
    public void makeSet(T item) {
        this.pointers.add(-1);
        this.contents.add(item);
    }

    @Override
    public int findSet(T item) {
        int index = this.contents.indexOf(item);
        if (index == -1) {
            return -1;
        }
        if (this.pointers.get(index) != -1){
            this.pointers.set(index, findSet(contents.get(pointers.get(index))));
        }

        return pointers.get(index) == -1 ? index : pointers.get(index);

    }

    @Override
    public boolean union(T item1, T item2) {
        int index1 = findSet(item1);
        int index2 = findSet(item2);
        if (index1 == -1 || index2 == -1 || index1 == index2) {
            return false;
        }
        //same weight situation ?
        //integer id?

        return true;
    }
}
