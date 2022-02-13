package beta.tiredb56.api.guis.clickgui;

import beta.tiredb56.api.util.font.CustomFont;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.api.guis.clickgui.setting.Setting;
import net.minecraft.client.gui.FontRenderer;

public abstract class AbstractClickGui implements IHook {

    public int x, y;


    Setting setting;

    public boolean dragging;

    public boolean extended;

    public FontRenderer fontRenderer = MC.fontRendererObj;

    public final double NORMAL_WIDTH = 120, NORMAL_HEIGHT = 15;

    public void drawSlider(int x, int y, int mouseX, int mouseY) {}

    public void renderInterface(int mouseX, int mouseY,double x, double y, double width, double height) {
    }

    public void renderInterface(double x, double y, double width, double height) {
    }

    public void drawInterface(int mouseX, int mouseY) {}

    public void drawInterface(int mouseX, int mouseY, boolean rectangle) {}

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void drawTextBox(int x, int y, int mouseX, int mouseY) {

    }


    public void drawCheckBox(int x, int y, int mouseX, int mouseY) {

    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }

    public void mouseReleased(int mouseX, int mouseY) {

    }

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
