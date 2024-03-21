package com.josinosle.magicengines.util.castgeometry;

import net.minecraft.world.entity.player.Player;

/**
 * Position vector class for spell casting inputs
 *
 * @author josinosle
 */
public class CastVector {
    /**
     * Cartesian coordinates of position vector
     */
    private final double xPos; private final double yPos; private final double zPos;

    /**
     * Casting player for the vector coordinates
     */
    private final Player castPlayer;

    /**
     * Constructor for a CastVector object
     *
     * @param x     x coordinate of the position vector
     * @param y     y coordinate of the position vector
     * @param z     z coordinate of the position vector
     * @param player        player responsible for the casting vector
     */
    public CastVector(double x, double y, double z, Player player){
        xPos = x;
        yPos = y;
        zPos = z;
        castPlayer = player;
    }

    /**
     * Takes the difference of two vectors and returns a vector relative to an origin point
     *
     * @param origin        the vectors origin point as a CastVector object
     * @return  a new CastVector object as vector relative to an origin
     */
    public CastVector vectorDifference(CastVector origin){
        double xDiff = xPos - origin.getX();
        double yDiff = yPos - origin.getY();
        double zDiff = zPos - origin.getZ();
        return new CastVector(xDiff, yDiff, zDiff, castPlayer);
    }

    /**
     *
     * @return  the modulus of the CastVector
     */
    public float modulus(){
        return (float) Math.sqrt(Math.pow(xPos,2)+Math.pow(yPos,2)+Math.pow(zPos,2));
    }

    /**
     *
     * @return the x position of the CastVector
     */
    public double getX(){
        return xPos;
    }

    /**
     *
     * @return  the y position of the CastVector
     */
    public double getY(){
        return yPos;
    }

    /**
     *
     * @return  the z position of the CastVector
     */
    public double getZ(){
        return zPos;
    }
}
