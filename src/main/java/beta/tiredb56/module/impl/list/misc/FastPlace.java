package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;

@ModuleAnnotation(name = "FastPlace", category = ModuleCategory.MISC, clickG = "Places faster blocks")
public class FastPlace extends Module {

    public NumberSetting delay = new NumberSetting("delay", this, 0, 0, 3, 1);

    @EventTarget
    public void onRender(UpdateEvent e) {
        MC.rightClickDelayTimer = delay.getValueInt();
        setDesc("Delay " + delay.getValue());
    }

    @Override
    public void onState() {
    }

    @Override
    public void onUndo() {
        MC.rightClickDelayTimer = 4;

    }
}
