package disjointsets;

public class test {

    public static void main(String[] args) {
        // Create an instance of UnionBySizeCompressingDisjointSets
        UnionBySizeCompressingDisjointSets<String> disjointSets = new UnionBySizeCompressingDisjointSets<>();

        // Add elements and perform union operations
        disjointSets.makeSet("A");
        disjointSets.makeSet("B");
        disjointSets.makeSet("C");


        // Test the findSet method
        System.out.println(disjointSets.pointers);
        System.out.println(disjointSets.contents);
        System.out.println(disjointSets.findSet("A"));
        System.out.println(disjointSets.findSet("B"));
        disjointSets.union("A","B");
        System.out.println(disjointSets.pointers);

        disjointSets.union("C","B");
        System.out.println(disjointSets.pointers);

    }
}
