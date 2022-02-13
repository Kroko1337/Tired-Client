package beta.tiredb56.api.guis.clickgui.setting;

import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;

import java.util.ArrayList;

public class SettingsManager {

    private ArrayList<Setting> settings;

    public SettingsManager(){
        this.settings = new ArrayList<>();
    }

    public void rSetting(Setting in) {
        boolean contains = false;
        for (Setting s : this.settings) {
            if (s.getModule().getName().equalsIgnoreCase(in.getModule().getName()) && s.getName().equalsIgnoreCase(in.getName())) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            this.settings.add(in);
        }
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod){
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getModule().equals(mod)){
                out.add(s);
            }
        }
        return out;
    }

    public ArrayList<Setting> getSettingsByMod(String mod){
        Module module = CheatMain.INSTANCE.moduleManager.moduleBy(mod);
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getModule().equals(module)){
                out.add(s);
            }
        }
        return out;
    }

    public ArrayList<String> getSettingsNameByMod(Module mod){
        ArrayList<String> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getModule().equals(mod)){
                out.add(s.getName());
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting settingBy(String name, Module mod){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name) && set.getModule().equals(mod)){
                return set;
            }
        }
        return null;
    }

    public Setting settingBy(String name, String mod) {
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name) && set.getModule().getName().equalsIgnoreCase(mod)){
                return set;
            }
        }
        return null;
    }
}
