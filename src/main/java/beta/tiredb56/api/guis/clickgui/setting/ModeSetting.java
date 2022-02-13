package beta.tiredb56.api.guis.clickgui.setting;


import beta.tiredb56.api.guis.clickgui.setting.impl.DeviderSetting;
import beta.tiredb56.module.Module;

import java.util.function.Supplier;

public class ModeSetting extends Setting {

    private String value;
    private String[] options;
    private int modeIndex;

    public ModeSetting(String name, Module parent, String[] options, Supplier<Boolean> dependency, DeviderSetting deviderSetting) {
        super(name, parent, dependency, deviderSetting);
        this.options = options;
        this.value = options[0];
        this.modeIndex = 0;
    }

    public ModeSetting(String name, Module parent, String[] options, Supplier<Boolean> dependency) {
        this(name, parent, options, dependency, null);
    }

    public ModeSetting(String name, Module parent, String[] options) {
        this(name, parent, options, () -> true);
    }

    public String[] getOptions() {
        return this.options;
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