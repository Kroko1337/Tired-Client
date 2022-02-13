package beta.tiredb56.api.guis.clickgui.impl.list;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.api.util.FileUtil;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;

public class ColorPicker {

	private int x;
	private int y;
	public static int HEIGHT = 15;
	public static int WIDTH = 120;
	public final ColorPickerSetting s;
	public final beta.tiredb56.api.util.renderapi.ColorPicker COLORPICKER;
	public boolean extended = false;
	public double animationCoolski;
	public double animation;
	public ColorPicker(ColorPickerSetting s) {
		this.s = s;
		this.COLORPICKER = new beta.tiredb56.api.util.renderapi.ColorPicker();
	}

	public void draw(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
		final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
		WIDTH = (int) animation;
		Scissoring.SCISSORING.startScissor();

		float amongus =  extended ? 20 : 0;
		animationCoolski = AnimationUtil.getAnimationState(animationCoolski, amongus, Math.max(3.6D, Math.abs((double) animationCoolski - amongus)) * 4);
		if (this.extended) {
			HEIGHT = (int) (80 + animationCoolski);
		} else {
			HEIGHT = 15;
		}
		Scissoring.SCISSORING.doScissor(x, y, x + WIDTH, y + HEIGHT);
		if (!this.extended) {
			Gui.drawRect(this.x, this.y, this.x + WIDTH, this.y + 15, Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
		}

		WIDTH = 120;

		if (this.extended) {
			try {
				Gui.drawRect(x, y, this.x + WIDTH, this.y + 15 + 80 + animationCoolski,Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
				this.COLORPICKER.drawColorPicker(mouseX, mouseY, (float) (x + 5), (float) (y + animationCoolski), this.s);
				Gui.drawRect(x + WIDTH - 25, y + animationCoolski, x + WIDTH - 2, y + 15 + 30, s.ColorPickerC.getRGB());
				FHook.github.drawStringWithShadow2("" + s.ColorPickerC.getRed() + ":" + s.ColorPickerC.getGreen() + ":" + s.ColorPickerC.getBlue(), x + WIDTH - 25, y + animationCoolski, -1);
				FileUtil.FILE_UTIL.saveColor();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		GlStateManager.resetColor();
		FHook.fontRenderer2.drawString(this.s.getName().split("->")[0], (int) ((float) (x + WIDTH) - (FHook.fontRenderer2.getStringWidth(this.s.getName().split("->")[0]) / 2f) - (float) WIDTH / 2), this.extended ? (float) (this.y + 15 - FHook.fontRenderer3.getHeight() / 2 - 15 / 2 + 1) : (float) (this.y + 15 - FHook.fontRenderer3.getHeight() / 2 - 15 / 2 - 1), -1);

		Scissoring.SCISSORING.disableScissor();
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		boolean isOver = mouseX > this.x && mouseX < this.x + 90 && mouseY > this.y && mouseY < this.y + 15;
		if (isOver && mouseButton == 1) {
			this.extended = !this.extended;
		}

		int yOffset = 15;
		if (this.extended) {
		}
	}
}