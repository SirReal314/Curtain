package dev.dubhe.curtain.mixins.rules.instant_block_updater_reintroduced;

import dev.dubhe.curtain.helpers.rule.instant_block_updater_reintroduced.NeighborUpdaterChangeableLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(Level.class)
public abstract class LevelMixin implements NeighborUpdaterChangeableLevel
{
    @Mutable
    @Shadow
    @Final
    protected NeighborUpdater neighborUpdater;

    private NeighborUpdater previousNeighborUpdater$CM = null;
    private boolean usingInstantNeighborUpdater$CM = false;

    @Override
    public void setUseInstantNeighborUpdater$CM(boolean useInstant)
    {
        if (useInstant == this.usingInstantNeighborUpdater$CM)
        {
            return;
        }
        if (useInstant)
        {
            this.previousNeighborUpdater$CM = this.neighborUpdater;
            this.neighborUpdater = new InstantNeighborUpdater((Level)(Object)this);
        }
        else
        {
            this.neighborUpdater = Objects.requireNonNull(this.previousNeighborUpdater$CM);
            this.previousNeighborUpdater$CM = null;
        }
        this.usingInstantNeighborUpdater$CM = useInstant;
    }
}
