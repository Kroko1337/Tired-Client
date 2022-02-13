package beta.tiredb56.api.guis.clickgui.impl.list;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.extension.processors.extensions.generally.RenderProcessor;
import beta.tiredb56.api.guis.clickgui.AbstractClickGui;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class Slider extends AbstractClickGui {

    public NumberSetting setting;

    private double renderWidth;

    public int WIDTH = 120, HEIGHT = 25;
    public float animationX;
    public double animation;

    public Slider(NumberSetting setting) {
        this.setting = setting;
    }

    public void drawSlider(int x, int y, int mouseX, int mouseY, boolean justSlider, boolean... onlyText) {
        this.x = x;
        this.y = y;
        final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);
        animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
        WIDTH = (int) animation;
        double value = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        if (this.dragging) {
            double valueX = (float) (mouseX) - (this.x);

            value = valueX / (float) (this.WIDTH);

            if (value < 0.0F) {
                value = 0F;
            }

            if (value > 1.0F) {
                value = 1.0F;
            }

        }

        double _value = setting.getMin() + (setting.getMax() - setting.getMin()) * value;

        double disValue = Math.round(_value / setting.getInc()) * setting.getInc();

        Scissoring.SCISSORING.startScissor();
        Scissoring.SCISSORING.doScissor(x, y, x + WIDTH, y + HEIGHT);

        final double percent = this.setting.getValue() * 100 / this.setting.getMax();
        final double calcWidth = (WIDTH - 20) * percent / 100;
        this.animationX = (float) AnimationUtil.getAnimationState((double) this.animationX, calcWidth + 10, Math.max(0.6D, Math.abs((double) this.animationX - calcWidth - 10)));
        setting.setValue(disValue);
        Gui.drawRect(this.x, this.y, this.x + this.WIDTH, this.y + this.HEIGHT, Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
        Gui.drawRect(x + 10, y + 10, x + NORMAL_WIDTH - 10, y + NORMAL_HEIGHT + 3, Integer.MIN_VALUE);

        String name = setting.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        final String toString = String.valueOf(Math.round(disValue * 10.0) / 10.0).replace(".0", "");

        FHook.fontRenderer2B.drawString2(" " + name, calculateMiddle(name, FHook.fontRenderer2B, x, (int) NORMAL_WIDTH), (int) (this.y + (FHook.fontRenderer2B.FONT_HEIGHT / 2f) - 4), -1);

        if (isOver(x, y, WIDTH, HEIGHT, mouseX, mouseY)) {
            FHook.fontRenderer2B.drawString(toString, x + 11, y + 6, -1);
        }
        GlStateManager.disableBlend();

        RenderProcessor.drawGradientSideways(x + 10, y + 10, this.x + animationX, y + NORMAL_HEIGHT + 3, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
        Scissoring.SCISSORING.disableScissor();
        WIDTH = 120;

        super.drawSlider(x, y, mouseX, mouseY);

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOver(x + 10, y, WIDTH - 10, HEIGHT, mouseX, mouseY)) {
            this.dragging = true;
            setting.dragged = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
        setting.dragged = false;
        super.mouseReleased(mouseX, mouseY);
    }

}
