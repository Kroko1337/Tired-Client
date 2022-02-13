package beta.tiredb56.module.impl.list.visual;
import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render3DEvent;
import beta.tiredb56.event.events.Render3DEvent2;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(name = "Breadcrumbs", category = ModuleCategory.RENDER, clickG = "Render a line from your movement")
public class Breadcrumbs extends Module {

	private final List<Vec3> breadcrumbs;

	public Breadcrumbs() {
		this.breadcrumbs = new ArrayList<>();
	}

	public ColorPickerSetting BreadcrumbsColor = new ColorPickerSetting("BreadcrumbsColor", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

	@EventTarget
	public void onRender(Render3DEvent2 e) {
		render(e);
	}

	public void render(Render3DEvent2 e) {
		final double rx = RenderManager.renderPosX;
		final double ry = RenderManager.renderPosY;
		final double rz = RenderManager.renderPosZ;
		if (this.breadcrumbs.size() >= 500) {
			this.breadcrumbs.remove(0);
		}
		final double x = interpolate(MC.thePlayer.prevPosX, MC.thePlayer.posX, e.partialTicks);
		final double y = interpolate(MC.thePlayer.prevPosY, MC.thePlayer.posY, e.partialTicks);
		final double z = interpolate(MC.thePlayer.prevPosZ, MC.thePlayer.posZ, e.partialTicks);
		this.breadcrumbs.add(new Vec3(x, y, z));
		GL11.glTranslated(-rx, -ry, -rz);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDepthMask(false);
		GL11.glLineWidth(2);
		GL11.glColor4f(new Color(BreadcrumbsColor.ColorPickerC.getRGB()).getRed() / 255.0f, new Color(BreadcrumbsColor.ColorPickerC.getRGB()).getGreen() / 255.0f, new Color(BreadcrumbsColor.ColorPickerC.getRGB()).getBlue() / 255.0f, .7f);
		GL11.glBegin(3);
		Vec3 lastCrumb = null;
		for (final Vec3 breadcrumb : this.breadcrumbs) {
			if (lastCrumb != null && lastCrumb.distanceTo(breadcrumb) > Math.sqrt(1.0)) {
				GL11.glEnd();
				GL11.glBegin(GL11.GL_LINE_STRIP);
			}
			GL11.glVertex3d(breadcrumb.xCoord, breadcrumb.yCoord, breadcrumb.zCoord);
			lastCrumb = breadcrumb;
		}
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glTranslated(rx, ry, rz);
		GlStateManager.resetColor();
	}

	public final double interpolate(final double old, final double now, final float partialTicks) {
		return old + (now - old) * partialTicks;
	}


	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}
}
