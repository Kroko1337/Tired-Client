package beta.tiredb56.clickgui2;

import beta.tiredb56.interfaces.IHook;

import java.io.IOException;

public class ClickGUIButton implements IHook {

    public int x, y, width, height;

    public String text;

    public ClickGUIButton(String text, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public void drawInterface(int mouseX, int mouseY, int x, int y) {
    }

    public void mouseClicked(int mouseX, int mouseY, int key) {

    }

    public void mouseReleased(int mouseX, int mouseY, int key) {
    }

    public void initButton() {
    }


    public void keyTyped(char typedChar, int keyCode) {
    }



}
