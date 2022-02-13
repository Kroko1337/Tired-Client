package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Random;

@ModuleAnnotation(name = "HawkGodMode", category = ModuleCategory.MISC, clickG = "Gives you godmode on hawk")
public class HawkGodMode extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        final Random random = new Random();
        if (MC.thePlayer.ticksExisted % 32 == 0) {
            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, Math.toRadians(MC.thePlayer.posY) / (12 - random.nextInt(12)), MC.thePlayer.posZ, MC.thePlayer.ticksExisted % 12 == 0));
        }

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
