package beta.tiredb56.api.guis.clickgui.impl;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.extension.processors.extensions.generally.RenderProcessor;
import beta.tiredb56.api.guis.clickgui.AbstractClickGui;
import beta.tiredb56.api.guis.clickgui.impl.list.TextBoxButton;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.api.util.Trie;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.module.ModuleCategory;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

public class Panel extends AbstractClickGui {

    private final ModuleCategory moduleCategory;

    public int x, y, dragX, dragY;

    public boolean over;
    public ArrayList<ModuleList> moduleListArrayList;
    public double animation;

    public static String suggestedString = "";
    public static String abstractString = "";
    public int animationHover;
    public int animationRight;
    private ArrayList<ModuleList> lists;
    public Panel(int x, int y, ModuleCategory category) {

        this.x = x;
        this.y = y;
        this.moduleCategory = category;

        moduleListArrayList = new ArrayList<>();

        for (Module module : CheatMain.INSTANCE.moduleManager.getModuleList()) {
            if (module.getModuleCategory() != this.moduleCategory)
                continue;
            moduleListArrayList.add(new ModuleList(module));
            lists = new ArrayList<>(moduleListArrayList);
        }

    }


    @Override
    public void drawInterface(int mouseX, int mouseY, boolean rectangle) {
        if (dragging) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;
        }

        final String categoryString = moduleCategory.displayName;

        animation = AnimationUtil.getAnimationState(animation, extended ? NORMAL_HEIGHT : 0, 120);

        final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);

        boolean mouseOver = (isOver(x, y, (int) NORMAL_WIDTH, (int) NORMAL_HEIGHT, mouseX, mouseY));
        int anim = mouseOver ? 244 : 0;

        animationHover = (int) AnimationUtil.getAnimationState(animationHover, anim, Math.max(0.6D, Math.abs((double) this.animationHover - anim)));

        if (!(moduleCategory == ModuleCategory.THEME)) {
            if (!Flat1.isState()) {
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(x, y, (int) (x + NORMAL_WIDTH), (int) (y + NORMAL_HEIGHT) + 2, 2, new Color(20, 20, 20).getRGB());

                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect(x, y, (int) (x + NORMAL_WIDTH), (int) (y + NORMAL_HEIGHT) + 2, 2, new Color(30, 30, 30, animationHover).getRGB());

                GlStateManager.resetColor();
                GlStateManager.disableBlend();

                FontManager.robotoF.drawString2(categoryString, (calculateMiddle(categoryString, FontManager.robotoF, x, (int) NORMAL_WIDTH)), extended ? (int) ((float) y + (float) NORMAL_HEIGHT - 14) : (int) ((float) y + (float) NORMAL_HEIGHT - 13), -1);
            } else {
                int height = (int) ((float) y + (float) NORMAL_HEIGHT - 11);

                if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {
                    height += 2;
                }
                if (rectangle) {
                    RenderProcessor.drawGradientSideways(x, y, (int) (x + NORMAL_WIDTH), (int) (y + NORMAL_HEIGHT) + 2, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.brighter().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB());
                }
                GlStateManager.resetColor();
                GlStateManager.disableBlend();

                FHook.big2.drawString(categoryString, (calculateMiddle(categoryString, FHook.big2, x, (int) NORMAL_WIDTH)),height, new Color(244, 244, 244, 210).getRGB());
            }
        } else {
            if (!Flat1.isState()) {
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(x, y, (int) (x + NORMAL_WIDTH), (int) (y + NORMAL_HEIGHT) + 2, new Color(20, 20, 20).getRGB());
                FHook.fontRenderer.drawString2(categoryString, x + 4, extended ? (int) ((float) y + (float) NORMAL_HEIGHT - 14) : (int) ((float) y + (float) NORMAL_HEIGHT - 13), -1);
            } else {
                if (rectangle) {
                    RenderProcessor.drawGradientSideways(x, y, (int) (x + NORMAL_WIDTH), (int) (y + NORMAL_HEIGHT) + 2, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.brighter().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB());
                    GlStateManager.resetColor();
                    GlStateManager.disableBlend();
                }
                int height = (int) ((float) y + (float) NORMAL_HEIGHT - 11);

                if (PerformanceGui.usingType == null || PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {
                    height += 2;
                }

                FHook.big2.drawString(categoryString, (calculateMiddle(categoryString, FHook.big2, x, (int) NORMAL_WIDTH)),height, -1);
            }

        }
        if (extended) {
            GlStateManager.resetColor();
            GlStateManager.disableBlend();
            int yAxis = (int) (0 + animation);

            if (!TextBoxButton.typing) {
                lists.removeIf(m -> !m.module.getName().toLowerCase().contains(m.typedString.toLowerCase()));
                for (ModuleList list : lists) {
                    abstractString = list.typedString;
                }
            } else {
                lists.clear();
                lists.addAll(moduleListArrayList);
            }

            for (ModuleList list : lists) {
                animationRight = (int) list.animationRight;
                list.renderInterface(mouseX, mouseY, x, y + yAxis, mouseX, mouseY);
                yAxis += animation;
            }
        }
    }


    public void onInitGUI() {
        for (ModuleList list : moduleListArrayList) {
            list.onInitGUI();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

        for (ModuleList list : moduleListArrayList) {
            lists = new ArrayList<>(moduleListArrayList);
            list.keyTyped(typedChar, keyCode);
            abstractString = list.typedString;

            ArrayList<String> names = new ArrayList<>();

            for (Module jude : CheatMain.INSTANCE.moduleManager.getModuleList()) {
                names.add(jude.name.toLowerCase());
            }

            Trie trie = new Trie(names);

            suggestedString = trie.suggest(list.typedString.toLowerCase()).toString();

            //System.out.println(trie.suggest(list.typedString.toLowerCase()));

        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean mouseOver = (isOver(x, y, (int) NORMAL_WIDTH, (int) NORMAL_HEIGHT, mouseX, mouseY));

        if (mouseOver) {
            if (mouseButton == 0) {
                this.dragX = x - mouseX;
                this.dragY = y - mouseY;
                this.dragging = true;
            } else if (mouseButton == 1) {
                this.extended = !extended;
            }
        }

        if (this.extended)
            lists.forEach(moduleButton -> moduleButton.mouseClicked(mouseX, mouseY, mouseButton));

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void mouseReleased(int mouseX, int mouseY) {

        this.dragging = false;
        if (this.extended)
            lists.forEach(moduleButton -> moduleButton.mouseReleased(mouseX, mouseY));
        super.mouseReleased(mouseX, mouseY);
    }
}
