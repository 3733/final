/*Andrew - Node Math Testing
* Purpose: To test the mathematics behind finding directiosn*/
/*ALL PASS - Steph*/

package sample;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NodeMathTesting {

    @Test
    public void testmod360()
    {
        // Here we are testing the mod360() method.
        assertEquals(0, NodeMath.mod360(360), 0.01f);
        assertEquals(1, NodeMath.mod360(361), 0.01f);
        assertEquals(40, NodeMath.mod360(400), 0.01f);
        assertEquals(359, NodeMath.mod360(359), 0.01f);
    }

    @Test
    public void testDistFormula1()
    {
        assertEquals(NodeMath.distForm(0,0,4,0), 4, 0.0000001);
        assertEquals(NodeMath.distForm(1,1,4,4), Math.sqrt(18), 0.0001);
        assertNotEquals(NodeMath.distForm(1,1,4,4), Math.sqrt(19), .1);
        assertEquals(NodeMath.distForm(0,0,0,0), 0, 0.00000001);
        assertEquals(NodeMath.distForm(4,4,5,5), Math.sqrt(2), 0.00000001);
        assertEquals(NodeMath.distForm(-1d,0,0,-1d), Math.sqrt(2), 0.1);
    }

    @Test
    public void testDistFormula2()
    {
        assertEquals(NodeMath.distForm(-1d,4d,-5d,-5d), Math.sqrt(97), 0.00001);
        assertEquals(NodeMath.distForm(-4d,-4d,-9d,10d), Math.sqrt(221), 0.000001);
        assertEquals(1,1,.001);
    }

    @Test
    public void testCosineLawFunction()
    {
        assertEquals(NodeMath.coslawAngle(3,4,5,1), Math.toDegrees(Math.asin(3d/5d)), 0.002);
        assertEquals(NodeMath.coslawAngle(5,12,13,1), Math.toDegrees(Math.asin(5d/13d)), 0.002);
        assertEquals(NodeMath.coslawAngle(5,12,13,2), Math.toDegrees(Math.asin(12d/13d)), 0.002);
        //assertEquals(NodeMath.coslawAngle(5,12,13,3), 90d, 0.002);
        // assertEquals(NodeMath.coslawAngle(3,4,5,1), Math.toDegrees(Math.asin(3d/5d)), 0.002);
    }

    @Test
    public void testRectPolarRadius()
    {
        assertEquals(NodeMath.rectToPolarRadius(1d,0d), 1d, 0.0000001);
        assertEquals(NodeMath.rectToPolarRadius(10d,0d), 10d, 0.0001);
        assertEquals(NodeMath.rectToPolarRadius(3.,4.), 5d, 0.1);
        assertEquals(NodeMath.rectToPolarRadius(5d,12d), 13d, .00001);
    }

    @Test
    public void testRectPolarTheta()
    {
        assertEquals(NodeMath.rectToPolarTheta(0d,0d), 0d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(0d,10d), 90d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(10d,0d), 0d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(0d,-10d), 270d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(-10d,0d), 180d, 0.0000001);
        // Non-edges
        assertEquals(NodeMath.rectToPolarTheta(1d,1d), 45d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(1d,-1d), 315d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(-1d,-1d), 225d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(-1d,1d), 135d, 0.0000001);
        assertEquals(NodeMath.rectToPolarTheta(.5, (-.5)*(Math.sqrt(3))), 300, 0.0000001);
    }

    @Test
    public void testFindAngle()
    {
        // This is the 0 angle case.
        // assertEquals(NodeMath.findAngle(0, 0, 0, 0, 0, 0), 0d, 0.0000001);

        // Now we do 90 degrees, case 1.
        assertEquals(NodeMath.findAngle(0, 4, 0, 0, -4, 0),
                90d, 0.0000001);
        // 90 deg case 2, with a shift of 2 up and 5 left.
        assertEquals(NodeMath.findAngle(-5, 4+2, -5, 2, -9, 2),
                90d, 0.0000001);
        // 90 deg, case 3.
        assertEquals(NodeMath.findAngle(5, 0, 0, 0, 0, 2),
                90d, 0.0000001);

        // This the 180 degree, case 1.
        assertEquals(NodeMath.findAngle(4, 0, 0, 0, -4, 0),
                180d, 0.0000001);
        // 180, case 2.
        assertEquals(NodeMath.findAngle(-4, 4, 0, 0, 4, -4),
                180d, 0.0000001);
        // This the 180 degree, case 3.
        assertEquals(NodeMath.findAngle(-4, 0, 0, 0, 4, 0),
                180d, 0.0000001);

        // This is 270 deg, case 1.
        assertEquals(NodeMath.findAngle(0, 4, 0, 0, 4, 0),
                270d, 0.0000001);
        // This is 270, case 2.
        assertEquals(NodeMath.findAngle(-5, 6, -5, 2, -1, 2),
                270d, 0.0000001);
        // This is 270, case 3.
        assertEquals(NodeMath.findAngle(5, 0, 0, 0, 0, -2),
                270d, 0.0000001);
    }
}
