package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.ArrayList;

@ModuleAnnotation(name = "Blink", category = ModuleCategory.MOVEMENT)
public class NCPBlink extends Module {

    private final ArrayList<Packet<?>> packetQueue = new ArrayList<>();

    @EventTarget
    public void onPacket(PacketEvent eventPacket) {
        if (eventPacket != null) {
            final Packet<?> packet = eventPacket.getPacket();
            if (packet instanceof C03PacketPlayer) {
                eventPacket.setCancelled(true);
                packetQueue.add(packet);

            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

    }


    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        if (!packetQueue.isEmpty()) {
            for (Packet<?> packet : packetQueue)
                MC.getNetHandler().addToSendQueue(packet);
        }
        packetQueue.clear();
    }
}
