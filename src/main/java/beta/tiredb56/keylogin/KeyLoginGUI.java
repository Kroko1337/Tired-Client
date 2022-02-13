package beta.tiredb56.keylogin;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.interfaces.IHook;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class KeyLoginGUI implements IHook {

    public enum LOGIN_STATE {
        NOT_LOGGED,
        LOGGED
    }

    public LOGIN_STATE login_state;

    public void renderInterface(double width, double height) {

    }

}
