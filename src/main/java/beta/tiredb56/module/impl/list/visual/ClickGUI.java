package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.tired.CheatMain;

import java.awt.*;

@ModuleAnnotation(name = "ClickGUI", category = ModuleCategory.RENDER, clickG = "ClickGui idk lol")
public class ClickGUI extends Module {

	public ColorPickerSetting colorPickerSetting = new ColorPickerSetting("ColorClickGUI", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

	public static ClickGUI getInstance() {
		return ModuleManager.getInstance(ClickGUI.class);
	}

	@Override
	public void onState() {
		MC.displayGuiScreen(CheatMain.INSTANCE.clickGui);
		unableModule();

	}

	@Override
	public void onUndo() {

	}
}
