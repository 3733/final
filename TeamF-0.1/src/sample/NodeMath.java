package sample;

public abstract class NodeMath{

    public static double mod360(double angle)
    {
        return ((angle + 360) % 360);
    }

    public static double distForm(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((Math.pow(x2 - x1,2d))+(Math.pow(y2 - y1,2d)));
    }

    // This find the radius of the vector.
    public static double rectToPolarRadius(double xin, double yin)
    {
        if (xin == 0 && yin == 0)
        {
            return 0;
        }
        else return Math.sqrt((Math.pow(xin,2d))+(Math.pow(yin,2d)));
    }
    // The selector is for saying which angle we want opposite form that side. If the whichside is 1, we find the angle opposite side A. You get the rest.
    // The default is case 1.

    // This function limits the output of the math to the range of low to high inclusive of boundaries.
    public static double inRange(double input, double low, double high)
    {
        if (input < low)
        {
            return low;
        }
        else if (input > high)
        {
            return high;
        }
        else return input;
    }

    // This is the actual function that makes swag happen.
    public static double coslawAngle(double sidea, double sideb, double sidec, int whichside)
    {   // If a side length is 0, then this isn't a triangle. I need to test for this in a slightly better way.
        switch(whichside)
        {
            case 1:
                if (sidec == 0 || sideb == 0)
                {
                    return 0;
                }
                else
                {
                    double part1 = ((Math.pow(sidec, 2)) + (Math.pow(sideb, 2)) - (Math.pow(sidea, 2))) / (2 * sidec * sideb);
                    // System.out.println(" Before aCos: " + "\t" + part1);
                    return mod360(Math.toDegrees(Math.acos(inRange(part1, -1, 1))));
                }
            case 2: // We specifically use this case.
                if (sidec == 0 || sidea == 0)
                {
                    return 0;
                }
                else
                {
                    double part1 = ((Math.pow(sidec, 2)) + (Math.pow(sidea, 2)) - (Math.pow(sideb, 2))) / (2 * sidec * sidea);
                    // System.out.println(" Before aCos: " + "\t" + part1);
                    return mod360(Math.toDegrees(Math.acos((inRange(part1, -1, 1)))));
                }
            case 3:
                if (sidea == 0 || sideb == 0)
                {
                    return 0;
                }
                else
                {
                    double part1 = ((Math.pow(sideb, 2)) + (Math.pow(sidea, 2)) - (Math.pow(sideb, 2))) / (2 * sideb * sidea);
                    // System.out.println(" Before aCos: " + "\t" + part1);
                    return mod360(Math.toDegrees(Math.acos((inRange(part1, -1, 1)))));
                }
            default:
                if (sidec == 0 || sideb == 0)
                {
                    return 0;
                }
                else
                {
                    double part1 = ((Math.pow(sidec, 2)) + (Math.pow(sideb, 2)) - (Math.pow(sidea, 2))) / (2 * sidec * sideb);
                    // System.out.println(" Before aCos: " + "\t" + part1);
                    return mod360(Math.toDegrees(Math.acos(inRange(part1, -1, 1))));
                }
        }
    }

    // This method returns the angle between a given an x and y coordinate and the x axis pointing right --> The angle returned is in degrees.
    public static double rectToPolarTheta(double xin, double yin)
    {
        if (xin == 0)
        {
            if (yin > 0)
            {
                return 90d;
            }
            else if (yin < 0)
            {
                return 270d;
            }
            else // (0,0)
            {
                return 0;
            }
        }
        else // When x != 0
        {
            if (yin == 0)
            {
                if (xin > 0)
                {
                    return 0;
                }
                else // xin < 0
                {
                    return 180d;
                }
            }
            else
            {
                // If we are in quadrant 1...
                if (xin > 0d && yin > 0d)
                {
                    return mod360(Math.toDegrees(Math.atan(yin / xin)));
                }
                else if (xin < 0d && yin > 0d) // We are in the second quadrant.
                {
                    return mod360(180d+Math.toDegrees(Math.atan(yin / xin)));
                }
                else if (xin < 0d && yin < 0d) // We are in the third quadrant, lower left.
                {
                    return mod360(180d + Math.toDegrees(Math.atan(yin / xin)));
                }
                else // if (xin > 0d && yin < 0d) // we are in the 4th quadrant.
                {
                    return mod360(Math.toDegrees(Math.atan(yin / xin)));
                }
            }
        }
    }

    // The first (x,y) is A, the second (x,y) is B, and the third (x,y) is point C, where we are headed.
    // This function returns the angle <ABC, with the angle being measured CCW.
    public static double findAngle(int axin1, int ayin1, int bxin2, int byin2, int cxin3, int cyin3)
    {
        // First, we convert to doubles and then normalize.
        double x1 = (double)axin1;
        double y1 = (double)ayin1;
        double x2 = (double)bxin2;
        double y2 = (double)byin2;
        double x3 = (double)cxin3;
        double y3 = (double)cyin3;

        // Now we need to normalize about the B coordinate, which is our current location.
        x1 = x1-x2;
        y1 = y1-y2;
        x3 = x3-x2;
        y3 = y3-y2;
        x2 = 0d;
        y2 = 0d;

        //System.out.println("(x1, y1): " + "(" + x1 + ", " + y1 +")----" + "(x2, y2): " + "(" + x2 + ", " + y2 +")----" + "(x3, y3): " + "(" + x3 + ", " + y3 +")");
        // Now we convert the BA-> vector into a polar coordinate.
        // This is the angle.
        double bToAangle = rectToPolarTheta(x1, y1);
        // This is the radius.
        double bToAradius = rectToPolarRadius(x1, y1);

        //System.out.println("bToAangle: " + bToAangle + " ---- " + "bToAradius: " + bToAradius);

        // Now the key here is to rotate the BC-> vector the angle of BA-> so that BA-> is horizontal and BC-> is at the correct position
        // relative to the new BA->.
        // This means we convert to polar, subtract the BA-> angle away from it, then

        // BC-> radius.
        double bToCradius = rectToPolarRadius(x3, y3);
        // The angle.
        double bToCangle = rectToPolarTheta(x3, y3);

        //System.out.println("bToCradius: " + bToCradius + " ---- " + "bToCangle: " + bToCangle);

        // Now we do the angle subtraction on both angles.

        bToCangle = bToCangle - bToAangle;
        bToAangle = bToAangle - bToAangle; // Which should equal 0, but we do this for kicks anyway.

        //System.out.println("bToCangle: " + bToCangle + " ---- " + "bToAangle: " + bToAangle);

        // NOTE: B is now the origin.

        // Now we go back to rectangular coordinates given the new angles. Remeber, we did NOT change the radius.
        // This is for the Ax and Ay.
        x1 = bToAradius * Math.cos(Math.toRadians(bToAangle));
        y1 = bToAradius * Math.sin(Math.toRadians(bToAangle)); // This line should equal 0!!!!!

        // Same here for the new BC-> vector.
        x3 = bToCradius * Math.cos(Math.toRadians(bToCangle));
        y3 = bToCradius * Math.sin(Math.toRadians(bToCangle));

        //System.out.println("(x1, y1): " + "(" + x1 + ", " + y1 +")----" + "(x2, y2): " + "(" + x2 + ", " + y2 +")----" +               "(x3, y3): " + "(" + x3 + ", " + y3 +")");

        // All the math above is to normalize the vectors to make the math below possible.

        // Now we need to figure out the angles of this new triangle, GIVEN THAT POINT C IS EITHER ABOVE OR BELOW THE X-AXIS!!!!!!!
        // This is the key to this long trig algorithm.
        // Note: The BC-> vector COULD ALSO be parallel to BA->.

        // now we want the side opposite <CAB
        double sideA = distForm(x2, y2, x3, y3);
        // sideB is the length opposite <ABC.
        double sideB = distForm(x1, y1, x3, y3);
        // finally, we do side C, which is opposite <ACB
        double sideC = distForm(x1, y1, x2, y2);

        //System.out.print("sideA: " + sideA + "\t" + "sideB: " + sideB + "\t" + "sideC: " + sideC);

        // now that we have the sides, we can now use the cosine law to solve for the angle we need, which is <ABC.
        double angleB = coslawAngle(sideA, sideB, sideC, 2);

        //System.out.println("\t" + " angleB before modding: " + angleB);

        // double angleA = coslawAngle(sideA, sideB, sideC, 1); double angleC = coslawAngle(sideA, sideB, sideC, 3);

        // Now that we know the distances between points, and the angles between them, we can do the final operations.
        if (y3 == 0)
        {
            return 0;
        }
        else if(y3 > 0) // If Point C is above the X-axis
        {
            return mod360(angleB);
        }
        else
        {
            return mod360(360d - angleB); // It is still okay for angleB to equal 180 for this case, a.k.a. y3 = 0;
        }
    }
}