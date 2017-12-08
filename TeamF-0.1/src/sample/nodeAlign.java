
//Andrew's Code
package sample;
        import java.lang.Math;

public class nodeAlign {
//    Node n1;

//    int x1;
//    int y1;
//    int x2;
//    int y2;
//    int x3;
//    int y3;
//
//    int finalX;
//    int finalY;
//    public nodeAlign(){
//        n1 = new Node("Align", 0, 0, "1", "Align", "Align", "Align", "Align", 'F');
//
//        x1 = 0;
//        y1 = 0;
//        x2 = 0;
//        y2 = 0;
//        x3 = 0;
//        y3 = 0;
//
//        finalX = 0;
//        finalY = 0;
//    }
    // the first node input to this function that is the standalone that needs to be fixed.
    private Node setNode(Node nd1, Node nd2, Node nd3)
    {
        double newx1; // for first node
        double newx2; // for second
        double newx3; // for third
        double newy1;
        double newy2;
        double newy3;

        Node n1 = new Node("Align", 0, 0, "1", "Test", "TEST", "Aligning", "Align", 'F');

        // the first node input to this function that is the standalone that needs to be fixed.
        // nd2 is the centered node now.
        newx1 = nd1.getxCoordinate() - nd2.getxCoordinate();
        newy1 = nd1.getyCoordinate() - nd2.getyCoordinate();

        newx3 = nd3.getxCoordinate() - nd2.getxCoordinate();
        newy3 = nd3.getyCoordinate() - nd2.getyCoordinate();

        // For redundancy, both equal 0.
        newx2 = 0; //nd2.getxCoordinate() - nd2.getxCoordinate();
        newy2 = 0; // nd2.getyCoordinate() - nd2.getyCoordinate();

        // now, from n2 to n3 is the good vector. n2 is at the origin.
        // n2 to n1 is the vector that needs fixing.



        // magnitude of good vector
        double magnitude = Math.sqrt(Math.pow(newx3, 2) + Math.pow(newy3, 2));

        // unit vector of good vector.
        double unitx = newx3 / magnitude;
        double unity = newy3 / magnitude;

        // dot product
        double dot = unitx * newx1 + unity * newy1;

        // now, we multiply the unit vector by the dot product to create the projection of the bad node onto the good node.
        double goodx1 = unitx * dot;
        double goody1 = unity * dot;

        // Since we shifted everything by the location of node 2, we shift back by adding node 2 so that all the nodes are the same,
        // except for the derpy node.
        goodx1 += ((double)nd2.getxCoordinate());
        goody1 += ((double)nd2.getyCoordinate());

        int retx = (int) goodx1;
        int rety = (int) goody1;

        n1.setxCoordinate(retx);
        n1.setyCoordinate(rety);

        return n1;
    }

    public int setGoodX(Node nd1, Node nd2, Node nd3){
        Node a1 = setNode(nd1, nd2, nd3);
        return a1.getxCoordinate();
    }
    public int setGoodY(Node nd1, Node nd2, Node nd3){
        Node a1 = setNode(nd1, nd2, nd3);
        return a1.getyCoordinate();
    }

//    public int getgoodX(Node N1, Node N2, Node N3)
//    {
//        x1 = N1.getxCoordinate();
//        x2 = N2.getxCoordinate();
//        x3 = N3.getxCoordinate();
//        setNode(N1, N2, N3);
//
//        return n1.getxCoordinate;
//    }

//    public int getgoodY(Node N1, Node N2, Node N3)
//    {
//        y1 = N1.getyCoordinate();
//        y2 = N2.getyCoordinate();
//        y3 = N3.getyCoordinate();
//        setNode(N1, N2, N3);
//        return n1.getyCoordinate;
//    }

//    public void alignThem(Node N1, Node N2, Node N3){
//        x1 = N1.getxCoordinate();
//        x2 = N2.getxCoordinate();
//        x3 = N3.getxCoordinate();
//
//        y1 = N1.getyCoordinate();
//        y2 = N2.getyCoordinate();
//        y3 = N3.getyCoordinate();
//        setNode(N1, N2, N3);
//        testEmbeddedDB.updateNodeXCoord(N1.getNodeID(), finalX);
//        testEmbeddedDB.updateNodeXCoord(N1.getNodeID(), finalY);
//    }

}