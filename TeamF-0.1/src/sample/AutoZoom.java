package sample;

import java.util.Vector;

/** This is the AutoZoom function
 * <p>
 *     The AutoZoom function allows the program to zoom when required
 * </p>
 * @param Vector<Node> path, int scale
 */

public class AutoZoom {


    public static Vector<Double> AutoZoom(Vector<Node> path, int scale){


        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for(Node n: path){

            if(n.getxCoordinate()>maxX){
                maxX = n.getxCoordinate();
            }

            if(n.getyCoordinate()>maxY){
                minY = n.getyCoordinate();
            }

            if(n.getxCoordinate()<minX){
                minX = n.getxCoordinate();
            }

            if(n.getyCoordinate()<minY){
                minY = n.getyCoordinate();
            }
        }


        minY=minY-scale;
        minX=minX-scale;
        maxY=maxY+scale;
        maxX=maxX+scale;

        double ActmidX = (maxX +minX)/2;
        double ActmidY = (maxY +minY)/2;


        double Xdist = maxX-minX;
        double Ydist = maxY-minY;
        double screenRatio = 1.5;
        if(Xdist >Ydist){
            Ydist = Xdist/screenRatio;
        }else {
            Xdist = Ydist *screenRatio;
        }

        double scaleRatio = 1+(5000/(Xdist+5000));

        Vector<Double> rectangle = new Vector<>();

        ActmidX = ActmidX-minX;
        ActmidY = ActmidY-minY;

        rectangle.add(ActmidX);
        rectangle.add(ActmidY);
        rectangle.add(scaleRatio);


        return rectangle;

    }
}
