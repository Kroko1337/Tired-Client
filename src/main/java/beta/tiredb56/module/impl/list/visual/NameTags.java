package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render3DEvent;
import beta.tiredb56.event.events.Render3DEvent2;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "NameTags", category = ModuleCategory.RENDER, clickG = "Render big name over players")
public class NameTags extends Module {

    private double animationX = 0;

    @EventTarget
    public void onRender(Render3DEvent2 e) {

        doRender(true, false, e.partialTicks);

    }

    public void  onRender2(Render3DEvent e) {
    }

    public void doRender(boolean other, boolean shouldRender, float partialticks) {
        for (Entity e : MC.theWorld.loadedEntityList) {
            if (e != MC.thePlayer && !e.isInvisible() && !e.isDead && e instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) e;
                if (entity instanceof EntityPlayer) {
                    if (shouldRender(entity) && Extension.EXTENSION.getGenerallyProcessor().renderProcessor.isInViewFrustrum(entity)) {
                        EntityPlayer player = (EntityPlayer) entity;
                        double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * MC.timer.renderPartialTicks - RenderManager.renderPosX;
                        double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * MC.timer.renderPartialTicks - RenderManager.renderPosY;
                        double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * MC.timer.renderPartialTicks - RenderManager.renderPosZ;

                        final double yAxis = 3.8;

                        GL11.glPushMatrix();
                        GL11.glTranslated(x, y + yAxis, z);
                        GL11.glScalef(-0.06f, -0.06f, -0.06f);

                        GL11.glRotated(-MC.getRenderManager().playerViewY, 0.0d, 1.0d, 0.0d);
                        GL11.glRotated(MC.getRenderManager().playerViewX, 1.0d, 0.0d, 0.0d);


                        String name = player.getName();

                        if (name.startsWith("LCA")) {
                            name = name.replace("LCA", "LUCIMODZIUWU");
                        }
                        if (name.startsWith("Felix")) {
                            name = name.replace("Felix", "BaboOmgDerKrasseste");
                        }

                        GlStateManager.disableDepth();
                        float width = FHook.fontRenderer.getStringWidth(name);
                        float progress = player.getHealth() / player.getMaxHealth();

                        Color color = Color.WHITE;
                        if (player.getHealth() > 15) {
                            color = ClickGUI.getInstance().colorPickerSetting.getColorPickerColor();
                        } else if (player.getHealth() > 7 && player.getHealth() <= 15) {
                            color = Color.YELLOW;
                        } else if (player.getHealth() <= 7) {
                            color = Color.RED;
                        }

                        if (other) {
                            Gui.drawRect(-width / 2 - 5, -2, width / 2 + 5, FHook.fontRenderer.FONT_HEIGHT + 2, new Color(20, 20, 20).getRGB());
                        }

                        int i = 0;
                        if (entity.getCurrentArmor(0) != null && entity.getCurrentArmor(0).getItem() instanceof ItemArmor) {
                            //	Gui.drawRect(21, -5, 27, -20, Integer.MIN_VALUE);
                            this.renderItem(entity.getCurrentArmor(0), 29, i, 0);
                        }

                        if (entity.getCurrentArmor(1) != null && entity.getCurrentArmor(1).getItem() instanceof ItemArmor) {
                            //Gui.drawRect(6, -5, width / 2 - 15, -20, Integer.MIN_VALUE);
                            this.renderItem(entity.getCurrentArmor(1), 13, i, 0);
                        }

                        if (entity.getCurrentArmor(2) != null && entity.getCurrentArmor(2).getItem() instanceof ItemArmor) {
                            //	Gui.drawRect(-11, -5, width / 2 - 30, -20, Integer.MIN_VALUE);
                            this.renderItem(entity.getCurrentArmor(2), -3, i, 0);
                        }

                        if (entity.getCurrentArmor(3) != null && entity.getCurrentArmor(3).getItem() instanceof ItemArmor) {
                            //Gui.drawRect(-23, -5, width / 2 - 50, -20, Integer.MIN_VALUE);
                            this.renderItem(entity.getCurrentArmor(3), -19, i, 0);
                        }

                        if (entity.getHeldItem() != null) {
                            this.renderItem(entity.getHeldItem(), -36, i, 0);
                        }

                        animationX = AnimationUtil.getAnimationState(animationX - width / 2 - 5, width / 2 - 5 + (width / 2 + 5 - -width / 2 + 5) * progress, 122);

                        Gui.drawRect(-width / 2 - 5, FHook.fontRenderer.FONT_HEIGHT + 1, -width / 2 - 5 + (width / 2 + 5 - -width / 2 + 5) * progress, FHook.fontRenderer.FONT_HEIGHT + 2, color.getRGB());


                        FontManager.robotoF.drawCenteredString(name, 0, 4, -1);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHead(player, (int) (-width / 2 - 20), -2, 15, 15);
                        GlStateManager.disableBlend();
                        GlStateManager.resetColor();
                        GlStateManager.disableDepth();
                        GL11.glTranslated(-x, -(y + 2.5D), -z);
                        GL11.glScalef(1.0f, 1.0f, 1.0f);
                        GlStateManager.enableDepth();
                        GL11.glPopMatrix();

                    }
                }
            }
        }
    }
    private boolean shouldRender(Entity entity) {
        Entity renderViewEntity = this.MC.getRenderViewEntity();
        double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) this.MC.timer.renderPartialTicks;
        double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) this.MC.timer.renderPartialTicks;
        double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) this.MC.timer.renderPartialTicks;
        return entity.isInRangeToRender3d(d0, d1, d2);
    }

    public void renderItem(ItemStack item, int xPos, int yPos, int zPos) {
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        IBakedModel ibakedmodel = MC.getRenderItem().getItemModelMesher().getItemModel(item);
        MC.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.scale(16.0F, 16.0F, 0.0F);
        GL11.glTranslated((double)(((float)xPos - 7.85F) / 16.0F), (double)((float)(-5 + yPos) / 16.0F), (double)((float)zPos / 16.0F));
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.disableLighting();
        ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
        if (ibakedmodel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            TileEntityItemStackRenderer.instance.renderByItem(item);
        } else {
            MC.getRenderItem().renderModel(ibakedmodel, -1);
        }

        GlStateManager.enableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
