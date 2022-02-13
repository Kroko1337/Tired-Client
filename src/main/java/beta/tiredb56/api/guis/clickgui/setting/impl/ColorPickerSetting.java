package beta.tiredb56.api.guis.clickgui.setting.impl;

import beta.tiredb56.api.guis.clickgui.setting.Setting;
import beta.tiredb56.module.Module;

import java.awt.*;
import java.util.function.Supplier;

public class ColorPickerSetting extends Setting {

	private String value;
	private boolean colorPicker;
	private int modeIndex;
	private boolean ColorPicker;
	public float HUE;
	public Color ColorPickerC;

	public ColorPickerSetting(String name, Module parent, boolean colorPicker, Color color, int HUE, Supplier<Boolean> dependency) {
		super(name, parent, dependency, colorPicker, color, HUE);
		this.colorPicker = colorPicker;
		this.ColorPickerC = color;
		this.HUE = (float) HUE;
		this.modeIndex = 0;
	}

	public Color getColorPickerColor() {
		return this.ColorPickerC;
	}

	public Color getColorPickerColor2() {
		return this.ColorPickerC;
	}

	public Color setColorPickerColor(Color color) {
		return ColorPickerC = color;
	}

	public void setHUE(int hue) {
		this.HUE = (float) hue;
	}

	public Color getColorPickerColor3() {
		return this.ColorPickerC;
	}

	public boolean getColorPicker() {
		return this.ColorPicker;
	}

	public float getHUE() {
		return this.HUE;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getModeIndex() {
		return modeIndex;
	}

	public void setModeIndex(int modeIndex) {
		this.modeIndex = modeIndex;
	}
}