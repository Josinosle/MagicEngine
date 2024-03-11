package com.josinosle.magicengines.util.castgeometry;

import net.minecraft.world.entity.player.Player;

public class CastVector {
    private final double xPos;
    private final double yPos;
    private final double zPos;
    private final Player castPlayer;

    public CastVector(double x, double y, double z, Player player){
        xPos = x;
        yPos = y;
        zPos = z;
        castPlayer = player;
    }

    public CastVector vectorDifference(CastVector origin){
        double xDiff = xPos - origin.getX();
        double yDiff = yPos - origin.getY();
        double zDiff = zPos - origin.getZ();
        return new CastVector(xDiff, yDiff, zDiff, castPlayer);
    }

    public float modulus(){
        return (float) Math.sqrt(Math.pow(xPos,2)+Math.pow(yPos,2)+Math.pow(zPos,2));
    }

    public double getX(){
        return xPos;
    }
    public double getY(){
        return yPos;
    }
    public double getZ(){
        return zPos;
    }
}
