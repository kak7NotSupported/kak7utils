package me.kak7.kak7utils.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftClientAccessor {
    @Accessor("rightClickDelay")
    int kak7utils$getItemUseCooldown();

    @Accessor("rightClickDelay")
    void kak7utils$setItemUseCooldown(int value);
}
