package net.minecraft.client.gui;

import beta.tiredb56.api.extension.Extension;
import beta.tiredb56.api.guis.altmanager.AltManager;
import beta.tiredb56.api.guis.creditsandupdates.GUI;
import beta.tiredb56.api.particle.ParticleRenderer;
import beta.tiredb56.api.performanceMode.PerformanceGui;
import beta.tiredb56.api.performanceMode.UsingType;
import beta.tiredb56.api.util.FileUtil;
import beta.tiredb56.api.util.TimeStorage;
import beta.tiredb56.api.util.Translate;
import beta.tiredb56.api.util.font.CustomFont;
import beta.tiredb56.api.util.font.FontManager;
import beta.tiredb56.interfaces.FHook;
import beta.tiredb56.shader.Shader;
import beta.tiredb56.shader.list.ArrayListShader;
import beta.tiredb56.shader.list.BackGroundShader;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.ShaderRenderer;
import beta.tiredb56.ui.userinterface.UIManager;
import beta.tiredb56.ui.userinterface.renderers.UIMainMenu;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.reflect.Reflector;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private int blurRadius = 0;

    /**
     * Counts the number of screen updates.
     */
    private final Shader shader;
    //    public String[] strings = new String[]{"+animations", "/chat only in hud bug fixxed", "+Smooth scroll", "+Changelog", "+better designed login ui", "new ui buttons with shader", "+smooth slider", "/fixxed blocksmc speed jump in air", "/Better mainmenu design", "/password now censored", "+ added inventorymove", "/fixxed crossair toggled when not toggled", "/fixxed minecraft font is now really minecraft font.", "+added windows notification when client starting.", "+added push speed settings to velocity", "+ added blocksmc2 fly speed setting", "+ added fastplace speed setting", "+added rectangle back to chat", "+fixxed keybind bug when first starting client", "-removed browser test module", "/improved code", "+ better performance when using nametags", "/ fixxed nametags not always working", "-Removed motion blur because bad", "+ fixxed fastladder", "+added smooth option to hotbar", "added rotations to scaffoldwalk"};
    private float updateCounter;
    private Translate translate;
    private ParticleRenderer particleRenderer;
    private boolean extended = true;
    /**
     * The splash message.
     */

    private ArrayListShader arrayListShader = new ArrayListShader(0);
    private ArrayList<String> changeLog = new ArrayList<>();
    private static ArrayList<String> autismsus = new ArrayList<>();
    private String splashText;
    private GuiButton buttonResetDemo;

    private int lastY;

    /**
     * Timer used to rotate the panorama, increases every tick.
     */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;
    public static ArrayList<String> sorted = new ArrayList<>();
    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;

    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    /**
     * An array of all the paths to the panorama pictures.
     */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;
    private final PerformanceGui performanceGui = new PerformanceGui();

    /**
     * Minecraft Realms button.
     */
    private GuiButton realmsButton;
    private boolean L;
    private GuiScreen M;
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;

    public GuiMainMenu() {
        changeLog.add("Test");
        changeLog.add("autism");
        autismsus.addAll(changeLog);
        shader = new BackGroundShader(0);
        this.translate = new Translate(0, 0);
        this.openGLWarning2 = field_96138_a;
        this.L = false;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = (String) list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var12) {
            ;
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException var11) {
                    ;
                }
            }
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    private boolean a() {
        return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.enumFloat) && this.M != null;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.panoramaTimer;

        if (this.a()) {
            this.M.updateScreen();
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {

        if (PerformanceGui.usingType == UsingType.HIGH_PERFORMANCE) {

        }

        if (PerformanceGui.isState) {
            FileUtil.FILE_UTIL.savePerformanceMode(PerformanceGui.usingType);
        }
        blurRadius = 0;
        this.translate = new Translate(0, 0);
        particleRenderer = new ParticleRenderer();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }
        int defaultHeight = this.height / 4 + 48;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, defaultHeight + 94, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, defaultHeight + 94, 98, 20, I18n.format("menu.quit", new Object[0])));


        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.setConnectedToRealms(false);

        if (this.a()) {
            this.M.a(this.width, this.height);
            this.M.initGui();
        }
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        int defaultHeight = this.height / 4 + 48;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, defaultHeight + 24, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, defaultHeight + 45, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(1222, 10, 10, 200, 20, "UpdatesAndCredits"));
        if (Reflector.GuiModList_Constructor.exists()) {
            this.buttonList.add(this.realmsButton = new GuiButton(14, this.width / 2 - 100, defaultHeight + 54, 98, 20, I18n.format("Account Login", new Object[0]).replace("Minecraft", "").trim()));
            this.buttonList.add(this.modButton = new GuiButton(6, 20, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods", new Object[0])));
        } else {
            this.buttonList.add(this.realmsButton = new GuiButton(14, this.width / 2 - 100, defaultHeight + 66, I18n.format("Account Login", new Object[0])));
        }
        this.buttonList.add(new GuiButton(20, 10, 40, "Change Performance Mode"));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 20) {
            mc.displayGuiScreen(new PerformanceGui());
        }

        if (button.id == 1222) {
            this.mc.displayGuiScreen(new GUI());
        }

        if (button.id == 14) {
            mc.displayGuiScreen(new AltManager());
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
            this.mc.displayGuiScreen((GuiScreen) Reflector.newInstance(Reflector.GuiModList_Constructor, new Object[]{this}));
        }

        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null) {
                GuiYesNo guiyesno = GuiSelectWorld.makeDeleteWorldYesNo(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void f() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(this.openGLWarningLink)});
                } catch (Throwable throwable) {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        translate.interpolate(resolution.getScaledWidth(), resolution.getScaledHeight(), 4);
        GL11.glPushMatrix();
        GL11.glTranslatef(resolution.getScaledWidth() / 2f * 1.5f, resolution.getScaledHeight() / 2f  * 2.5f, 0);
        GL11.glScaled(translate.getX() / resolution.getScaledWidth(), translate.getY() / resolution.getScaledHeight(), 0);
        GL11.glTranslatef(-resolution.getScaledWidth() / 2f  * 1.5f, -resolution.getScaledHeight() / 2f  * 2.5f, 0);
        GlStateManager.disableAlpha();

        final int defaultHeight = this.height / 4 + 48;
        final double widthA = 140;
        Display.setTitle(CheatMain.TITLE_STRING + " FPS: " + Minecraft.getDebugFPS());

        blurRadius ++;

        int maxBlur = 50;

        CheatMain.INSTANCE.discordRPC.setStage(new String[] {"Real Fanta client no cap ", " Tired on top w. allah"});

        if (blurRadius > maxBlur) {
            blurRadius = maxBlur;
        }

        if (blurRadius < 0) {
            blurRadius = 0;
        }


        shader.render(0, 0, width, height);

        //   Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MIN_VALUE);
//        RenderUtil.glDrawRoundedRectEllipse(width / 3f, height / 4f + 15, width / 3, height / 4f + 45, RenderUtil.RoundingMode.FULL, 42, 5, Integer.MIN_VALUE);
        GlStateManager.popMatrix();

        ShaderRenderer.startBlur();
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRectangle(width / 2f - widthA, height / 4f + 25, width / 2f + widthA, height / 4f + 175, 12, Integer.MIN_VALUE);
        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);
        ShaderRenderer.startBlur();


        if (extended) {
            Gui.drawRect(width, 1, width - 211, 420, -1);
        }


        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);
        ShaderRenderer.stopBlur();
        FHook.login.drawCenteredString("TIRED", width / 2 - 2, height / 4 + 40, -1);

        final boolean isOver = isOver(width - 25, 5, 16, 10, mouseX, mouseY);


        FontManager.SFPROBig.drawString("...", width - 25, 1, isOver ? Integer.MIN_VALUE : -1);

        ArrayList<String> mods = new ArrayList<>(changeLog);
        mods.sort(Comparator.comparingDouble(m -> -FontManager.SFPRO.getStringWidth(m)));
        if (extended) {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            lastY = 30;
            FontManager.SFPROBig.drawStringWithShadow(formatter.format(date), (width - 138), lastY, -1);

        }

        if (extended) {
            Gui.drawRect(width, lastY + 49, width - 211, lastY + 50, Integer.MIN_VALUE);
        }

        if (extended) {

            long estimatedTime = System.currentTimeMillis() - TimeStorage.currentTime;

            int seconds = (int) (estimatedTime / 1000) % 60;
            int minutes = (int) ((estimatedTime / (1000 * 60)) % 60);
            int hours = (int) ((estimatedTime / (1000 * 60 * 60)) % 24);


            FontManager.SFPROBig.drawString("ClientVersion: " + CheatMain.INSTANCE.VERSION, width - 200, lastY + 58 + .5f, Integer.MIN_VALUE);
            FontManager.SFPROBig.drawString("PlayTime: " + hours + "h " + minutes + "m, " + seconds + "s", width - 200, lastY + 81 + .5f, Integer.MIN_VALUE);

            arrayListShader.drawShader();
            FontManager.SFPROBig.drawString("PlayTime: " + hours + "h " + minutes + "m, " + seconds + "s", width - 200, lastY + 81, -1);
            FontManager.SFPROBig.drawString("ClientVersion: " + CheatMain.INSTANCE.VERSION, width - 200, lastY + 58, -1);
            arrayListShader.stop();
        }

        if (Reflector.FMLCommonHandler_getBrandings.exists()) {
            Object object = Reflector.call(Reflector.FMLCommonHandler_instance);
            List list = Lists.<String>reverse((List) Objects.requireNonNull(Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[]{Boolean.valueOf(true)})));

            for (int l1 = 0; l1 < list.size(); ++l1) {
                String s1 = (String) list.get(l1);

                if (!Strings.isNullOrEmpty(s1)) {
                    this.drawString(this.fontRendererObj, s1, 2, this.height - (10 + l1 * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
                }
            }

            if (Reflector.ForgeHooksClient_renderMainMenu.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_renderMainMenu, this, this.fontRendererObj, this.width, this.height);
            }
        } else {
            FontManager.robotoF.drawStringWithShadow2("Based on 1.8.9 coded by Felix1337", 2, this.height - 10, -1);
        }

        String s2 = "Copyright Mojang AB. Do not distribute!";

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton) this.buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.a()) {
            this.M.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (this.modUpdateNotification != null) {
            this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        final boolean isOver = isOver(width - 25, 5, 16, 10, mouseX, mouseY);
        if (isOver) {

            extended = !extended;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        if (this.a()) {
            this.M.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        if (this.M != null) {
            this.M.onGuiClosed();
        }
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }


    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
