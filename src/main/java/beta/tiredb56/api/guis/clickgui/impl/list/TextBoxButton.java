package beta.tiredb56.api.guis.clickgui.impl.list;

import beta.tiredb56.api.guis.clickgui.AbstractClickGui;
import beta.tiredb56.api.guis.clickgui.setting.impl.TextBoxSetting;
import beta.tiredb56.api.util.FileUtil;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TextBoxButton extends AbstractClickGui {

	public int WIDTH = 120;
	public final int HEIGHT = 15;

	private boolean hovered;
	public boolean allSelected = false;

	public static boolean typing = false;
	public final TextBoxSetting set;
	public double animation;
	public double animation2;
	public TextBoxButton(TextBoxSetting value) {
		this.set = value;
	}

	@Override
	public void drawTextBox(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
		animation = AnimationUtil.getAnimationState(animation, 120, Math.max(3.6D, Math.abs((double) animation - 120)) * 2);
		WIDTH = (int) animation;

		Scissoring.SCISSORING.startScissor();
		Scissoring.SCISSORING.doScissor(x, y, x + WIDTH, y + HEIGHT);

		Gui.drawRect(this.x, this.y, this.x + WIDTH, this.y + HEIGHT, Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
		Gui.drawRect(this.x + 1, this.y + 1, this.x + WIDTH - 1, this.y + HEIGHT - 1,  Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());

		FHook.fontRenderer.drawStringWithShadow((set.getName().equalsIgnoreCase("") ? "" : set.getName() + ": ") + set.getValue() + (this.typing ? "_" : ""), x + 3, y + 2, -1);

		double values = allSelected ? FHook.fontRenderer.getStringWidth((set.getName().equalsIgnoreCase("") ? "" : set.getName() + ": ") + set.getValue() + (this.typing ? "_" : "")) : 0;
		animation2 = AnimationUtil.getAnimationState(animation2, values, Math.max(3.6D, Math.abs((double) animation - values)) * 4);

		Gui.drawRect(allSelected ? this.x + 2 : this.x, this.y + 3, this.x + animation2, this.y + HEIGHT - 2, new Color(14, 114, 214, 136).getRGB());
		Scissoring.SCISSORING.disableScissor();
		super.drawTextBox(x, y, mouseX, mouseY);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (isMouseOnButton(mouseX, mouseY)) {
			if (mouseButton == 0) typing = !typing;
		}

		if (!typing) {
			FileUtil.FILE_UTIL.saveSettings();
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	@Override
	public void keyTyped(char typedChar, int keyCode) {
		Keyboard.enableRepeatEvents(true);
			if (typing) {
				if (GuiScreen.isKeyComboCtrlA(keyCode)  && !this.set.getValue().isEmpty()) {
					this.allSelected = true;
				}
				if (allSelected) {
					if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !this.set.getValue().isEmpty()) {
						this.set.setValue("");
						allSelected = false;
					}
				} else if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !this.set.getValue().isEmpty()) {
					this.set.setValue(this.set.getValue().substring(0, this.set.getValue().length() - 1));
				} else if (keyCode == Keyboard.KEY_RETURN) {
					typing = false;

				} else if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && (set.getValue().length() < 20)) {
					this.set.setValue(this.set.getValue() + typedChar);
				} else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
					this.set.setValue(this.set.getValue() + GuiScreen.getClipboardString());
				}

				if (!typing) {
					FileUtil.FILE_UTIL.saveSettings();
				}

		}
		super.keyTyped(typedChar, keyCode);
	}



	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + WIDTH && y > this.y && y < this.y + HEIGHT;
	}

}
