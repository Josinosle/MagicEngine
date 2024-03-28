package com.josinosle.magicengines.item.staves;

/**
 * Abstract class for staves to follow
 *
 * @author josinosle
 */
public class WoodenStave extends AbstractStave {

    /**
     * Mana efficiency when casting using a stave, represented as a decimal mana cost multiplier
     */
    private static final float manaEfficiency = 1F;

    /**
     * Constructor for wands
     *
     * @param properties        Properties for the wand item
     */
    public WoodenStave(Properties properties) {
        super(properties);
    }

}
