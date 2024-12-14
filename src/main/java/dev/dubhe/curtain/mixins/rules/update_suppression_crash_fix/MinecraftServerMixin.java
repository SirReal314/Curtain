package dev.dubhe.curtain.mixins.rules.update_suppression_crash_fix;

import dev.dubhe.curtain.CurtainRules;
import dev.dubhe.curtain.utils.Messenger;
import dev.dubhe.curtain.utils.ThrowableSuppression;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Redirect(
            method = "tickChildren",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;tick(Ljava/util/function/BooleanSupplier;)V"
            ),
            require = 0
    )
    private void fixUpdateSuppressionCrashTick(ServerLevel serverWorld, BooleanSupplier shouldKeepTicking) {
        if (!CurtainRules.updateSuppressionCrashFix) {
            serverWorld.tick(shouldKeepTicking);
            return;
        }
        try {
            serverWorld.tick(shouldKeepTicking);
        } catch (ReportedException e) {
            if (e.getCause() instanceof ThrowableSuppression throwableSuppression) {
                logUpdateSuppression(throwableSuppression.pos, (MinecraftServer) (Object) this);
            } else {
                throw e;
            }
        } catch (ThrowableSuppression e) {
            logUpdateSuppression(e.pos, (MinecraftServer) (Object) this);
        }
    }

    private void logUpdateSuppression(BlockPos pos, MinecraftServer server) {
        // Construct the log message using the Messenger utility
        Component logMessage = Messenger.c(
                "w Server crash prevented in: ",
                "m world tick ",
                "w - at: ",
                Messenger.tp("g", pos)
        );

        // Log the message to the server's log and notify players
        Messenger.LOG.warn(logMessage.getString());
        Messenger.print_server_message(server, logMessage);
    }
}
