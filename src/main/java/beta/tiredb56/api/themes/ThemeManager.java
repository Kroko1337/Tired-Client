package beta.tiredb56.api.themes;

public class ThemeManager {

    public enum Themes {
        TIRED, FANTA
    }

    public Themes theme;

    public ThemeManager(Themes theme) {
        this.theme = theme;
    }


}
