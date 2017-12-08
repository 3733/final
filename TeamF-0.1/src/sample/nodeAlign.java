//Willis' Code (edited)
package sample;
import java.awt.*;
import java.lang.Math;

/**
 * class to get the location (point projection on the line)
 */
public class nodeAlign {
    public Point A ;
    public Point B ;
    public Point C ;
    //get dot product of e1,e2

    public nodeAlign(Node n1, Node n2, Node n3){
        A = new Point(n1.getxCoordinate(), n1.getyCoordinate());
        B = new Point(n2.getxCoordinate(), n2.getyCoordinate());
        C = new Point(n3.getxCoordinate(), n3.getyCoordinate());
    }

    public Point getProjectedPointOnLine()
    {
    Point e1 = new Point(B.x-A.x,B.y-A.y);
    Point e2 = new Point(C.x-A.x,C.y-A.y);

    double valDp = dotProduct(e1,e2);

    double lenLineE1 = Math.sqrt(e1.x * e1.x + e1.y * e1.y);
    double lenLineE2 = Math.sqrt(e2.x * e2.x + e2.y * e2.y);

    double cos = valDp / (lenLineE1 * lenLineE2);

    double projLenOfLine = cos * lenLineE2;

    Point D = new Point((int)(A.x + (projLenOfLine * e1.x) / lenLineE1),
            (int)(A.y + (projLenOfLine * e1.y) / lenLineE1));

        return D;
    }

    public double dotProduct(Point a, Point b) {
        return a.x * b.y - a.y * b.x;
    }

    public int getgoodX()
    {
        return getProjectedPointOnLine().x;
    }

    public int getgoodY()
    {
        return getProjectedPointOnLine().y;
    }


}

//Andrew's Code
/*package sample;
        import java.lang.Math;

public class nodeAlign {
    Node n1;

    int x1;
    int y1;
    int x2;
    int y2;
    int x3;
    int y3;

    int finalX;
    int finalY;
    public nodeAlign(){
        n1 = new Node("Align", 0, 0, "1", "Align", "Align", "Align", "Align", 'F');

        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        x3 = 0;
        y3 = 0;

        finalX = 0;
        finalY = 0;
    }
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

    public int setGoodX(){
        return x1;
    }
    public int setGoodY(){
        return y1;
    }

    public int getgoodX(Node N1, Node N2, Node N3)
    {
        x1 = N1.getxCoordinate();
        x2 = N2.getxCoordinate();
        x3 = N3.getxCoordinate();
        setNode(N1, N2, N3);

        return x1;
    }

    public int getgoodY(Node N1, Node N2, Node N3)
    {
        y1 = N1.getyCoordinate();
        y2 = N2.getyCoordinate();
        y3 = N3.getyCoordinate();
        setNode(N1, N2, N3);
        return y1;
    }

    public void alignThem(Node N1, Node N2, Node N3){
        x1 = N1.getxCoordinate();
        x2 = N2.getxCoordinate();
        x3 = N3.getxCoordinate();

        y1 = N1.getyCoordinate();
        y2 = N2.getyCoordinate();
        y3 = N3.getyCoordinate();
        setNode(N1, N2, N3);
        testEmbeddedDB.updateNodeXCoord(N1.getNodeID(), finalX);
        testEmbeddedDB.updateNodeXCoord(N1.getNodeID(), finalY);
    }

}*/