package net.dhuumme.advanced.difficulty;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ModDifficulty {

    public static void init() {
        // Inscrire un événement pour chaque tick du serveur
        ServerTickEvents.END_SERVER_TICK.register(ModDifficulty::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            updateDifficulty(server, player.getWorld());
        }
    }

    private static void updateDifficulty(MinecraftServer server, World world) {
        long timeOfDay = world.getTimeOfDay() % 24000; // Récupère le temps actuel dans le cycle de 24 000 ticks

        if (timeOfDay == 13000) { // C'est la nuit qui commence
            executeCommand(server, "say §4La nuit est tombée");
            executeCommand(server, "say Les nuits sont difficiles");
            server.setDifficulty(Difficulty.HARD, true);
        } else if (timeOfDay == 23000) { // C'est le jour qui commence
            executeCommand(server, "say §eLe jour vient de se lever!");
            executeCommand(server, "say Encore une journée normale");
            server.setDifficulty(Difficulty.NORMAL, true);
        }
    }

    private static void executeCommand(MinecraftServer server, String command) {
        ServerCommandSource commandSource = server.getCommandSource();
        server.getCommandManager().executeWithPrefix(commandSource, command);
    }
}

