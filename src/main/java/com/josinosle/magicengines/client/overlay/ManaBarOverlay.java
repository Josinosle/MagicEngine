package com.josinosle.magicengines.client.overlay;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.client.clientdata.ClientManaData;
import com.josinosle.magicengines.config.ClientConfigs;
import com.josinosle.magicengines.content.item.MagicWand;
import com.josinosle.magicengines.mana.PlayerMana;
import com.josinosle.magicengines.mana.PlayerManaProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Optional;

/**
 * Class to render the mana bar
 *
 * @author Florian Hirson
 */
public class ManaBarOverlay {

    /**
     * ResourceLocation of the mana bar texture
     */
    public final static ResourceLocation MANA_BAR_TEXTURE = new ResourceLocation(MagicEngines.MOD_ID, "textures/gui/icons.png");

    /**
     * Enum with the possible UI Anchor positions
     */
    public enum Anchor {
        HUNGER,
        XP,
        CENTER,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    /**
     * Enum with the possible mana bar display options
     */
    public enum Display {
        NEVER,
        ALWAYS,
        CONTEXTUAL
    }

    /**
     * Default width of the mana bar in pixels
     */
    static final int DEFAULT_BAR_WIDTH = 98;
    /**
     * Default width of the xp bar in pixels
     */
    static final int XP_BAR_WIDTH = 188;
    /**
     * Default height of the image in pixels
     */
    static final int IMAGE_HEIGHT = 21;
    /**
     * Default height of the hotbar in pixels
     */
    static final int HOTBAR_HEIGHT = 25;
    /**
     * Default height of the icon row in pixels
     */
    static final int ICON_ROW_HEIGHT = 14;
    /**
     * Default width of each character to display the mana value in pixels
     */
    static final int CHAR_WIDTH = 6;
    /**
     * Horizontal offset in pixels to align the mana bar
     */
    static final int HUNGER_BAR_OFFSET = 50;
    /**
     * Default screen border margin in pixels used when the mana bar is at the corner of the screen
     */
    static final int SCREEN_BORDER_MARGIN = 20;
    /**
     * Default text color
     * Please check {@link net.minecraft.ChatFormatting} for more color options
     */
    static final int TEXT_COLOR = ChatFormatting.AQUA.getColor();

    /**
     * Method used to render the mana bar
     *
     * @param forgeGui     a forge wrapper around {@link Gui} to be able to render {@link IGuiOverlay HUD overlays}.
     * @param poseStack    a stack holding four-dimensional matrix entries offset to the current position of the mana bar
     * @param partialTick  the amount of time, in fractions of a tick, that has passed since the last full tick (unused but is mandatory)
     * @param screenWidth  width of the screen in pixels
     * @param screenHeight height of the screen in pixels
     * @see <a href="https://docs.minecraftforge.net/en/1.19.x/gui/screens/">Forge Docs Screens</a>
     */
    public static void renderManaBar(ForgeGui forgeGui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        var player = Minecraft.getInstance().player;

        if (player != null) {
            if (!shouldShowManaBar(player))
                return;

            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
                int barX, barY;
                int configOffsetY = ClientConfigs.MANA_BAR_Y_OFFSET.get();
                int configOffsetX = ClientConfigs.MANA_BAR_X_OFFSET.get();
                Anchor anchor = ClientConfigs.MANA_BAR_ANCHOR.get();
                if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP styled Mana bar when actively jumping on a horse
                    return;
                barX = calculateBarX(anchor, screenWidth) + configOffsetX;
                barY = calculateBarY(anchor, screenHeight, forgeGui) - configOffsetY;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.setShaderTexture(0, MANA_BAR_TEXTURE);

                int barWidth = anchor == Anchor.XP ? XP_BAR_WIDTH : DEFAULT_BAR_WIDTH;
                int spriteX = anchor == Anchor.XP ? 68 : 0;
                int spriteY = anchor == Anchor.XP ? 40 : 0;
                GuiComponent.blit(poseStack, barX, barY, spriteX, spriteY, barWidth, IMAGE_HEIGHT, 256, 256);
                forgeGui.blit(poseStack, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (barWidth * Math.min((ClientManaData.getPlayerMana() / (double) mana.getMaxMana()), 1)), IMAGE_HEIGHT);

                int textX, textY;
                String manaFraction = (ClientManaData.getPlayerMana()) + "/" + mana.getMaxMana();

                textX = barX + barWidth / 2 - (int) ((("" + ClientManaData.getPlayerMana()).length() + 0.5) * CHAR_WIDTH);
                textY = barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

                if (ClientConfigs.MANA_BAR_TEXT_VISIBLE.get()) {
                    forgeGui.getFont().drawShadow(poseStack, manaFraction, textX, textY, TEXT_COLOR);
                }
            });
        }
    }

    /**
     * Method used to decide if the mana bar should be shown or not
     *
     * @param player the player.
     * @return true or false
     */
    public static boolean shouldShowManaBar(Player player) {
        //We show mana if they are holding a magic wand that can cast spells or if their mana is not full
        var display = ClientConfigs.MANA_BAR_DISPLAY.get();
        final Optional<PlayerMana> playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).resolve();
        if (playerMana.isPresent()) {
            final boolean isManaFull = playerMana.get().isManaFull();

            return !player.isSpectator() && display != Display.NEVER &&
                    (display == Display.ALWAYS || player.isHolding(itemStack -> itemStack.getItem() instanceof MagicWand || isManaFull));
        } else {
            return false;
        }
    }

    /**
     * Method used to calculate the X coordinate of the mana bar
     *
     * @param anchor      an enum value telling where the mana bar is
     * @param screenWidth the width of the screen in pixels
     * @return the X coordinate in pixels
     */
    private static int calculateBarX(Anchor anchor, int screenWidth) {
        if (anchor == Anchor.XP)
            return screenWidth / 2 - 91 - 3; //Vanilla's Pos - 3
        if (anchor == Anchor.HUNGER || anchor == Anchor.CENTER)
            return screenWidth / 2 - DEFAULT_BAR_WIDTH / 2 + (anchor == Anchor.CENTER ? 0 : HUNGER_BAR_OFFSET);
        else if (anchor == Anchor.TOP_LEFT || anchor == Anchor.BOTTOM_LEFT)
            return SCREEN_BORDER_MARGIN;
        else return screenWidth - SCREEN_BORDER_MARGIN - DEFAULT_BAR_WIDTH;

    }

    /**
     * Method used to calculate the Y coordinate of the mana bar
     *
     * @param anchor       an enum value telling where the mana bar is
     * @param screenHeight the height of the screen in pixels
     * @param forgeGui     a forge wrapper around {@link Gui} to be able to render {@link IGuiOverlay HUD overlays} used to get the height of the food bar to render the mana bar on top of it
     * @return the Y coordinate in pixels
     */
    private static int calculateBarY(Anchor anchor, int screenHeight, ForgeGui forgeGui) {
        if (anchor == Anchor.XP)
            return screenHeight - 32 + 3 - 8; //Vanilla's Pos - 8
        if (anchor == Anchor.HUNGER)
            return screenHeight - (getAndIncrementFoodBarHeight(forgeGui) - 2) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.CENTER)
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.TOP_LEFT || anchor == Anchor.TOP_RIGHT)
            return SCREEN_BORDER_MARGIN;
        return screenHeight - SCREEN_BORDER_MARGIN - IMAGE_HEIGHT;

    }

    /**
     * Method used to calculate the Y offset of the mana bar compared to the food bar
     *
     * @param forgeGui a forge wrapper around {@link Gui} to be able to render {@link IGuiOverlay HUD overlays} used to get the height of the food bar to render the mana bar on top of it
     * @return the Y offset in pixels
     */
    private static int getAndIncrementFoodBarHeight(ForgeGui forgeGui) {
        return forgeGui.rightHeight + 5;
    }
}
