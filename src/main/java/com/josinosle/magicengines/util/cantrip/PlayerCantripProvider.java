package com.josinosle.magicengines.util.cantrip;

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

public class PlayerCantripProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerCantrip> PLAYER_CANTRIP = CapabilityManager.get(new CapabilityToken<>() { });

    private PlayerCantrip cantrip = null;
    private final LazyOptional<PlayerCantrip> optional = LazyOptional.of(this::createPlayerCantrip);

    private PlayerCantrip createPlayerCantrip() {
        if(this.cantrip == null){
            this.cantrip = new PlayerCantrip();
        }

        return this.cantrip;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == PLAYER_CANTRIP){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCantrip().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCantrip().loadNBTData(nbt);
    }
}
