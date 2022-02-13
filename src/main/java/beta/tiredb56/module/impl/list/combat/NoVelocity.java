package beta.tiredb56.module.impl.list.combat;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.PacketEvent;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.event.events.VelocityEvent;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@ModuleAnnotation(name = "NoVelocity", category = ModuleCategory.COMBAT, clickG = "Reduces Velocity")

public class NoVelocity extends Module {

    public ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Cancel", "Jump", "Matrix2", "AAC3", "Matrix", "Push", "Intave"});


    @EventTarget
    public void onUpdate(UpdateEvent e) {
        setDesc(mode.getValue());
        switch (mode.getValue()) {
            case "Jump":
                if (MC.thePlayer.hurtTime != 0 && MC.thePlayer.onGround && !MC.thePlayer.isInWater()) {
                    MC.thePlayer.jump();
                }
                break;
            case "AAC3":
                if (MC.thePlayer.hurtTime != 0) {
                    MC.thePlayer.onGround = true;
                }
                break;

            case "Push": {
                if ( MC.thePlayer.hurtTime == 6) {

                    ClientHelper.INSTANCE.doSpeedup(.31);
                }
            }
            break;

            case "Matrix2": {

                double reducer = 0.05F;


                //Reducing.
                switch (MC.thePlayer.hurtTime) {
                    case 1:
                        reducer /= 5;
                        break;
                    case 2:
                        reducer *= 0.02;
                        break;
                    case 3:
                        reducer /= 4;
                        break;
                    default:
                        reducer = 0.016F;
                }


                final boolean killAuraState = KillAura.getInstance().isState();

                final float yaw = (float) Math.toRadians(MC.thePlayer.rotationYaw);

                if (MC.thePlayer.hurtTime != 0 && MC.thePlayer.hurtTime != 6) {

                    MC.thePlayer.motionZ = Math.cos(yaw) * reducer;
                    MC.thePlayer.motionX = -Math.sin(yaw) * reducer;
                }
            }
            break;

            case "Matrix":
                double stator = 0.02;
                switch (MC.thePlayer.hurtTime) {
                    case 1:
                        stator += 0.1;
                        break;
                    case 2:
                        stator += 0.02;
                        break;
                }
                if (MC.thePlayer.hurtTime != 1 && MC.thePlayer.hurtTime != 2 && MC.thePlayer.hurtTime != 0) {
                    float yaw = (float) Math.toRadians(MC.thePlayer.rotationYaw);
                    final double x = -Math.sin(yaw) * stator;
                    final double z = Math.cos(yaw) * stator;
                    MC.thePlayer.motionX = x;
                    MC.thePlayer.motionZ = z;
                }
                break;

            case "Intave":
                boolean airState = false;
                if (MC.thePlayer.hurtTime != 0) {
                    if (MC.thePlayer.getHealth() < 16) {
                        if (!MC.thePlayer.onGround && MC.thePlayer.hurtTime != 0) {
                            MC.thePlayer.setSprinting(MC.thePlayer.ticksExisted % 27 == 0);
                            if (MC.thePlayer.ticksExisted % 27 == 0) {
                                IngameChatLog.INGAME_CHAT_LOG.doLog("Lowered Velocity w. sprint");
                            }
                        }
                    }

                    if (MC.thePlayer.hurtTime != 0 && MC.thePlayer.onGround && MC.thePlayer.ticksExisted % 16 == 0) {
                        IngameChatLog.INGAME_CHAT_LOG.doLog("Lowered Velocity w. jump");
                        MC.thePlayer.jump();

                    }

                }
                if (MC.thePlayer.hurtTime != 0 && !MC.thePlayer.isInWater() && MC.thePlayer.ticksExisted % 65 == 0 && MC.thePlayer.motionY < .32) {
                    airState = true;
                    IngameChatLog.INGAME_CHAT_LOG.doLog("Lowered Velocity w. motion");
                    float yaw = (float) Math.toRadians(MC.thePlayer.rotationYaw);
                    MC.thePlayer.motionX = -Math.sin(yaw) * 0.21;
                    MC.thePlayer.motionZ = Math.cos(yaw) * 0.21;
                    MC.gameSettings.keyBindSprint.pressed = true;
                } else {
                    MC.gameSettings.keyBindSprint.pressed = false;

                }
        }
    }

    @EventTarget
    public void onVelocity(VelocityEvent e) {

    }

    @EventTarget
    public void onPacket(PacketEvent e) {
        if ("Cancel".equals(mode.getValue())) {
            if (e.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) e.getPacket();
                if (velocity.getEntityID() == MC.thePlayer.getEntityId()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        MC.gameSettings.keyBindSprint.pressed = false;
        MC.timer.timerSpeed = 1f;
    }
}
