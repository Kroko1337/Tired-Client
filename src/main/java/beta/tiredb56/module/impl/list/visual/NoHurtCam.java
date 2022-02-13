package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;

@ModuleAnnotation(name = "NoHurtCam", category = ModuleCategory.RENDER, clickG = "You wont see any Damage!")
public class NoHurtCam extends Module {

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
