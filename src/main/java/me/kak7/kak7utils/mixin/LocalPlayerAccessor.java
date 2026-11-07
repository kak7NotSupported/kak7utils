package me.kak7.kak7utils.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessor {
    /**
     * Vanilla auto-jump counter: while it is above zero, {@code LocalPlayer.aiStep()} presses jump
     * through the regular input ({@code input.makeJump()}), so the jump goes the normal way
     * and the server sees the jump key in the player input packet.
     */
    @Accessor("autoJumpTime")
    void kak7utils$setAutoJumpTime(int ticks);
}
