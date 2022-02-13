package beta.tiredb56.shader.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.impl.HudModule;
import beta.tiredb56.module.impl.list.visual.Blur;
import beta.tiredb56.shader.ShaderProgram;
import beta.tiredb56.api.util.StencilUtil;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.*;

public class BlurShader implements IHook {

    private final ShaderProgram kawaseUp = new ShaderProgram("fragment/blur.frag");

    private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);

    private float radius;

    public BlurShader(float radius) {
        this.radius = radius;
    }

    public void blur() {
        blur(0, 0, -1, -1);
    }

    public void blur(float x, float y, float width, float height) {

        if (!CheatMain.INSTANCE.moduleManager.moduleBy(Blur.class).isState()) return;
        StencilUtil.checkSetupFBO(IHook.MC.getFramebuffer());

        blurBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(blurBuffer, false);

        kawaseUp.initShader();
        setupUniforms(1, 0);
        blurBuffer.framebufferClear();
        blurBuffer.bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, MC.getFramebuffer().framebufferTexture);
        kawaseUp.renderTexture();
        blurBuffer.unbindFramebuffer();

        //        blurShader.renderFrameBufferOnly(blurBuffer);
        //        blurShader.renderTexture();

        kawaseUp.initShader();
        setupUniforms(0, 1);
        MC.getFramebuffer().bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, blurBuffer.framebufferTexture);
        kawaseUp.renderTexture();
        kawaseUp.deleteShader();


    }

    public void delete() {
        blurBuffer.deleteFramebuffer();
        kawaseUp.deleteShader();
    }

    public void setupUniforms(float x, float y) {
        kawaseUp.setUniformi("currentTexture", 0);
        kawaseUp.setUniformf("texelSize", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        kawaseUp.setUniformf("coords", x, y);
        kawaseUp.setUniformf("blurRadius", 11);
        kawaseUp.setUniformf("uRTPixelSizePixelSizeHalf", 20, 20, 20, 20);
        kawaseUp.setUniformf("blursigma", 8);
    }

    public float radius() {
        return radius;
    }

    public void radius(float radius) {
        this.radius = radius;
    }
}

