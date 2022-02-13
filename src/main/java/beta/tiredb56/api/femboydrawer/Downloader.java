package beta.tiredb56.api.femboydrawer;

import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.tired.CheatMain;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Downloader implements IHook {

    public static void download(String url) throws IOException {

        InputStream in;
        try {
            in = new URL(url).openStream();

            final File file = new File( MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME + "/femboys");
            if (!file.exists()) {
                file.mkdirs();
            }


            Files.copy(in, new File(MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME + "/femboys",  url.substring(url.length() - 5)).toPath(), StandardCopyOption.REPLACE_EXISTING);
            //        System.out.println(file.getName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}