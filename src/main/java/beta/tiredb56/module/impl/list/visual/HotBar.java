package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;

@ModuleAnnotation(name = "HotBar", category = ModuleCategory.RENDER, clickG = "Better hotbar design")
public class HotBar extends Module {
    public BooleanSetting smooth = new BooleanSetting("smooth", this, true);

    public ModeSetting hotbarMode = new ModeSetting("hotbarMode", this, new String[]{"B40", "B48", "B50"});

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    public static HotBar getInstance() {
        return ModuleManager.getInstance(HotBar.class);
    }

}
