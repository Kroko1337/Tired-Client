package beta.tiredb56.api.util.gui;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.util.Translate;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.impl.HudModule;
import beta.tiredb56.module.impl.list.combat.KillAura;
import beta.tiredb56.module.impl.list.visual.ArrowESP;
import beta.tiredb56.module.impl.list.visual.HotBar;
import beta.tiredb56.module.impl.list.visual.Notifications;
import beta.tiredb56.module.impl.list.visual.TabGui;
import beta.tiredb56.notification.newnotifications.NotifyManager;
import beta.tiredb56.shader.list.ArrayListShader;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.ShaderRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.opengl.GL11;

public class LayerRenderer extends ShaderRenderLayer implements IHook {
    public float float2;
    private float animationX;
    private Translate translate;

    private ArrayListShader arrayListShader = new ArrayListShader(0);

    public LayerRenderer() {
        this.translate = new Translate(0, 0);
    }

    @Override
    public void renderLayerWBlur() {

        if (HudModule.getInstance().isState()) {
            HudModule.getInstance().renderLogo(true);
        }

        if (ArrowESP.getInstance().state) {
            ArrowESP.getInstance().renderArrow2();
        }



        ShaderRenderer.startBlur();

        if (TabGui.getInstance().state) {
            TabGui.getInstance().render(true);
        }


        MC.ingameGUI.renderChatFinal(true);


        if (CheatMain.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
            NotifyManager.drawNotifications(true);
        }
        targetHUD2(true);
        renderScoreBoard();
        if (CheatMain.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
            NotifyManager.drawNotifications(true);
        }
        if (CheatMain.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), true);
        }

        if (ArrowESP.getInstance().state) {
            ArrowESP.getInstance().renderArrow2();
        }
        targetHUD(true);
        HudModule.getInstance().drawArrayNoText(true);

        ShaderRenderer.stopBlur(12);
    }

    private void renderScoreBoard() {
        Scoreboard scoreboard = MC.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(MC.thePlayer.getName());

        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        final ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreobjective1 != null) {
            final ScaledResolution scaledresolution = new ScaledResolution(MC);
            MC.ingameGUI.renderScoreboard(scoreobjective1, scaledresolution);
        }

    }

    private void targetHUD2(boolean rect) {
        final ScaledResolution sr = new ScaledResolution(MC);

        float startX = 77;
        float renderX = (sr.getScaledWidth() / 2f) + startX;
        if (KillAura.getInstance().isState() && KillAura.getInstance().getCurrentEntity() != null && KillAura.getInstance().getCurrentEntity() instanceof EntityPlayer) {
            if (float2 < 0.5F) {
                translate.interpolate((sr.getScaledWidth() / 2f) , sr.getScaledHeight() / 2, 12);

            }
        } else {

            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        if ((KillAura.getInstance().isState() && KillAura.getInstance().tickEntity == null)) {
            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        GL11.glPushMatrix();
        GL11.glTranslatef(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0);
        GL11.glScaled(0.5 + translate.getX() / sr.getScaledWidth() - float2, 0.5 + translate.getY() / sr.getScaledHeight() - float2, 0);
        GL11.glTranslatef(-sr.getScaledWidth() / 2f, -sr.getScaledHeight() / 2f, 0);

        if (KillAura.getInstance().tickEntity != null) {
            if (translate.getX() != 0.0) {
                KillAura.getInstance().renderOnly();
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }
    private void targetHUD(boolean rect) {
        final ScaledResolution sr = new ScaledResolution(MC);

        float startX = 77;
        float renderX = (sr.getScaledWidth() / 2f) + startX;
        if (KillAura.getInstance().isState() && KillAura.getInstance().getCurrentEntity() != null && KillAura.getInstance().getCurrentEntity() instanceof EntityPlayer) {
            if (float2 < 0.5F) {
                translate.interpolate((sr.getScaledWidth() / 2f) , sr.getScaledHeight() / 2, 12);

            }
        } else {

            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        if ((KillAura.getInstance().isState() && KillAura.getInstance().tickEntity == null)) {
            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        GL11.glPushMatrix();
        GL11.glTranslatef(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0);
        GL11.glScaled(0.5 + translate.getX() / sr.getScaledWidth() - float2, 0.5 + translate.getY() / sr.getScaledHeight() - float2, 0);
        GL11.glTranslatef(-sr.getScaledWidth() / 2f, -sr.getScaledHeight() / 2f, 0);

        if (KillAura.getInstance().tickEntity != null) {
            if (translate.getX() != 0.0) {
                KillAura.getInstance().renderTargetHUD(rect);
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }

    @Override
    public void renderNormalLayer() {

        if (CheatMain.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
            NotifyManager.drawNotifications(true);
        }


        targetHUD(false);
        MC.ingameGUI.renderChatFinal(false);
     //   Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlineRect(20, 20, 120, 120, -1);
        if (CheatMain.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), false);
        }
        if (HudModule.getInstance().isState()) {
            HudModule.getInstance().renderLogo(true);
        }
        if (HudModule.getInstance().colorType.getValue().equalsIgnoreCase("shader")) {
            HudModule.getInstance().drawArrayNoText(true);
        } else {
            HudModule.getInstance().drawArray(true);
        }
        if (HudModule.getInstance().colorType.getValue().equalsIgnoreCase("shader")) {
            arrayListShader.drawShader();
            HudModule.getInstance().drawArray(false);

            targetHUD2(true);
            arrayListShader.stop();
        }
        if (TabGui.getInstance().state) {
            TabGui.getInstance().render(true);
        }
        ShaderRenderer.startDropShadow();
        MC.ingameGUI.renderChatFinal(true);
        HudModule.getInstance().drawArray(true);
        targetHUD(true);
        if (CheatMain.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), true);
        }
        renderScoreBoard();
        ShaderRenderer.stopDropShadow();

    }


}
