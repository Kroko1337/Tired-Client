package beta.tiredb56.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import beta.tiredb56.api.guis.clickgui.setting.ModeSetting;
import beta.tiredb56.api.guis.clickgui.setting.NumberSetting;
import beta.tiredb56.api.guis.clickgui.setting.Setting;
import beta.tiredb56.api.guis.clickgui.setting.impl.BooleanSetting;
import beta.tiredb56.api.guis.clickgui.setting.impl.ColorPickerSetting;
import beta.tiredb56.module.Module;
import beta.tiredb56.tired.CheatMain;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Config {

	private final String name;

	public String version;

	public String creationTime;

	public Config(String name) {
		this.name = name;
	}

	public JsonObject serialize() {
		JsonObject jsonObject = new JsonObject();
		CheatMain.INSTANCE.moduleManager.getModuleList().forEach(module -> {
			if (!module.getName().equalsIgnoreCase("configs")) {

				JsonObject moduleObject = new JsonObject();
//                moduleObject.addProperty("Keybind", module.getKey());
				moduleObject.addProperty("State", module.isState());
				JsonObject settingsObject = new JsonObject();

				CheatMain.INSTANCE.settingsManager.getSettingsByMod(module).forEach(setting -> {
					if (setting instanceof BooleanSetting) {
						settingsObject.addProperty(setting.getName(), ((BooleanSetting) setting).getValue());
					}
					if (setting instanceof ModeSetting) {
						settingsObject.addProperty(setting.getName(), ((ModeSetting) setting).getValue());
					}
					if (setting instanceof NumberSetting) {
						settingsObject.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
					}
					if (setting instanceof ColorPickerSetting) {
						System.out.println(setting.getName() + " " + setting.getValue());
						settingsObject.addProperty(setting.getName(), "" + ((ColorPickerSetting) setting).getColorPickerColor());
					}

				});
				moduleObject.add("Settings", settingsObject);
				Calendar currentTimeNow = Calendar.getInstance();
				settingsObject.addProperty("Version: ", "b" +CheatMain.INSTANCE.VERSION);
				settingsObject.addProperty("Time: ", currentTimeNow.getTime() + "");
				jsonObject.add(module.getName(), moduleObject);
			}
		});
		return jsonObject;
	}

	public boolean deserialize(JsonElement jsonElement) {
		try {
			if (jsonElement instanceof JsonNull)
				return false;

			final List<Module> antiStackOverflow = CheatMain.INSTANCE.moduleManager.getModuleList();
			for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
				for (final Module module : antiStackOverflow) {
					if (entry.getKey().equalsIgnoreCase(module.getName())) {
						final JsonObject jsonModule = (JsonObject) entry.getValue();
						if (!module.getName().equalsIgnoreCase("configs")) {
							if (module.isState() != jsonModule.get("State").getAsBoolean()) {
								module.executeMod();
							}

//                            module.setKey(jsonModule.get("Keybind").getAsInt());

							JsonObject settings = jsonModule.get("Settings").getAsJsonObject();
							for (Map.Entry<String, JsonElement> setting : settings.entrySet()) {
								if (CheatMain.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName()) != null) {
									Setting set = CheatMain.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName());

									if (set instanceof BooleanSetting) {
										((BooleanSetting) set).setValue(setting.getValue().getAsBoolean());
									}

									if (set instanceof ModeSetting) {
										((ModeSetting) set).setValue(setting.getValue().getAsString());
										if (Arrays.asList(((ModeSetting) set).getOptions()).contains(setting.getValue().getAsString())) {
											((ModeSetting) set).setModeIndex(Arrays.asList(((ModeSetting) set).getOptions()).indexOf(setting.getValue().getAsString()));
										}
									}
									if (set instanceof NumberSetting) {
										((NumberSetting) set).setValue(setting.getValue().getAsDouble());
									}

								} else {
									if (setting.getValue().getAsString().startsWith("b")) {
										version = setting.getValue().getAsString();
									} else {
										creationTime = setting.getValue().getAsString();
									}
								}
							}

						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean loadOnlyNoSet(JsonElement jsonElement) {
		try {
			if (jsonElement instanceof JsonNull)
				return false;

			final List<Module> antiStackOverflow = CheatMain.INSTANCE.moduleManager.getModuleList();
			for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
				for (final Module module : antiStackOverflow) {
					if (entry.getKey().equalsIgnoreCase(module.getName())) {
						final JsonObject jsonModule = (JsonObject) entry.getValue();
						if (!module.getName().equalsIgnoreCase("configs")) {

							JsonObject settings = jsonModule.get("Settings").getAsJsonObject();
							for (Map.Entry<String, JsonElement> setting : settings.entrySet()) {
								if (CheatMain.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName()) != null) {
									Setting set = CheatMain.INSTANCE.settingsManager.settingBy(setting.getKey(), module.getName());

									if (set instanceof ModeSetting) {
										setting.getValue().getAsString();
									}

								} else {
									if (setting.getValue().getAsString().startsWith("b")) {
										version = setting.getValue().getAsString();
									} else {
										creationTime = setting.getValue().getAsString();
									}
								}
							}

						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String name() {
		return name;
	}
}