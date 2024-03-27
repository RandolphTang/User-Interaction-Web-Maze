package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {

        // Hint: you'll probably need to include something like the following:
        // this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));

        Collection<EdgeWithData<Room, Wall>> edges = new HashSet<>();

        Set<Wall> result = new HashSet<>();

        for (Wall wall : walls) {

            EdgeWithData<Room, Wall> each = new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(),
                rand.nextDouble(), wall);
            edges.add(each);

        }

        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> mst =
            minimumSpanningTreeFinder.findMinimumSpanningTree((new MazeGraph(edges)));


        for (EdgeWithData<Room, Wall> each : mst.edges()) {

            result.add(each.data());

        }

        return result;

    }
}
