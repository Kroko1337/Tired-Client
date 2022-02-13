package beta.tiredb56.shader.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.shader.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public class ShaderESP implements IHook {

    private final ShaderProgram blurShader = new ShaderProgram("fragment/glowesp.frag");

    private Framebuffer entityOutlineFramebuffer;

    private float radius;

    public ShaderESP(float radius) {
        this.radius = radius;
    }

    public void render(float partialTicks) {
        GlStateManager.enableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();


        MC.entityRenderer.setupCameraTransform(partialTicks, 0);

    }

    public void deleteShader(float partialTicks) {
        final Minecraft mc = Minecraft.getMinecraft();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getFramebuffer().bindFramebuffer(true);

        mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        mc.entityRenderer.setupOverlayRendering();




        blurShader.renderTexture();
        blurShader.deleteShader();




        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderShader(int x, int y, Framebuffer framebuffer) {
        blurShader.initShader();
        setupUniforms(x, y);
        blurShader.renderFrameBufferOnly(framebuffer);


    }

    private void doShaderPass(final Framebuffer framebuffer) {
        blurShader.initShader();
        setupUniforms(1, 1);
        blurShader.renderFrameBufferOnly(framebuffer);
        blurShader.renderTexture();


    }


    public void setupUniforms(float x, float y) {
        blurShader.setUniformi("currentTexture", 0);
        blurShader.setUniformf("texelSize", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        blurShader.setUniformf("coords", x, y);
        blurShader.setUniformf("blurRadius", 5);
        blurShader.setUniformf("blursigma", 6);
        blurShader.setUniformf("u_color", 244, 1, 1);
    }

    public float radius() {
        return radius;
    }

    public void radius(float radius) {
        this.radius = radius;
    }
}


