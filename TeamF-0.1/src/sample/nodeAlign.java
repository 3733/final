package sample;

public class nodeAlign {
    Node n1;

    // the first node input to this function that is the standalone that needs to be fixed.
    private void setNode(Node nd1, Node nd2, Node nd3)
    {
        double newx1; // for first node
        double newx2; // for second
        double newx3; // for third
        double newy1;
        double newy2;
        double newy3;

        // the first node input to this function that is the standalone that needs to be fixed.
        // nd2 is the centered node now.
        newx1 = nd1.getxCoordinate() - nd2.getxCoordinate();
        newy1 = nd1.getyCoordinate() - nd2.getyCoordinate();

        newx3 = nd3.getxCoordinate() - nd2.getxCoordinate();
        newy3 = nd3.getyCoordinate() - nd2.getyCoordinate();

        // For redundancy
        newx2 = nd2.getxCoordinate() - nd2.getxCoordinate();
        newy2 = nd2.getyCoordinate() - nd2.getyCoordinate();

        // now, from n2 to n3 is the good vector. n2 is at the origin.
        // n2 to n1 is the vector that needs fixing.

        // dot product
        double dot = newx3 * newx1 + newy3 * newy1;

        // magnitude of good vector
        double magnitude = Math.sqrt(Math.pow(newx3, 2) + Math.pow(newy3, 2));

        // unit vector of good vector.
        double unitx = newx3 / magnitude;
        double unity = newy3 / magnitude;

        // now, we multiply the unit vector by the dot product to create the projection of the bad node onto the good node.
        double goodx1 = unitx * dot;
        double goody1 = unity * dot;

        // Since we shifted everything by the location of node 2, we shift back by adding node 2 so that all the nodes are the same,
        // except for the derpy node.
        goodx1 += ((double)nd2.getxCoordinate());
        goody1 += ((double)nd2.getyCoordinate());

        n1.setxCoordinate((int) goodx1);
        n1.setyCoordinate((int) goody1);
    }

    public int getgoodX(Node N1, Node N2, Node N3)
    {
        setNode(N1, N2, N3);
        return n1.getxCoordinate();
    }

    public int getgoodY(Node N1, Node N2, Node N3)
    {
        setNode(N1, N2, N3);
        return n1.getyCoordinate();
    }



}
