package me.kak7.kak7utils.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Accessor("itemUseCooldown")
    int kak7utils$getItemUseCooldown();

    @Accessor("itemUseCooldown")
    void kak7utils$setItemUseCooldown(int value);
}
