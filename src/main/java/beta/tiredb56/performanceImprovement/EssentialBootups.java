package beta.tiredb56.performanceImprovement;

import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.impl.list.visual.Blur;
import beta.tiredb56.shader.list.BlurShader;
import beta.tiredb56.tired.CheatMain;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Die class soll für eine gute performance sorgen, sie bootet nur modules usw,
 * Shader sollen z.b nur gestartet werden, wenn normal performance ausgewählt ist
 */

public class EssentialBootups {

    private BlurShader blurShader;

    @Getter
    private final List<Module> notAllowedBootModules = new ArrayList<>(); //modules die performance ziehen

    public EssentialBootups() {
        this.bootUp();
    }

    private void bootUp() {
        if (PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {
            notAllowedBootModules.add(new Blur());
            System.out.println("Caused by High Performance mode, " + notAllowedBootModules + " Where removed.");
        }

    }

    public BlurShader getBlurShader(int radius) {

        if (blurShader == null) {
            blurShader = new BlurShader(radius);
        }
        if (blurShader.radius() != 0) {
            blurShader.radius(radius);
        }
        return blurShader;
    }

}
