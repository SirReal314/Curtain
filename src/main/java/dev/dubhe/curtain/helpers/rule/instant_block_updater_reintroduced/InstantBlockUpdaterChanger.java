package dev.dubhe.curtain.helpers.rule.instant_block_updater_reintroduced;

import dev.dubhe.curtain.CurtainRules;
import dev.dubhe.curtain.events.utils.ServerEventHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class InstantBlockUpdaterChanger {
    public static void apply(boolean useInstant)
    {
        MinecraftServer server =  ServerEventHandler.minecraftServer;
        if (server != null)
        {
            server.execute(() -> {
                for (ServerLevel level : server.getAllLevels())
                {
                    ((NeighborUpdaterChangeableLevel)level).setUseInstantNeighborUpdater$CM(useInstant);
                }
            });
        }
    }

    public static void apply() {
        apply(CurtainRules.reIntroduceInstantBlockUpdates);
    }
}