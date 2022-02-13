package beta.tiredb56.api.util.renderapi;

import beta.tiredb56.api.extension.Extension;

public class AnimationUtil {

    public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float) ((double) Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * speed);
        animation = animation < finalState ? (Math.min(animation + (double) add, finalState)) : (Math.max(animation - (double) add, finalState));
        return animation;
    }

}
