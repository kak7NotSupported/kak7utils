package me.kak7.kak7utils;

import lombok.Getter;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Hotkeys {

    @Getter
    private static KeyBinding keyBindingTrigger;
    @Getter
    private static KeyBinding keyBindingGamemodeChanger;
    @Getter
    private static KeyBinding keyBindingAutoParkour;

    public static void initialize() {
        keyBindingTrigger = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.kak7Util.triggerbotkey", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "category.kak7util.kak7util" // The translation key of the keybinding's category.
        ));

        keyBindingGamemodeChanger = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.kak7Util.gamemodechanger", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_F4, // The keycode of the key
                "category.kak7util.kak7util" // The translation key of the keybinding's category.
        ));

        keyBindingAutoParkour = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.kak7Util.autoparkourkey", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_LEFT_ALT, // The keycode of the key
                "category.kak7util.kak7util" // The translation key of the keybinding's category.
        ));
    }

}
