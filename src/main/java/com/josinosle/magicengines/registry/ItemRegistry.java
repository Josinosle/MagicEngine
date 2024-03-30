package com.josinosle.magicengines.registry;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.item.Grimoire;
import com.josinosle.magicengines.item.jokeitem.BeanWand;
import com.josinosle.magicengines.item.staves.MagicWand;
import com.josinosle.magicengines.item.staves.WoodenStave;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicEngines.MOD_ID);


    //creative mode tab init
    public static class ModCreativeTab extends CreativeModeTab {
        private ModCreativeTab(int index, String label) {
            super(index, label);
        }

        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(MAGIC_WAND.get());
        }
        public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length, "magicengines");
    }

    //magic wand init
    public static final RegistryObject<Item> MAGIC_WAND = ITEMS.register("magic_wand", () -> new MagicWand(new Item.Properties()
            .stacksTo(1)
            .tab(ModCreativeTab.instance)
    ));

    //bean wand init
    public static final RegistryObject<Item> BEAN_WAND = ITEMS.register("bean_wand", () -> new BeanWand(new Item.Properties()
            .stacksTo(1)
            .tab(ModCreativeTab.instance)
    ));

    //wooden stave init
    public static final RegistryObject<Item> WOODEN_STAVE = ITEMS.register("wooden_stave", () -> new WoodenStave(new Item.Properties()
            .stacksTo(1)
            .tab(ModCreativeTab.instance)
    ));

    //grimoire init
    public static final RegistryObject<Item> GRIMOIRE = ITEMS.register("grimoire", () -> new Grimoire(new Item.Properties()
            .stacksTo(1)
            .tab(ModCreativeTab.instance)
    ));

}


