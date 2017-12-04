package sample;

import java.util.Vector;

public interface IPathFinder {

    public Vector<Node> findPath(Node Start, Node End, Map map);
}
