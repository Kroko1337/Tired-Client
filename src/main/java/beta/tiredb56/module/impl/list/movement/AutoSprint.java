package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;

@ModuleAnnotation(name = "AutoSprint", category = ModuleCategory.MOVEMENT, clickG = "Sprinting automatically")
public class AutoSprint extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        MC.thePlayer.setSprinting(ClientHelper.INSTANCE.moving());
    }
    @Override
    public void onState() {

    }
    @Override
    public void onUndo() {

    }
}
