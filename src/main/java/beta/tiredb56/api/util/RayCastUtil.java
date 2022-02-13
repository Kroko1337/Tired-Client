package beta.tiredb56.api.util;

import beta.tiredb56.interfaces.IHook;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;

import java.util.List;

public class RayCastUtil implements IHook {



    public static final Entity raycastEntity(double range, float[] rotations)
    {
        final Entity player = MC.getRenderViewEntity();

        if (player != null && MC.theWorld != null)
        {
            final Vec3 eyeHeight = player.getPositionEyes(MC.timer.renderPartialTicks);

            final Vec3 looks = getVectorForRotation(rotations[0], rotations[1]);
            final Vec3 vec = eyeHeight.addVector(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range);
            final List<Entity> list = MC.theWorld.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range).expand(1, 1, 1), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));

            Entity raycastedEntity = null;

            for (Entity entity : list)
            {
                if (!(entity instanceof EntityLivingBase)) continue;

                final float borderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyeHeight, vec);

                if (axisalignedbb.isVecInside(eyeHeight))
                {
                    if (range >= 0.0D)
                    {
                        raycastedEntity = entity;
                        range = 0.0D;
                    }
                }
                else if (movingobjectposition != null)
                {
                    double distance = eyeHeight.distanceTo(movingobjectposition.hitVec);

                    if (distance < range || range == 0.0D)
                    {

                        if (entity == player.ridingEntity)
                        {
                            if (range == 0.0D)
                            {
                                raycastedEntity = entity;
                            }
                        }
                        else
                        {
                            raycastedEntity = entity;
                            range = distance;
                        }
                    }
                }
            }
            return raycastedEntity;
        }
        return null;
    }

    public static final Vec3 getVectorForRotation(float yaw, float pitch)
    {
        final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
        final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
        final double f2 = -Math.cos(Math.toRadians(-pitch));
        final double f3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }


}
