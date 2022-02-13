package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.util.TimerUtil;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventTick;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;
import beta.tiredb56.module.Module;
import net.minecraft.util.AxisAlignedBB;

@ModuleAnnotation(name = "Step", category = ModuleCategory.MOVEMENT)
public class Step extends Module {

    public static double moveSpeed;
    public TimerUtil timer = new TimerUtil();
    public static boolean enabled;
    private boolean resetNextTick;
    private double preY;

    @EventTarget
    public void onTick(EventTick e) {
        
    }
    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (allowStep()) {
            MC.thePlayer.jump();
            ClientHelper.INSTANCE.doSpeedup(0.33);
        }
    }

    private boolean allowStep() {
        return MC.thePlayer.isCollidedHorizontally && MC.thePlayer.onGround;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
    }
}
