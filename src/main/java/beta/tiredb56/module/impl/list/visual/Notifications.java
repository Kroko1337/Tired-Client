package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render2DEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;

@ModuleAnnotation(name = "Notifications", category = ModuleCategory.RENDER)
public class Notifications extends Module {



	@EventTarget
	public void onRender(Render2DEvent e) {
	}

	@Override
	public void onState() {

	}

	@Override
	public void onUndo() {

	}
}
