package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventPreMotion;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;

@ModuleAnnotation(name = "NoBob", category = ModuleCategory.RENDER, clickG = "Cool arm animation idk")
public class NoBob extends Module {


    float oldGamma;

    @EventTarget
    public void onRender(EventPreMotion e) {
        MC.thePlayer.distanceWalkedModified = 0.0f;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {


    }
}
