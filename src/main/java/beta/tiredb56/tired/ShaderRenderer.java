package beta.tiredb56.tired;

import beta.tiredb56.api.util.StencilUtil;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.shader.list.ArrayListShader;
import beta.tiredb56.shader.list.BackGroundShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ShaderRenderer implements IHook {

    private static BackGroundShader backGroundShader;

    public ShaderRenderer() {
        backGroundShader = new BackGroundShader(0);
    }

    public static void renderBG() {
        final ScaledResolution sr = new ScaledResolution(MC);
        backGroundShader.render(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
    }

    public static void startBlur() {
        StencilUtil.initStencilToWrite();
    }

    public static void stopBlur() {
        StencilUtil.readStencilBuffer(1);
        CheatMain.INSTANCE.getBlurShader(22).blur();

        StencilUtil.uninitStencilBuffer();
        GlStateManager.enableBlend();
        IHook.MC.entityRenderer.setupOverlayRendering();
    }

    public static void stopBlur(int radius) {
        StencilUtil.readStencilBuffer(1);
        CheatMain.INSTANCE.getBlurShader(radius).blur();
        StencilUtil.uninitStencilBuffer();
    }


}
