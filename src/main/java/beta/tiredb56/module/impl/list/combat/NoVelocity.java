package beta.tiredb56.module.impl.list.combat;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.event.events.VelocityEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleAnnotation(name = "NoVelocity", category = ModuleCategory.COMBAT, clickG = "Reduces Velocity")

public class NoVelocity extends Module {

    private final ModeSetting modeSetting = new ModeSetting("VelocityMode", this, new String[]{"MatrixBackTP", "MatrixHorizontal", "Reduce", "Hawk"});

    public NumberSetting reduceStrenght = new NumberSetting("reduceStrenght", this, .5, .1, 1, .01, () -> modeSetting.getValue().equalsIgnoreCase("Reduce"));

    private int matrixTicks;

    private boolean didReduceHawk;
    private boolean hawkTicks;

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        boolean didPush;
        switch (modeSetting.getValue()) {
            case "MatrixBackTP":

                if (MC.thePlayer.hurtTime != 0) {
                    matrixTicks++;
                } else {
                    matrixTicks = 0;
                }

                if (matrixTicks > 6) {
                    final float yaw = KillAura.getInstance().isState() ? KillAura.getInstance().serverRotations[0] : MC.thePlayer.rotationYaw;
                    MC.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * .07;
                    MC.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * .07;
                    didPush = true;
                } else {
                    didPush = false;
                }

                if (didPush) {
                    MC.thePlayer.motionY *= (MC.thePlayer.motionY) * .6F;
                }
                break;

            case "MatrixHorizontal": {
                final float yaw = KillAura.getInstance().isState() ? KillAura.getInstance().serverRotations[0] : MC.thePlayer.rotationYaw;
                double stator = 0.02;
                switch (MC.thePlayer.hurtTime) {
                    case 1:
                        stator += 0.1;
                        break;
                    case 2:
                        stator += 0.004;
                        break;
                }
                if (MC.thePlayer.hurtTime != 1 && MC.thePlayer.hurtTime != 2 && MC.thePlayer.hurtTime != 0) {
                    final double x = -Math.sin(yaw) * stator;
                    final double z = Math.cos(yaw) * stator;
                    MC.thePlayer.motionX = x;
                    MC.thePlayer.motionZ = z;

                }
                break;
            }

            case "Hawk":
                if (MC.thePlayer.hurtTime != 0) {
                    MC.thePlayer.motionX *= .6F;
                    MC.thePlayer.motionZ *= .6F;

                }
        }

    }

    @EventTarget
    public void onVelocity(VelocityEvent e) {

        if (modeSetting.getValue().equalsIgnoreCase("Reduce")) {
            if (MC.thePlayer.hurtTime != 0) {
                e.setFlag(true);
                e.setSprint(true);
                e.setMotion(reduceStrenght.getValue());
            }
        }

    }

    @EventTarget
    public void onPacket(PacketEvent e) {


    }

    @Override
    public void onState() {
        matrixTicks = 0;
    }

    @Override
    public void onUndo() {
        matrixTicks = 0;

    }
}
