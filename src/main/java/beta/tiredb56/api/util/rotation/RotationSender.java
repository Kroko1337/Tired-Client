package beta.tiredb56.api.util.rotation;

import beta.tiredb56.api.util.MathUtil;
import beta.tiredb56.api.util.Rotations;
import beta.tiredb56.interfaces.IHook;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RotationSender implements IHook {


    public static float[] doGCD(float yaw, float pitch, float[] prevRots) {
        final float yawDifference = yaw - prevRots[0], pitchDifference = pitch - prevRots[1];
        float f1 = (float) (0.5 * 0.5 * 0.5 * 8.0F);
        float f2 = (float) ((int) (yawDifference * f1 * 5.0F));
        float f3 = (float) ((int) (pitchDifference * f1 * 5.0F));
        return setupRotationFIX(prevRots[0], prevRots[1], f2, f3);
    }

    public static float[] setupRotationFIX(float currentYaw, float currentPitch, float yaw, float pitch) {
        currentYaw = (float) ((double) currentYaw + (double) yaw * 0.15D);
        currentPitch = (float) ((double) currentPitch + (double) pitch * 0.15D);
        return new float[]{currentYaw, currentPitch};
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        final double xDiff = x - MC.thePlayer.posX;
        final double zDiff = z - MC.thePlayer.posZ;
        final double yDiff = y - MC.thePlayer.posY - (double) MC.thePlayer.getEyeHeight();
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        final float pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }


    public static float[] getEntityRotations(final Entity entity, final float[] prevRotations, int randomFactor, boolean predict, boolean bestVec) {


        double x, y, z;

        if (!bestVec) {
            x = entity.posX;
            y = entity.posY + entity.getEyeHeight();
            z = entity.posZ;
        } else {
            final Vec3 bestVecValue = vec(MC.thePlayer.getPositionEyes(MC.timer.renderPartialTicks), entity.getEntityBoundingBox());
            x = bestVecValue.xCoord;
            y = bestVecValue.yCoord;
            z = bestVecValue.zCoord;
        }


        if (predict) {
            boolean sprinting = entity.isSprinting();
            boolean sprintingPlayer = MC.thePlayer.isSprinting();

            float walkingSpeed = 0.10000000149011612f; //https://minecraft.fandom.com/wiki/Sprinting

            float sprintMultiplication = sprinting ? 1.25f : walkingSpeed;
            float sprintMultiplicationPlayer = sprintingPlayer ? 1.25f : walkingSpeed;

            float xMultiplication = (float) ((entity.posX - entity.prevPosX) * sprintMultiplication);
            float zMultiplication = (float) ((entity.posZ - entity.prevPosZ) * sprintMultiplication);

            float xMultiplicationPlayer = (float) ((MC.thePlayer.posX - MC.thePlayer.prevPosX) * sprintMultiplicationPlayer);
            float zMultiplicationPlayer = (float) ((MC.thePlayer.posZ - MC.thePlayer.prevPosZ) * sprintMultiplicationPlayer);


            if (xMultiplication != 0.0f && zMultiplication != 0.0f || xMultiplicationPlayer != 0.0f && zMultiplicationPlayer != 0.0f) {
                x += xMultiplication + xMultiplicationPlayer;
                z += zMultiplication + zMultiplicationPlayer;
            }
        }

        final double xDiff = x - MC.thePlayer.posX;
        final double zDiff = z - MC.thePlayer.posZ;
        final double yDiff = y - MC.thePlayer.posY - (double) MC.thePlayer.getEyeHeight();
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / Math.PI);


        if (MC.gameSettings.keyBindSneak.pressed || MC.gameSettings.keyBindJump.pressed || MC.gameSettings.keyBindBack.pressed || MC.gameSettings.keyBindRight.pressed || MC.gameSettings.keyBindLeft.pressed) {
            yaw += MathHelper.clamp_float((float) MathUtil.getRandom(1, Rotations.yawDifference), 0, 9);
            pitch += MathHelper.clamp_float((float) MathUtil.getRandom(1, Rotations.pitchDifference), 0, 9);
        }

        pitch = MathHelper.clamp_float(pitch, -90, 90);

        return doGCD(yaw, pitch, new float[]{yaw, pitch});
    }

    public static Vec3 vec(Vec3 look, AxisAlignedBB axisAlignedBB) {
        return new Vec3(MathHelper.clamp_double(look.xCoord, axisAlignedBB.minX, axisAlignedBB.maxX), MathHelper.clamp_double(look.yCoord, axisAlignedBB.minY, axisAlignedBB.maxY), MathHelper.clamp_double(look.zCoord, axisAlignedBB.minZ, axisAlignedBB.maxZ));
    }

    public static float readYaw() {

        try {
            if (!(new File("differences/YawDifferences.txt")).exists())
                (new File("differences/YawDifferences.txt")).mkdir();

            if (!(new File("differences/YawDifferences.txt")).exists())
                (new File("differences/YawDifferences.txt")).createNewFile();


            final BufferedReader yawReader = new BufferedReader(new FileReader(("differences/YawDifferences.txt")));

            String line;

            while ((line = yawReader.readLine()) != null) {
                return Float.parseFloat(line);

            }
            yawReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
