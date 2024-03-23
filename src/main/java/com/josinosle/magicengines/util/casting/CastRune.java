package com.josinosle.magicengines.util.casting;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * Class for a casting rune
 *
 * @author josinosle
 */
public class CastRune {
    private int castMagnitude;
    private int rune = 0;
    private final ArrayList<Vec3> vectorComposition = new ArrayList<>();

    public CastRune(){}

    private void calculateCastMagnitude(){
        double sum = 0;
        Vec3 previousVector = vectorComposition.get(0);
        for (Vec3 i : vectorComposition){
            sum += (i.subtract(previousVector).length());
        }
        sum = (sum / (vectorComposition.size()-1));

        castMagnitude = (int)sum;
    }

    /**
     * Method for formatting a rune from a set of vectors to an integer
     */
    private void formatRune(){
        // tail of the vector composition
        int i = vectorComposition.size()-1;

        // find dot product of the 3 vectors from the tail
        int valueToBePushed = dotProduct(
                vectorComposition.get(i),
                vectorComposition.get(i-2),
                vectorComposition.get(i-1)
                );

        // clever way of concatenating an integer
        this.rune = this.rune*10 + valueToBePushed;
    }

    /**
     * Get method for the runes code
     *
     * @return  A rune integer
     */
    public int getRune(){
        return this.rune;
    }

    /**
     * Get method for a casting vector
     *
     * @param i     Element
     * @return      A CastVector object
     */
    public Vec3 getCastVector(int i){
        if (i==-1) return vectorComposition.get(vectorComposition.size()-1);
        else if (i==-2) return vectorComposition.get(vectorComposition.size()-2);
        else if (i==-3) return vectorComposition.get(vectorComposition.size()-3);
        return vectorComposition.get(i);
    }

    /**
     * Get method for a casting magnitude
     *
     * @return      Integer cast magnitude
     */
    public int getCastMagnitude(){
        return castMagnitude;
    }

    /**
     *
     * Add method for the vector composition
     *
     * @param vector        Vector to be added
     */
    public void addVectorToRune(Vec3 vector){
        vectorComposition.add(vector);
        calculateCastMagnitude();
        if(this.vectorComposition.size()>2) {
            formatRune();
            System.out.println(rune);
        }
    }

    /**
     * Method to calculate the dot product of two vectors relative to an origin point
     *
     * @param vector1   the first logical vector
     * @param vector2   the second logical vector
     * @param vectorOrigin  the origin point for the logical vectors
     * @return a string variable containing the alphabetic character represented by the dot product
     */
    private static int dotProduct(Vec3 vector1, Vec3 vector2, Vec3 vectorOrigin) {

        // calculate 2 working vectors
        Vec3 calcVector1 = vector1.subtract(vectorOrigin);
        Vec3 calcVector2 = vector2.subtract(vectorOrigin);

        // calc dot product
        float xProd = (float) (calcVector1.x() * calcVector2.x());
        float yProd = (float) (calcVector1.y() * calcVector2.y());
        float zProd = (float) (calcVector1.z() * calcVector2.z());
        float dotProduct = (float) ((xProd + yProd + zProd) / (calcVector1.length() * calcVector2.length()));

        // return a character
        if (dotProduct <= -0.85) return 1;
        if (dotProduct <= -0.15) return 4;
        if (dotProduct <= 0.15) return 3;
        if (dotProduct <= 0.85) return 2;
        return 1;
    }

    public boolean isRuneEmpty(){
        return this.rune == 0;
    }
}
