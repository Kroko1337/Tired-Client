package beta.tiredb56.api.util;

import beta.tiredb56.interfaces.IHook;
import beta.tiredb56.tired.CheatMain;

import lombok.SneakyThrows;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum GitHubDownloader implements IHook {

    GIT_HUB_DOWNLOADER("https://github.com/FelixH2012/TiredConfigs");

    private final String mainPath = MC.mcDataDir + "/" + CheatMain.INSTANCE.CLIENT_NAME + "/onlineconfigs";


    @SneakyThrows
    public void download() {
    }

    final String LINK;

    GitHubDownloader(String link) {
        this.LINK = link;
    }

}
