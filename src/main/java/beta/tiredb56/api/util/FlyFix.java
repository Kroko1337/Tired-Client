package beta.tiredb56.api.util;

import beta.tiredb56.interfaces.IHook;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public final class FlyFix implements IHook {

    private static double calculateGround() {

        AxisAlignedBB playerBoundingBox = MC.thePlayer.getEntityBoundingBox();
        double blockHeight = 1.0D;

        for (double ground = MC.thePlayer.posY; ground > 0.0D; ground -= blockHeight) {
            AxisAlignedBB customBox = new AxisAlignedBB(playerBoundingBox.maxX, ground + blockHeight, playerBoundingBox.maxZ, playerBoundingBox.minX, ground, playerBoundingBox.minZ);
            if (MC.theWorld.checkBlockCollision(customBox)) {
                if (blockHeight <= 0.05D) {
                    return ground + blockHeight;
                }

                ground += blockHeight;
                blockHeight = 0.05D;
            }
        }

        return 0.0D;
    }

    public static void handleVanillaKickBypass() {
        double ground = calculateGround();
        for (double posY = MC.thePlayer.posY; posY > ground; posY -= 8.0D) {

            MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, posY, MC.thePlayer.posZ, true));
            if (posY - 8.0D < ground) {
                break;
            }
        }
        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, ground, MC.thePlayer.posZ, true));
        for (double posY = ground; posY < MC.thePlayer.posY; posY += 8.0D) {
            MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, posY, MC.thePlayer.posZ, true));
            if (posY + 8.0D > IHook.MC.thePlayer.posY) {
                break;
            }
        }

        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY, MC.thePlayer.posZ, true));
    }
}

