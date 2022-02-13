package beta.tiredb56.module.impl.list.visual;

import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.event.EventTarget;
import beta.tiredb56.event.events.Render2DEvent;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.tired.ClientHelper;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

@ModuleAnnotation(name = "Crossair", category = ModuleCategory.RENDER, clickG = "Better looking crossair")
public class Crossair extends Module {

    public NumberSetting length = new NumberSetting("length", this, 0, 0, 5, .1);
    public NumberSetting gap = new NumberSetting("gap", this, 0, 0, 5, .1);
    public NumberSetting width = new NumberSetting("width", this, 0, 0, 5, .1);
    public NumberSetting size = new NumberSetting("size", this, 0, 0, 5, .1);
    public NumberSetting dynamicgap  = new NumberSetting("dynamicgap", this, 0, 0, 5, .1);
    private BooleanSetting dynamic = new BooleanSetting("dynamic", this, true);
    private int color = new Color(0xff4d4c).getRGB();
    public ColorPickerSetting colorPickerSetting = new ColorPickerSetting("ColorCrossair", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);
    private double animation = 0;


    public void render() {
        ScaledResolution sr = new ScaledResolution(MC);
        int midWidth = sr.getScaledWidth() / 2;
        int midHeight = sr.getScaledHeight() / 2;

        render(midWidth, midHeight);

    }

    public void render(int x, int y) {
        final double middlex = x;
        final double middley = y;
        // top box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered(middlex - (width.getValue()), middley - (gap.getValue() + length.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), middlex + (width.getValue()), middley - (gap.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), 0.5, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // bottom box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered(middlex - (width.getValue()), middley + (gap.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), middlex + (width.getValue()), middley + (gap.getValue() + length.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), 0.5,  ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // left box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered(middlex - (gap.getValue() + length.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), middley - (width.getValue()), middlex - (gap.getValue()) - ((ClientHelper.INSTANCE.moving()&& dynamic.getValue()) ? dynamicgap.getValue() : 0), middley + (width.getValue()), 0.5,  ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // right box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered(middlex + (gap.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), middley - (width.getValue()), middlex + (gap.getValue() + length.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), middley + (width.getValue()), 0.5,  ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);

    }


    public static Crossair getInstance() {
        return ModuleManager.getInstance(Crossair.class);
    }

    @EventTarget
    public void onRender(Render2DEvent e) {
        render();
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
