package beta.tiredb56.api.guis.clickgui.impl;

import beta.tiredb56.api.guis.clickgui.impl.list.*;
import beta.tiredb56.api.util.Scissoring;
import beta.tiredb56.module.impl.list.visual.ClickGUI;
import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.clickgui.AbstractClickGui;

import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.guis.clickgui.setting.Setting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.TextBoxSetting;
import beta.tiredb56.api.util.Translate;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.module.Module;
import beta.tiredb56.api.util.renderapi.AnimationUtil;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.tired.CheatMain;

import beta.tiredb56.api.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;

public class ModuleList extends AbstractClickGui {

    public final Module module;

    private final ArrayList<CheckBox> checkBoxArrayList;
    private final ArrayList<Slider> sliders;
    private final ArrayList<ModeSelector> modeSelectors;
    private final ArrayList<ColorPicker> colorPickers;
    private final ArrayList<TextBoxButton> textBoxButtons;
    private double x, y;
    @Getter
    @Setter
    public String typedString = "";
    public boolean allSelected = false;
    private boolean typing = false;
    private int overTicks = 0;
    private int scrollAmount = 4;
    public float animationY;
    public float animationRight;
    public int animationHover;

    private int circleAnim;

    public ModuleList(Module module) {
        this.module = module;
        this.checkBoxArrayList = new ArrayList<>();
        this.modeSelectors = new ArrayList<>();
        this.colorPickers = new ArrayList<>();
        this.sliders = new ArrayList<>();
        this.textBoxButtons = new ArrayList<>();
        if (CheatMain.INSTANCE.settingsManager.getSettingsByMod(module) != null) {
            for (Setting s : CheatMain.INSTANCE.settingsManager.getSettingsByMod(module)) {
                if (s instanceof BooleanSetting) {
                    checkBoxArrayList.add(new CheckBox((BooleanSetting) s));
                } else if (s instanceof NumberSetting) {
                    sliders.add(new Slider((NumberSetting) s));
                } else if (s instanceof ModeSetting) {
                    modeSelectors.add(new ModeSelector((ModeSetting) s, module));
                } else if (s instanceof ColorPickerSetting) {
                    colorPickers.add(new ColorPicker((ColorPickerSetting) s));
                } else if (s instanceof TextBoxSetting) {
                    textBoxButtons.add(new TextBoxButton((TextBoxSetting) s));
                }
            }
        }
    }

    @Override
    public void renderInterface(int mouseX, int mouseY, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;

        final Module Flat1 = CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class);

        if (!Flat1.isState()) {
            Gui.drawRect(x, y - 1, x + NORMAL_WIDTH, y + NORMAL_HEIGHT + 1, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
            Gui.drawRect(x, y - 1, x + NORMAL_WIDTH, y + NORMAL_HEIGHT - 1, new Color(20, 20, 20).getRGB());

            final double sus = module.isState() ? NORMAL_WIDTH : 0;
            animationRight = (float) AnimationUtil.getAnimationState(animationRight, sus, Math.max(4.6D, Math.abs((double) this.animationRight - sus) * 2));

            Gui.drawRect(x, y - 1, x + animationRight, y + NORMAL_HEIGHT, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB());
            //	Gui.drawRect(x + NORMAL_WIDTH - 2, y, x + NORMAL_WIDTH, y + NORMAL_HEIGHT - 1, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

            boolean over = isOver((int) x, (int) y, (int) NORMAL_WIDTH, (int) NORMAL_HEIGHT, (int) width, (int) height);

            int anim = over ? 244 : 0;

            animationHover = (int) AnimationUtil.getAnimationState(animationHover, anim, Math.max(0.6D, Math.abs((double) this.animationHover - anim)));

            Gui.drawRect(x, y - 1, x + NORMAL_WIDTH, y + NORMAL_HEIGHT, new Color(40, 40, 40, animationHover).getRGB());

            if (TextBoxButton.typing) {
                typedString = "";
            }


            if (!typedString.isEmpty()) {
                if (module.name.toLowerCase().contains(typedString.toLowerCase())) {
                    Gui.drawRect(x, y - 1, x + NORMAL_WIDTH, y + NORMAL_HEIGHT - 1, new Color(50, 50, 50).getRGB());
                    //	FHook.fontRenderer.drawString2(module.getName(), (calculateMiddle(module.getName(), FHook.fontRenderer, (int) x, (int) NORMAL_WIDTH)), (int) ((float) y + (float) NORMAL_HEIGHT - 14), -1);
                }
            }



            FontManager.robotoF.drawStringWithShadow(module.getName(), (calculateMiddle(module.getName(), FontManager.robotoF, (int) x, (int) NORMAL_WIDTH)), (int) ((float) y + (float) NORMAL_HEIGHT - 14), -1);

            if (!checkBoxArrayList.isEmpty() || !sliders.isEmpty() || !modeSelectors.isEmpty()) {
                //FontManager.entypo.drawStringWithShadow("i", (int) x + (int) NORMAL_WIDTH - 9, (int) y + 1, -1);
                GlStateManager.resetColor();
            }
        } else {

            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(x, y + 2, x + NORMAL_WIDTH, y + NORMAL_HEIGHT + 2, Integer.MIN_VALUE);

            GlStateManager.resetColor();
            GlStateManager.disableBlend();
            float anim = module.isState() ? 80 : 0;
            circleAnim = (int) AnimationUtil.getAnimationState(circleAnim, anim, Math.max(3.6D, Math.abs((double) circleAnim  - 310)));

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            Scissoring.SCISSORING.doScissor((float) x, (float) y + 2, (float) x + (float) NORMAL_WIDTH, (float) y + (float) NORMAL_HEIGHT + 2);
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(x + (NORMAL_WIDTH / 2), y + 10, circleAnim, Integer.MIN_VALUE);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            FHook.fontRenderer.drawStringWithShadow(module.getName(), (calculateMiddle(module.getName(), FHook.fontRenderer, (int) x, (int) NORMAL_WIDTH)), (int) ((float) y + (float) NORMAL_HEIGHT - 11), -1);

        }

        GlStateManager.resetColor();
        GlStateManager.disableBlend();

        //FHook.fontRenderer.drawString2(getTypedString(), scaledResolution.getScaledWidth() / 2, 10, -1);
        GlStateManager.resetColor();
        GlStateManager.disableBlend();

        int size = 0;
        if (this.extended) {
            int yAxis = 0;

            int wheel = Mouse.getDWheel();

            if (wheel < 0) {
                if (yAxis + height > this.NORMAL_HEIGHT) scrollAmount -= 16;
            } else if (wheel > 0) {
                scrollAmount += 34;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }


            this.animationY = (float) AnimationUtil.getAnimationState((double) this.animationY, scrollAmount, Math.max(0.6D, Math.abs((double) this.animationY - animationY)) * 282);
            GlStateManager.pushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            Scissoring.SCISSORING.scissorOtherWay(x, 0, x + NORMAL_WIDTH + 220, 485.0);
            Scissoring.SCISSORING.scissorOtherWay(x, y - 23, (int) x + NORMAL_WIDTH + 220, 315);

            for (CheckBox checkBox : checkBoxArrayList) {
               if (checkBox.value.isVisible()) {
                   checkBox.drawCheckBox((int) x + (int) NORMAL_WIDTH + 5, (int) (checkBoxArrayList.size() > 225 ? (int) y + yAxis - 44 + animationY : (int) (y + yAxis) + animationY), (int) width, (int) height);
                   size = checkBoxArrayList.size();
                   yAxis += checkBox.HEIGHT;
               }

            }

            for (TextBoxButton textBoxButton : textBoxButtons) {
                if (TextBoxButton.typing) {
                    typing = false;
                }
                if (textBoxButton.set.isVisible()) {
                    textBoxButton.drawTextBox((int) x + (int) NORMAL_WIDTH + 5, (int) (textBoxButtons.size() > 225 ? (int) y + yAxis - 44 + animationY : (int) (y + yAxis) + animationY), (int) width, (int) height);
                    size = textBoxButtons.size();
                    yAxis += textBoxButton.HEIGHT;
                }
            }
            for (ColorPicker colorPickers : colorPickers) {
                if (colorPickers.s.isVisible()) {
                    colorPickers.draw((int) x + (int) NORMAL_WIDTH + 5, (int) (checkBoxArrayList.size() > 225 ? (int) y + yAxis - 44 + animationY : (int) (y + yAxis) + animationY), (int) width, (int) height);
                    size = checkBoxArrayList.size();
                    yAxis += ColorPicker.HEIGHT;
                }
            }

            for (ModeSelector modeSelector : modeSelectors) {
                if (modeSelector.modeSetting.isVisible()) {
                    modeSelector.drawSlider((int) x + (int) NORMAL_WIDTH + 5, (int) (size > 225 ? (int) y + yAxis - 44 + animationY : (int) (y + yAxis) + animationY), (int) width, (int) height);
                    yAxis += modeSelector.HEIGHT;
                }
            }

            for (Slider slider : sliders) {
                if (slider.setting.isVisible()) {
                    slider.drawSlider((int) x + (int) NORMAL_WIDTH + 5, (int) (size > 225 ? (int) y + yAxis - 124 + animationY : (int) (y + yAxis) + animationY), (int) width, (int) height, false, false);
                    yAxis += slider.HEIGHT;
                }
            }



            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();
        } else {
            animationY = 0;
            for (Slider slider : sliders) {
                slider.animationX = 0;
                slider.animation = 0;
            }
            for (CheckBox checkBox : checkBoxArrayList) {
                checkBox.animationToggle = 0;
                checkBox.translate = new Translate(0, 0);
                checkBox.animation = 0;
                checkBox.float2 = 0;
            }

            for (TextBoxButton textBoxButton : textBoxButtons) {
                textBoxButton.animation = 0;
                textBoxButton.animation2 = 0;
                textBoxButton.allSelected = false;
                textBoxButton.typing = false;
            }

            for (ModeSelector modeSelector : modeSelectors) {
                modeSelector.animation = 0;
            }

            for (ColorPicker colorPickers : colorPickers) {
                colorPickers.animation = 0;
            }
        }

        final boolean over = isOver((int) x, (int) y, (int) NORMAL_WIDTH, (int) NORMAL_HEIGHT, mouseX, mouseY);
        if (over) {
            overTicks++;
        } else {
            overTicks = 0;
        }

        if (overTicks > 170) {
            if (!module.clickGUIText.equalsIgnoreCase("mod")) {
                Gui.drawRect(mouseX, mouseY - 6, mouseX + FontManager.confortaa.getStringWidth(module.clickGUIText) + 5, mouseY - 10 + NORMAL_HEIGHT, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
                Gui.drawRect(mouseX, mouseY - 5, mouseX + FontManager.confortaa.getStringWidth(module.clickGUIText) + 5, mouseY - 10 + NORMAL_HEIGHT, new Color(30, 30, 30).getRGB());
                FontManager.confortaa.drawStringWithShadow(module.clickGUIText, mouseX, mouseY - 4, -1);
            }
        }

        super.renderInterface(mouseX, mouseY, x, y, width, height);
    }

    public void onInitGUI() {
        circleAnim = 0;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        textBoxButtons.forEach(textBoxButton -> textBoxButton.keyTyped(typedChar, keyCode));
        Keyboard.enableRepeatEvents(true);

        typing = typedChar != 0 || !typedString.isEmpty();

        if (TextBoxButton.typing) {
            typing = false;
        }

        if (typing) {
            if (GuiScreen.isKeyComboCtrlA(keyCode) && !getTypedString().isEmpty()) {
                this.allSelected = true;
            }
            if (allSelected) {
                if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !getTypedString().isEmpty()) {
                    setTypedString("");
                    allSelected = false;
                }
            } else if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !getTypedString().isEmpty()) {
                setTypedString(getTypedString().substring(0, getTypedString().length() - 1));
            } else if (keyCode == Keyboard.KEY_RETURN) {
                this.typing = false;

            } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar) && (Objects.requireNonNull(getTypedString()).length() < 20)) {
                setTypedString(getTypedString() + typedChar);
            } else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
                setTypedString(getTypedString() + GuiScreen.getClipboardString());
            }

        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        sliders.forEach(slider -> slider.mouseReleased(mouseX, mouseY));
        dragging = false;
        super.mouseReleased(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final boolean over = isOver((int) x, (int) y, (int) NORMAL_WIDTH, (int) NORMAL_HEIGHT, mouseX, mouseY);

        if (mouseButton == 0) {
            dragging = true;

        }
        if (over) {
            switch (mouseButton) {
                case 0:
                    module.executeMod();
                    break;
                case 1:
                    this.extended = !extended;
            }
        }

        if (extended) {
            colorPickers.forEach(checkboxButton -> checkboxButton.mouseClicked(mouseX, mouseY, mouseButton));
            checkBoxArrayList.forEach(checkboxButton -> checkboxButton.mouseClicked(mouseX, mouseY, mouseButton));
            sliders.forEach(slider -> slider.mouseClicked(mouseX, mouseY, mouseButton));
            textBoxButtons.forEach(sus -> sus.mouseClicked(mouseX, mouseY, mouseButton));
            modeSelectors.forEach(modeSelectors -> modeSelectors.mouseClicked(mouseX, mouseY, mouseButton));
            FileUtil.FILE_UTIL.saveSettings();
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
