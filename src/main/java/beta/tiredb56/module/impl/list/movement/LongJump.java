package beta.tiredb56.module.impl.list.movement;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.ClientHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

@ModuleAnnotation(name = "LongJump", category = ModuleCategory.MOVEMENT, clickG = "Jump longer and faster")
public class LongJump extends Module {

    public ModeSetting LONGJUMP_MODE = new ModeSetting("LONGJUMP_MODE", this, new String[]{"Normal", "BlocksMC"});

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        if (!this.state) return;

        if (MC.thePlayer.hurtTime == 0) return;

        if (MC.thePlayer.onGround) {
            for (int i = 0; i < 2; i++) {
                MC.thePlayer.jump();
            }
        } else {

            MC.thePlayer.motionY += 0.2F;

            ClientHelper.INSTANCE.doSpeedup(1.96);
        }
    }

    @Override
    public void onState() {
        double x = MC.thePlayer.posX;
        double y = MC.thePlayer.posY;
        double z = MC.thePlayer.posZ;
        MC.timer.timerSpeed = 1F;
        sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
        sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(getWorld(), new BlockPos(-1, -1, -1))), 0, 0.94f, 0));
        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 3.35, z, false));
        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        MC.thePlayer.motionX = 0;
        MC.thePlayer.motionY = 0;
        MC.thePlayer.motionZ = 0;
        MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY + 0.42, MC.thePlayer.posZ);
        MC.thePlayer.posY += .6;
    }

    @Override
    public void onUndo() {
        MC.timer.timerSpeed = 1F;

    }
}
