package dev.dubhe.curtain.mixins.rules.update_suppression_crash_fix;

import com.mojang.authlib.GameProfile;
import dev.dubhe.curtain.CurtainRules;
import dev.dubhe.curtain.utils.Messenger;
import dev.dubhe.curtain.utils.ThrowableSuppression;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Redirect(
            method = "doTick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;tick()V"
            ),
            require = 0
    )
    private void fixUpdateSuppressionCrashPlayerTick(Player playerEntity) {
        if (!CurtainRules.updateSuppressionCrashFix) {
            super.tick();
            return;
        }
        try {
            super.tick();
        } catch (ReportedException e) {
            if (e.getCause() instanceof ThrowableSuppression throwableSuppression) {
                logUpdateSuppressionPlayer(throwableSuppression.pos, (ServerPlayer) (Object) this);
            } else {
                throw e;
            }
        } catch (ThrowableSuppression e) {
            logUpdateSuppressionPlayer(e.pos, (ServerPlayer) (Object) this);
        }
    }

    private void logUpdateSuppressionPlayer(BlockPos pos, ServerPlayer player) {
        // Construct the log message using the Messenger utility
        Component logMessage = Messenger.c(
                "w Server crash prevented in: ",
                "m player tick ",
                "w - at: ",
                Messenger.tp("g", pos)
        );

        // Log the message to the server's log and notify the player
        Messenger.LOG.warn(logMessage.getString());
        player.sendSystemMessage(logMessage);
    }
}
