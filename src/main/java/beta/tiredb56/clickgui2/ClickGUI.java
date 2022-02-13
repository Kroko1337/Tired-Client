package beta.tiredb56.clickgui2;

import beta.tiredb56.clickgui2.list.CategoryButton;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen implements IHook {

    private double x, y, dragY, dragX;
    private boolean dragging;

    public ArrayList<CategoryButton> buttons = new ArrayList<>();

    public CategoryButton currentCategory;

    private boolean over;

    private final double width = 500, height = 250;

    public ClickGUI() {
        final ScaledResolution scaledResolution = new ScaledResolution(MC);
        this.x = MC.displayWidth / 2f - width / 2;
        this.y = MC.displayHeight / 2f - height / 2;

    }

    @Override
    public void initGui() {
        this.buttons.clear();

        for (ModuleCategory c : ModuleCategory.values()) {

            String categoryname = c.name().substring(0, 1).toUpperCase() + c.name().substring(1).toLowerCase();
            CategoryButton cscb = new CategoryButton(categoryname, (int) x, (int) y, mc.fontRendererObj.getStringWidth(categoryname), mc.fontRendererObj.FONT_HEIGHT, c);

            this.buttons.add(cscb);

            y += 30;
        }


        for (CategoryButton cb : buttons) {
            cb.initButton();
        }

        super.initGui();

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (dragging) {
            x = (mouseX - dragX);
            y = (mouseY - dragY);
        }

        final double x1 = this.x, x2 = this.x + width, y1 = this.y, y2 = this.y + height;

        Gui.drawRect(x1, y1, x2, y2, new Color(21, 21, 21).getRGB());

        int yAxis = 30;
        for (CategoryButton cb : buttons) {
            cb.drawInterface(mouseX, mouseY, (int) (x1 + 5), (int) (y + yAxis));
            yAxis += 30;
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (dragging) {
            dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isInside(mouseX, mouseY, this.x, this.y, width, 30)) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = (mouseX - x);
                dragY = (mouseY - y);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    boolean isInside(int pointX, int pointY, double x, double y, double width, double height) {
        return (pointX >= x && pointX <= x + width) && (pointY >= y && pointY <= y + height);
    }

}
