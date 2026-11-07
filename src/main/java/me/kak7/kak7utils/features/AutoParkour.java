package me.kak7.kak7utils.features;

import me.kak7.kak7utils.Hotkeys;
import me.kak7.kak7utils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class AutoParkour {

    private final Minecraft mc = Minecraft.getInstance();
    private final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    private volatile boolean isSneakingActivated = false;

    private AutoParkour() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (mc.level != null && shouldTriggerAutoParkour()) {
                attemptParkour();
            }
        });
    }


    public static void initialize() {
        new AutoParkour();
    }

    private boolean shouldTriggerAutoParkour() {
        return (!config.isUseHotKeyMode() || Hotkeys.getKeyBindingAutoParkour().isDown()) && mc.player != null;
    }

    private void attemptParkour() {
        checkAndPerformSlimeGlitch();
        if (isParkourPossible()) {
            mc.player.jumpFromGround();
        }

    }

    private boolean isParkourPossible() {
        var jumpKey = mc.options.keyJump;
        if (!mc.player.onGround() || mc.player.isShiftKeyDown() || jumpKey.isDown()) {
            return false;
        }

        AABB playerBox = mc.player.getBoundingBox().move(0, -0.5, 0).inflate(-0.001, 0, -0.001);
        boolean isPathBlocked = mc.level.getBlockCollisions(mc.player, playerBox).iterator().hasNext();
        return !isPathBlocked;
    }


    // todo переделать говно (треды создаются)
    // virtual thread
    /*Executors.newVirtualThreadPerTaskExecutor().execute(() -> {
        // do something
    });*/
    private void checkAndPerformSlimeGlitch() {
        if (!config.isUseSlimeGlitch() || mc.player == null || mc.level == null) return;

        if (isPlayerCloseToSlimeBelow() && !isSneakingActivated) {
            isSneakingActivated = true;
            mc.options.keyShift.setDown(true);

            if (!isSneakingActivated) return;
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mc.options.keyShift.setDown(false);
                isSneakingActivated = false;
            }).start();
        }
    }

    private boolean isPlayerCloseToSlimeBelow() {
        Block supportingBlock = mc.level.getBlockState(mc.level.findSupportingBlock(mc.player, mc.player.getBoundingBox().move(0, -1, 0).inflate(0, 0, 0)).orElse(new BlockPos(0, -1, 0))).getBlock();
        return supportingBlock == Blocks.SLIME_BLOCK;
    }

}
