package com.josinosle.magicengines.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerMana {

    private int mana;
    private int maxMana = 10000;

    public int getMana(){
        return mana;
    }

    public void addMana(int add){
        this.mana = (int) (mana + (maxMana -mana)*0.01*add);
    }

    public void subMana(int sub){
        this.mana = mana - sub;
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
        this.maxMana = source.maxMana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
        nbt.putInt("maxMana", maxMana);
    }

    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getInt("mana");
        maxMana = nbt.getInt("maxMana");
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void addMaxMana(int addition) {
        this.maxMana += addition;
    }

    public boolean isManaFull() {
        return mana == maxMana;
    }
}
