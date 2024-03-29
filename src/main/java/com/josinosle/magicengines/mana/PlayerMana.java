package com.josinosle.magicengines.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    private int mana;

    private int maxMana = 10000;

    public int getMana(){
        return mana;
    }

    public void addMana(int add){
        this.mana = (int) (mana + (maxMana -mana)*0.05*add);
    }

    public void subMana(int sub){
        this.mana = mana - sub;
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
        nbt.putInt("maxMana",maxMana);
    }

    public void loadNBTData(CompoundTag nbt){
        nbt.getInt("mana");
        nbt.getInt("maxMana");
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isManaFull() {
        return mana == maxMana;
    }
}
