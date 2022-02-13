package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.impl.list.combat.KillAura;
import beta.tiredb56.module.impl.list.movement.Flight;
import beta.tiredb56.module.impl.list.movement.Speed;
import beta.tiredb56.module.impl.list.world.ScaffoldWalk;
import beta.tiredb56.notification.NotificationRenderer;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.TiredCore;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.awt.*;

@ModuleAnnotation(name = "FlagDetector", category = ModuleCategory.MISC, clickG = "Disable modules when flagged")
public class FlagDetector extends Module {
    public boolean disable;

    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            if (CheatMain.INSTANCE.moduleManager.findModuleByClass(Speed.class).isState()) {
                disable = true;
                CheatMain.INSTANCE.moduleManager.findModuleByClass(Speed.class).executeMod();
            }
            if (CheatMain.INSTANCE.moduleManager.findModuleByClass(ScaffoldWalk.class).isState()) {
                disable = true;
                CheatMain.INSTANCE.moduleManager.findModuleByClass(ScaffoldWalk.class).executeMod();
            }
            if (CheatMain.INSTANCE.moduleManager.findModuleByClass(Flight.class).isState()) {
                disable = true;
                CheatMain.INSTANCE.moduleManager.findModuleByClass(Flight.class).executeMod();
            }
            if (CheatMain.INSTANCE.moduleManager.findModuleByClass(KillAura.class).isState()) {
                CheatMain.INSTANCE.moduleManager.findModuleByClass(KillAura.class).executeMod();
                disable = true;
            }
        }
        if (disable) {
            alertFlag("Disabled module during flag, if you think its because the bypass, contact: PeeakorX#2753");
            this.disable = false;
        }
    }

    @Override
    public void onState() {

    }

    public void alertFlag(String mod) {
        TiredCore.CORE.notificationRenderer.sendNotification(NotificationRenderer.notifyType.ERROR, mod, "You got flagged. (Flag Detector) - watch ur settings.", Color.RED);
    }

    @Override
    public void onUndo() {

    }
}
