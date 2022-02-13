package beta.tiredb56.ui.userinterface.renderers;

import beta.tiredb56.ui.userinterface.UI;
import beta.tiredb56.ui.userinterface.UIAnnotation;
import beta.tiredb56.ui.userinterface.element.ElementHoveringButton;
import beta.tiredb56.ui.userinterface.element.ElementText;

@UIAnnotation
public class UIMainMenu extends UI {

    private final ElementHoveringButton elementHoveringButton;
    private final ElementText elementText;

    public UIMainMenu() {
        elementHoveringButton = new ElementHoveringButton(this, 42, 144, 25,"Test");
        elementText = new ElementText(this, ElementText.FONT.IBMPlexSans, 12, 122, "SussyAmongus");

    }

    @Override
    public void renderUI(int mouseX, int mouseY) {

        update();

        renderShaderBackground();

        elementHoveringButton.renderButtonRectangle(mouseX, mouseY);

        elementHoveringButton.renderButtonText();

       // elementText.renderText();

    }
}
