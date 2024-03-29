package com.josinosle.magicengines.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Integer> TELEKENETIC_SLAM_REQUIRED_MANA_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_DEFENSE_REQUIRED_MANA_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Integer> ABSTRACT_SPELL_DAMAGE_REQUIRED_MANA_AMOUNT;
    public static final ForgeConfigSpec.ConfigValue<Integer> FART_REQUIRED_MANA_AMOUNT;

    public static final ForgeConfigSpec.ConfigValue<Integer> TESTICULAR_TORSION_REQUIRED_MANA_AMOUNT;

    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Common");
        BUILDER.push("Spells");
        TELEKENETIC_SLAM_REQUIRED_MANA_AMOUNT = BUILDER.define("telekeneticSlamRequiredManaAmount", 1000);
        PLAYER_DEFENSE_REQUIRED_MANA_AMOUNT = BUILDER.define("playerDefenseRequiredManaAmount", 1000);
        ABSTRACT_SPELL_DAMAGE_REQUIRED_MANA_AMOUNT = BUILDER.define("abstractSpellDamageRequiredManaAmount", 1500);
        FART_REQUIRED_MANA_AMOUNT = BUILDER.define("fartRequiredManaAmount", 690);
        TESTICULAR_TORSION_REQUIRED_MANA_AMOUNT = BUILDER.define("testicularTorsion", 6900);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
