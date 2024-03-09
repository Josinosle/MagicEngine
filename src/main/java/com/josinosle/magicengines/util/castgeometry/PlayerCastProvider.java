package com.josinosle.magicengines.util.castgeometry;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCastProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<CastLogic> PLAYER_CAST = CapabilityManager.get(new CapabilityToken<CastLogic>() {});

    private CastLogic playerCast = null;
    //private final LazyOptional<CastLogic> optional = LazyOptional.of(this::createPlayerCast);

    private CastLogic createCastLogic(){
        if(this.playerCast == null){
            this.playerCast = new CastLogic();
        }
        return this.playerCast;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return null;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        //createCastLogic().saveNBTData(nbt);
        return nbt;
        //return null;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {

    }
}
