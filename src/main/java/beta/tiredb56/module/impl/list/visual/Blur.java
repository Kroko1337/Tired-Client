package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;

@ModuleAnnotation(name = "Blur", category = ModuleCategory.RENDER, clickG = "Renders blur on textures")
public class Blur extends Module {
    @Override
    public void onState() {

        if (PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {
            unableModule();
        }

    }

    @Override
    public void onUndo() {

    }
}
