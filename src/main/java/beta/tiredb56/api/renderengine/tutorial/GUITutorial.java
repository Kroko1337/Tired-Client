package beta.tiredb56.api.renderengine.tutorial;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.extension.processors.extensions.generally.RenderProcessor;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.shader.Shader;
import beta.tiredb56.shader.list.BackGroundShader;
import beta.tiredb56.tired.ShaderRenderer;
import net.minecraft.client.gui.GuiScreen;

public class GUITutorial extends GuiScreen {

    public TutorialState tutorialState;

    private final Shader shader;

    public GUITutorial() {
        shader = new BackGroundShader(0);
        tutorialState = TutorialState.STATE1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        shader.render(0, 0, width, height);

        final double widthA = 140;

        ShaderRenderer.startBlur();
        RenderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MAX_VALUE);
        ShaderRenderer.stopBlur();

        if (tutorialState == TutorialState.STATE1) {
            FHook.big2.drawStringWithShadow("Welcome to Tired!", width / 2f + widthA - widthA * 1.40, height / 4f + 37, -1);
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
    }


}
