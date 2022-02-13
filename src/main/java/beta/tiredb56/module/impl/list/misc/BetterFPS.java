package beta.tiredb56.module.impl.list.misc;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventRenderEntity;
import beta.tiredb56.event.events.UpdateEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@ModuleAnnotation(name = "BetterFPS", category = ModuleCategory.MISC, clickG = "Gives you better fps")
public class BetterFPS extends Module {

    public BooleanSetting uglyModels = new BooleanSetting("uglyModels", this, true);

    @EventTarget
    public void onUpdate(UpdateEvent e) {
            EntityLivingBase entity = getFarthest();
            if (entity == null) {
                MC.gameSettings.renderDistanceChunks = 4;
            } else {
                MC.gameSettings.renderDistanceChunks = MC.thePlayer.getDistanceToEntity(entity) > 16 * 6 ? 6 : (int) (MC.thePlayer.getDistanceToEntity(entity) / 16);
            }

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    @EventTarget
    public void onRenderPlayer(EventRenderEntity event){
        if(event.getEntity().getDistanceToEntity(MC.thePlayer) > 90){
            event.setCancelled(true);
        }
    }

    public static BetterFPS getInstance() {
        return CheatMain.INSTANCE.moduleManager.moduleBy(BetterFPS.class);
    }

    private EntityLivingBase getFarthest() {
        double dist = 96;
        EntityLivingBase target = null;
        for (Entity object : MC.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                EntityLivingBase cast = (EntityLivingBase) object;
                double currentDist = MC.thePlayer.getDistanceToEntity(cast);
                if (currentDist >= dist) {
                    dist = currentDist;
                    target = cast;
                }

            }
        }
        return target;
    }

}
