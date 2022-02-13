package beta.tiredb56.module.impl.list.combat;


import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleAnnotation(name = "Criticals", category = ModuleCategory.COMBAT)
public class Criticals extends Module {

    @EventTarget
    public void onCrit(UpdateEvent e) {
        if (KillAura.getInstance().isState() && KillAura.getInstance().getCurrentEntity() != null) {
         //   sendPacketUnlogged(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 0.11, MC.thePlayer.posZ, false));
       //     sendPacketUnlogged(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 0.1100013579, MC.thePlayer.posZ, false));
         //   sendPacketUnlogged(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 0.1100013579, MC.thePlayer.posZ, false));
        }
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
