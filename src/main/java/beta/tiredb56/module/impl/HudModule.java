package beta.tiredb56.module.impl;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.TextBoxSetting;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.api.util.font.CustomFont;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.EventTick;
import beta.tiredb56.event.events.Render2DEvent;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.module.impl.list.visual.ArrowESP;
import beta.tiredb56.module.impl.list.visual.Blur;
import beta.tiredb56.module.impl.list.visual.HotBar;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;


@ModuleAnnotation(name = "Hud", category = ModuleCategory.RENDER)

public class HudModule extends Module {


    public BooleanSetting sorted = new BooleanSetting("sorted", this, true);
    public BooleanSetting rightBar = new BooleanSetting("rightBar", this, true);
    public BooleanSetting colorBackground = new BooleanSetting("colorBackground", this, true);
    public BooleanSetting onlyImportant = new BooleanSetting("onlyImportant", this, true);
    public BooleanSetting description = new BooleanSetting("description", this, true);
    public BooleanSetting textshadow = new BooleanSetting("textshadow", this, true);
    public BooleanSetting minecraftFont = new BooleanSetting("minecraftFont", this, true);
    public ColorPickerSetting backgroundColor = new ColorPickerSetting("backgroundColor", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);
    public ColorPickerSetting textColor = new ColorPickerSetting("textColor", this, true, new Color(244, 0, 0, 255), (new Color(244, 0, 0, 255)).getRGB(), null);
    public ModeSetting fontType = new ModeSetting("fontType", this, new String[]{"SFPro", "Comfortaa"}, () -> !minecraftFont.getValue());
    public ModeSetting colorType = new ModeSetting("colorType", this, new String[]{"Rainbow", "Fade", "Shader", "Custom"});
    //public ModeSetting logoType = new ModeSetting("logoType", this, new String[]{"Rect", "Tired2", "Tired3", "TiredLogo", "Text", "Fanta", "Suicide", "Saint", "Koksense", "none"});
    public NumberSetting rainbowTime = new NumberSetting("rainbowTime", this, 3, 1, 6, .1);
    public NumberSetting backgroundAlpha = new NumberSetting("backgroundAlpha", this, 122, 1, 244, 1);
    public NumberSetting yAxis = new NumberSetting("yAxis", this, 1, 0, 20, 1);

    public NumberSetting xAxis = new NumberSetting("xAxis", this, 1, 0, 20, 1);
    // public NumberSetting size = new NumberSetting("size", this, 50, 10, 200, 1, () -> logoType.getValue().equalsIgnoreCase("Fanta") || logoType.getValue().equalsIgnoreCase("TiredLogo"));

    public ColorPickerSetting color1 = new ColorPickerSetting("color1", this, true, new Color(244, 0, 0, 255), (new Color(244, 0, 0, 255)).getRGB(), () -> colorType.getValue().equalsIgnoreCase("Fade"));
    public ColorPickerSetting color2 = new ColorPickerSetting("color2", this, true, new Color(244, 0, 0, 255), (new Color(244, 0, 0, 255)).getRGB(), () -> colorType.getValue().equalsIgnoreCase("Fade"));

    public static ArrayList<Module> listToUse;
    public TextBoxSetting text = new TextBoxSetting("", this, "Tired");
    public float animationX;

    public float borderSize;
    public int yPlus;

    public CustomFont font;
    float bps;

    public HudModule() {

    }

    public static HudModule getInstance() {
        return ModuleManager.getInstance(HudModule.class);
    }

    @EventTarget
    public void onTick(EventTick e) {
        setDesc("Font: " + fontType.getValue() + " Color: " + colorType.getValue());
    }

    public void renderLogo(boolean rect) {

        // final String args = text.getValue() + " | " + "Ping: " + ping + " | " + "FPS: " + Minecraft.getDebugFPS() + " | " + name;
        int rainbowCol = beta.tiredb56.api.util.renderapi.ColorUtil.rainbow((long) (System.nanoTime() * rainbowTime.getValue()), 22, 1).getRGB();

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.push();
        Scissoring.SCISSORING.doScissor(5, 8,     FontManager.confortaaBig.getStringWidth(text.getValue() + " Client @" + Minecraft.getDebugFPS() + "FPS"), 52);
        FontManager.confortaaBig.drawStringWithShadow(text.getValue() + " Client @" + Minecraft.getDebugFPS() + "FPS", 5, 8, -1);
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.pop();
    }

    @EventTarget
    public void onRender(Render2DEvent e) {
        switch (fontType.getValue()) {

            case "moon": {
                font = FontManager.moonF;
                break;
            }
            case "notosans": {
                font = FontManager.notosansF;
                break;
            }

            case "SFPro": {
                font = FontManager.SFPRO;
            }
            break;
            case "roboto": {
                font = FontManager.robotoF;
                break;
            }

            case "arial": {
                font = FHook.fontRenderer;
                break;
            }
            case "Thin":
                font = FontManager.robotoT;
                break;

            case "BebasFont":
                font = FontManager.bebasF;
                break;

            case "Comfortaa": {
                font = FontManager.confortaa;
                break;
            }

            case "IBMPlexSansF":
                font = FontManager.IBMPlexSans;
                break;
        }

        final ScaledResolution sr = new ScaledResolution(MC);

        if (CheatMain.INSTANCE.moduleManager.findModuleByClass(ArrowESP.class).isState()) {
            ArrowESP.getInstance().renderArrow();
        }


        float rat = IHook.MC.timer.ticksPerSecond * IHook.MC.timer.timerSpeed;
        this.bps = (float) (MC.thePlayer.getDistance(MC.thePlayer.lastTickPosX, MC.thePlayer.posY, MC.thePlayer.lastTickPosZ) * rat);

        String server;

        if (MC.getCurrentServerData() != null) {
            server = MC.getCurrentServerData().serverIP;
        } else {
            server = "Survival";
        }

        borderSize = rightBar.getValue() ? 1 : 0;
        int rainbowCol = beta.tiredb56.api.util.renderapi.ColorUtil.rainbow((long) (System.nanoTime() * rainbowTime.getValue()), 22, 1).getRGB();
        int texColor = colorType.getValue().equalsIgnoreCase("Custom") ? textColor.ColorPickerC.getRGB() : colorType.getValue().equalsIgnoreCase("Rainbow") ? rainbowCol : beta.tiredb56.api.util.renderapi.ColorUtil.getGradientOffset(color1.getColorPickerColor(), color2.getColorPickerColor2(), yPlus * 10 * 100).getRGB();
        if (!HotBar.getInstance().hotbarMode.getValue().equalsIgnoreCase("B48")) {
            FontManager.IBMPlexSans.drawStringWithShadow("BPS: " + Math.round(bps * 10.0) / 10.0, FontManager.IBMPlexSans.getStringWidth("Server: " + server) + 10, sr.getScaledHeight() - 14, texColor);

            FontManager.IBMPlexSans.drawStringWithShadow("Server: " + server, 4, sr.getScaledHeight() - 14, texColor);
        }
        ModuleManager.sortedModList.sort(minecraftFont.getValue() ? (m1, m2) -> MC.fontRendererObj.getStringWidth(m2.getNameWithSuffix()) - MC.fontRendererObj.getStringWidth(m1.getNameWithSuffix()) : (m1, m2) -> font.getStringWidth(m2.getNameWithSuffix()) - font.getStringWidth(m1.getNameWithSuffix()));
        if (onlyImportant.getValue()) {
            for (Module module : CheatMain.INSTANCE.moduleManager.getModuleList()) {
                if (module.getModuleCategory() == ModuleCategory.COMBAT || module.getModuleCategory() == ModuleCategory.PLAYER) {
                    listToUse = sorted.getValue() ? ModuleManager.sortedModList : (ArrayList<Module>) CheatMain.INSTANCE.moduleManager.getModuleList();
                }
            }
        } else {
            listToUse = sorted.getValue() ? ModuleManager.sortedModList : (ArrayList<Module>) CheatMain.INSTANCE.moduleManager.getModuleList();
        }

        animationX = (float) AnimationUtil.getAnimationState(animationX, MC.gameSettings.showDebugProfilerChart ? GuiOverlayDebug.y2 : yAxis.getValue(), 120);


        drawArray(!CheatMain.INSTANCE.moduleManager.moduleBy(Blur.class).state);


    }

    public void drawArray(boolean rect) {
        ScaledResolution sr = new ScaledResolution(MC);


        int yAxis = (int) animationX;
        if (onlyImportant.getValue()) {
            for (Module o : listToUse) {
                o.allowRender = o.getModuleCategory() == ModuleCategory.COMBAT || o.getModuleCategory() == ModuleCategory.PLAYER || o.getModuleCategory() == ModuleCategory.MOVEMENT;
            }
        } else {
            for (Module o : listToUse) {
                o.allowRender = true;
            }
        }
        for (Module o : listToUse) {
            if (o.allowRender) {
                if (o.isState()) {
                    if (o.posX > 0.0F) {
                        o.posX -= Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * Math.max(25.0F, Math.abs(o.posX - 0.0F) * 4.0F);
                    }

                    if (o.posX < 0.0F) {
                        o.posX = 0.0F;
                    }
                } else {
                    double max = minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5;
                    if ((double) o.posX < max) {
                        o.posX = (float) ((double) o.posX + (double) Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * Math.max(2.0D, Math.abs((double) o.posX - max) * 2.0D));
                    }

                    if ((double) o.posX > max) {
                        o.posX = (float) max;
                    }
                }
                if (o.posX < (float) (minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5)) {
                    if (o.posX < (float) (minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5)) {
                        int rainbowCol = beta.tiredb56.api.util.renderapi.ColorUtil.rainbow((long) (System.nanoTime() * rainbowTime.getValue()), yPlus * 4, 1).getRGB();
                        int additional = minecraftFont.getValue() ? 0 : fontType.getValue().equalsIgnoreCase("bebas") || fontType.getValue().equalsIgnoreCase("moon") || fontType.getValue().equalsIgnoreCase("notosans") ? 1 : fontType.getValue().equalsIgnoreCase("Comfortaa") ? -2 : fontType.getValue().equalsIgnoreCase("IBMPlexSansF") ? 2 : 0;
                        if (minecraftFont.getValue() ? o.posX < (MC.fontRendererObj.getStringWidth(o.getNameWithSuffix()) + 1) : o.posX < (font.getStringWidth(o.getNameWithSuffix()) + 1)) {
                            int xD = (int) ((minecraftFont.getValue() ? !o.getDesc().isEmpty() ? MC.fontRendererObj.getStringWidth(o.getDesc()) + 5 : 1 : font.getStringWidth(o.getDesc()) + 3) + xAxis.getValue());
                            GlStateManager.disableBlend();
                            int texColor = colorType.getValue().equalsIgnoreCase("Custom") ? textColor.ColorPickerC.getRGB() : colorType.getValue().equalsIgnoreCase("Rainbow") ? rainbowCol : beta.tiredb56.api.util.renderapi.ColorUtil.getGradientOffset(new Color(this.getColors()[0]), new Color(this.getColors()[1]), ((double) yPlus / 12.4D) * (System.currentTimeMillis() * (yPlus * 0.0000003))).getRGB();


                            if (!minecraftFont.getValue()) {
                                if (rect) {
                                    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect((int) (sr.getScaledWidth() - (int) font.getStringWidth(o.getName()) - 1) + (int) o.posX - (int) borderSize - xD, (int) yAxis, (int) sr.getScaledWidth() - (int) borderSize - (int) xAxis.getValueInt(), (int) (yAxis + font.FONT_HEIGHT), new Color(backgroundColor.ColorPickerC.getRed(), backgroundColor.ColorPickerC.getGreen(), backgroundColor.ColorPickerC.getBlue(), backgroundAlpha.getValueInt()).getRGB());
                                }
                                GlStateManager.resetColor();
                                GlStateManager.disableBlend();
                                if (textshadow.getValue()) {
                                    font.drawStringWithShadow2(o.getName(), (int) (sr.getScaledWidth() - borderSize - font.getStringWidth(o.getName()) - 2.0F + o.posX + 2.5) - xD, (yAxis) - additional, texColor);
                                } else {
                                    font.drawString2(o.getName(), (int) (sr.getScaledWidth() - borderSize - font.getStringWidth(o.getName()) - 2.0F + o.posX + 2.0F) - xD, (yAxis) - additional, texColor);
                                }
                                if (!o.getDesc().isEmpty()) {
                                    if (textshadow.getValue()) {
                                        font.drawStringWithShadow2(o.getDesc(), (int) (sr.getScaledWidth() - borderSize - font.getStringWidth(o.getDesc()) - 2.0F + o.posX + 1.0F) - xAxis.getValue(), (yAxis) - additional, new Color(168, 168, 168).getRGB());
                                    } else {
                                        font.drawString2(o.getDesc() + " ", (float) ((int) (sr.getScaledWidth() - borderSize - font.getStringWidth(o.getDesc()) - 2.0F + o.posX + 1.0F) - xAxis.getValue()), (yAxis) - additional, new Color(168, 168, 168).getRGB());
                                    }
                                }
                            } else {
                                GlStateManager.resetColor();
                                GlStateManager.disableBlend();
                                if (rect) {
                                  //  Gui.drawRect((float) (sr.getScaledWidth() - MC.fontRendererObj.getStringWidth(o.getName()) - 1) - 2 + o.posX - borderSize - xD, (float) yAxis - 1, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), (float) (yAxis + MC.fontRendererObj.FONT_HEIGHT), new Color(backgroundColor.ColorPickerC.getRed(), backgroundColor.ColorPickerC.getGreen(), backgroundColor.ColorPickerC.getBlue(), backgroundAlpha.getValueInt()).getRGB());
                                }
                                if (textshadow.getValue()) {
                                    MC.fontRendererObj.drawStringWithShadow(o.getName(), (int) (sr.getScaledWidth() - borderSize - MC.fontRendererObj.getStringWidth(o.getName()) - 2.0F + o.posX + 1.0F) - xD, (yAxis) - additional, texColor);
                                } else {
                                    MC.fontRendererObj.drawString(o.getName(), (int) (sr.getScaledWidth() - borderSize - MC.fontRendererObj.getStringWidth(o.getName()) - 2.0F + o.posX + 1.0F) - xD, (yAxis) - additional, texColor);
                                }
                                if (!o.getDesc().isEmpty()) {
                                    if (textshadow.getValue()) {
                                        MC.fontRendererObj.drawStringWithShadow(o.getDesc(), (float) ((int) (sr.getScaledWidth() - borderSize - MC.fontRendererObj.getStringWidth(o.getDesc()) - 2.0F + o.posX + 1.0F) - 1 - xAxis.getValue()), (yAxis) - additional, new Color(168, 168, 168).getRGB());
                                    } else {
                                        MC.fontRendererObj.drawString(o.getDesc(), (float) ((int) (sr.getScaledWidth() - borderSize - MC.fontRendererObj.getStringWidth(o.getDesc()) - 2.0F + o.posX + 1.0F) - 1 - xAxis.getValue()), (yAxis) - additional, new Color(168, 168, 168).getRGB());
                                    }
                                }
                            }
                            Gui.drawRect((float) (sr.getScaledWidth()) - xAxis.getValue(), minecraftFont.getValue() ? (float) yAxis - 2 : yAxis, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), minecraftFont.getValue() ? (yAxis + MC.fontRendererObj.FONT_HEIGHT) : (yAxis + font.FONT_HEIGHT), texColor);
                            yAxis = minecraftFont.getValue() ? (int) ((double) yAxis + 1 + (MC.fontRendererObj.FONT_HEIGHT) + 0) : (int) ((double) yAxis + (font.FONT_HEIGHT));

                            yPlus = yAxis;
                        }
                    }
                }
            }
        }

    }


    public void drawArrayNoText(boolean rect) {
        ScaledResolution sr = new ScaledResolution(MC);


        int yAxis = (int) animationX;
        if (onlyImportant.getValue()) {
            for (Module o : listToUse) {
                o.allowRender = o.getModuleCategory() == ModuleCategory.COMBAT || o.getModuleCategory() == ModuleCategory.PLAYER || o.getModuleCategory() == ModuleCategory.MOVEMENT;
            }
        } else {
            for (Module o : listToUse) {
                o.allowRender = true;
            }
        }
        for (Module o : listToUse) {
            if (o.allowRender) {
                if (o.isState()) {
                    if (o.posX > 0.0F) {
                        o.posX -= Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * Math.max(25.0F, Math.abs(o.posX - 0.0F) * 4.0F);
                    }

                    if (o.posX < 0.0F) {
                        o.posX = 0.0F;
                    }
                } else {
                    double max = minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5;
                    if ((double) o.posX < max) {
                        o.posX = (float) ((double) o.posX + (double) Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * Math.max(2.0D, Math.abs((double) o.posX - max) * 2.0D));
                    }

                    if ((double) o.posX > max) {
                        o.posX = (float) max;
                    }
                }
                if (o.posX < (float) (minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5)) {
                    if (o.posX < (float) (minecraftFont.getValue() ? MC.fontRendererObj.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5 : font.getStringWidth(o.getNameWithSuffix() + o.getDesc()) + 5)) {
                        int rainbowCol = beta.tiredb56.api.util.renderapi.ColorUtil.rainbow((long) (System.nanoTime() * rainbowTime.getValue()), yPlus * 4, 1).getRGB();
                        int additional = minecraftFont.getValue() ? 0 : fontType.getValue().equalsIgnoreCase("bebas") || fontType.getValue().equalsIgnoreCase("moon") || fontType.getValue().equalsIgnoreCase("notosans") ? 1 : fontType.getValue().equalsIgnoreCase("Comfortaa") ? -2 : fontType.getValue().equalsIgnoreCase("IBMPlexSansF") ? 2 : 0;
                        if (minecraftFont.getValue() ? o.posX < (MC.fontRendererObj.getStringWidth(o.getNameWithSuffix()) + 1) : o.posX < (font.getStringWidth(o.getNameWithSuffix()) + 1)) {
                            int xD = (int) ((minecraftFont.getValue() ? !o.getDesc().isEmpty() ? MC.fontRendererObj.getStringWidth(o.getDesc()) + 5 : 1 : font.getStringWidth(o.getDesc()) + 3) + xAxis.getValue());
                            GlStateManager.disableBlend();
                            int texColor = colorType.getValue().equalsIgnoreCase("Custom") ? textColor.ColorPickerC.getRGB() : colorType.getValue().equalsIgnoreCase("Rainbow") ? rainbowCol : beta.tiredb56.api.util.renderapi.ColorUtil.getGradientOffset(new Color(this.getColors()[0]), new Color(this.getColors()[1]), ((double) yPlus / 12.4D) * (System.currentTimeMillis() * (yPlus * 0.0000003))).getRGB();

                            if (rect) {

                                if (colorBackground.getValue() && !minecraftFont.getValue()) {
                                    Gui.drawRect((float) (sr.getScaledWidth() - font.getStringWidth(o.getName()) - 1) + o.posX - borderSize - xD, (float) yAxis, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), (float) (yAxis + font.FONT_HEIGHT), texColor);
                                }

                                if (colorBackground.getValue() && minecraftFont.getValue()) {
                                    Gui.drawRect((float) (sr.getScaledWidth() - MC.fontRendererObj.getStringWidth(o.getName()) - 1) - 2 + o.posX - borderSize - xD, (float) yAxis - 1, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), (float) (yAxis + MC.fontRendererObj.FONT_HEIGHT), texColor);
                                }
                            }

                            if (!minecraftFont.getValue()) {
                                if (rect) {
                                    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect((int) (sr.getScaledWidth() - (int) font.getStringWidth(o.getName()) - 1) + (int) o.posX - (int) borderSize - xD, (int) yAxis, (int) sr.getScaledWidth() - (int) borderSize - (int) xAxis.getValueInt(), (int) (yAxis + font.FONT_HEIGHT), new Color(backgroundColor.ColorPickerC.getRed(), backgroundColor.ColorPickerC.getGreen(), backgroundColor.ColorPickerC.getBlue(), backgroundAlpha.getValueInt()).getRGB());
                                }
                                GlStateManager.resetColor();
                                GlStateManager.disableBlend();
                            } else {
                                GlStateManager.resetColor();
                                GlStateManager.disableBlend();
                                if (rect) {
                                    Gui.drawRect((float) (sr.getScaledWidth() - MC.fontRendererObj.getStringWidth(o.getName()) - 1) - 2 + o.posX - borderSize - xD, (float) yAxis - 1, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), (float) (yAxis + MC.fontRendererObj.FONT_HEIGHT), new Color(backgroundColor.ColorPickerC.getRed(), backgroundColor.ColorPickerC.getGreen(), backgroundColor.ColorPickerC.getBlue(), backgroundAlpha.getValueInt()).getRGB());
                                }
                            }
                            Gui.drawRect((float) (sr.getScaledWidth()) - xAxis.getValue(), minecraftFont.getValue() ? (float) yAxis - 2 : yAxis, (float) sr.getScaledWidth() - borderSize - xAxis.getValue(), minecraftFont.getValue() ? (yAxis + MC.fontRendererObj.FONT_HEIGHT) : (yAxis + font.FONT_HEIGHT), texColor);
                            yAxis = minecraftFont.getValue() ? (int) ((double) yAxis + 1 + (MC.fontRendererObj.FONT_HEIGHT) + 0) : (int) ((double) yAxis + (font.FONT_HEIGHT));

                            yPlus = yAxis;
                        }
                    }
                }
            }
        }

    }


    public int[] getColors() {
        try {
            return new int[]{color1.ColorPickerC.getRGB(), color2.ColorPickerC.getRGB()};
        } catch (Exception e) {
            return new int[]{Color.white.getRGB(), Color.black.getRGB()};
        }
    }

    @Override
    public void onState() {
    }

    @Override
    public void onUndo() {

    }
}
