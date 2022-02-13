package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventRenderModel;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@ModuleAnnotation(name = "Chams", category = ModuleCategory.RENDER, clickG = "See players through walls")
public class Chams extends Module {

	public ColorPickerSetting chamsColor = new ColorPickerSetting("chamsColor", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

	@EventTarget
	public void onRender(EventRenderModel eventRenderModel) {

	}


	private boolean isValid(Entity entity) {
		return entity != MC.thePlayer && entity.getEntityId() != -1488 && isValidType(entity) && (!entity.isInvisible() && (entity instanceof EntityLivingBase && (!(((EntityLivingBase) entity).getHealth() <= 0 || entity.isDead))));
	}

	private boolean isValidType(Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void onState() {

	}
	@Override
	public void onUndo() {

	}
}
