package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleAnnotation(name = "FastLadder", category = ModuleCategory.MOVEMENT, clickG = "Climb faster on ladders")
public class FastLadder extends Module {

    public int fastLadderTicks = 0;

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        if (!shouldExecuteFastLadder()) return;

        MC.thePlayer.motionY += 0.1F;


    }

    public boolean shouldExecuteFastLadder() {
        return MC.thePlayer.isOnLadder() && MC.thePlayer.hurtTime == 0;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
