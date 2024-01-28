package maps;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {

    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1.0;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 5;


    private final double resizingLoadFactorThreshold;
    //private final int initialChainCount;

    private final int chainInitialCapacity;

    private int size;

    private int elementSize = 0;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {

        this.chains = this.createArrayOfChains(initialChainCount);

        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;

        this.chainInitialCapacity = chainInitialCapacity;

        this.size = initialChainCount;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {

        int targetLocation = Math.abs(key.hashCode() % this.size);


        if (this.chains[targetLocation] != null) {
            return this.chains[targetLocation].get(key);
        }


        return null;

    }

    @Override
    public V put(K key, V value) {

        double compareRatio = (double) this.elementSize / this.size;

        if (compareRatio == this.resizingLoadFactorThreshold) {


            this.size *= 2;

            AbstractIterableMap<K, V>[] newChainedMap =
                this.createArrayOfChains(this.size);


            for (AbstractIterableMap<K, V> eachMap : this.chains) {
                if (eachMap != null) {
                    for (Map.Entry<K, V> entry : eachMap) {

                        int eachTargetLocation = Math.abs(entry.getKey().hashCode() % this.size);

                        if (newChainedMap[eachTargetLocation] == null) {
                            newChainedMap[eachTargetLocation] = this.createChain(this.chainInitialCapacity);
                        }


                        newChainedMap[eachTargetLocation].put(entry.getKey(), entry.getValue());


                    }
                }

            }

            this.chains = newChainedMap;

        }

        int targetLocation = Math.abs(key.hashCode() % this.size);


        if (this.chains[targetLocation] == null) {

            this.chains[targetLocation] = this.createChain(this.chainInitialCapacity);
        }


        V result = this.chains[targetLocation].put(key, value);


        if (result == null) {

            this.elementSize++;
        }

        return result;
    }

    @Override
    public V remove(Object key) {


        V result = null;

        int targetLocation = Math.abs(key.hashCode() % this.size);


        if (this.chains[targetLocation] != null) {


            result = this.chains[targetLocation].remove(key);

            if (result != null) {
                this.elementSize--;
            }
        }

        return result;

    }

    @Override
    public void clear() {

        this.elementSize = 0;
        this.chains = this.createArrayOfChains(this.chainInitialCapacity);


    }

    @Override
    public boolean containsKey(Object key) {

        int targetLocation = Math.abs(key.hashCode() % this.size);
        if (this.chains[targetLocation] != null) {
            return this.chains[targetLocation].containsKey(key);
        }

        return false;
    }

    @Override
    public int size() {
        return this.elementSize;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }


    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;

        private int currentArrayIndex;


        private Iterator<Map.Entry<K, V>> mainIterator;

        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.currentArrayIndex = 0;

            while (this.mainIterator == null && this.currentArrayIndex < this.chains.length) {

                if (this.chains[this.currentArrayIndex] != null) {

                    this.mainIterator = this.chains[this.currentArrayIndex].entrySet().iterator();


                }
                this.currentArrayIndex++;


            }


        }

        @Override
        public boolean hasNext() {


            while ((this.currentArrayIndex < this.chains.length - 1) &&
                (this.chains[this.currentArrayIndex] == null || !this.mainIterator.hasNext())) {

                AbstractIterableMap<K, V> currentMap = this.chains[this.currentArrayIndex++];

                if (currentMap != null) {
                    this.mainIterator = currentMap.entrySet().iterator();
                }


            }

            return (this.mainIterator != null && this.mainIterator.hasNext());

        }

        @Override
        public Map.Entry<K, V> next() {

            if (this.hasNext()) {

                return this.mainIterator.next();

            }

            throw new NoSuchElementException();
        }
    }
}
