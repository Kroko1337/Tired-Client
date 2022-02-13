package beta.tiredb56.api.guis.clickgui.impl.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.AbstractClickGui;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.Translate;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CheckBox extends AbstractClickGui {

	public final BooleanSetting value;

	public int WIDTH = 120;
	public final int HEIGHT = 15;

	public Translate translate;
	public double animation;
	public float float2;
	public float animationToggle;
	public CheckBox(BooleanSetting value) {
		this.value = value;
		this.translate = new Translate(0, 0);
	}

	@Override
	public void drawCheckBox(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		boolean state = value.getValue();

		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
		animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
		WIDTH = (int) animation;
		this.animationToggle = (float) AnimationUtil.getAnimationState((double) this.animationToggle, dragging ? x : this.x + WIDTH, Math.max(4.6D, Math.abs((double) this.animationToggle - animationToggle)) * 282);

		Scissoring.SCISSORING.startScissor();
		Scissoring.SCISSORING.doScissor(x, y, x + WIDTH, y + HEIGHT);
		Gui.drawRect(this.x, this.y, this.x + WIDTH, this.y + HEIGHT, Flat1.isState() ? Integer.MIN_VALUE :  new Color(20, 20, 20).getRGB());

		boolean isMouseOver = (mouseX > this.x + 2) && (mouseX < this.x - 2 + 15) && (mouseY > this.y + 2) && (mouseY < this.y - 2 + 15); //cant use the void from abstract class because the values are diffrent

		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(this.x + 2, this.y + 5, this.x + WIDTH - WIDTH + 9, this.y + HEIGHT - 3, new Color(10, 10, 10).getRGB());

		if (isMouseOver) {
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(this.x + 2, this.y + 5, this.x + WIDTH - WIDTH + 9, this.y + HEIGHT - 3, Integer.MIN_VALUE);
		}

		//float2 = (float) AnimationUtil.getAnimationState(float2, isMouseOver ? 1.1 : 0, 20);
		String name  =value.getName();
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		FHook.fontRenderer.drawString(name, x + 12, y + 7, -1);

		if (value.getValue()) {
			if (float2 < 0.5F) {
				translate.interpolate(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2, 8);
				float2 += 0.001 * Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta;
			}
		} else {

			translate.interpolate(0, 0, 5);
			float2 = 0;
		}

		if (value.getValue()) {
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(this.x + 2, this.y + 5, this.x + WIDTH - WIDTH + 9, this.y + HEIGHT - 3, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((x + 6), y + (float) HEIGHT, 0);
		GL11.glScaled(0.5 + translate.getX() / scaledResolution.getScaledWidth() - float2, 0.5 + translate.getY() / scaledResolution.getScaledHeight() - float2, 0);
		GL11.glTranslatef(-(x + 6), -(y + (float) HEIGHT), 0);

		if (PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE && this.translate.getX() > 280.0F) {
			GlStateManager.enableRescaleNormal();
			GlStateManager.disableBlend();
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawCheckMark((float)x + 8.5F, (float)(y + 2), 7, Integer.MIN_VALUE);
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawCheckMark((float)x + 8.5F, (float)(y + 1), 7, -1);
		}

		GL11.glPopMatrix();
		WIDTH = 120;
		Scissoring.SCISSORING.disableScissor();
		//Rect(this.x + 9, this.y + 3, this.x - 2 + 16, this.y - 2 + 10, Color.RED.getRGB());
		super.drawCheckBox(x, y, mouseX, mouseY);
	}



	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {


		boolean isMouseOver = (mouseX > this.x + 2) && (mouseX < this.x - 2 + 15) && (mouseY > this.y + 2) && (mouseY < this.y - 2 + 15); //cant use the void from abstract class because the values are diffrent
		if (isMouseOver) {
			value.setValue(!(boolean) value.getValue());
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
