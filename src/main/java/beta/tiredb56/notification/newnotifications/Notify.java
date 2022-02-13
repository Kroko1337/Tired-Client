package beta.tiredb56.notification.newnotifications;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.extension.processors.extensions.generally.RenderProcessor;
import beta.tiredb56.api.util.TimerUtil;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.interfaces.IHook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

@AllArgsConstructor
public class Notify implements IHook {

	private String notifyMessage;
	private final TimerUtil timerUtil;
	@Getter
	private double width, height, yAxis, lastYaxis;

	private long stayTime;
	private double animation;

	private String title;

	public Notify(String title, String notifyMessage) {
		this.notifyMessage = notifyMessage;
		this.title = title;
		(this.timerUtil = new TimerUtil()).doReset();
		this.width = FHook.fontRenderer2.getStringWidth(notifyMessage) + 55;
		this.height = 30.0;
		this.animation = this.width;
		this.stayTime = 2000L;
		this.yAxis = -1.0;
	}

	public void renderNotification(double gettingY, double lastYaxis, boolean rect) {
		this.lastYaxis = lastYaxis;
		this.animation = AnimationUtil.getAnimationState(this.animation, this.finished() ? this.width : 0.0, Math.max(this.finished() ? 200 : 30, Math.abs(this.animation - (this.finished() ? this.width : 0.0)) * 5.0));
		if (yAxis == -1) {
			this.yAxis = gettingY;
		} else {
			this.yAxis = AnimationUtil.getAnimationState(yAxis, gettingY, 120);
		}
		final ScaledResolution res = new ScaledResolution(MC);

		final int x1 = (int) (res.getScaledWidth() - this.width + this.animation);
		final int x2 = (int) (res.getScaledWidth() + this.animation);
		final int y1 = (int) this.yAxis;
		final int y2 = (int) (y1 + this.height);

		//RenderProcessor.drawGradientSideways(x1, y1 - 1, x2, y2 , ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

		if (rect) {
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(x1, y1, x2, y2,  new Color(20, 20, 20, 120).getRGB());
		}

		FontManager.SFPRO.drawString(this.title, (float) (x1) + 4, (float) (y1 + this.height / 3.5) - 3, -1);
		FHook.fontRenderer.drawString(this.notifyMessage, (float) (x1) + 4.0f, (float) (y1 + this.height / 3.5) + 9, -1);

	}

	public boolean finished() {
		return timerUtil.reachedTime(stayTime) && yAxis == lastYaxis;
	}

	public boolean shouldDelete() {
		return finished() && animation >= width;
	}

}
