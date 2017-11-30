package sample;

import java.util.Vector;

public class PathAlgorithm {

    private PathFinder pathfinder;

    public PathAlgorithm(PathFinder pathfinder){
        this.pathfinder = pathfinder;
    }

    public Vector<Node> executeStrategy(Node Start, Node End, Map map){
        return pathfinder.findPath(Start, End,map);
    }
}
