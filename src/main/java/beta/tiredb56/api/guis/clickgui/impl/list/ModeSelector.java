package beta.tiredb56.api.guis.clickgui.impl.list;

import beta.tiredb56.api.guis.clickgui.AbstractClickGui;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModeSelector extends AbstractClickGui {

	public ModeSetting modeSetting;

	public int WIDTH = 120, HEIGHT = 21;

	public double animation;
	private ModeSetting set;
	private int x;
	private int y;
	private Module mod;

	private int modeIndex;
	public boolean extended = false;
	public String[] settings;

	public ModeSelector(ModeSetting setting, Module mod) {
		this.modeSetting = setting;
		this.set = setting;
		this.mod = mod;
		this.modeIndex = 0;
	}

	@Override
	public void drawSlider(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		String name  =set.getName();
		name = name.substring(0,1).toUpperCase() + name.substring(1);

		String finalStr = name + ": " + set.getValue();

		if (finalStr.length() > 21) {
			WIDTH = 150;
		} else {
			WIDTH = 120;
		}
		animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
		WIDTH = (int) animation;
		final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
		//   Gui.drawRect(x, y , x+ (WIDTH), y + HEIGHT + 1, Color.RED.getRGB());
		Gui.drawRect(x, y, x + WIDTH, y + HEIGHT, Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
		Gui.drawRect(x, y + HEIGHT, x + 2, y + HEIGHT, new Color(20, 20, 20).getRGB());
		if (isMouseOnButton(mouseX, mouseY)) {
			Gui.drawRect(x, y, x + WIDTH, y + HEIGHT, new Color(10,10, 10, 80).getRGB());
		}

		Scissoring.SCISSORING.startScissor();
		Scissoring.SCISSORING.doScissor(x, y, x + WIDTH, y + HEIGHT);

		FHook.fontRenderer.drawString2(name + ": " + set.getValue(), (x + 2), (y + 6), -1);
		Scissoring.SCISSORING.disableScissor();
		super.drawSlider(x, y, mouseX, mouseY);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (isMouseOnButton(mouseX, mouseY)) {
			int maxIndex = set.getOptions().length;

			if (mouseButton == 0) {

				if (modeIndex + 1 >= maxIndex && modeIndex - 1 < 0) {
					modeIndex = 0;
				} else {
					modeIndex++;
				}

			}
			if (mouseButton == 1) {

				if (modeIndex + 1 >= maxIndex && modeIndex - 1 < 0)
					modeIndex = 0;
				else
					modeIndex--;

			}
			if (maxIndex <= 1) return;
			if (modeIndex >= maxIndex) modeIndex = 0;
			if (modeIndex < 0) modeIndex = maxIndex - 1;

			set.setValue(set.getOptions()[modeIndex]);
			set.setModeIndex(modeIndex);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + WIDTH && y > this.y && y < this.y + HEIGHT;
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		super.mouseReleased(mouseX, mouseY);
	}
}
