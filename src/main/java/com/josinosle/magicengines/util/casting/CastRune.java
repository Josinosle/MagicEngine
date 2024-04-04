package com.josinosle.magicengines.util.casting;

import net.minecraft.util.Mth;
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

    public CastRune(int magnitude, int rune){
        this.castMagnitude = Math.max(magnitude, 1);
        this.rune = rune;
    }

    private void calculateCastMagnitude(){
        double sum = 0;
        Vec3 previousVector = vectorComposition.get(0);

        // calculate mean vector magnitudes for the vector composition
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

        // find rationalised dot product of the 3 vectors from the tail
        int valueToBePushed = rationalisedDotProduct(
                vectorComposition.get(i),
                vectorComposition.get(i-2),
                vectorComposition.get(i-1)
                );

        // check if the vector is different to the previous and that the vector isnt in a straight line
        if ( valueToBePushed == 0 || ( vectorComposition.get(i-1).subtract(vectorComposition.get(i)).length() ) < 0.05 ) return;

        // clever way of concatenating an integer (vulnerable to overflow error)
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
     * Get method for a casting vector with special conditions for use cases
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
    private static int rationalisedDotProduct(Vec3 vector1, Vec3 vector2, Vec3 vectorOrigin) {

        // calculate 2 temporary vectors relative to an origin point vector
        Vec3 calcVector1 = vector1.subtract(vectorOrigin);
        Vec3 calcVector2 = vector2.subtract(vectorOrigin);

        // calculate vector dot product
        float xCoefficient = (float) (calcVector1.x() * calcVector2.x());
        float yCoefficient = (float) (calcVector1.y() * calcVector2.y());
        float zCoefficient = (float) (calcVector1.z() * calcVector2.z());
        float dotProduct = (xCoefficient + yCoefficient + zCoefficient);

        // evil over engineered rationalisation
        float rationalisationCoefficient = (float) Mth.fastInvSqrt(calcVector1.lengthSqr() * calcVector2.lengthSqr());

        //multiply dot product by rationalisationCoefficient
        dotProduct = dotProduct * rationalisationCoefficient;

        // return an integer based on dot product result
        if (dotProduct <= -0.75) return 0;
        if (dotProduct <= -0.25) return 4;
        if (dotProduct <= 0.25) return 3;
        if (dotProduct <= 0.75) return 2;
        return 1;
    }

    public boolean isRuneEmpty(){
        return this.rune == 0;
    }
}
