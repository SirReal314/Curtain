package dev.dubhe.curtain.mixins.rules.update_suppression_crash_fix;

import dev.dubhe.curtain.CurtainRules;
import dev.dubhe.curtain.helpers.rule.update_suppression_crash_fix.ThrowableSuppression;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InstantNeighborUpdater.class)
public class InstantNeighborUpdaterMixin {

    @Inject(
            method = "neighborChanged(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onNeighborChanged(BlockState state, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean movedByPiston, CallbackInfo ci) {
        if (CurtainRules.updateSuppressionCrashFix) {
            ci.cancel();

            try {
                InstantNeighborUpdaterAccessor accessor = (InstantNeighborUpdaterAccessor) this;
                Level level = accessor.getLevel();

                NeighborUpdater.executeUpdate(level, state, pos, sourceBlock, sourcePos, movedByPiston);
            } catch (StackOverflowError | ThrowableSuppression e) {
                throw new ThrowableSuppression("Update suppression", pos);
            }
        }
    }
}
