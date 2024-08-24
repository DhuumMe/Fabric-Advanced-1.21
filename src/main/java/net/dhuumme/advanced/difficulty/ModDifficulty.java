package net.dhuumme.advanced.difficulty;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ModDifficulty {

    private static boolean isNight = false;
    private static boolean isDay = false;

    public static void init() {
        // Inscrire un événement pour chaque tick du serveur
        ServerTickEvents.END_SERVER_TICK.register(ModDifficulty::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        updateDifficulty(server, server.getOverworld()); // Vérifie uniquement l'Overworld
    }

    private static void updateDifficulty(MinecraftServer server, World world) {
        long timeOfDay = world.getTimeOfDay() % 24000; // Récupère le temps actuel dans le cycle de 24 000 ticks

        if (timeOfDay >= 14000 && timeOfDay < 22000) { // C'est la nuit
            if (!isNight) { // Si la nuit commence
                executeCommand(server, "title @a actionbar {\"text\":\"La nuit s'annonce difficile\",\"color\":\"dark_red\"}");
                server.setDifficulty(Difficulty.HARD, true);
                isNight = true;
                isDay = false; // Réinitialiser l'état du jour
            }
        } else { // C'est le jour
            if (!isDay) { // Si le jour commence
                executeCommand(server, "title @a actionbar {\"text\":\"Tout semble normal aujourd'hui\",\"color\":\"gold\"}");
                server.setDifficulty(Difficulty.NORMAL, true);
                isDay = true;
                isNight = false; // Réinitialiser l'état de la nuit
            }
        }
    }

    private static void executeCommand(MinecraftServer server, String command) {
        ServerCommandSource commandSource = server.getCommandSource();
        server.getCommandManager().executeWithPrefix(commandSource, command);
    }
}