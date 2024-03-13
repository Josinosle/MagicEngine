package com.josinosle.magicengines.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    private int mana;

    public int getMana(){
        return mana;
    }

    public void addMana(int add){
        int maxMana = 10000;
        this.mana = (int) (mana + (maxMana -mana)*0.05*add)  ;

    }

    public void subMana(int sub){
        this.mana = mana - sub;
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
    }
}
