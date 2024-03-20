package com.josinosle.magicengines.util.castgeometry;

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
import java.util.Objects;

public class CastHelper {

    public static void castSpell(ArrayList<String> castStack, CastVector position, ServerLevel level, ServerPlayer player){
        player.sendSystemMessage(Component.literal("Cast Stack").withStyle(ChatFormatting.GOLD));

        // spell targets
        ArrayList<Entity> targetList = castTarget(castStack, player,position);

        for (String castStackIteration : castStack){

            // print current rune effect
            System.out.println(castStackIteration);

            // unaspected damage spell
            if (Objects.equals(castStackIteration, "BBA")){
                // iterate through working targets
                for (Entity entityIteration : targetList) {
                    new SpellDamage(entityIteration, player);
                    player.sendSystemMessage(Component.literal("Unaspected Damage").withStyle(ChatFormatting.DARK_AQUA));
                }
                continue;
            }

            // protection spell
            if (Objects.equals(castStackIteration, "CBD")){
                // iterate through working targets
                new PlayerDefence(player, targetList);
                player.sendSystemMessage(Component.literal("Protective Barrier").withStyle(ChatFormatting.DARK_AQUA));

                continue;
            }

            // telekenetic slam
            if (Objects.equals(castStackIteration, "CAB")){
                // iterate through working targets
                for (Entity entityIteration : targetList) {
                    new TelekeneticSlam(entityIteration, player);
                    player.sendSystemMessage(Component.literal("Telekinetic Slam").withStyle(ChatFormatting.DARK_AQUA));
                }
                continue;
            }

            // force flatulence
            if(Objects.equals(castStackIteration, "ACAB")) {
                new SpellFart(level, player, position);
                player.sendSystemMessage(Component.literal("Force Flatulence ").withStyle(ChatFormatting.DARK_AQUA));
                continue;
            }

            if(!(castStackIteration.length()==1)) {
                // null rune error message
                player.sendSystemMessage(Component.literal("Invalid Rune:" + castStackIteration).withStyle(ChatFormatting.DARK_RED));
            }
        }
    }

    // cast targeting method
    private static ArrayList<Entity> castTarget(ArrayList<String> castStack, ServerPlayer player, CastVector vector){

        // define temp entity list
        ArrayList<Entity> entities = new ArrayList<>();
        String target = "None";

        for (String castStackIteration : castStack){

            // self condition
            if (Objects.equals(castStackIteration, "B")){
                entities.add(player);
                target = "Self";
                break;
            }

            // single target condition
            if (Objects.equals(castStackIteration, "C")){
                target = "Target";

                // define a bounding box for a single block radius
                AABB boundBox = new AABB(vector.getX() - 0.5, vector.getY() - 0.5, vector.getZ() - 0.5, vector.getX() + 0.5, vector.getY() + 0.5, vector.getZ() + 0.5);

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
            if (Objects.equals(castStackIteration, "D")){

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
