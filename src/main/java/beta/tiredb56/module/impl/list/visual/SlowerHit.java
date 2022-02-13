package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;

@ModuleAnnotation(name = "SlowerHIT", category = ModuleCategory.RENDER, clickG = "Slower hit animation")
public class SlowerHit extends Module {

    public NumberSetting value = new NumberSetting("value",  this, 1, 1, 100, 1);

    public static SlowerHit getInstance() {
        return ModuleManager.getInstance(SlowerHit.class);
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
