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

        if (this.contents.contains(item)) {
            throw new IllegalArgumentException();
        }

        this.pointers.add(-1);
        this.contents.add(item);


    }

    @Override
    public int findSet(T item) {

        if (!this.contents.contains(item)) {
            throw new IllegalArgumentException();
        }

        int index = this.contents.indexOf(item);

        if (this.pointers.get(index) >= 0){
            this.pointers.set(index, findSet(contents.get(pointers.get(index))));
        }

        return this.pointers.get(index) < 0 ? index : this.pointers.get(index);

    }

    @Override
    public boolean union(T item1, T item2) {

        if (!this.contents.contains(item1) || !this.contents.contains(item2)) {
            throw new IllegalArgumentException();
        }

        int index1 = findSet(item1);
        int index2 = findSet(item2);

        if(index1 == index2){
            return false;
        }

        if (this.pointers.get(index1) < this.pointers.get(index2)) {
            this.pointers.set(index1, this.pointers.get(index1) + this.pointers.get(index2));
            this.pointers.set(index2, index1);
        } else {
            this.pointers.set(index2, this.pointers.get(index1) + this.pointers.get(index2));
            this.pointers.set(index1, index2);
        }

        return true;

    }
}
