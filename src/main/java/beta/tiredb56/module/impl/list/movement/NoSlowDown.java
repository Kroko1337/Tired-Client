package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.SlowDownEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleAnnotation(name = "NoSlowDown", category = ModuleCategory.MOVEMENT, clickG = "Disable slowdown while blocking or eating")
public class NoSlowDown extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (MC.thePlayer.onGround && ClientHelper.INSTANCE.moving() && MC.thePlayer.isUsingItem()) {
            assert MC.thePlayer != null;
            MC.thePlayer.motionZ *= 1.7003;
            MC.thePlayer.motionX *= 1.7003;
        }
    }

    @EventTarget
    public void onEvent(SlowDownEvent e) {
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
