package beta.tiredb56.api.guis.femdrawer;

import beta.tiredb56.api.femboydrawer.ExternalImageDrawer;
import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.tired.CheatMain;
import beta.tiredb56.tired.TiredCore;
import lombok.SneakyThrows;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class GuiFemboy extends GuiScreen {
    private int scrollAmount = 4;
    private ArrayList<File> resourceLocations = new ArrayList<>();
    public static ArrayList<ExternalImageDrawer> externalImageDrawers = new ArrayList<>();
    private ExternalImageDrawer externalImage;

    public GuiFemboy() {

    }

    @SneakyThrows
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        final File folder = new File(IHook.MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME + "/femboys");

        int yAdd = 0;
        int x = 120;
        int y = 10;
        int wheel = Mouse.getDWheel();

        if (wheel < 0) {
            if (height > 12) scrollAmount -= 66;
        } else if (wheel > 0) {
            scrollAmount += 77;
            if (scrollAmount > 0)
                scrollAmount = 0;
        }

        for (File location : TiredCore.resourceLocations) {
            File inFile = new File(location.getPath());

            if (isPng(inFile)) {

                System.out.println(TiredCore.externalImageDrawers);

                for (ExternalImageDrawer externalImageDrawer : TiredCore.externalImageDrawers) {

                    externalImageDrawer.draw(20, scrollAmount + 20 + yAdd, 120, 120);

                    yAdd += 140;

                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @SneakyThrows
    @Override
    public void initGui() {

        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean isPng(File file) throws IOException {
        try (FileInputStream is = new FileInputStream(file)) {
            return is.read() == 137;
        }
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }


    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
}
