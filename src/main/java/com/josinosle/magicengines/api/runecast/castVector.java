package com.josinosle.magicengines.api.runecast;

public class castVector {
    private final int xPos;
    private final int yPos;
    private final int zPos;

    public castVector(int x,int y,int z){
        xPos = x;
        yPos = y;
        zPos = z;
    }

    public castVector vectorDifference(castVector origin){
        int xDiff = xPos - origin.getX();
        int yDiff = yPos - origin.getY();
        int zDiff = zPos - origin.getZ();
        return new castVector(xDiff, yDiff, zDiff);
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
