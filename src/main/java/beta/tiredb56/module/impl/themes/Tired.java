package beta.tiredb56.module.impl.themes;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.CheatMain;

@ModuleAnnotation(name = "Tired", category = ModuleCategory.THEME)
public class Tired extends Module {
	@Override
	public void onState() {
		CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class).setState(false);
	}
	@Override
	public void onUndo() {

	}
}
