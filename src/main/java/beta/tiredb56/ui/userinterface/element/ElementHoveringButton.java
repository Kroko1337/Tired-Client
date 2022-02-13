package beta.tiredb56.ui.userinterface.element;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.ui.userinterface.UI;

import java.awt.*;

public class ElementHoveringButton {

    private final UI ui;

    private final int x, y;

    public float animation;

    private final int size;

    private boolean over;

    private final String text;

    public ElementHoveringButton(UI ui, int x, int y, int size, String text) {
        this.ui = ui;
        this.x = x;
        this.y = y;
        this.text = text;
        this.size = size;
    }


    public void renderButtonText() {

        FontManager.SFPRO.drawString(text, x / 2 * (x / 2), y, -1);

    }

    public void renderButtonRectangle(int mouseX, int mouseY) {

        animation = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animation, over ? 1110 : 0, Math.max(3.6D, Math.abs((double) animation - 1200)) * 4);

        over = ui.mouseWithinCircle(mouseX, mouseY, x, y, size);

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.push();

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, size, new Color(20, 20, 20).getRGB());


        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, animation, Integer.MIN_VALUE);


        if (over)
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x, y, size, new Color(30, 30, 30).getRGB());


        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.pop();

    }

}
