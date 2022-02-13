package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventPreMotion;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.units.qual.A;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.status.client.C01PacketPing;

import java.util.ArrayList;

@ModuleAnnotation(name = "Debug", category = ModuleCategory.MISC)
public class Debug extends Module {

    private ArrayList<Packet> packets = new ArrayList<>();

    @EventTarget
    public void onPre(EventPreMotion e) {

    }

    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof C01PacketPing) {
            if (MC.thePlayer.ticksExisted % 52 == 0) {
                e.setCancelled(true);
                packets.add(e.getPacket());
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        for (Packet<?> packet : packets) {
            if (MC.thePlayer.ticksExisted % 52 == 0) {
                sendPacket(packet);
            }
        }

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        MC.timer.timerSpeed = 1F;
    }
}
