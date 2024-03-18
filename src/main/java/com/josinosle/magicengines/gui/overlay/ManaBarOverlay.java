package com.josinosle.magicengines.gui.overlay;

import com.josinosle.magicengines.MagicEngines;
import com.josinosle.magicengines.mana.ClientManaData;
import com.josinosle.magicengines.config.ClientConfigs;
import com.josinosle.magicengines.item.MagicWand;
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
     * Enum with the possible mana bar display options
     */
    public enum Display {
        NEVER,
        ALWAYS,
        CONTEXTUAL
    }

    /**
     * Default width of the xp bar in pixels
     */
    static final int BAR_WIDTH = 188;
    /**
     * Default height of the image in pixels
     */
    static final int IMAGE_HEIGHT = 21;
    /**
     * Default height of the icon row in pixels
     */
    static final int ICON_ROW_HEIGHT = 14;
    /**
     * Default width of each character to display the mana value in pixels
     */
    static final int CHAR_WIDTH = 6;
    /**
     * Default text color
     * Please check {@link net.minecraft.ChatFormatting} for more color options
     */
    static final int TEXT_COLOR = ChatFormatting.WHITE.getColor();

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
                if (player.getJumpRidingScale() > 0) //Hide XP styled Mana bar when actively jumping on a horse
                    return;
                barX = calculateBarX(screenWidth) + configOffsetX;
                barY = calculateBarY(screenHeight) - configOffsetY;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                RenderSystem.setShaderTexture(0, MANA_BAR_TEXTURE);

                int spriteX = 68;
                int spriteY = 40;
                GuiComponent.blit(poseStack, barX, barY, spriteX, spriteY, BAR_WIDTH, IMAGE_HEIGHT, 256, 256);
                forgeGui.blit(poseStack, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (BAR_WIDTH * Math.min((ClientManaData.getPlayerMana() / (double) mana.getMaxMana()), 1)), IMAGE_HEIGHT);

                // Text Drawing Renderer
                if (ClientConfigs.MANA_BAR_TEXT_VISIBLE.get()) {
                    int textX, textY;
                    String manaFraction = (ClientManaData.getPlayerMana()) + "/" + mana.getMaxMana();

                    textX = barX + BAR_WIDTH / 2 - (int) ((("" + ClientManaData.getPlayerMana()).length() + 0.5) * CHAR_WIDTH);
                    textY = barY + ICON_ROW_HEIGHT / 3 ;

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

            return !player.isSpectator()  && !player.isCreative() && display != Display.NEVER &&
                    (display == Display.ALWAYS || player.isHolding(itemStack -> itemStack.getItem() instanceof MagicWand || isManaFull));
        } else {
            return false;
        }
    }

    /**
     * Method used to calculate the X coordinate of the mana bar
     *
     * @param screenWidth the width of the screen in pixels
     * @return the X coordinate in pixels
     */
    private static int calculateBarX(int screenWidth) {
        return screenWidth / 2 - 94; //Vanilla's Pos - 3
    }

    /**
     * Method used to calculate the Y coordinate of the mana bar
     *
     * @param screenHeight the height of the screen in pixels
     * @return the Y coordinate in pixels
     */
    private static int calculateBarY(int screenHeight) {
        return screenHeight - 32 - 24; //Vanilla's Pos - 24
    }
}
