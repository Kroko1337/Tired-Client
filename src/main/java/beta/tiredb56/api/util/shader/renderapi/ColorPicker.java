package beta.tiredb56.api.util.renderapi;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;

public class ColorPicker implements IHook {
	public ColorPicker() {
	}

	public void drawColorPicker(int mouseX, int mouseY, float x, float y, ColorPickerSetting s) throws IOException {
		int colorSize = 78;
		int pixel = (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) ? 20 : 15;
		int xC = (int) x;
		int yC = (int) y;

		int j;
		for (j = 0; j < 360; ++j) {
			Gui.drawRect(xC + colorSize - 20, yC + j * colorSize / 360, (double) (xC + colorSize + 10), (int) (yC + j * colorSize / 360 + 2), Color.HSBtoRGB(1.0F - (float) j / 360.0F, 1.0F, 1.0F));
			if (s.HUE == 1.0F - (float) j / 360.0F && !Mouse.isButtonDown(0)) {
				new Color((int) s.HUE);
				Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((float) (xC + colorSize + 5), (float) (yC + j * colorSize / 360) - 4.0F, 3.0F, new Color(241, 241, 241).getRGB());
			}

			if (Mouse.isButtonDown(0) && mouseX >= xC + colorSize + 5 && mouseX <= xC + colorSize + 10 && mouseY >= yC + j * colorSize / 360 && mouseY <= yC + 360 * colorSize / 360 + 1) {
				s.HUE = 1.0F - (float) j / 360.0F;
			}
		}

		int i;

		//Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPicture(new ResourceLocation("assets/minecraft/client/img_1.png"), (int) x, (int) y, 50, 20);

		for (i = xC; i < xC + colorSize; i += colorSize / pixel) {
			for (j = yC; j < yC + colorSize; j += colorSize / pixel) {
				Gui.drawRect((double) i, j, (double) ((float) (i + colorSize / pixel)), (int) ((float) (j + colorSize / pixel) + 1.0F), Color.HSBtoRGB(s.HUE, (float) (i - xC) / (float) colorSize, 1.0F - (float) (j - yC) / (float) colorSize));
				if (Mouse.isButtonDown(0) && mouseX >= i && mouseY >= j && (double) ((float) mouseX) <= (double) ((float) (i + colorSize / pixel)) && (float) mouseY <= (float) (j + colorSize / pixel) + 1.0F) {
					s.ColorPickerC = new Color(Color.HSBtoRGB(s.getHUE(), (float) (i - xC) / (float) colorSize, 1.0F - (float) (j - yC) / (float) colorSize));
				}
			}
		}

		for (i = xC; i < xC + colorSize; i += colorSize / pixel) {
			for (j = yC; j < yC + colorSize; j += colorSize / pixel) {
				if (s.getColorPickerColor2().getRGB() == Color.HSBtoRGB(s.getHUE(), (float) (i - xC) / (float) colorSize, 1.0F - (float) (j - yC) / (float) colorSize)) {
					Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((float) (i + colorSize / pixel), (float) (j + colorSize / pixel), 4.0F, Color.white.getRGB());
					Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle((float) (i + colorSize / pixel), (float) (j + colorSize / pixel), 3.0F, s.getColorPickerColor().getRGB());
				}
			}
		}

	}
}
