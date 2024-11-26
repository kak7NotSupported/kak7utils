package me.kak7.kak7utils.features;

import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class AutoParkour {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private volatile boolean isSneakingActivated = false;

    private AutoParkour() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (mc.world != null && shouldTriggerAutoParkour()) {
                attemptParkour();
            }
        });
    }


    public static void initialize() {
        new AutoParkour();
    }

    private boolean shouldTriggerAutoParkour() {
        return (!config.isUseHotKeyMode() || Hotkeys.getKeyBindingAutoParkour().isPressed()) && mc.player != null;
    }

    private void attemptParkour() {
        checkAndPerformSlimeGlitch();
        if (isParkourPossible()) {
            mc.player.jump();
        }

    }

    private boolean isParkourPossible() {
        var jumpKey = mc.options.jumpKey;
        if (!mc.player.isOnGround() || mc.player.isSneaking() || jumpKey.isPressed()) {
            return false;
        }

        Box playerBox = mc.player.getBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001);
        boolean isPathBlocked = mc.world.getBlockCollisions(mc.player, playerBox).iterator().hasNext();
        return !isPathBlocked;
    }


    // todo переделать говно (треды создаются)
    // virtual thread
    /*Executors.newVirtualThreadPerTaskExecutor().execute(() -> {
        // do something
    });*/
    private void checkAndPerformSlimeGlitch() {
        if (!config.isUseSlimeGlitch() || mc.player == null || mc.world == null) return;

        if (isPlayerCloseToSlimeBelow() && !isSneakingActivated) {
            isSneakingActivated = true;
            mc.options.sneakKey.setPressed(true);

            if (!isSneakingActivated) return;
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.options.sneakKey.setPressed(false);
                isSneakingActivated = false;
            }).start();
        }
    }

    private boolean isPlayerCloseToSlimeBelow() {
        Block supportingBlock = mc.world.getBlockState(mc.world.findSupportingBlockPos(mc.player, mc.player.getBoundingBox().offset(0, -1, 0).expand(0, 0, 0)).orElse(new BlockPos(0, -1, 0))).getBlock();
        return supportingBlock == Blocks.SLIME_BLOCK;
    }

}
