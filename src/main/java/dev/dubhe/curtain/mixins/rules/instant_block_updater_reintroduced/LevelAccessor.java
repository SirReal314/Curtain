package dev.dubhe.curtain.mixins.rules.instant_block_updater_reintroduced;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Level.class)
public interface LevelAccessor {
    @Accessor("neighborUpdater") @Mutable
    void setNeighborUpdater(NeighborUpdater updater);
}
