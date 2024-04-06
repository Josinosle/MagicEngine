package com.josinosle.magicengines.util.cantrip;

import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.LinkedList;

@AutoRegisterCapability
public class PlayerCantrip {

    // array of LinkedLists containing the cast runes (each LinkedList is a cantrip)
    private LinkedList<CastRune>[] castRunes = new LinkedList[4];

    /**
     * Cantrip set method
     * @param castRunesImported     import a premade cantrip of {@link CastRune} objects
     * @param index     import the index of which cantrip to override
     */
    public void setCantrip (LinkedList<CastRune> castRunesImported, int index){
        this.castRunes[index] = castRunesImported;
    }

    // copy from source method
    public void copyFrom(PlayerCantrip source){
        this.castRunes = source.castRunes;
    }

    /**
     * Convert {@link CastRune} LinkedList to composing primitives and save {@link CompoundTag} data
     *
     * @param nbt   nbt tag to save to
     */
    public void saveNBTData(CompoundTag nbt){
        // dissect cast runes into composing arrays and a rune size pointer

        LinkedList<Integer> runeList = new LinkedList<>();
        LinkedList<Integer> magnitudeList = new LinkedList<>();

        // loop through the array of cantrips to format the important data
        for (int cantripIndex = 0; cantripIndex < 4; cantripIndex++) {
            for (int i = 0; i < 9; i++) {
                // check the castrune is not null and that the castrune trying to call wont return a index not in range error
                if (castRunes[cantripIndex] != null && i < castRunes[cantripIndex].size()) {

                    // add data to saveable formats of lists
                    runeList.addLast(castRunes[cantripIndex].get(i).getRune());
                    magnitudeList.addLast(castRunes[cantripIndex].get(i).getCastMagnitude());

                    continue;
                }
                // default value of arrays (important for delimiting the arrays)
                runeList.addLast(0);
                magnitudeList.addLast(0);
            }
        }

        // save formatted lists into NBT
        nbt.putIntArray("runeList", runeList);
        nbt.putIntArray("magnitudeList", magnitudeList);
    }

    /**
     * Pull {@link CompoundTag} and convert into appropriate list of {@link CastRune}
     *
     * @param nbt   nbt tag to load
     */
    public void loadNBTData(CompoundTag nbt){

        // pull NBT primitives
        int [] pulledRuneList = nbt.getIntArray("runeList");
        int [] pulledMagnitudeList = nbt.getIntArray("magnitudeList");

        // build the CastArrays from the single array
        int cantripIndex = 0;
        castRunes[0] = new LinkedList<>();

        // loop through the pulled rune list to begin formatting back to a readable LinkedList
        for (int i = 0; i < 36; i++) {
            if (pulledRuneList[i] != 0) {

                // build pushable CastRune
                CastRune runeToPush = new CastRune(
                        pulledMagnitudeList[i],
                        pulledRuneList[i]
                );

                castRunes[cantripIndex].addLast(runeToPush);
            }
            // condition to begin constructing new CastRunes LinkedList
            if (( i+1) % 9 == 0 && i != 35) { // checks if the next iteration's index value is a factor of 9
                cantripIndex++; // switches to a new cantrip
                castRunes[cantripIndex] = new LinkedList<>();
            }
        }
    }

    /**
     * Casting Logic for {@link PlayerCantrip}
     * @param vector vector where cast takes place
     * @param player player to cast upon
     * @param manaEfficiency mana efficiency passed down from stave
     * @param cantripIndex index of cantrip (1-4)
     */
    public void castCantrip (Vec3 vector, ServerPlayer player, double manaEfficiency, int cantripIndex) {
        // check if the cantrip has been set by a capability
        if (castRunes[cantripIndex].isEmpty()) {
            // return error message to the chat
            player.sendSystemMessage(Component.literal("No Cantrip Saved").withStyle(ChatFormatting.DARK_RED));
            return;
        }
        CastHelper.cast(castRunes[cantripIndex], vector, player, manaEfficiency); // cast the cantrip
    }
}
