package beta.tiredb56.api.guis.clickgui;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.impl.Panel;
import beta.tiredb56.api.particle.ParticleRenderer;
import beta.tiredb56.api.util.Translate;
import beta.tiredb56.api.util.font.CustomFont;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.shader.list.ArrayListShader;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.ShaderRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {

    private final ArrayList<beta.tiredb56.api.guis.clickgui.impl.Panel> panelArrayList;

    private ParticleRenderer particleRenderer;
    private Translate translate;
    private static String sussy = "";
    private final ArrayListShader arrayListShader = new ArrayListShader(0);
    private double fadeAnimation = 20;
    private int circleAnim;
    public float susAmongus;
    private int fadeAnimButton;

    public ClickGui() {
        this.panelArrayList = new ArrayList<>();
        int xAxis = 0;
        for (ModuleCategory category : ModuleCategory.values()) {
            panelArrayList.add(new beta.tiredb56.api.guis.clickgui.impl.Panel(20 + xAxis, 20, category));



            xAxis += 130;
        }
        for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            double maxDist = Math.sqrt(sr.getScaledWidth() * sr.getScaledWidth() + sr.getScaledHeight() * sr.getScaledHeight()) / 4;
            double mDist = Math.sqrt(Math.pow(panel.x - Mouse.getX() / 2f, 2) + Math.pow(panel.y - Mouse.getY() / 2, 2));

            if (mDist < maxDist) {
                panel.x += 50;
                panel.y += 20;
            }

            panel.extended = false;
        }

        this.translate = new Translate(0, 0);

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        susAmongus = 0;
        particleRenderer = new ParticleRenderer();
        this.translate = new Translate(0, 0);
        fadeAnimation = 0;
        for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
            panel.animation = 0;
            panel.animationHover = 0;

            panel.onInitGUI();

            panel.animationRight = 0;
        }
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        particleRenderer.draw(mouseX, mouseY);
        final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
        if (!Flat1.state) {
            ShaderRenderer.stopBlur();
        }

        this.susAmongus = (int) AnimationUtil.getAnimationState(susAmongus, 1000, Math.max(3.6D, Math.abs((double) susAmongus - 1000)) * 2);


        fadeAnimation = AnimationUtil.getAnimationState(fadeAnimation, 118, 140);

        int anim = mouseWithinCircle(mouseX, mouseY, 20, height - 20, 16) ? 1200 : 0;
        circleAnim = (int) AnimationUtil.getAnimationState(circleAnim, anim, Math.max(3.6D, Math.abs((double) circleAnim - 1200)) * 4);

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, susAmongus, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), (int) fadeAnimation).getRGB());

        translate.interpolate(resolution.getScaledWidth(), resolution.getScaledHeight(), 13);
        GlStateManager.pushMatrix();
        GL11.glTranslatef(resolution.getScaledWidth() / 2f, resolution.getScaledHeight() / 2f, 0);
        GL11.glScaled(translate.getX() / resolution.getScaledWidth(), translate.getY() / resolution.getScaledHeight(), 0);
        GL11.glTranslatef(-resolution.getScaledWidth() / 2f, -resolution.getScaledHeight() / 2f, 0);

        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (Flat1.state) {

            for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
                sussy = panel.abstractString;
                panel.drawInterface(mouseX, mouseY, false);
            }
            ShaderRenderer.startBlur();
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, susAmongus, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), 122).getRGB());
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(20, height - 20, circleAnim, new Color(ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRed(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getGreen(), ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getBlue(), 122).getRGB());
            for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
                sussy = panel.abstractString;
                panel.drawInterface(mouseX, mouseY, false);
            }
            ShaderRenderer.stopBlur(12);

            for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
                sussy = panel.abstractString;
                panel.drawInterface(mouseX, mouseY, true);
            }


        } else {
            for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
                panel.drawInterface(mouseX, mouseY, true);
            }
        }


        String suggested = Panel.suggestedString.replace("[", "").replace("]", "");

        String typed = Panel.abstractString;

        FHook.big2.drawCenteredStringWithShadow(typed, sr.getScaledWidth() / 2f, 10, -1);

        if (suggested.contains(",")) {
            suggested = suggested.split(",") [0];
        }

        if (typed.length() > 0) {
            if (suggested.length() > typed.length()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
                    typed = suggested;
                    Panel.abstractString = typed;
                }
                FHook.big2.drawCenteredStringWithShadow(suggested.substring(typed.length()), sr.getScaledWidth() / 2f + FHook.big2.getStringWidth(typed) / 2f + (FHook.big2.getStringWidth(suggested.substring(typed.length())) / 2f), 10, Color.GRAY.getRGB());
            }
        }
        double valueSus = mouseWithinCircle(mouseX, mouseY, 20, height - 20, 16) ? 140 : 0;
        fadeAnimButton = (int) AnimationUtil.getAnimationState(fadeAnimButton, valueSus, Math.max(3.6D, Math.abs((double) fadeAnimButton - valueSus)) * 4);

        // FHook.big2.drawCenteredStringWithShadow(sussy, sr.getScaledWidth() / 2, 10, -1);

        fadeAnimButton = (int) AnimationUtil.getAnimationState(fadeAnimButton, valueSus, Math.max(3.6D, Math.abs((double) fadeAnimButton - valueSus)) * 4);

        ShaderRenderer.startBlur();
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(20, height - 20, 17, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();
        FHook.fontRenderer3.drawStringWithShadow("Config", 10, height - 23, -1);

        GlStateManager.popMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
            panel.keyTyped(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (beta.tiredb56.api.guis.clickgui.impl.Panel panel : this.panelArrayList) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (mouseWithinCircle(mouseX, mouseY, 20, height - 20, 16)) {
            mc.displayGuiScreen(CheatMain.INSTANCE.configGui);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean mouseWithinCircle(final int mouseX, final int mouseY, final double x, final double y, final double radius) {
        final double dx = mouseX - x;
        final double dy = mouseY - y;
        return Math.sqrt(dx * dx + dy * dy) < radius;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel panel : this.panelArrayList) {
            panel.mouseReleased(mouseX, mouseY);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}
