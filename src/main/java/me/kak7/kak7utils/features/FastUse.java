package me.kak7.kak7utils.features;

import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.kak7.kak7utils.mixin.MinecraftClientAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public class FastUse {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final KeyBinding keyBindingAutoParkour = Hotkeys.getKeyBindingAutoParkour();
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    private FastUse() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null || !config.isFastPlaceEnabled() || !keyBindingAutoParkour.isPressed()) {
                return;
            }
            if (config.isFixedItemUseCooldown2()) {
                int cooldown = ((MinecraftClientAccessor) mc).kak7utils$getItemUseCooldown();
                if (cooldown > 2) {
                    ((MinecraftClientAccessor) mc).kak7utils$setItemUseCooldown(2);
                }
                return;
            }
            ((MinecraftClientAccessor) mc).kak7utils$setItemUseCooldown(0);
        });
    }

    public static void initialize() {
        new FastUse();
    }
}
