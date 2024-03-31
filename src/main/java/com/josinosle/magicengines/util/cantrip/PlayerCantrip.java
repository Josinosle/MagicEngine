package com.josinosle.magicengines.util.cantrip;

import com.josinosle.magicengines.util.casting.CastHelper;
import com.josinosle.magicengines.util.casting.CastRune;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;

@AutoRegisterCapability
public class PlayerCantrip {

    // array of ArrayLists containing the cast runes (each ArrayList is a cantrip)
    private ArrayList<CastRune>[] castRunes = new ArrayList[4];

    /**
     * Cantrip set method
     * @param castRunesImported     import a premade cantrip of {@link CastRune} objects
     * @param index     import the index of which cantrip to override
     */
    public void setCantrip (ArrayList<CastRune> castRunesImported, int index){
        this.castRunes[index] = castRunesImported;
    }

    // copy from source method
    public void copyFrom(PlayerCantrip source){
        this.castRunes = source.castRunes;
    }

    /**
     * Convert {@link CastRune} ArrayList to composing primitives and save {@link CompoundTag} data
     *
     * @param nbt   nbt tag to save to
     */
    public void saveNBTData(CompoundTag nbt){
        // dissect cast runes into composing arrays and a rune size pointer

        ArrayList<Integer> runeList = new ArrayList<>();
        ArrayList<Integer> magnitudeList = new ArrayList<>();

        // loop through the array of cantrips to format the important data
        for (int cantripIndex = 0; cantripIndex < 4; cantripIndex++) {
            for (int i = 0; i < 9; i++) {
                // check the castrune is not null and that the castrune trying to call wont return a index not in range error
                if (castRunes[cantripIndex] != null && i < castRunes[cantripIndex].size()) {
                    // add data to saveable formats of lists
                    runeList.add(castRunes[cantripIndex].get(i).getRune());
                    magnitudeList.add(castRunes[cantripIndex].get(i).getRune());
                    continue;
                }
                // default value of arrays (important for delimiting the arrays)
                runeList.add(0);
                magnitudeList.add(0);
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
        // loop through the pulled rune list to begin formatting back to a readable ArrayList
        for (int i = 0; i < pulledRuneList.length; i++) {
            if (pulledRuneList[i] != 0) {
                // build pushable CastRune
                CastRune runeToPush = new CastRune();
                runeToPush.forceCreateRune(
                        pulledRuneList[i],
                        pulledMagnitudeList[i]
                );
                // if certain castRune is null then create it
                if (castRunes[cantripIndex] == null) {
                    castRunes[cantripIndex] = new ArrayList<>();
                }
                castRunes[cantripIndex].add(
                        i,
                        runeToPush
                );
            }
            // condition to begin constructing new CastRunes ArrayList
            if (( i+1) % 9 == 0) {
                cantripIndex++;
            }
        }
    }

    /**
     * Casting Logic for {@link PlayerCantrip}
     * @param vector vector where cast takes place
     * @param player player to cast upon
     * @param manaEfficiecy mana efficiency passed down from stave
     * @param cantripIndex index of cantrip (1-4)
     */
    public void castCantrip (Vec3 vector, ServerPlayer player, double manaEfficiecy, int cantripIndex) {
        // check if the cantrip has been set by a capability
        if (castRunes[cantripIndex] == null || castRunes[cantripIndex].get(0).isRuneEmpty()) {
            // return error message to the chat
            player.sendSystemMessage(Component.literal("No Cantrip Saved").withStyle(ChatFormatting.DARK_RED));
            return;
        }
        CastHelper.cast(castRunes[cantripIndex], vector, player, manaEfficiecy); // cast the cantrip
    }
}
