package com.josinosle.magicengines.client.overlay;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.config.ClientConfigs;
import com.josinosle.magicengines.content.item.MagicWand;
import com.josinosle.magicengines.mana.PlayerMana;
import com.josinosle.magicengines.mana.PlayerManaProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import java.util.Optional;

public class ManaBarOverlay {
    public final static ResourceLocation TEXTURE = new ResourceLocation(MagicEngines.MOD_ID, "textures/gui/icons.png");

    public enum Anchor {
        Hunger,
        XP,
        Center,
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }

    public enum Display {
        Never,
        Always,
        Contextual
    }

    static final int DEFAULT_IMAGE_WIDTH = 98;
    static final int XP_IMAGE_WIDTH = 188;
    static final int IMAGE_HEIGHT = 21;
    static final int HOTBAR_HEIGHT = 25;
    static final int ICON_ROW_HEIGHT = 11;
    static final int CHAR_WIDTH = 6;
    static final int HUNGER_BAR_OFFSET = 50;
    static final int SCREEN_BORDER_MARGIN = 20;
    static final int TEXT_COLOR = ChatFormatting.AQUA.getColor();

    public static void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        var player = Minecraft.getInstance().player;

        if (!shouldShowManaBar(player))
            return;

        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
            int barX, barY;
            //TODO: cache these?
            int configOffsetY = ClientConfigs.MANA_BAR_Y_OFFSET.get();
            int configOffsetX = ClientConfigs.MANA_BAR_X_OFFSET.get();
            Anchor anchor = ClientConfigs.MANA_BAR_ANCHOR.get();
            if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP Mana bar when actively jumping on a horse
                return;
            barX = getBarX(anchor, screenWidth) + configOffsetX;
            barY = getBarY(anchor, screenHeight, gui) - configOffsetY;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, TEXTURE);

            int imageWidth = anchor == Anchor.XP ? XP_IMAGE_WIDTH : DEFAULT_IMAGE_WIDTH;
            int spriteX = anchor == Anchor.XP ? 68 : 0;
            int spriteY = anchor == Anchor.XP ? 40 : 0;
            gui.blit(poseStack, barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 256, 256);
            gui.blit(poseStack, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (imageWidth * Math.min((mana.getMana() / (double) mana.getMaxMana()), 1)), IMAGE_HEIGHT);

            int textX, textY;
            String manaFraction = (mana.getMana()) + "/" + mana.getMaxMana();

            textX = barX + imageWidth / 2 - (int) ((("" + mana.getMana()).length() + 0.5) * CHAR_WIDTH);
            textY = barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

            if (ClientConfigs.MANA_BAR_TEXT_VISIBLE.get()) {
                gui.getFont().drawShadow(poseStack, manaFraction, textX, textY, TEXT_COLOR);
                //gui.getFont().draw(poseStack, manaFraction, textX, textY, TEXT_COLOR);
            }
        });
    }

    public static boolean shouldShowManaBar(Player player) {
        //We show mana if they are holding an item that can cast spells or if their mana is not full
        var display = ClientConfigs.MANA_BAR_DISPLAY.get();
        final Optional<PlayerMana> playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve();
        if(playerMana.isPresent()) {
            final boolean isManaFull = playerMana.get().isManaFull();

            return !player.isSpectator() && display != Display.Never &&
                    (display == Display.Always || player.isHolding(itemStack -> itemStack.getItem() instanceof MagicWand || isManaFull));
        } else {
            return false;
        }
    }

    private static int getBarX(Anchor anchor, int screenWidth) {
        if (anchor == Anchor.XP)
            return screenWidth / 2 - 91 - 3; //Vanilla's Pos - 3
        if (anchor == Anchor.Hunger || anchor == Anchor.Center)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2 + (anchor == Anchor.Center ? 0 : HUNGER_BAR_OFFSET);
        else if (anchor == Anchor.TopLeft || anchor == Anchor.BottomLeft)
            return SCREEN_BORDER_MARGIN;
        else return screenWidth - SCREEN_BORDER_MARGIN - DEFAULT_IMAGE_WIDTH;

    }

    private static int getBarY(Anchor anchor, int screenHeight, ForgeGui gui) {
        if (anchor == Anchor.XP)
            return screenHeight - 32 + 3 - 8; //Vanilla's Pos - 8
        if (anchor == Anchor.Hunger)
            return screenHeight - (getAndIncrementRightHeight(gui) - 2) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.Center)
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.TopLeft || anchor == Anchor.TopRight)
            return SCREEN_BORDER_MARGIN;
        return screenHeight - SCREEN_BORDER_MARGIN - IMAGE_HEIGHT;

    }

    private static int getAndIncrementRightHeight(ForgeGui gui) {
        int x = gui.rightHeight;
        gui.rightHeight += 10;
        return x;
    }
}
