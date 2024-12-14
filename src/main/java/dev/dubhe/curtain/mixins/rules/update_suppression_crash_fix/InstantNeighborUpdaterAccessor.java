package dev.dubhe.curtain.mixins.rules.update_suppression_crash_fix;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InstantNeighborUpdater.class)
public interface InstantNeighborUpdaterAccessor {
    @Accessor("level")
    Level getLevel();
}