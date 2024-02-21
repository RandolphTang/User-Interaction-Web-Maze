package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {

        HashMap<V, E> spt = new HashMap<>();

        ExtrinsicMinPQ<V> minPQ = createMinPQ();

        HashMap<V, Double> distance = new HashMap<>();

        if (Objects.equals(start, end)) {
            return spt;
        }

        minPQ.add(start, 0.0);
        distance.put(start, 0.0);

        while (!minPQ.isEmpty()) {
            V curr = minPQ.removeMin();
            if (curr.equals(end)) {
                break;
            }

            for (E edge : graph.outgoingEdgesFrom(curr)) {
                V next = edge.to();
                double newDistance = distance.get(curr) + edge.weight();

                if (!distance.containsKey(next) || newDistance < distance.get(next)) {
                    distance.put(next, newDistance);
                    spt.put(next, edge);
                    minPQ.add(next, newDistance);
                }
            }
        }

        return spt;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {

        List<E> collection = new ArrayList<>();

        if (!spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }

        V finalL = end;
        while (finalL != null && !finalL.equals(start)) {
            E edge = spt.get(finalL);
            collection.add(edge);
            finalL = edge.to();
        }

        if (!Objects.equals(finalL, start)) {
            return new ShortestPath.Failure<>();
        }

        return new ShortestPath.Success<>(collection);

    }

}
