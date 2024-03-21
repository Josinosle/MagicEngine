package com.josinosle.magicengines.util.casting;

import com.josinosle.magicengines.spells.spellcontent.combat.PlayerDefence;
import com.josinosle.magicengines.spells.spellcontent.combat.SpellDamage;
import com.josinosle.magicengines.spells.spellcontent.combat.TelekeneticSlam;
import com.josinosle.magicengines.spells.spellcontent.fun.SpellFart;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

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
     * @param level         the level upon which casting effects occur
     * @param player        the player responsible for the casting logic
     */
    public static void castSpell(ArrayList<Integer> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        player.sendSystemMessage(Component.literal("Cast Stack").withStyle(ChatFormatting.GOLD));

        // spell targets
        ArrayList<Entity> targetList = castTarget(castStack, player,position);

        for (int castStackIteration : castStack){

            // print current rune effect
            System.out.println(castStackIteration);

            // unaspected damage spell
            if (castStackIteration == 221){
                // iterate through working targets
                for (Entity entityIteration : targetList) {
                    new SpellDamage(entityIteration, player);
                    player.sendSystemMessage(Component.literal("Unaspected Damage").withStyle(ChatFormatting.DARK_AQUA));
                }
                continue;
            }

            // protection spell
            if (castStackIteration == 324){
                // iterate through working targets
                new PlayerDefence(player, targetList);
                player.sendSystemMessage(Component.literal("Protective Barrier").withStyle(ChatFormatting.DARK_AQUA));

                continue;
            }

            // telekenetic slam
            if (castStackIteration == 312){
                // iterate through working targets
                for (Entity entityIteration : targetList) {
                    new TelekeneticSlam(entityIteration, player);
                    player.sendSystemMessage(Component.literal("Telekinetic Slam").withStyle(ChatFormatting.DARK_AQUA));
                }
                continue;
            }

            // force flatulence
            if(castStackIteration == 1312) {
                new SpellFart(level, player, position);
                player.sendSystemMessage(Component.literal("Force Flatulence ").withStyle(ChatFormatting.DARK_AQUA));
                continue;
            }

            // chat output for an erroneous rune
            player.sendSystemMessage(Component.literal("Invalid Rune").withStyle(ChatFormatting.DARK_RED));
        }
    }

    /**
     * Method to search a casting stack to find a rune indicating a casting target
     *
     * @param castStack     the casting stack containing all runes in the current cast
     * @param player        the player responsible for the cast logic
     * @param vector        the vector on which logic acts upon
     * @return  an array list containing the entities targeted
     */
    private static ArrayList<Entity> castTarget(ArrayList<Integer> castStack, ServerPlayer player, CastVector vector){

        // define temp entity list
        ArrayList<Entity> entities = new ArrayList<>();
        String target = "None";

        for (int castStackIteration : castStack){

            // self condition
            if (castStackIteration == 2){
                entities.add(player);
                target = "Self";
                break;
            }

            // single target condition
            if (castStackIteration == 3){
                target = "Target";

                // define a bounding box for a single block radius
                AABB boundBox = new AABB(vector.getX() - 1, vector.getY() - 1, vector.getZ() - 1, vector.getX() + 1, vector.getY() + 1, vector.getZ() + 1);

                // add entities in a bounding box to working list
                List<Entity> entToDamage = player.getLevel().getEntities(null, boundBox);

                for(Entity entity : entToDamage) {
                    if (entity.getId() != player.getId()) {
                        entities.add(entity);
                    }
                }

                break;
            }

            // entities in area condition
            if (castStackIteration == 4){

                // define a bounding box
                AABB boundBox = new AABB(vector.getX() - 5, vector.getY() - 5, vector.getZ() - 5, vector.getX() + 5, vector.getY() + 5, vector.getZ() + 5);

                // add entities in a bounding box to working list
                List<Entity> entToDamage = player.getLevel().getEntities(null, boundBox);
                for(Entity entity : entToDamage) {
                    if (entity.getId() != player.getId()) {
                        entities.add(entity);
                    }
                }

                target = "Area";
                break;
            }
        }

        // return the list of entities
        player.sendSystemMessage(Component.literal("Target: " + target).withStyle(ChatFormatting.WHITE));
        return entities;
    }
}
