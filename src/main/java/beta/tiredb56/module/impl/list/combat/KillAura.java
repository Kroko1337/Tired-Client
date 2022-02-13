package beta.tiredb56.module.impl.list.combat;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.logger.impl.IngameChatLog;
import beta.tiredb56.api.util.*;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.api.util.rotation.RotationSender;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.*;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import lombok.Getter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@ModuleAnnotation(name = "KillAura", key = Keyboard.KEY_NONE, category = ModuleCategory.COMBAT, clickG = "Attack players around you!")
public class KillAura extends Module {

    public float[] prevRotation = new float[2];

    public float scaleAnimation;

    public Entity tickEntity;

    private float animationX;

    @Getter
    private Entity currentEntity;
    @Getter
    private final TimerUtil timerUtil = new TimerUtil();

    @Getter
    private final TimerUtil timerUtil2 = new TimerUtil();

    private int stabCount = 0;

    public float[] animationsPrev = new float[3];

    private boolean executed = false;

    public float[] animations = new float[3];

    private boolean isAutoBlocking = false;

    public float alpha;

    private String distance, toString;

    public float[] lastRotations;

    public NumberSetting smoothing = new NumberSetting("Smoothing", this, 30, 1, 200, 1);
    public float[] serverRotations = new float[2];
    private float[] serverAngles = new float[2];
    public NumberSetting minCPS = new NumberSetting("minCPS", this, 12, 1, 20, 1);
    public NumberSetting maxCPS = new NumberSetting("maxCPS", this, 12, 1, 20, 1);
    public NumberSetting preRange = new NumberSetting("preRange", this, 1, .1, 6, .1);
    public NumberSetting range = new NumberSetting("Range", this, 3, .1, 6, .1);
    public BooleanSetting rotations = new BooleanSetting("Rotations", this, true);
    public BooleanSetting targetHUD = new BooleanSetting("targetHUD", this, true);
    public BooleanSetting autoBlock = new BooleanSetting("autoBlock", this, true);
    public BooleanSetting randomRotations = new BooleanSetting("randomRotations", this, true);
    public BooleanSetting UUIDCheck = new BooleanSetting("UUIDCheck", this, true);
    public BooleanSetting bestBlock = new BooleanSetting("bestBlock", this, true, () -> autoBlock.getValue());
    public BooleanSetting NewHit = new BooleanSetting("1.9Hitting", this, true);
    public BooleanSetting crack = new BooleanSetting("crack", this, true);
    public BooleanSetting lockView = new BooleanSetting("lockView", this, true, () -> rotations.getValue());
    public BooleanSetting rayCast = new BooleanSetting("rayCast", this, true);
    public BooleanSetting movementFix = new BooleanSetting("movementFix", this, true, () -> rotations.getValue());
    public BooleanSetting stab = new BooleanSetting("stab", this, true);
    public BooleanSetting renderRotations = new BooleanSetting("RenderRotations", this, true, () -> rotations.getValue());
    public BooleanSetting predict = new BooleanSetting("predict", this, true, () -> rotations.getValue());
    public BooleanSetting bestVec = new BooleanSetting("bestVec", this, true, () -> rotations.getValue());
    public BooleanSetting checkWalls = new BooleanSetting("Walls", this, true);
    public BooleanSetting players = new BooleanSetting("Players", this, true);
    public BooleanSetting villagers = new BooleanSetting("Villagers", this, true);
    public ModeSetting autoBlockMode = new ModeSetting("AutoBlockMode", this, new String[]{"Legit", "Full", "Stab", "Fake"}, () -> autoBlock.getValue());
    public ModeSetting raycastMode = new ModeSetting("raycastMode", this, new String[]{"ClickMouse", "MC"}, () -> rayCast.getValue());
    public ModeSetting targetHudMode = new ModeSetting("targetHudMode", this, new String[]{"Tired1", "Rounded"}, () -> targetHUD.getValue());
    public ModeSetting randomMode = new ModeSetting("randomMode", this, new String[]{"Heuristics", "Math.Random"}, () -> randomRotations.getValue());
    public BooleanSetting mobs = new BooleanSetting("Mobs", this, true);
    public BooleanSetting animals = new BooleanSetting("Animals", this, true);
    public BooleanSetting invisibles = new BooleanSetting("Invisibles", this, true);
    public BooleanSetting rotationESP = new BooleanSetting("rotationESP", this, true);
    public BooleanSetting noSprint = new BooleanSetting("noSprint", this, true);
    public BooleanSetting velocityReducement = new BooleanSetting("velocityReducement", this, true);
    public BooleanSetting aliveCheck = new BooleanSetting("AliveCheck", this, true);
    public BooleanSetting tickAttack = new BooleanSetting("tickAttack", this, true);

    public BooleanSetting absoluteRot = new BooleanSetting("absoluteRot", this, true);
    private final ArrayList<Entity> fakePlayers = new ArrayList<>();

    private final ArrayList<UUID> fakes = new ArrayList<>();
    private final ArrayList<UUID> tooProof = new ArrayList<>();
    private final ArrayList<Entity> notDuplicates = new ArrayList<>();

    public KillAura() {
        this.lastRotations = new float[]{0.0f, 0.0f};
    }

    //aus minecraft src
    @EventTarget
    public void onPacket(PacketEvent e) {

        if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
            final S0CPacketSpawnPlayer cPacketSpawnPlayer = (S0CPacketSpawnPlayer) e.getPacket();
            final MojangUtil mojangUtil = MojangUtil.getInstance();
            try {
                if (!mojangUtil.existUUID(cPacketSpawnPlayer.getPlayer().toString())) {
                    fakes.add(cPacketSpawnPlayer.getPlayer());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getPacket() instanceof S14PacketEntity) {
            final S14PacketEntity packetEntity = (S14PacketEntity) e.getPacket();
            if (rotations.getValue()) {
                if (getCurrentEntity() != null && absoluteRot.getValue()) {
                    Rotations.server_yaw = packetEntity.func_149060_h() ? (float) (packetEntity.func_149066_f() * 360) / 256.0F : Rotations.server_yaw;
                    Rotations.server_pitch = packetEntity.func_149060_h() ? (float) (packetEntity.func_149063_g() * 360) / 256.0F : Rotations.server_pitch;
                }
            }
        }
    }

    @EventTarget
    public void onAttack(AttackingEvent e) {
        if (tickAttack.getValue()) {
            for (int j = 0; j < MC.timer.elapsedTicks; ++j) {
                if (!NewHit.getValue()) {
                    if (getTimerUtil().reachedTime((long) (1000.0 / Extension.EXTENSION.getBlatantProcessor().cpsDrop.getNeededClicks(minCPS.getValueInt(), maxCPS.getValueInt())))) {
                        if (!isAutoBlocking) {
                            doAttack();
                        }
                        getTimerUtil().doReset();
                    }
                } else {
                    if (MC.thePlayer.getCurrentEquippedItem().getItem() == null) {
                        return;
                    }

                    Item currentHolding = MC.thePlayer.getHeldItem().getItem();

                    double delay = 0;

                    if (!(currentHolding instanceof ItemSword) && !(currentHolding instanceof ItemAxe)) {
                        delay = 1.4;
                    }

                    if (currentHolding instanceof ItemSword) {
                        delay = 1.6;
                    }

                    if (currentHolding instanceof ItemAxe) {
                        delay = 1.3;
                    }

                    if (timerUtil.reachedTime((long) (1000.0 / delay))) {
                        doAttack();
                        timerUtil.doReset();
                    }
                }
            }
        } else {

            if (!NewHit.getValue()) {
                if (getTimerUtil().reachedTime((long) (1000.0 / Extension.EXTENSION.getBlatantProcessor().cpsDrop.getNeededClicks(minCPS.getValueInt(), maxCPS.getValueInt())))) {
                    if (!isAutoBlocking) {
                        doAttack();
                    }
                    getTimerUtil().doReset();
                }
            } else {
                if (MC.thePlayer.getCurrentEquippedItem().getItem() == null) {
                    return;
                }

                Item currentHolding = MC.thePlayer.getHeldItem().getItem();

                double delay = 0;

                if (!(currentHolding instanceof ItemSword) && !(currentHolding instanceof ItemAxe)) {
                    delay = 1.4;
                }

                if (currentHolding instanceof ItemSword) {
                    delay = 1.6;
                }

                if (currentHolding instanceof ItemAxe) {
                    delay = 1.3;
                }

                if (timerUtil.reachedTime((long) (1000.0 / delay))) {
                    doAttack();
                    timerUtil.doReset();
                }
            }
        }
    }

    private void checkPlayers() {
        if (!tooProof.isEmpty()) {
            boolean finished = true;
            for (UUID uuid : tooProof) {
                final EntityPlayer entityPlayer = getWorld().getPlayerEntityByUUID(uuid);
                if (entityPlayer != null) {
                    if (getPlayer().searchPlayers(entityPlayer.getName()).size() <= 1) {
                        notDuplicates.add(entityPlayer);
                    }
                } else {
                    finished = false;
                }
            }
            if (finished)
                tooProof.clear();
        }

        if (!fakes.isEmpty()) {
            boolean finished = true;
            for (UUID uuid : fakes) {
                final EntityPlayer entityPlayer = getWorld().getPlayerEntityByUUID(uuid);
                if (entityPlayer != null) {
                    if (!fakePlayers.contains(entityPlayer)) {
                        fakePlayers.add(entityPlayer);
                    }
                } else {
                    finished = false;
                }
            }
            if (finished)
                fakes.clear();
        }
    }

    @EventTarget
    public void onRotation(RotationEvent e) {


        prevRotation[0] = Rotations.server_yaw;
        prevRotation[1] = Rotations.server_pitch;


        lastRotations[0] = serverAngles[0];
        lastRotations[1] = serverAngles[1];

        serverRotations[0] = serverAngles[0];
        serverRotations[1] = serverAngles[1];

        float yawGCD, pitchGCD;



        float yaw = serverAngles[0];
        float pitch = serverAngles[1];
        final float gcd = MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float a2 = gcd * gcd * gcd * 1.2f;
        yaw -= yaw % a2;
        pitch -= pitch % a2;
        yawGCD = yaw;
        pitchGCD = pitch;

        serverRotations[0] = yawGCD;
        serverRotations[1] = pitchGCD;


        Rotations.server_yaw = serverRotations[0];
        Rotations.server_pitch = serverRotations[1];
        setupRotationUsing(e, new float[]{Rotations.server_yaw, Rotations.server_pitch});
        MC.thePlayer.rotationPitchHead = e.getPitch();
    }
    @EventTarget
    public void onUpdate(UpdateEvent e) {
        checkPlayers();
        if (getCurrentEntity() != null) {

            final float[] clazzRotations = RotationSender.getEntityRotations(getCurrentEntity(), predict.getValue(), bestVec.getValue(), randomMode.getValue().equalsIgnoreCase("Heuristics") && randomRotations.getValue());

            final float[] serverAngle = new float[]{Rotations.beforeYaw, Rotations.beforePitch};

            if (randomRotations.getValue() && randomMode.getValue().equalsIgnoreCase("Math.Random")) {
                clazzRotations[0] += MathUtil.getRandom(-4, 6);
                clazzRotations[1] += MathUtil.getRandom(-4, 6);
            }
            serverAngles = smoothAngle(clazzRotations, serverAngle);


            //      serverAngles = RotationSender.getEntityRotations(getCurrentEntity(), new float[]{Rotations.beforeYaw, Rotations.beforePitch}, smoothing.getValueInt(), predict.getValue(), bestVec.getValue());
        } else {
            alpha = 0;
        }


        if (MC.thePlayer.ticksExisted % 10 == 0) {
            tickEntity = getCurrentEntity();
        }

        for (Entity entity : MC.theWorld.loadedEntityList) {
            if (shouldAttack(entity, preRange.getValue())) {
                setEntity(entity);
            }
        }

        if (getCurrentEntity() != null && noSprint.getValue()) {
            MC.thePlayer.setSprinting(false);
        }

        if (!shouldAttack(currentEntity, preRange.getValue())) {
            setEntity(null);
            MC.thePlayer.ticksos = 0;
            {
                //MC.thePlayer.itemInUseCount = 0;
                //     MC.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        }


        if (getCurrentEntity() == null) {
            {
                animations[0] = 0;
                animations[1] = 0;
                animations[2] = 0;
            }
            executed = false;
            isAutoBlocking = false;
            if (stab.getValue()) {
                stabCount--;
                MC.gameSettings.keyBindRight.setPressed(false);
            }
        }

        if (autoBlock.getValue() && getCurrentEntity() != null) {
               doAutoBlock();
            this.isAutoBlocking = false;
        }

        if (stab.getValue()) {
            if (MC.thePlayer.onGround) {
                doStab();
            }
        }

        Rotations.pitchDifference = Math.abs(Rotations.server_pitch - prevRotation[1]);
        Rotations.yawDifference = Math.abs(Rotations.server_yaw - prevRotation[0]);

    }

    private void doStab() {

        distance = String.valueOf(BigDecimal.valueOf(getCurrentEntity().getDistanceToEntity(MC.thePlayer)).round(new MathContext(1, RoundingMode.HALF_DOWN)).floatValue());

        toString = String.valueOf(range.getValueInt());

        if (distance.startsWith(toString)) {
            stabCount += 1;
        }

        if (autoBlockMode.getValue().equalsIgnoreCase("stab")) {
            if (stabCount > 2) {
                MC.rightClickMouse();
            }
        }

        if (stabCount == 5) {

            MC.gameSettings.keyBindJump.setPressed(true);
            MC.gameSettings.keyBindBack.setPressed(true);

            MC.gameSettings.keyBindRight.setPressed(true);
            executed = true;
            stabCount--;
        } else {
            executed = false;
        }

        if (executed) {
            if (getTimerUtil2().reachedTime(200)) {
                MC.gameSettings.keyBindRight.setPressed(false);
                getTimerUtil2().doReset();
            }
        }
        if (stabCount > 3) {
            stabCount = 0;
        }

        if (stabCount < 0) {
            stabCount = 0;
        }

    }

    private void doAttack() {
        if (getCurrentEntity() == null) return;

        //abfragen


        if (rayCast.getValue()) {
            switch (raycastMode.getValue()) {
                case "ClickMouse":
                    MC.clickMouse();
                    if (crack.getValue()) {
                        for (int i = 0; i < 2; i++) {
                            MC.thePlayer.onCriticalHit(getCurrentEntity());
                            MC.thePlayer.onEnchantmentCritical(getCurrentEntity());
                        }
                    }

                    break;
                case "MC":
                    if (RayCastUtil.raycastEntity(range.getValue(), new float[]{Rotations.server_yaw, Rotations.server_pitch}) != null) {
                        MC.getNetHandler().addToSendQueue(new C02PacketUseEntity(Objects.requireNonNull(RayCastUtil.raycastEntity(range.getValue(), new float[]{Rotations.server_yaw, Rotations.server_pitch})), C02PacketUseEntity.Action.ATTACK));
                        MC.thePlayer.swingItem();
                    }
            }
        } else {

            MC.thePlayer.swingItem();
            MC.getNetHandler().addToSendQueue(new C02PacketUseEntity(getCurrentEntity(), C02PacketUseEntity.Action.ATTACK));

        }
    }

    private void doAutoBlock() {
        if (MC.thePlayer.getHeldItem() == null || !(MC.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
        if (shouldAutoBlock() && autoBlock.getValue()) {
            switch (autoBlockMode.getValue()) {
                case "Legit": {
                    if (MC.thePlayer.ticksExisted % 5 == 0) {
                        MC.rightClickMouse();
                        this.isAutoBlocking = true;
                    }
                }
                break;
                case "Full": {
                //    C07PacketPlayerDigging unblock = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN);
                //    MC.thePlayer.sendQueue.addToSendQueue(unblock);
                    MC.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, MC.thePlayer.getPosition(), EnumFacing.DOWN));
                    MC.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(getCurrentEntity(), new Vec3(getCurrentEntity().posX, getCurrentEntity().posY, getCurrentEntity().posZ)));
                }
                    this.isAutoBlocking = true;

                    break;

            }
        }
    }


    public void renderTargetHUD(boolean rect) {
        float startX = 77;
        float renderX = (sr.getScaledWidth() / 2f) + startX;
        float renderY = (sr.getScaledHeight() / 2f) + 50;

        switch (targetHudMode.getValue()) {
            case "Tired1":
                int maxX2 = 30;
                float healthPercentage = ((EntityPlayer) tickEntity).getHealth() / ((EntityPlayer) tickEntity).getMaxHealth();
                float maxX = Math.max(maxX2, FontManager.robotoF.getStringWidth(KillAura.getInstance().name) + 120);
                animationX = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animationX, (maxX * healthPercentage), 88);
                if (rect) {
                    Gui.drawRect(renderX, renderY, renderX + maxX, renderY + 60, new Color(30, 30, 30).getRGB());
                }
                Gui.drawRect(renderX, renderY + 58, renderX + (animationX), renderY + 60, ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRGB());

                FontManager.SFPRO.drawString(tickEntity.getName(), renderX + 6, renderY + 7, -1);
              //  FontManager.robotoF.drawString("Health: " + Math.round(((EntityPlayer) tickEntity).getHealth() / 10.0) * 10.0, renderX + 44, renderY + 21, -1);
                FontManager.robotoF.drawString("Health: " + Math.round(((EntityPlayer) tickEntity).getHealth() / 10.0) * 10.0, renderX + 44, renderY + 26, -1);

                String itemName;

                if (((EntityPlayer) tickEntity).getHeldItem() != null) {
                    itemName = ((EntityPlayer) tickEntity).getHeldItem().getDisplayName();
                } else {
                    itemName = "None";
                }

                FHook.fontRenderer.drawString("Item: " + itemName, renderX + 44, renderY + 41, -1);
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHead(((EntityPlayer) tickEntity), (int) renderX + 6, (int) renderY + 20, 30, 30);

                alpha = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(alpha, 22, 420);

                //Gui.drawRect((int) renderX + 6, (int) renderY + 20, renderX + 36, renderY + 50, new Color(120, 10, 10, (int) alpha).getRGB());

                break;
            case "Rounded":

                int maxX22 = 30;
                float healthPercentage2 = ((EntityPlayer) tickEntity).getHealth() / ((EntityPlayer) tickEntity).getMaxHealth();
                float maxX3 = Math.max(maxX22, FontManager.robotoF.getStringWidth(KillAura.getInstance().name) + 120);
                animationX = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animationX, (maxX3 * healthPercentage2), 88);
                if (rect) {
                    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(renderX, renderY, renderX + maxX3, renderY + 60, 3, -1);
                }
                //    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(renderX, renderY, renderX + maxX3, renderY + 60, 3, Integer.MIN_VALUE);
                Gui.drawRect(renderX, renderY + 58, renderX + (animationX), renderY + 60, ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRGB());

                FontManager.SFPRO.drawString(tickEntity.getName(), renderX + 6, renderY + 7, -1);
                FontManager.robotoF.drawString("Health: " + Math.round(((EntityPlayer) tickEntity).getHealth() / 10.0) * 10.0, renderX + 44, renderY + 26, -1);

                String itemName2;

                if (((EntityPlayer) tickEntity).getHeldItem() != null) {
                    itemName2 = ((EntityPlayer) tickEntity).getHeldItem().getDisplayName();
                } else {
                    itemName2 = "None";
                }

                FHook.fontRenderer.drawString("Item: " + itemName2, renderX + 44, renderY + 41, -1);
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHeadCircle(((EntityPlayer) tickEntity), (int) renderX + 6, (int) renderY + 20, 30, 30);
                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();

                //    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawPlayerHeadCircle(((EntityPlayer) getCurrentEntity()), (int) renderX + 6, (int) renderY + 20, 30, 30);

        }

    }

    public void renderOnly() {
        float startX = 77;
        float renderX = (sr.getScaledWidth() / 2f) + startX;
        float renderY = (sr.getScaledHeight() / 2f) + 50;

        switch (targetHudMode.getValue()) {
            case "Tired1":
            case "Rounded":

                int maxX2 = 30;
                float healthPercentage = ((EntityPlayer) tickEntity).getHealth() / ((EntityPlayer) tickEntity).getMaxHealth();
                float maxX = Math.max(maxX2, FontManager.robotoF.getStringWidth(KillAura.getInstance().name) + 120);
                animationX = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animationX, (maxX * healthPercentage), 88);
                Gui.drawRect(renderX, renderY + 58, renderX + (animationX), renderY + 60, ClickGUI.getInstance().colorPickerSetting.getColorPickerColor().getRGB());
                break;
        }

    }

    @EventTarget
    public void onRender2D(Render2DEvent e) {
    }


    private boolean shouldAutoBlock() {
        if (!bestBlock.getValue()) {
            return true;
        } else {
            return MC.thePlayer.getHealth() < 10 || distance.startsWith(toString);
        }

    }

    private void setupRotationUsing(final RotationEvent eventPreMotion, float[] rotation) {

        if (currentEntity == null) return;
        if (!rotations.getValue()) return;
        if (lockView.getValue()) {
            MC.thePlayer.rotationYaw = rotation[0];
            MC.thePlayer.rotationPitch = rotation[1];
        } else {

            eventPreMotion.setYaw(rotation[0]);
            eventPreMotion.setPitch(rotation[1]);
        }

    }
    public void setEntity(Entity e) {
        this.currentEntity = e;
    }


    @EventTarget
    public void onMoveFlying(MoveFlyingEvent e) {
        if (getCurrentEntity() == null) return;
        if (movementFix.getValue()) {
            e.setYaw(Rotations.server_yaw);
        }
    }

    @EventTarget
    public void onSilent(EventStrafe e) {
    }


    @EventTarget
    public void jump(JumpEvent event) {

        if (getCurrentEntity() == null) return;
        event.setYaw(Rotations.server_yaw);

    }


    @EventTarget
    public void getLook(EventLook event) {
        if (rotations.getValue()) {
            {

                event.setRotations(serverRotations);
            }
        }
    }

    @EventTarget
    public void onRender(Render3DEvent e) {
        if (rotationESP.getValue()) {
            if (getCurrentEntity() != null) {
                renderESP();
            }
        }
    }


    public boolean shouldAttack(Entity player, double extension) {
        if (player == null) return false;
        if (!(player instanceof EntityLivingBase)) {
            return false;
        }
        if (player == MC.thePlayer) {
            return false;
        }
        if (player instanceof EntityPlayer && !players.getValue()) return false;
        if (player instanceof EntityAnimal && !animals.getValue()) return false;
        if (player instanceof EntityMob && !mobs.getValue()) return false;
        if (player instanceof EntityVillager && !villagers.getValue()) return false;

        if (UUIDCheck.getValue() && fakePlayers.contains(player)) return false;

        if (player instanceof EntityArmorStand) return false;
        if (player.isInvisibleToPlayer(MC.thePlayer) && !invisibles.getValue()) return false;
        if (!checkWalls.getValue() && !MC.thePlayer.canEntityBeSeen(player)) return false;
        if (player.getName().equals("") || player.getName().contains("[NPC]") || player.getName().contains("CIT-") || player.getName().contains("Shop") || player.getName().contains("Upgrades") || (MC.getCurrentServerData() != null && (MC.getCurrentServerData().serverIP.toLowerCase().contains("blocksmc") && player.getName().toLowerCase().startsWith("CIT-"))))
            return false;
        if (aliveCheck.getValue() && !player.isEntityAlive()) return false;
        return player != MC.thePlayer && MC.thePlayer.getDistanceToEntity(player) <= range.getValue() + extension;
    }

    @Override
    public void onState() {
    }

    public static KillAura getInstance() {
        return ModuleManager.getInstance(KillAura.class);
    }

    private void renderESP() {
        double x = getCurrentEntity().lastTickPosX + (getCurrentEntity().posX - getCurrentEntity().lastTickPosX) * MC.timer.renderPartialTicks - RenderManager.renderPosX;
        double y = getCurrentEntity().lastTickPosY + (getCurrentEntity().posY - getCurrentEntity().lastTickPosY) * MC.timer.renderPartialTicks - RenderManager.renderPosY;
        double z = getCurrentEntity().lastTickPosZ + (getCurrentEntity().posZ - getCurrentEntity().lastTickPosZ) * MC.timer.renderPartialTicks - RenderManager.renderPosZ;
        if (getCurrentEntity() instanceof EntityPlayer) {

            x -= 0.275;
            z -= 0.275;
            y += getCurrentEntity().getEyeHeight() - 0.225 - (getCurrentEntity().isSneaking() ? 0.25 : 0.0);

            y -= Math.abs((Rotations.server_pitch) / 50);

            final double mid = 0.275;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            final double rotAdd = -0.25 * (Math.abs(Rotations.server_pitch) / 90.0f);

            animations[0] = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animations[0], x, 55);

            animations[1] = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animations[1], y, 3);

            animations[2] = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(animations[2], z, 55);

            if (MC.thePlayer.ticksExisted % 4 == 0) {
                animationsPrev[0] = animations[0];
                animationsPrev[1] = animations[1];
                animationsPrev[2] = animations[2];
            }

            GL11.glTranslated(0.0, rotAdd, 0.0);
            GL11.glTranslated(animations[0] + mid, animations[1] + mid, animations[2] + mid);
            GL11.glRotated(-Rotations.server_yaw % 360.0f, 0.0, 1.0, 0.0);
            GL11.glTranslated(-(animations[0] + mid), -(animations[1] + mid), -(animations[2] + mid));
            GL11.glTranslated(animations[0] + mid, animations[1] + mid, animations[2] + mid);

            GL11.glTranslated(-(animations[0] + mid), -(animations[1] + mid), -(animations[2] + mid));


            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);

            final Color color = Color.RED;

            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.15f);
            GL11.glLineWidth(1);

            scaleAnimation = (float) beta.tiredb56.api.util.renderapi.AnimationUtil.getAnimationState(scaleAnimation, Math.max(MC.thePlayer.getDistanceToEntity(getCurrentEntity()) / 5.5, 0.27), Math.max(.2D, Math.abs((double) scaleAnimation - Math.max(MC.thePlayer.getDistanceToEntity(getCurrentEntity()) / 5.5, 0.27))));

            double width2 = scaleAnimation;

            final double width1 = 0.0005;
            {
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBoundingBox(new AxisAlignedBB(x - width1, y - width1, z - width1, x + width2 + width1, y + width2 + width1, z + width2 + width1));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.15f);
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBoundingBox(new AxisAlignedBB(x - width1, y - width1, z - width1, x + width2 + width1, y + width2 + width1, z + width2 + width1));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.15f);
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlinedBoundingBox(new AxisAlignedBB(x - width1, y - width1, z - width1, x + width2 + width1, y + width2 + width1, z + width2 + width1));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.15f);
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlinedBoundingBox(new AxisAlignedBB(x - width1, y - width1, z - width1, x + width2 + width1, y + width2 + width1, z + width2 + width1));
            }
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onUndo() {
        if (autoBlock.getValue()) {
            MC.thePlayer.ticksos = 0;
            {

                MC.thePlayer.itemInUseCount = 0;
                MC.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        }
        if (stab.getValue()) {
            MC.gameSettings.keyBindRight.setPressed(false);
        }
    }

    public float[] constrainAngle(float[] vector) {

        vector[0] = (vector[0] % 360F);
        vector[1] = (vector[1] % 360F);

        while (vector[0] <= -180) {
            vector[0] = (vector[0] + 360);
        }

        while (vector[1] <= -180) {
            vector[1] = (vector[1] + 360);
        }

        while (vector[0] > 180) {
            vector[0] = (vector[0] - 360);
        }

        while (vector[1] > 180) {
            vector[1] = (vector[1] - 360);
        }

        return vector;
    }


    private float[] smoothAngle(float[] dst, float[] src) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        smoothedAngle = constrainAngle(smoothedAngle);
        int random = MathUtil.getRandom(14, 24);
        smoothedAngle[0] = (src[0] - smoothedAngle[0] / smoothing.getValueInt() * random);
        smoothedAngle[1] = (src[1] - smoothedAngle[1] / smoothing.getValueInt() * random);
        return smoothedAngle;
    }


}
