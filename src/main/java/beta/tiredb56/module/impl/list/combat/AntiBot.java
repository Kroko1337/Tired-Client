package beta.tiredb56.module.impl.list.combat;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@ModuleAnnotation(name = "AntiBot", category = ModuleCategory.COMBAT)
public class AntiBot extends Module {

    public BooleanSetting checkName = new BooleanSetting("checkName", this, true);
    public BooleanSetting checkTablist = new BooleanSetting("checkTablist", this, true);


    @EventTarget
    public void onUpdate(UpdateEvent event) {

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    private boolean isValidEntityName(Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return true;
        }
        final String name = getName((EntityPlayer) entity);
        return name.length() <= 16 && name.length() >= 3 && name.matches("[a-zA-Z0-9_]*");
    }

    public boolean isInTabList(Entity entity) {
        if (MC.isSingleplayer())
            return true;
        for (NetworkPlayerInfo playerInfo : getPlayer().sendQueue.getPlayerInfoMap()) {
            if (playerInfo.getGameProfile().getId().equals(entity.getUniqueID()))
                return true;
        }
        return false;
    }

}
