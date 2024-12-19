package me.kak7.kak7utils.features;

import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TriggerBot {

    private final Random r = new Random();

    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final KeyBinding keyBindingTrigger = Hotkeys.getKeyBindingTrigger();

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
            if (keyBindingTrigger.wasPressed()) {
                config.setTriggerbotEnabled(!config.isTriggerbotEnabled());
                playSound(config.isTriggerbotEnabled() ? SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value() : SoundEvents.BLOCK_NOTE_BLOCK_GUITAR.value());
            }

            if (config.isTriggerbotEnabled() && mc.player != null && mc.interactionManager != null) {
                mainChecks();
            }
        });
    }

    private void mainChecks() {
        if (mc.player.getAttackCooldownProgress(0.0F) == 1.0F && mc.targetedEntity != null) {
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
        mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
        mc.player.swingHand(Hand.MAIN_HAND);
    }


    private void renderSwitcherUI() {

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            // when renderui is disabled and triggerbot is disabled return
            if (!config.isRenderSwitcherUI() || !config.isTriggerbotEnabled()) return;

            int screenWidth = mc.getWindow().getScaledWidth();
            int screenHeight = mc.getWindow().getScaledHeight();

            // get hotbar position
            int hotbarY = screenHeight - 22;
            int hotbarX = screenWidth / 2 - 91;

            // draw hotbar
            if (config.isRenderHotbarHighight()) {
                drawContext.drawHorizontalLine(hotbarX, hotbarX + 181, hotbarY, config.getColor());
            }

            // highlight crosshair
            if (config.isRenderCrosshairHighlight()) {
                drawContext.drawBorder(screenWidth / 2 - 3, screenHeight / 2 - 3, 5, 5, config.getColor());
            }


            drawContext.draw();
        });

    }

    @Deprecated
    public boolean canDoCrit() {
        int beforeY = (int) mc.player.getY();
        return !mc.player.isTouchingWater() && !mc.player.isInLava() && !mc.player.isOnGround() && mc.player.getY() <= beforeY;
    }


}
