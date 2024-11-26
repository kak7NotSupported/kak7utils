package me.kak7.kak7utils.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import lombok.Getter;
import lombok.Setter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Getter
@Config(name = "kak7Util")
public class ModConfig implements ConfigData, ModMenuApi {

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

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // todo return a screen
        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }
}
