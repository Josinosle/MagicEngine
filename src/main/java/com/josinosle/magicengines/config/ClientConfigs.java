package com.josinosle.magicengines.config;

import com.josinosle.magicengines.gui.overlay.ManaBarOverlay;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<ManaBarOverlay.Display> MANA_BAR_DISPLAY;
    public static final ForgeConfigSpec.ConfigValue<Integer> MANA_BAR_Y_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> MANA_BAR_X_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Boolean> MANA_BAR_TEXT_VISIBLE;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("UI");
        BUILDER.push("ManaBar");
        BUILDER.comment("By default (Contextual), the mana bar only appears when you are holding a magic item or are not at max mana.");
        MANA_BAR_DISPLAY = BUILDER.defineEnum("manaBarDisplay", ManaBarOverlay.Display.CONTEXTUAL);
        BUILDER.comment("Used to adjust mana bar's position.");
        MANA_BAR_Y_OFFSET = BUILDER.define("manaBarYOffset", 0);
        MANA_BAR_X_OFFSET = BUILDER.define("manaBarXOffset", 0);
        MANA_BAR_TEXT_VISIBLE = BUILDER.define("manaBarTextVisible", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

}
