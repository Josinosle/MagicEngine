package com.josinosle.magicengines.util.casting;

import com.josinosle.magicengines.entity.spells.abstractspell.AbstractSpellEntity;
import com.josinosle.magicengines.mana.PlayerManaProvider;
import com.josinosle.magicengines.registry.SpellRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to turn a casting list array into an effect output
 * @author josinosle
 */
public class CastHelper {

    /**
     * Method to convert a cast stack into logical inputs to trigger a spell action in the spell utility classes
     *
     * @param castStack     the casting stack containing all runes in the current cast
     * @param position      the position vector of the current spell logic
     */
    public static void cast(ArrayList<CastRune> castStack, Vec3 position, ServerPlayer player, double manaEfficiency){

        boolean isRayRecursion = false;

        // loop through to check if this scenario is a ray recursion
        for (CastRune rune : castStack) {
            if (rune.getRune() == -1) {
                isRayRecursion = true;
                break;
            }
        }
        // print condition for ray recursion being false
        if (!isRayRecursion) {
            player.sendSystemMessage(Component.literal("Cast Stack").withStyle(ChatFormatting.GOLD));
        }

        int manaCastCost = 0; // mana cost for whole cast instantiate

        // spell targets
        ArrayList<LivingEntity> targetList = new ArrayList<>();

        for (CastRune castStackIteration : castStack) {

            // buffer rune condition
            if (castStackIteration.getRune() == 1) {
                targetList.clear();
                continue;
            }

            // add targeting condition
            if (targetList.isEmpty()) {
                targetList = castTarget(castStackIteration, castStack, player, position, manaEfficiency);
                // condition where list has been cleared due to ray targetting method
                if (targetList == null) {
                    return;
                }
                if (targetList.isEmpty()) {
                    player.sendSystemMessage(Component.literal("Stack syntax error: no targets defined").withStyle(ChatFormatting.DARK_RED));
                    break;
                }
            }

            // find rune in switch statement
            switch (castStackIteration.getRune()) {
                case 221:
                    manaCastCost += SpellRegistry.UNASPECTED_DAMAGE.get().triggerCast(player, targetList, manaEfficiency, castStackIteration.getCastMagnitude());
                    player.sendSystemMessage(Component.literal("Abstract Damage (Damage: " + castStackIteration.getCastMagnitude() + (")")).withStyle(ChatFormatting.DARK_AQUA));
                    continue;
                    // abstract damage effect
                case 324:
                    manaCastCost += SpellRegistry.KINETIC_DEFENSE.get().triggerCast(player, targetList, manaEfficiency, castStackIteration.getCastMagnitude());
                    player.sendSystemMessage(Component.literal("Kinetic Protection").withStyle(ChatFormatting.DARK_AQUA));
                    continue;
                    // kinetic protection effect
                case 321:
                    manaCastCost += SpellRegistry.MAGIC_DEFENSE.get().triggerCast(player, targetList, manaEfficiency, castStackIteration.getCastMagnitude());
                    player.sendSystemMessage(Component.literal("Magic Protection").withStyle(ChatFormatting.DARK_AQUA));
                    continue;
                    // magic protection effect
                case 322:
                    manaCastCost += SpellRegistry.ELEMENTAL_DEFENSE.get().triggerCast(player, targetList, manaEfficiency, castStackIteration.getCastMagnitude());
                    player.sendSystemMessage(Component.literal("Elemental Protection").withStyle(ChatFormatting.DARK_AQUA));
                    continue;
                    // elemental protection effect
                case 312:
                    manaCastCost += SpellRegistry.THROW.get().triggerCast(player, targetList, manaEfficiency, castStackIteration.getCastMagnitude());
                    player.sendSystemMessage(Component.literal("Push (Push Power: " + castStackIteration.getCastMagnitude() + ")").withStyle(ChatFormatting.DARK_AQUA));
                    continue;
                    // push effect
            }

            // chat output for an erroneous rune
            if (castStackIteration.getRune() > 4) {
                    player.sendSystemMessage(Component.literal("Invalid Rune: " + castStackIteration.getRune()).withStyle(ChatFormatting.DARK_RED));
                    break;
            }
        }

        // change max mana (training effect)
        int integerToAdd = manaCastCost / 100;
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {

            // add integer to mana maximum
            mana.addMaxMana(integerToAdd);
        });
    }

    /**
     * Method to search a casting stack to find a rune indicating a casting target
     *
     * @param rune      rune containing data
     * @param castStack {@link ArrayList} of {@link CastRune} to pass onto targeting
     * @param player    the player responsible for the cast logic
     * @param vector    the vector on which logic acts upon
     * @return an array list containing the entities targeted
     */
    private static ArrayList<LivingEntity> castTarget(CastRune rune, ArrayList<CastRune> castStack, ServerPlayer player, Vec3 vector, double pManaEfficiency) {

        // define temp entity list
        ArrayList<LivingEntity> entities = new ArrayList<>();
        String target = "None";

        // self condition
        if (rune.getRune() == 2){
            entities.add(player);
            target = "Self";
        }

        // single target condition
        if (rune.getRune() == 3){
            Level level = player.getLevel();
            if (!level.isClientSide) {
                AbstractSpellEntity abstractSpell = new AbstractSpellEntity(level,player,castStack,pManaEfficiency);
                abstractSpell.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(abstractSpell);
            }
            return null;
        }

        // entities in area condition
        if (rune.getRune() == 4 || rune.getRune() == -1){

            // find rune's magnitude
            int runeMag = rune.getCastMagnitude();

            target = "Area (radius: "+runeMag+")";

            // conditions if originating from a ray recursion
            if (rune.getRune() == -1) {
                runeMag = 3;
                target = "Ray";
            }

            // define a bounding box
            AABB boundBox = new AABB(vector.x() - runeMag, vector.y() - runeMag, vector.z() - runeMag, vector.x() + runeMag, vector.y() + runeMag, vector.z() + runeMag);


            // cast bounding box entities to living entities type
            List<LivingEntity> entToDamage = new ArrayList<>();
            for (Entity entity : player.getLevel().getEntities(null, boundBox)) {
                if (entity instanceof LivingEntity) entToDamage.add((LivingEntity) entity);
            }

            // add entities in a bounding box to working list
            for(LivingEntity entity : entToDamage) {
                if (entity.getId() != player.getId()) {
                    entities.add(entity);
                }
            }
        }
        // return the list of entities
        player.sendSystemMessage(Component.literal("Target: " + target).withStyle(ChatFormatting.WHITE));
        return entities;
    }
}




