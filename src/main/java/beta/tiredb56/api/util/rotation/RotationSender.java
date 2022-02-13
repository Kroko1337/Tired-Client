package beta.tiredb56.api.util.rotation;


import beta.tiredb56.api.util.MathUtil;
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

    private static double heuristicsX, heuristicsY, heuristicsZ;

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

    public static float[] getEntityRotations(final Entity entity, boolean predict, boolean bestVec, boolean heuristics) {


        double x, y, z;

        if (!bestVec || heuristics) {
            x = entity.posX;
            y = entity.posY + entity.getEyeHeight();
            z = entity.posZ;
        } else {
            final Vec3 bestVecValue = vec(MC.thePlayer.getPositionEyes(MC.timer.renderPartialTicks), entity.getEntityBoundingBox());
            x = bestVecValue.xCoord;
            y = bestVecValue.yCoord;
            z = bestVecValue.zCoord;
        }

        final double minX = entity.boundingBox.minX - entity.posX;
        final double maxX = entity.boundingBox.maxX - entity.posX;
        final double minY = entity.boundingBox.minY - entity.posY;
        final double maxY = entity.boundingBox.maxY - entity.posY;
        final double minZ = entity.boundingBox.minZ - entity.posZ;
        final double maxZ = entity.boundingBox.maxZ - entity.posZ;

        if (heuristics) {

            heuristicsX = getRandomSin(minX, maxX, 400.0D) + Math.random() / 350.0D;
            heuristicsY = getRandomSin(minY, maxY, 900.0D) + Math.random() / 350.0D - 1.4;
            heuristicsZ = getRandomSin(minZ, maxZ, 300.0D) + Math.random() / 350.0D ;
        } else {
            heuristicsX = 0;
            heuristicsY = 0;
            heuristicsZ = 0;
        }

        x += heuristicsX;
        y += heuristicsY;
        z += heuristicsZ;


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


        pitch = MathHelper.clamp_float(pitch, -90, 90);

        return doGCD(yaw, pitch, new float[]{yaw, pitch});
    }

    public static double getRandomSin(double min, double max, double timeFactor) {
        double random = Math.sin((double)System.currentTimeMillis() / timeFactor) * (max - min);
        if (random < 0.0D) {
            random = Math.abs(random);
        }

        return random + min;
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
