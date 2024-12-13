package dev.dubhe.curtain;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.curtain.api.PlanExecution;
import dev.dubhe.curtain.api.rules.RuleManager;
import dev.dubhe.curtain.commands.LogCommand;
import dev.dubhe.curtain.commands.PlayerCommand;
import dev.dubhe.curtain.commands.RuleCommand;
import dev.dubhe.curtain.events.rules.PlayerEventHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.PerfCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CurtainServer {
    public static final String ID = "curtain";

    public static MinecraftServer minecraft_server;
    private static CommandDispatcher<CommandSourceStack> currentCommandDispatcher;
//    public static CurtainScriptServer scriptServer;
    public static RuleManager ruleManager;
//    public static final List<CurtainExtension> extensions = new ArrayList<>();

    public CurtainServer() {
        ruleManager = new RuleManager(CurtainSettings.curtainVersion, "curtain", "Curtain Mod");
        ruleManager.parseSettingsClass(CurtainSettings.class);
//        extensions.forEach(CurtainExtension::onGameStarted);
//        CurtainScriptServer.parseFunctionClasses();
    }

//    public static void manageExtension(CurtainExtension extension) {
//        extensions.add(extension);
//        // for extensions that come late to the party, after server is created / loaded
//        // we will handle them now.
//        // that would handle all extensions, even these that add themselves really late to the party
//        if (currentCommandDispatcher != null) {
//            extension.registerCommands(currentCommandDispatcher);
//            CurtainSettings.LOG.warn(extension.getClass().getSimpleName() + " extension registered too late!");
//            CurtainSettings.LOG.warn("This won't be supported in later Curtain versions and may crash the game!");
//        }
//    }

    public static void onServerLoaded(MinecraftServer server) {
        CurtainServer.minecraft_server = server;
        // shoudl not be needed - that bit needs refactoring, but not now.
//        SpawnReporter.reset_spawn_stats(server, true);
//
//        ruleManager.attachServer(server);
//        extensions.forEach(e -> {
//            SettingsManager sm = e.customSettingsManager();
//            if (sm != null) sm.attachServer(server);
//            e.onServerLoaded(server);
//        });
//        scriptServer = new CurtainScriptServer(server);
//        MobAI.resetTrackers();
//        LoggerRegistry.initLoggers();
        //TickSpeed.reset();
    }

    public static void onServerLoadedWorlds(MinecraftServer minecraftServer) {
//        HopperCounter.resetAll(minecraftServer, true);
//        extensions.forEach(e -> e.onServerLoadedWorlds(minecraftServer));
//        scriptServer.initializeForWorld();
    }

    public static void tick(MinecraftServer server) {
//        TickSpeed.tick();
//        HUDController.update_hud(server, null);
//        if (scriptServer != null) scriptServer.tick();

        //in case something happens
//        CurtainRules.impendingFillSkipUpdates.set(false);

//        extensions.forEach(e -> e.onTick(server));
    }

    @Deprecated
    public static void registerCurtainCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
    }

    public static void registerCurtainCommands(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
//        if (ruleManager == null) { // bootstrap dev initialization check
//            return;
//        }
//        ruleManager.registerCommand(dispatcher);
//        extensions.forEach(e -> {
//            SettingsManager sm = e.customSettingsManager();
//            if (sm != null) sm.registerCommand(dispatcher);
//        });
//        TickCommand.register(dispatcher);
//        ProfileCommand.register(dispatcher);
//        CounterCommand.register(dispatcher);
//        LogCommand.register(dispatcher);
//        SpawnCommand.register(dispatcher);
//        PlayerCommand.register(dispatcher);
//        //CameraModeCommand.register(dispatcher);
//        InfoCommand.register(dispatcher);
//        DistanceCommand.register(dispatcher);
//        PerimeterInfoCommand.register(dispatcher);
//        DrawCommand.register(dispatcher);
//        ScriptCommand.register(dispatcher);
//        MobAICommand.register(dispatcher);
//        // registering command of extensions that has registered before either server is created
//        // for all other, they will have them registered when they add themselves
//        extensions.forEach(e -> e.registerCommands(dispatcher));
//        currentCommandDispatcher = dispatcher;
//
//        if (environment != Commands.CommandSelection.DEDICATED)
//            PerfCommand.register(dispatcher);
//
//        if (FMLLoader.isProduction())
//            TestCommand.register(dispatcher);
//        // todo 1.16 - re-registerer apps if that's a reload operation.
    }

    public static void onPlayerLoggedIn(ServerPlayer player) {
//        ServerNetworkHandler.onPlayerJoin(player);
//        LoggerRegistry.playerConnected(player);
//        scriptServer.onPlayerJoin(player);
//        extensions.forEach(e -> e.onPlayerLoggedIn(player));

    }

    public static void onPlayerLoggedOut(ServerPlayer player) {
//        PlayerEventHandler.onPlayerLeave(player);
//        LoggerRegistry.playerDisconnected(player);
//        extensions.forEach(e -> e.onPlayerLoggedOut(player));
    }

    public static void clientPreClosing() {
//        if (scriptServer != null) scriptServer.onClose();
//        scriptServer = null;
    }

    public static void onServerClosed(MinecraftServer server) {
        // this for whatever reason gets called multiple times even when joining on SP
        // so we allow to pass multiple times gating it only on existing server ref
//        if (minecraft_server != null) {
//            if (scriptServer != null) scriptServer.onClose();
//            scriptServer = null;
//            ServerNetworkHandler.close();
//            currentCommandDispatcher = null;
//
//            LoggerRegistry.stopLoggers();
//            HUDController.resetScurtainHUDs();
//            extensions.forEach(e -> e.onServerClosed(server));
//            minecraft_server = null;
//        }
//
//        // this for whatever reason gets called multiple times even when joining;
//        TickSpeed.reset();
    }
    public static void onServerDoneClosing(MinecraftServer server) {
//        ruleManager.detachServer();
    }

    public static void registerExtensionLoggers() {
//        extensions.forEach(CurtainExtension::registerLoggers);
    }

    public static void onReload(MinecraftServer server) {
//        scriptServer.reload(server);
//        extensions.forEach(e -> e.onReload(server));
    }

}
