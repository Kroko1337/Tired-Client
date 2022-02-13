package beta.tiredb56.shader.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.shader.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class DropShadow implements IHook {

    private final ShaderProgram blurShader = new ShaderProgram("fragment/glowesp.frag");

    private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);


    private float radius;

    public DropShadow(float radius) {
        this.radius = radius;
    }

    public void render() {


        blurBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(blurBuffer, false);
        blurBuffer.framebufferClear();
        blurBuffer.bindFramebuffer(true);


    }

    public void deleteShader() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(true);


        MC.entityRenderer.setupOverlayRendering();
        MC.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        blurShader.initShader();
        setupUniforms(1, 0);
        glBindTexture(GL_TEXTURE_2D, blurBuffer.framebufferTexture);
        blurShader.renderTexture();

        GL20.glUseProgram(0);
    }

    public void setupUniforms(float x, float y) {
        blurShader.setUniformi("currentTexture", 0);
        blurShader.setUniformf("texelSize", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        blurShader.setUniformf("coords", x, y);
        blurShader.setUniformf("blurRadius", 3.3f);
        blurShader.setUniformf("blursigma", 11);
        blurShader.setUniformf("u_color", 0, 0, 0);
    }

    public float radius() {
        return radius;
    }

    public void radius(float radius) {
        this.radius = radius;
    }
}


