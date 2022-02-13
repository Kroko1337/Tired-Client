package beta.tiredb56.module;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventManager;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.impl.HudModule;
import beta.tiredb56.module.impl.list.visual.Notifications;
import beta.tiredb56.notification.newnotifications.NotifyManager;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Module implements IHook {

	public final String name = getClass().getAnnotation(ModuleAnnotation.class).name();

	public final String clickGUIText = getClass().getAnnotation(ModuleAnnotation.class).clickG();

	public String desc = getClass().getAnnotation(ModuleAnnotation.class).desc();

	public int key = getClass().getAnnotation(ModuleAnnotation.class).key();

	public boolean allowRender = true;

	public final ModuleCategory moduleCategory = getClass().getAnnotation(ModuleAnnotation.class).category();

	public final ScaledResolution sr = new ScaledResolution(MC);

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if (HudModule.getInstance().description.getValue()) {
			this.desc = desc;
		} else {
			this.desc = "";
		}
	}

	public boolean state = false;

	public float posX;

	public abstract void onState();

	public abstract void onUndo();

	public void executeMod() {
		if (!state) {
			doEvent();
			setState(true);
			if (CheatMain.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
				NotifyManager.sendClientMessage("ModuleManager", "Toggled: " + name);
			}
			return;
		}
		if (CheatMain.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
			NotifyManager.sendClientMessage("ModuleManager", "Disabled: " + name);
		}
		undoEvent();
		setState(false);
	}

	public void unableModule() {
		if (state) {
			undoEvent();
			state = false;
			setState(false);
		}
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public String getNameWithSuffix() {
		return name + " " + desc;
	}

	public int getKey() {
		return key;
	}

	public ModuleCategory getModuleCategory() {
		return moduleCategory;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void doEvent() {
		EventManager.register(this);
		this.onState();
	}

	public void undoEvent() {
		EventManager.unregister(this);
		this.onUndo();
	}

}
