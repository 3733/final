//package sample;
//import java.awt.*;
//import java.lang.Math;
//
///**
// * class to get the location (point projection on the line)
// */
//public class nodeAlign {
//    public Point A ;
//    public Point B ;
//    public Point C ;
//    //get dot product of e1,e2
//
//    public Point getProjectedPointOnLine()
//    {
//    Point e1 = new Point(B.x-A.x,B.y-A.y);
//    Point e2 = new Point(C.x-A.x,C.y-A.y);
//
//    double valDp = dotProduct(e1,e2);
//
//    double lenLineE1 = Math.sqrt(e1.x * e1.x + e1.y * e1.y);
//    double lenLineE2 = Math.sqrt(e2.x * e2.x + e2.y * e2.y);
//
//    double cos = valDp / (lenLineE1 * lenLineE2);
//
//    double projLenOfLine = cos * lenLineE2;
//
//    Point D = new Point((int)(A.x + (projLenOfLine * e1.x) / lenLineE1),
//            (int)(A.y + (projLenOfLine * e1.y) / lenLineE1));
//
//        return D;
//    }
//
//    public double dotProduct(Point a, Point b) {
//        return a.x * b.y - a.y * b.x;
//    }
//
//
//
//}
//
//
//
//
//}
