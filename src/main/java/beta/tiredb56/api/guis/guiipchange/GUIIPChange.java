package beta.tiredb56.api.guis.guiipchange;

import beta.tiredb56.api.extension.processors.extensions.generally.RenderProcessor;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.shader.list.BackGroundShader;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.ShaderRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GUIIPChange extends GuiScreen implements IHook {

    private final BackGroundShader backGroundShader;
    private int blurRadius = 0;

    public GUIIPChange() {
        this.backGroundShader = new BackGroundShader(0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.backGroundShader.render(0, 0, width, height);

        final double widthA = 140;

        blurRadius++;

        int maxBlur = 50;

        CheatMain.INSTANCE.discordRPC.setStage(new String[]{"Tired-Client.de", " Tired on top w. allah"});

        if (blurRadius > maxBlur) {
            blurRadius = maxBlur;
        }

        if (blurRadius < 0) {
            blurRadius = 0;
        }

        ShaderRenderer.startBlur();
        RenderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur(6);
        RenderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MIN_VALUE);
        String s = "Minecraft 1.8.8";
        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);
        ShaderRenderer.startBlur();
        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);
        ShaderRenderer.stopBlur(6);
        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);

        FHook.login.drawStringWithShadow("Ip change only works for Fritzbox", width / 2 - 2, height / 4 + 70, -1);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
