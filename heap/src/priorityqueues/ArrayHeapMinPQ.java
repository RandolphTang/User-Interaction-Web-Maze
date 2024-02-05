package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; //imported by students
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;

    private int size;
    private HashMap<T, Integer> map;

    public ArrayHeapMinPQ() {

        items = new ArrayList<>();
        this.map = new HashMap<>();
        size = 0;

    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        if (a < size && b < size) {
            PriorityNode<T> aVal = items.get(a);
            PriorityNode<T> bVal = items.get(b);
            items.set(a, bVal);
            this.map.put(aVal.getItem(), b);
            this.map.put(bVal.getItem(), a);
            items.set(b, aVal);
        }
    }

    //helper function to restore heap
    private void checkUp(PriorityNode<T> item, int index) {

        int parentIndex = (index - 1) / 2; // set parentIndex
        PriorityNode<T> parent = items.get(parentIndex); // get parent node

        // if the priority of the curr is lower than parent,
        // then percolate up
        if (item.getPriority() < parent.getPriority()) {
            swap(index, parentIndex); // swap the two items
            checkUp(parent, parentIndex); // recursively percolate up?
        }
    }

    private void checkDown(PriorityNode<T> item, int index) {

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if (leftChild < this.size && items.get(leftChild).getPriority() < item.getPriority()) {
            smallest = leftChild;
        }
        if (rightChild < this.size && items.get(rightChild).getPriority() < item.getPriority()) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            checkDown(this.items.get(smallest), smallest);
        }

    }

    @Override
    public void add(T item, double priority) {
        if (!this.contains(item)) {
            this.map.put(item, this.size);
            PriorityNode<T> val = new PriorityNode<>(item, priority);
            items.add(val);

            int index = this.size;

            if (size > 0) {
                this.checkUp(val, index);
            }


            this.size++;
        }
    }

    @Override
    public boolean contains(T item) {
        // O(logn)

        return this.map.containsKey(item);

    }

    @Override
    public T peekMin() { //O(logn)

        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }

        return this.items.get(0).getItem();

    }

    @Override
    public T removeMin() {

        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }

        this.size--;

        T min = this.items.get(0).getItem();
        this.map.remove(min);

        this.items.set(0, this.items.get(size - 1));
        this.items.set(size - 1, null);


        checkDown(this.items.get(0), 0);

        return min;

    }

    @Override
    public void changePriority(T item, double priority) { //O(logn)
        if (size() == 0 || !this.contains(item)) {
            throw new NoSuchElementException("the item is not present in the PQ");
        }

        Integer location = this.map.get(item);

        PriorityNode<T> parent = new PriorityNode<>(item, priority);

        if (priority < items.get(location).getPriority()) {
            checkUp(parent, location);
        } else {
            checkDown(parent, location);
        }

    }

    @Override
    public int size() {
        return size;
    }
}
