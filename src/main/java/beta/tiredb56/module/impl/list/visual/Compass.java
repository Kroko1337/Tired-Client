package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render2DEvent;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.TiredCore;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(name = "Compass", category = ModuleCategory.RENDER, clickG = "Render Compass in your screen")
public class Compass extends Module {

	private final double width, height;
	private final List<Direction> directions;
	private float animation;

	public Compass() {
		this.width = 120;
		this.height = 10;
		this.directions = new ArrayList<>();

		directions.add(new Direction("N", 1));
		directions.add(new Direction("195", 2));
		directions.add(new Direction("210", 2));
		directions.add(new Direction("NE", 3));
		directions.add(new Direction("240", 2));
		directions.add(new Direction("255", 2));
		directions.add(new Direction("E", 1));
		directions.add(new Direction("285", 2));
		directions.add(new Direction("300", 2));
		directions.add(new Direction("SE", 3));
		directions.add(new Direction("330", 2));
		directions.add(new Direction("345", 2));
		directions.add(new Direction("S", 1));
		directions.add(new Direction("15", 2));
		directions.add(new Direction("30", 2));
		directions.add(new Direction("SW", 3));
		directions.add(new Direction("60", 2));
		directions.add(new Direction("75", 2));
		directions.add(new Direction("W", 1));
		directions.add(new Direction("105", 2));
		directions.add(new Direction("120", 2));
		directions.add(new Direction("NW", 3));
		directions.add(new Direction("150", 2));
		directions.add(new Direction("165", 2));
	}

	@EventTarget
	public void onRender(Render2DEvent event) {
		animation = (float) AnimationUtil.getAnimationState(animation, MC.gameSettings.keyBindCommand.isKeyDown() && (!MC.isIntegratedServerRunning() ||MC.thePlayer.sendQueue.getPlayerInfoMap().size() > 1) ? 140 : !TiredCore.CORE.notificationRenderer.notifications.isEmpty() && CheatMain.INSTANCE.moduleManager.moduleBy(Notifications.class).isState() ? 67 : 30, 122);


		ScaledResolution scaledResolution = new ScaledResolution(MC);
		final double screenCenter = scaledResolution.getScaledWidth() / 2;
		final double yaw = (MC.thePlayer.rotationYaw % 360) * 2 + 360 * 3;
		GlStateManager.pushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		Scissoring.SCISSORING.scissorOtherWay(screenCenter - 100, 0, 212, 485.0);
		double count = 0;
		for (final Direction direction : directions) {
			final double position = screenCenter + (count * 30) - yaw;
			double resetPosition;

			if (direction.getType() == 1) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 2) {
				resetPosition = position - FontManager.confortaa.getStringWidth(direction.getDirection()) / 2;
				FontManager.confortaa.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 3) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			}

			//FontManager.bebasF.drawStringWithShadow("" + yaw, screenCenter - FontManager.bebasF.getStringWidth("" + MC.thePlayer.rotationYawHead), yPos - 18, color(scaledResolution, -1));
			count++;
		}
		for (final Direction direction : directions) {
			final double position = screenCenter + (count * 30) - yaw;
			double resetPosition = 0;

			if (direction.getType() == 1) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 2) {
				resetPosition = position - FontManager.confortaa.getStringWidth(direction.getDirection()) / 2;
				FontManager.confortaa.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 3) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			}

			count++;
		}
		for (final Direction direction : directions) {
			final double position = screenCenter + (count * 30) - yaw;
			double resetPosition = 0;

			if (direction.getType() == 1) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 2) {
				resetPosition = position - FontManager.confortaa.getStringWidth(direction.getDirection()) / 2.5;
				FontManager.confortaa.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			} else if (direction.getType() == 3) {
				resetPosition = position - FontManager.bebasF.getStringWidth(direction.getDirection()) / 2;
				FontManager.bebasF.drawStringWithShadow(direction.getDirection(), resetPosition, animation, color(scaledResolution, resetPosition));
			}
			count++;
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GlStateManager.popMatrix();

	}

	public Color color(ScaledResolution sr, double offset) {
		final double offs = 255 - Math.abs(sr.getScaledWidth() / 2 - offset) * 1.8;
		final Color c = beta.tiredb56.api.util.renderapi.ColorUtil.cleanColorCol(Color.WHITE.getRGB(), (float) .8);
		return c;
	}

	@Override
	public void onState() {

	}

	@Override
	public void onUndo() {

	}

	public class Direction {

		private final String direction;
		private final int type;

		public Direction(String direction, int type) {
			this.direction = direction;
			this.type = type;
		}

		public String getDirection() {
			return direction;
		}

		public int getType() {
			return type;
		}
	}

}
