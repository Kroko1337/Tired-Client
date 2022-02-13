package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventTick;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.shader.list.BlurShader;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.ShaderRenderer;

@ModuleAnnotation(name = "MotionBlur", category = ModuleCategory.RENDER)
public class MotionBlur extends Module {

    private BlurShader blurShader = new BlurShader(6);

    @EventTarget
    public void onTick(EventTick e) {


        CheatMain.INSTANCE.getBlurShader(22).blur();
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {


    }
}
