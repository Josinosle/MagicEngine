package com.josinosle.magicengines.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyboardHelper {

    public static final String KEY_CATEGORY_MAGIC_ENGINE = "key.category.magicengine.magic";

    // cast in air
    public static final String KEY_CAST_INAIR = "key.magicengine.cast_inair";

    public static final KeyMapping CAST_INAIR_KEY = new KeyMapping(KEY_CAST_INAIR, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_MAGIC_ENGINE);

    // cast calculation
    public static final String KEY_CAST_CASTCALC = "key.magicengine.cast_castcalc";

    public static final KeyMapping CASTCALC_KEY = new KeyMapping(KEY_CAST_CASTCALC, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY_MAGIC_ENGINE);

    // cantrip mode
    public static final String KEY_CAST_CANTRIP = "key.magicengine.cast_cantrip";

    public static final KeyMapping CASTCANTRIP_KEY = new KeyMapping(KEY_CAST_CANTRIP, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY_MAGIC_ENGINE);

}
