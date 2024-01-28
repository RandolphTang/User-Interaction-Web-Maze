package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private int size;
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.size = 0;

    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {

        for (int i = 0; i < size; i++) {
            if (this.entries[i].getKey().equals(key)) {
                return this.entries[i].getValue();
            }
        }

        return null;

    }

    @Override
    public V put(K key, V value) {


        if (size == this.entries.length) {
            ArrayMap<K, V> newMap = new ArrayMap<>(this.entries.length * 2);
            for (int i = 0; i < this.size; i++) {

                newMap.entries[i] = new SimpleEntry<>(this.entries[i]);

            }

            this.entries = newMap.entries;
        }

        if (this.containsKey(key)) {

            for (int i = 0; i < this.entries.length; i++) {
                if (this.entries[i].getKey().equals(key)) {
                    V oldValue = this.entries[i].getValue();
                    entries[i] = new SimpleEntry<>(key, value);
                    return oldValue;
                }
            }
        } else {
            this.entries[size] = new SimpleEntry<>(key, value);
            this.size++;
        }

        return null;

    }

    @Override
    public V remove(Object key) {


        for (int i = 0; i < this.size; i++) {
            if (this.entries[i].getKey().equals(key)) {
                V oldKey = this.entries[i].getValue();
                this.entries[i] = null;

                if (this.entries[size - 1] != null) {
                    SimpleEntry<K, V> relocate = new SimpleEntry<>(entries[size - 1]);
                    this.entries[size - 1] = null;
                    this.entries[i] = relocate;
                }

                this.size--;

                return oldKey;
            }
        }


        return null;

    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.entries[i] = null;
        }

        this.size = 0;

    }

    @Override
    public boolean containsKey(Object key) {

        for (int i = 0; i < this.size; i++) {
            if (this.entries[i].getKey().equals(key)) {

                return true;

            }
        }

        return false;


    }

    @Override
    public int size() {
        return this.size;

    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }


    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private int currentIndex;

        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {

            currentIndex = 0;
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {

            return (currentIndex < this.entries.length &&
                this.entries[currentIndex] != null);


        }

        @Override
        public Map.Entry<K, V> next() {

            if (this.hasNext()) {
                currentIndex++;
                return this.entries[currentIndex - 1];
            }
            throw new NoSuchElementException();
        }
    }
}
