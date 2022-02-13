package beta.tiredb56.shader.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.shader.Shader;
import beta.tiredb56.shader.ShaderProgram;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class ArrayListShader2 extends Shader implements IHook {
    private long startTime;

    public ArrayListShader2(int pass) {
        super(new ShaderProgram("fragment/BackgroundAsGradient.glsl"));
        this.pass = pass;
        startTime = System.currentTimeMillis();
    }


    public void drawShader() {

        frameBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(frameBuffer, false);
        frameBuffer.framebufferClear();
        frameBuffer.bindFramebuffer(true);


    }

    public void stop() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(true);


        MC.entityRenderer.setupOverlayRendering();
        MC.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        shaderProgram.initShader();
        setUniforms();
        glBindTexture(GL_TEXTURE_2D, frameBuffer.framebufferTexture);
        shaderProgram.renderTexture();

        GL20.glUseProgram(0);

    }

    @Override
    public void setUniforms() {

        float time = (System.currentTimeMillis() - this.startTime) / 500f;
        GL20.glUniform1f(shaderProgram.getUniform("time"), time);
        GL20.glUniform1f(shaderProgram.getUniform("alpha"), .6f);
        GL20.glUniform2f(shaderProgram.getUniform("resolution"), 222, 222);
    }


}

