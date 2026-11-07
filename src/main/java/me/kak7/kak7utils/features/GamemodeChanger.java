package me.kak7.kak7utils.features;

import com.google.common.base.MoreObjects;
import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;

public class GamemodeChanger {
    private final KeyMapping keyBindingGamemodeChanger = Hotkeys.getKeyBindingGamemodeChanger();
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public GamemodeChanger() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindingGamemodeChanger.consumeClick() & config.isUseFastGamemodeSwitcher()) {

                if (!Minecraft.getInstance().player.isSpectator()) {
                    Minecraft.getInstance().player.connection.sendCommand("gamemode spectator");
                } else {
                    LocalPlayer entity = Minecraft.getInstance().player;
                    GameType gamemode = Minecraft.getInstance().gameMode.getPreviousPlayerMode();
                    entity.connection.sendCommand("gamemode " + MoreObjects.firstNonNull(gamemode, GameType.CREATIVE).getName());
                }
            }
        });
    }

    // todo singleton
    public static void initialize() {
        new GamemodeChanger();
    }


}
