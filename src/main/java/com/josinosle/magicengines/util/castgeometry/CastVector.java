package com.josinosle.magicengines.util.castgeometry;

import net.minecraft.world.entity.player.Player;

public class CastVector {
    private final int xPos;
    private final int yPos;
    private final int zPos;
    private final Player castPlayer;

    public CastVector(int x, int y, int z, Player player){
        xPos = x;
        yPos = y;
        zPos = z;
        castPlayer = player;
    }

    public CastVector vectorDifference(CastVector origin){
        int xDiff = xPos - origin.getX();
        int yDiff = yPos - origin.getY();
        int zDiff = zPos - origin.getZ();
        return new CastVector(xDiff, yDiff, zDiff, castPlayer);
    }

    public float modulus(){
        return (float) Math.sqrt(Math.pow(xPos,2)+Math.pow(yPos,2)+Math.pow(zPos,2));
    }

    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
    public int getZ(){
        return zPos;
    }
}
