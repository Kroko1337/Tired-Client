package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

@ModuleAnnotation(name = "NoFall", category = ModuleCategory.MOVEMENT, clickG = "prevents fallDamage")
public class NoFall extends Module {

    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            if (shouldExecuteNoFall()) {
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (!MC.thePlayer.onGround && MC.thePlayer.fallDistance > 1.2) {
            MC.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
         //   MC.thePlayer.motionY -= 1;
        }
    }

    public boolean shouldExecuteNoFall() {
        return MC.thePlayer.fallDistance > 2F;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
