package me.kak7.kak7utils.features;

import com.google.common.base.MoreObjects;
import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.world.GameMode;

import java.util.HashMap;

public class GamemodeChanger {
    private final KeyBinding keyBindingGamemodeChanger = Hotkeys.getKeyBindingGamemodeChanger();
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public GamemodeChanger() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindingGamemodeChanger.wasPressed() & config.isUseFastGamemodeSwitcher()) {

                if (!MinecraftClient.getInstance().player.isSpectator()) {
                    MinecraftClient.getInstance().player.networkHandler.sendCommand("gamemode spectator");
                } else {
                    ClientPlayerEntity entity = MinecraftClient.getInstance().player;
                    GameMode gamemode = MinecraftClient.getInstance().interactionManager.getPreviousGameMode();
                    entity.networkHandler.sendCommand("gamemode " + MoreObjects.firstNonNull(gamemode, GameMode.CREATIVE).getName());
                }
            }
        });
    }

    // todo singleton
    public static void initialize() {
        new GamemodeChanger();
    }


}
