package sample;

import java.util.Vector;

public interface PathFinder {

    public Vector<Node> findPath(Node Start, Node End, Map map);
}
