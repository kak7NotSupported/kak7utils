package me.kak7.kak7utils.config;

import lombok.Getter;
import lombok.Setter;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Getter
@Config(name = "kak7Util")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.PrefixText
    @Setter
    boolean triggerbotEnabled = false;
    boolean doSound = false;
    boolean avoidAntiCheat = true;
    boolean renderSwitcherUI = false;
    boolean renderCrosshairHighlight = false;
    boolean renderHotbarHighight = true;

    // color picker
    @ConfigEntry.ColorPicker(allowAlpha = true)
    int color = -2130771968;

    @ConfigEntry.Gui.PrefixText
    @Setter
    boolean autoParkourEnabled = false;
    boolean useHotKeyMode = false;
    boolean useSlimeGlitch = true;
    boolean useFastGamemodeSwitcher = true;

    @ConfigEntry.Gui.PrefixText
    boolean fastPlaceEnabled = false;
    boolean fixedItemUseCooldown2 = false;
}
