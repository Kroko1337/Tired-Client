package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventPreMotion;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "Phase", key = Keyboard.KEY_NONE, category = ModuleCategory.MOVEMENT, clickG = "Clip down.")
public class Phase extends Module {

    @EventTarget
    public void onSend(PacketEvent e) {
    }


    @EventTarget
    public void onUpdate(EventPreMotion e) {

    }

    @Override
    public void onState() {
        MC.thePlayer.setPositionAndUpdate(MC.thePlayer.posX,MC.thePlayer.posY -3,MC.thePlayer.posZ);
    }

    @Override
    public void onUndo() {
    }
}
