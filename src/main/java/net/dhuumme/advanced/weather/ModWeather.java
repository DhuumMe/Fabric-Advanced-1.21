package net.dhuumme.advanced.weather;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;

import java.util.Random;

public class ModWeather {

    private static final Random random = new Random();
    private static final double THUNDERSTORM_CHANCE = 0.3375;

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(ModWeather::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        ServerWorldProperties properties = (ServerWorldProperties) world.getLevelProperties();
        if (world.isRaining() && !world.isThundering()) {
            if (random.nextDouble() < THUNDERSTORM_CHANCE) {
                properties.setThunderTime(6000);
                properties.setThundering(true);
            }
        }
    }
}