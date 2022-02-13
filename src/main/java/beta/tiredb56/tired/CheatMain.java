package beta.tiredb56.tired;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.femboydrawer.ExternalImageDrawer;
import beta.tiredb56.api.femboydrawer.FemboyScraper;
import beta.tiredb56.api.guis.ConfigGui;
import beta.tiredb56.api.guis.clickgui.ClickGui;
import beta.tiredb56.api.guis.clickgui.setting.SettingsManager;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.api.util.DiscordRPC;
import beta.tiredb56.api.util.FileUtil;
import beta.tiredb56.api.util.GitHubDownloader;
import beta.tiredb56.api.util.WindowsTray;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.config.ConfigManager;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.module.ModuleManager;
import beta.tiredb56.module.impl.list.visual.Blur;
import beta.tiredb56.module.impl.themes.IntegerT;
import beta.tiredb56.module.impl.themes.Tired;
import beta.tiredb56.performanceImprovement.EssentialBootups;
import beta.tiredb56.shader.list.BlurShader;
import beta.tiredb56.ui.userinterface.UIManager;
import lombok.Getter;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.Objects;

public enum CheatMain {

    INSTANCE("Tired", "60", new String[]{"Felix1337"});

    public static final String PROJECT_NAME = "5 Months of Tired Client! (still buggy lol) ";

    public static PerformanceGui performanceGui;

    public ShaderRenderer shaderRenderer;

    public SettingsManager settingsManager;

    public ModuleManager moduleManager;

    public UIManager uiMainMenu;

    public FontManager fontManager;

    public ConfigManager configManager;

    public ClickGui clickGui;

    public EssentialBootups bootups;

    public DiscordRPC discordRPC;

    public ConfigGui configGui;

    public static String TITLE_STRING;

    public void doClient() {

        Thread thread = new Thread(TiredCore.CORE::initCore);
        thread.start();


        this.uiMainMenu = new UIManager();
        this.settingsManager = new SettingsManager();
        FileUtil.FILE_UTIL.loadPerformanceMode();
        this.fontManager = new FontManager();
        this.fontManager.bootstrap();
        Extension.EXTENSION.setupExtension();
        bootups = new EssentialBootups();
        moduleManager = new ModuleManager();
        clickGui = new ClickGui();
        TITLE_STRING = CLIENT_NAME + " b" + VERSION + " : " + "Coded by " + CODER[0] + " ProjectBuildAlias: " + PROJECT_NAME + " - 03.02.2022)";
        Display.setTitle(TITLE_STRING);
        FileUtil.FILE_UTIL.loadKeybinds();
        FileUtil.FILE_UTIL.loadSettings();
        GitHubDownloader.GIT_HUB_DOWNLOADER.download();
        this.configManager = new ConfigManager();
        this.configManager.init();
        FileUtil.FILE_UTIL.loadColors();
        FemboyScraper.getAndDownloadPosts(12);



        FileUtil.FILE_UTIL.loadTime();
        FileUtil.FILE_UTIL.loadAlt();
        FileUtil.FILE_UTIL.getClientData();

        shaderRenderer = new ShaderRenderer();

        performanceGui = new PerformanceGui();
        configGui = new ConfigGui();

        {
            CheatMain.INSTANCE.moduleManager.findModuleByClass(Tired.class).setState(false);
            CheatMain.INSTANCE.moduleManager.findModuleByClass(IntegerT.class).setState(true);
        }

        try {
            WindowsTray.WINDOWS_TRAY.displayTray("Welcome to tired. current build: " + VERSION);
        } catch (AWTException e) {
            e.printStackTrace();
        }


        if (PerformanceGui.usingType == UsingType.NORMAL_PERFORMANCE) {
            moduleManager.moduleBy(Blur.class).setState(true);
        }


        if (IHook.MC.gameSettings.ofFastRender)
            IHook.MC.gameSettings.ofFastRender = false;


        if (IHook.MC.gameSettings.fancyGraphics) {
            IHook.MC.gameSettings.fancyGraphics = false;
        }

    }

    @Getter
    public final String CLIENT_NAME;
    @Getter
    public final String VERSION;

    public final String[] CODER;


    CheatMain(String clientName, String clientVersion, String[] coder) {
        this.CLIENT_NAME = clientName;
        this.VERSION = clientVersion;
        this.CODER = coder;
    }


    public BlurShader getBlurShader(int radius) {
        return bootups.getBlurShader(radius);
    }

    public ClickGui getClickGui() {
        return clickGui;
    }
}
