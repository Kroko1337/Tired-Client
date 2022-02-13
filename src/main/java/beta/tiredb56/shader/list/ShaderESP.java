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

public class ShaderESP implements IHook {

    private final ShaderProgram blurShader = new ShaderProgram("fragment/glowesp.frag");

    private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);

    private final ShaderProgram kawaseUp = new ShaderProgram("fragment/blur.frag");

    private float radius;

    public ShaderESP(float radius) {
        this.radius = radius;
    }

    public void render(float partialTicks) {

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        blurBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(blurBuffer, true);
        blurBuffer.framebufferClear();
        blurBuffer.bindFramebuffer(true);
        MC.entityRenderer.setupCameraTransform(partialTicks, 0);


    }

    public void deleteShader(float partialTicks) {
        final Minecraft mc = Minecraft.getMinecraft();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getFramebuffer().bindFramebuffer(true);

        mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();

        blurShader.initShader();
        setupUniforms(1, 0);
        mc.entityRenderer.setupOverlayRendering();
        blurShader.renderFrameBufferOnly(blurBuffer);
        blurShader.deleteShader();
        GlStateManager.disableBlend();
        mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        GlStateManager.disableDepth();
    }

    public void setupUniforms(float x, float y) {
        blurShader.setUniformi("currentTexture", 0);
        blurShader.setUniformf("texelSize", (float) (1.2 / MC.displayWidth), (float) (1.2 / MC.displayHeight));
        blurShader.setUniformf("coords", x, y);
        blurShader.setUniformf("blurRadius", 2);
        blurShader.setUniformf("blursigma", 6);
        blurShader.setUniformf("u_color", (float) (ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRed() / 255.0f), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getGreen() / 255.0f, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getBlue() / 255.0f);
    }

    public float radius() {
        return radius;
    }

    public void radius(float radius) {
        this.radius = radius;
    }
}


