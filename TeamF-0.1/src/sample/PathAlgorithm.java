package sample;

import java.util.Vector;

public class PathAlgorithm {

    private IPathFinder pathfinder;

    public PathAlgorithm(IPathFinder pathfinder){
        this.pathfinder = pathfinder;
    }

    public Vector<Node> executeStrategy(Node Start, Node End, Map map){
        return pathfinder.findPath(Start, End,map);
    }
}
