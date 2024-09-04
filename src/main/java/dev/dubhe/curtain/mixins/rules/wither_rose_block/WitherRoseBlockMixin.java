package dev.dubhe.curtain.mixins.rules.wither_rose_block;

import dev.dubhe.curtain.CurtainRules;
import net.minecraft.world.level.block.WitherRoseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherRoseBlock.class)
public abstract class WitherRoseBlockMixin {
    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
    private void toughWitherRouse(CallbackInfoReturnable<Boolean> cir)
    {
        if (CurtainRules.toughWitherRose)
        {
            cir.setReturnValue(true);
        }
    }
}
