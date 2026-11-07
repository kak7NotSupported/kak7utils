package me.kak7.kak7utils;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class Hotkeys {

    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("kak7util", "kak7util"));

    @Getter
    private static KeyMapping keyBindingTrigger;
    @Getter
    private static KeyMapping keyBindingGamemodeChanger;
    @Getter
    private static KeyMapping keyBindingAutoParkour;

    public static void initialize() {
        keyBindingTrigger = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.kak7Util.triggerbotkey", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                CATEGORY // The keybinding's category.
        ));

        keyBindingGamemodeChanger = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.kak7Util.gamemodechanger", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_F4, // The keycode of the key
                CATEGORY // The keybinding's category.
        ));

        keyBindingAutoParkour = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.kak7Util.autoparkourkey", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_LEFT_ALT, // The keycode of the key
                CATEGORY // The keybinding's category.
        ));
    }

}
