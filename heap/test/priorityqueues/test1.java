package priorityqueues;

public class test1 {
    public static void main(String[] args) {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();


        pq.add("Jean Valjean", 3);
        pq.add("Javert", 7);
        pq.add("Cosette", 9);


        pq.changePriority("Cosette", 1
        );

        System.out.println(pq.items);


        // pq.add("Marius", 4);
        // pq.add("Eponine", 1);
        // pq.add("Gavroche", 8);
        // pq.add("Fantine", 5);
        // pq.add("Thenardier", 2);
        // pq.add("Enjolras", 6);
        // pq.add("Grantaire", 10);


        // while (!pq.isEmpty()) {
        //     pq.removeMin();
        // }

    }

}
