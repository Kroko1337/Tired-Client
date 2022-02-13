package beta.tiredb56.module;

public enum ModuleCategory {

	COMBAT("Combat"),
	MOVEMENT("Movement"),
	PLAYER("Player"),
	RENDER("Render"),
	MISC("Misc"),
	THEME("Theme");
	public final String displayName;

	ModuleCategory(String displayName) {

		this.displayName = displayName;
	}

}
