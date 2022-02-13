package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@ModuleAnnotation(name = "WorldTime", category = ModuleCategory.MISC, clickG = "Changes world time")
public class WorldTime extends Module {

    public NumberSetting time = new NumberSetting("Time", this, -1000, -1000, 50000, 1);

    @EventTarget
    public void onTime(PacketEvent e) {
        if (e.getPacket() instanceof S03PacketTimeUpdate) {
            e.setCancelled(true);

        }
    }

    @EventTarget
    public void onUpdate() {
        MC.theWorld.setWorldTime(time.getValueInt());
    }

    @Override
    public void onState() {
        MC.theWorld.setWorldTime(time.getValueInt());
    }

    @Override
    public void onUndo() {

    }
}
