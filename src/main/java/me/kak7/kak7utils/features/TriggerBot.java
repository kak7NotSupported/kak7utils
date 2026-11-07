package me.kak7.kak7utils.features;

import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TriggerBot {

    private final Random r = new Random();

    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    private final Minecraft mc = Minecraft.getInstance();
    private final KeyMapping keyBindingTrigger = Hotkeys.getKeyBindingTrigger();

    public static void initialize() {
        new TriggerBot();
        // todo singleton
    }

    private TriggerBot() {
        registerChecks();
        renderSwitcherUI();
    }

    private void playSound(SoundEvent sound) {
        if (!(config.isDoSound())) return;
        mc.player.playSound(sound, 1.0F, 1.0F);
    }

    private void registerChecks() {


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindingTrigger.consumeClick()) {
                config.setTriggerbotEnabled(!config.isTriggerbotEnabled());
                playSound(config.isTriggerbotEnabled() ? SoundEvents.NOTE_BLOCK_FLUTE.value() : SoundEvents.NOTE_BLOCK_GUITAR.value());
            }

            if (config.isTriggerbotEnabled() && mc.player != null && mc.gameMode != null) {
                mainChecks();
            }
        });
    }

    private void mainChecks() {
        if (mc.player.getAttackStrengthScale(0.0F) == 1.0F && mc.crosshairPickEntity != null) {
            if (config.isAvoidAntiCheat()) {
                try {
                    // todo сделать что-то с таймингами, не бьет по радиусу (иногда)
                    TimeUnit.MILLISECONDS.sleep(this.r.nextInt(50) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            attack();
        }
    }

    private void attack() {
        mc.gameMode.attack(mc.player, mc.crosshairPickEntity);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }


    private void renderSwitcherUI() {

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("kak7util", "triggerbot_switcher"), (graphics, deltaTracker) -> {
            // when renderui is disabled and triggerbot is disabled return
            if (!config.isRenderSwitcherUI() || !config.isTriggerbotEnabled()) return;

            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            // get hotbar position
            int hotbarY = screenHeight - 22;
            int hotbarX = screenWidth / 2 - 91;

            // draw hotbar
            if (config.isRenderHotbarHighight()) {
                graphics.horizontalLine(hotbarX, hotbarX + 181, hotbarY, config.getColor());
            }

            // highlight crosshair
            if (config.isRenderCrosshairHighlight()) {
                int crosshairX = screenWidth / 2 - 3;
                int crosshairY = screenHeight / 2 - 3;
                int crosshairSize = 5;
                int color = config.getColor();
                graphics.horizontalLine(crosshairX, crosshairX + crosshairSize - 1, crosshairY, color);
                graphics.horizontalLine(crosshairX, crosshairX + crosshairSize - 1, crosshairY + crosshairSize - 1, color);
                graphics.verticalLine(crosshairX, crosshairY, crosshairY + crosshairSize - 1, color);
                graphics.verticalLine(crosshairX + crosshairSize - 1, crosshairY, crosshairY + crosshairSize - 1, color);
            }
        });

    }

    @Deprecated
    public boolean canDoCrit() {
        int beforeY = (int) mc.player.getY();
        return !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.onGround() && mc.player.getY() <= beforeY;
    }


}
