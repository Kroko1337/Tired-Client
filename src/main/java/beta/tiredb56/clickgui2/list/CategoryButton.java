package beta.tiredb56.clickgui2.list;

import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.clickgui2.ClickGUIButton;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.ModuleCategory;

public class CategoryButton extends ClickGUIButton {


    public CategoryButton(String text, int x, int y, int width, int height, ModuleCategory category) {
        super(text, x, y, width, height);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawInterface(int mouseX, int mouseY, int x, int y) {
        {

            FontManager.SFPROBig.drawStringWithShadow(text, x, y, -1);

        }
        super.drawInterface(mouseX, mouseY, x, y);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int key) {
        super.mouseClicked(mouseX, mouseY, key);
    }

    @Override
    public void initButton() {
        int x = this.x + 65;
        int y = 110;
        super.initButton();
    }
}
