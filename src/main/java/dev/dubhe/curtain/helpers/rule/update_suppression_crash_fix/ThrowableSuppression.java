package dev.dubhe.curtain.helpers.rule.update_suppression_crash_fix;

import net.minecraft.core.BlockPos;

public class ThrowableSuppression extends RuntimeException{

    public final BlockPos pos;
    public ThrowableSuppression(String message, BlockPos pos) {
        super(message);
        this.pos = pos;
    }
}