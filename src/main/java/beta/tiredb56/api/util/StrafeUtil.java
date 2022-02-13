package beta.tiredb56.api.util;

import beta.tiredb56.event.events.EventStrafe;
import beta.tiredb56.interfaces.IHook;
import net.minecraft.util.MathHelper;

public class StrafeUtil implements IHook {

    public static void customSilentMoveFlying(EventStrafe event, float yaw) {
        final int difference = (int) ((MathHelper.wrapAngleTo180_float(MC.thePlayer.rotationYaw - yaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
        float strafe = event.getStrafe(), forward = event.getForward(), friction = event.getFriction();
        float calcForward = 0.0F;
        float calcStrafe = 0.0F;
        switch (difference) {
            case 0: {
                calcForward = forward;
                calcStrafe = strafe;
                break;
            }
            case 1: {
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            }
            case 2: {
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            }
            case 3: {
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            }
            case 4: {
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            }
            case 5: {
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            }
            case 6: {
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            }
            case 7: {
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
            }
        }

        if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
            calcForward *= 0.5F;
        }

        if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
            calcStrafe *= 0.5F;
        }

        float d = calcStrafe * calcStrafe + calcForward * calcForward;
        if (d >= 1.0E-4F) {
            d = MathHelper.sqrt_float(d);
            if (d < 1.0F) {
                d = 1.0F;
            }

            d = friction / d;
            calcStrafe *= d;
            calcForward *= d;
            final float yawSin = MathHelper.sin((float) ((double) yaw * Math.PI / 180.0D));
            final float yawCos = MathHelper.cos((float) ((double) yaw * Math.PI / 180.0D));
            MC.thePlayer.motionX += calcStrafe * yawCos - calcForward * yawSin;
            MC.thePlayer.motionZ += calcForward * yawCos + calcStrafe * yawSin;
        }

    }

}
