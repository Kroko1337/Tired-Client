package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render3DEvent2;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ModuleAnnotation(name = "ESP", category = ModuleCategory.RENDER, clickG = "You can see targets anywhere")

public class ESP extends Module {

    private final NumberSetting thickness = new NumberSetting("Thickness", this, 1.5f, 1.5f, 5.0f, 0.25f);

    public ESP() {

    }

    @EventTarget
    public void onRender(Render3DEvent2 event) {
        renderReal2D(event);
    }

    public final void renderReal2D(Render3DEvent2 event) {
        MC.theWorld.loadedEntityList.forEach(entity -> {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase ent = (EntityLivingBase) entity;
                if (ent != MC.thePlayer && ent instanceof EntityPlayer && Extension.EXTENSION.getGenerallyProcessor().renderProcessor.isInViewFrustrum(ent)) {

                    final double xAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(entity.posX, entity.lastTickPosX, event.partialTicks);
                    final double yAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(entity.posY, entity.lastTickPosY, event.partialTicks);
                    final double zAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(entity.posZ, entity.lastTickPosZ, event.partialTicks);
                    final double width = entity.width / 1.5;
                    final double height = entity.height + (entity.isSneaking() ? -0.3 : 0.2);
                    final AxisAlignedBB aabb = new AxisAlignedBB(xAxis - width, yAxis, zAxis - width, xAxis + width, yAxis + height + 0.05, zAxis + width);
                    final List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));

                    MC.entityRenderer.setupCameraTransform(event.partialTicks, 0);
                    Vector4d position = null;

                    for (Vector3d vector : vectors) {
                        vector = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.project(vector.x - MC.getRenderManager().viewerPosX, vector.y - MC.getRenderManager().viewerPosY, vector.z - MC.getRenderManager().viewerPosZ);
                        if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                            if (position == null) {
                                position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                            }
                            position.x = Math.min(vector.x, position.x);
                            position.y = Math.min(vector.y, position.y);
                            position.z = Math.max(vector.x, position.z);
                            position.w = Math.max(vector.y, position.w);
                        }
                    }

                    MC.entityRenderer.setupOverlayRendering();
                    if (position != null) {
                        final int color = Color.BLACK.getRGB(), black = -1;
                        GL11.glPushMatrix();
                        final double posX = position.x;
                        final double posY = position.y;
                        final double endPosX = position.z;
                        final double endPosY = position.w;

                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX + .5, posY, posX - 0.5D, posY + (endPosY - posY) / 3.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX + .5, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 3.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX - 0.5D, posY + 1, posX + (endPosX - posX) / 3.5D, posY + 0.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - (endPosX - posX) / 3.5D, posY + 1, endPosX, posY + 0.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - 1, posY, endPosX, posY + (endPosY - posY) / 3.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - 1, endPosY, endPosX, endPosY - (endPosY - posY) / 3.5D, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - (endPosX - posX) / 3.5D, endPosY - 1, endPosX - 0.5D, endPosY, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX, endPosY - 1D, posX + (endPosX - posX) / 3.5D, endPosY, black);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX, posY, posX - 0.5D, posY + (endPosY - posY) / 3.5D, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 3.5D, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.5D, posY + 0.5D, color);


                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - (endPosX - posX) / 3.5D, posY, endPosX, posY + 0.5D, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - 0.5D, posY, endPosX, posY + (endPosY - posY) / 3.5D, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - 0.5D, endPosY, endPosX, endPosY - (endPosY - posY) / 3.5D, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(posX, endPosY - 0.5D, posX + (endPosX - posX) / 3.5D, endPosY, color);
                        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(endPosX - (endPosX - posX) / 3.5D, endPosY - 0.5D, endPosX - 0.5D, endPosY, color);
                        GL11.glPopMatrix();
                    }
                }
            }
        });
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}