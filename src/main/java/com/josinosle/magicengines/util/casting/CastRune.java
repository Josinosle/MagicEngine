package com.josinosle.magicengines.util.casting;

import java.util.ArrayList;

/**
 * Class for a casting rune
 *
 * @author josinosle
 */
public class CastRune {
    private int castMagnitude;
    private int rune = 0;
    private final ArrayList<CastVector> vectorComposition = new ArrayList<>();

    public CastRune(){}

    private void calculateCastMagnitude(ArrayList<CastVector> vectors){
        double sum = 0;
        for (CastVector i : vectors){
            sum = i.modulus();
        }
        sum = (sum / vectors.size());

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
    public CastVector getCastVector(int i){
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
     * @param vector        CastVector vector to be added
     */
    public void addVectorToRune(CastVector vector){
        vectorComposition.add(vector);
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
    private static int dotProduct(CastVector vector1, CastVector vector2, CastVector vectorOrigin) {

        // calculate 2 working vectors
        CastVector calcVector1 = vector1.vectorDifference(vectorOrigin);
        CastVector calcVector2 = vector2.vectorDifference(vectorOrigin);

        // calc dot product
        float xProd = (float) (calcVector1.getX() * calcVector2.getX());
        float yProd = (float) (calcVector1.getY() * calcVector2.getY());
        float zProd = (float) (calcVector1.getZ() * calcVector2.getZ());
        float dotProduct = (xProd + yProd + zProd) / (calcVector1.modulus() * calcVector2.modulus());

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
