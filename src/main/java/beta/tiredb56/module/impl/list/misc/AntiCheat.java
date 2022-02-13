package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.Module;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ModuleAnnotation(name = "AntiCheat", category = ModuleCategory.MISC, clickG = "Checks players for cheats")
public class AntiCheat extends Module {


    private double lastDist;
    private boolean lastOnGround;

    private int buffer = 0;
    private double blockSlipperiness = 0.91;
    private double lastHorizontalDistance = 0.0;

    private double lastDeltaY = 0.0d, buffer2 = 0.0d;

    private int airticks = 0;

    private double lastDeltaY2 = 0.0d, buffer3 = 0.0d;
    private int ticks = 0;


    @EventTarget
    public void onUpdate(UpdateEvent event) {
        for (Entity e : MC.theWorld.loadedEntityList) {
            if (!(e instanceof EntityPlayer)) return;

            if (e == MC.thePlayer && ((EntityPlayerSP) e).hurtTime != 0) return;

            double xDifference = Math.abs(e.posX - e.prevPosX);
            double zDifference = Math.abs(e.posZ - e.prevPosZ);

            double yDifference = Math.abs(e.posY - e.prevPosY);

            double blockSlipperiness = this.blockSlipperiness;
            double attributeSpeed = 1.d;

            final int modifierJump = getPotionLevel((EntityPlayer) e, Potion.jump);
            attributeSpeed += getPotionLevel((EntityPlayer) e, Potion.moveSpeed) * (float) 0.2 * attributeSpeed;
            attributeSpeed += getPotionLevel((EntityPlayer) e, Potion.moveSlowdown) * (float) -.15 * attributeSpeed;

            if (e.onGround) {
                blockSlipperiness *= 0.91f;

                if (e.isSprinting()) attributeSpeed *= 1.3;
                attributeSpeed *= 0.16277136 / Math.pow(blockSlipperiness, 3);
                if (yDifference > 0.4199 + modifierJump * 0.1 && e.isSprinting()) {
                    attributeSpeed += 0.2;
                }
            } else {
                attributeSpeed = e.isSprinting() ? 0.026 : 0.02;
                blockSlipperiness = 0.91f;
            }

            final double horizontalDistance = magnitude(xDifference, zDifference);
            final double movementSpeed = (horizontalDistance - lastHorizontalDistance) / attributeSpeed;

            if (movementSpeed > 1.0) {
                buffer = Math.min(500, buffer + 10);

                if (buffer > 40) {
                    IngameChatLog.INGAME_CHAT_LOG.doLog(" §c" + e.getName() + " §7 Flagged Speed (B)");

                    buffer /= 2;
                }
            } else {
                buffer = Math.max(buffer - 1, 0);
            }


            final double distanceBetween = (xDifference * xDifference) + (zDifference * zDifference);


            double lastDist = this.lastDist;
            this.lastDist = distanceBetween;

            boolean onGround = e.onGround;
            boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = onGround;

            float friction = 0.91F;
            double shifted = lastDist * friction;
            double equalness = distanceBetween - shifted;
            double scaled = equalness * 138;

            // Update previous values
            this.blockSlipperiness = friction;

            this.lastHorizontalDistance = horizontalDistance * blockSlipperiness;


            if (!onGround && !lastOnGround) {
                if (scaled >= 1.0) {
                    IngameChatLog.INGAME_CHAT_LOG.doLog(" §c" + e.getName() + " §7 Flagged Speed (A)");
                }
            }
            final double estimation = (lastDeltaY - 0.08) * 0.9800000190734863;

            double deltaY = Math.abs(e.posY - e.prevPosY);

            final double acceleration = Math.abs(deltaY - lastDeltaY2);

            if (!e.onGround) {
                ++ticks;

                if (ticks > 6 && horizontalDistance > 0.1 && (deltaY == 0.0 || acceleration == 0.0)) {
                    buffer3 += 0.25;

                    if (buffer3 > 0.75)
                        IngameChatLog.INGAME_CHAT_LOG.doLog(" §c" + e.getName() + " §7 Flagged Motion (B)");
                } else {
                    buffer3 = Math.max(buffer3 - 0.12, 0.0);
                }
            } else {
                buffer3 = 0;
                ticks = 0;
            }

            this.lastDeltaY2 = deltaY;


            final boolean touchingAir = !e.onGround;

            final boolean resetting = Math.abs(deltaY) + 0.0980000019 < 0.05;

            if (resetting) return;

            if (touchingAir) {
                ++ticks;

                if (ticks > 5 && Math.abs(estimation - deltaY) > 0.01) {
                    buffer2 += 1.5;
                }
            } else {
                ticks = 0;
            }

            this.lastDeltaY = deltaY;
        }
    }


    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    public static double magnitude(final double... points) {
        double sum = 0.0;

        for (final double point : points) {
            sum += point * point;
        }

        return Math.sqrt(sum);
    }

    public int getPotionLevel(final EntityPlayer player, final Potion effect) {
        final int effectId = effect.getId();

        if (!player.isPotionActive(effect.getId())) return 0;

        return player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getPotionID() == effectId).map(PotionEffect::getAmplifier).findAny().orElse(0) + 1;
    }

    public double getBaseMoveSpeed(EntityPlayer e) {
        double baseSpeed = 0.2875;

        if (e.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1 + .2 * (e.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        return baseSpeed;
    }

    public float getSpeed(EntityLivingBase e) {
        return (float) Math.sqrt((e.posX - e.prevPosX) * (e.posX - e.prevPosX) + (e.posZ - e.prevPosZ) * (e.posZ - e.prevPosZ));
    }

    public static <T> boolean hasDuplicate(Iterable<T> all) {
        Set<T> set = new HashSet<>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (T each : all) if (!set.add(each)) return true;
        return false;
    }

}
