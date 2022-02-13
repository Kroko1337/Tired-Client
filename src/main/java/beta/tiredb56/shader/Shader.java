package beta.tiredb56.shader;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.interfaces.IHook;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.*;

public abstract class Shader implements IHook {

    protected int pass;

    protected ShaderProgram shaderProgram;
    protected Framebuffer frameBuffer = new Framebuffer(MC.displayWidth, MC.displayHeight, false);

    protected float width, height;

    public Shader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public abstract void setUniforms();

    public void render(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        frameBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(frameBuffer, false);
        frameBuffer.framebufferClear();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(false);
        doShaderPass(MC.getFramebuffer());
        pass++;
    }

    private void doShaderPass(Framebuffer framebufferIn) {
        shaderProgram.initShader();
        setUniforms();
        glBindTexture(GL_TEXTURE_2D, framebufferIn.framebufferTexture);
        shaderProgram.renderTexture();
        shaderProgram.deleteShader();
    }

    public int pass() {
        return pass;
    }

    public void pass(int pass) {
        this.pass = pass;
    }
}
