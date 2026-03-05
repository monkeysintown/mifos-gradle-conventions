package org.mifos.convention.project.boot.layout;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;

import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_MODULES_PATH_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_MODULE_FOLDER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MODULE_DOC_PATTERN;
import static org.mifos.convention.project.base.MifosProjectUtils.getProperty;
import static org.mifos.convention.project.base.MifosProjectUtils.include;

public class MifosProjectDocLayoutPlugin implements Plugin<Settings> {
    private static final Logger log = Logging.getLogger(MifosProjectDocLayoutPlugin.class);

    @Override
    public void apply(Settings settings) {
        // settings.getPlugins().apply(MifosProjectBaseLayoutPlugin.class);

        for(int i=0; i < MIFOS_PROJECT_MAX_SIZE; i++) {
            var projectNum = i;
            var properties = settings.getExtensions().getExtraProperties().getProperties();
            var projectPluginIds = new HashMap<String, String>();
            var modulesFolder = getProperty(properties, MIFOS_PROJECTS_PROPERTY_MODULES_PATH_TEMPLATE, projectNum);
            var projectIdPrefix = getProperty(properties, MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_ID) + "-";

            if(modulesFolder.isPresent()) {
                var modulesBasePath = Path.of(settings.getRootProject().getProjectDir().getPath(), modulesFolder.orElse(MIFOS_PROJECT_DEFAULT_MODULE_FOLDER).toString());

                try {
                    if(Files.exists(modulesBasePath)) {
                        Files.walkFileTree(modulesBasePath, EnumSet.noneOf(FileVisitOption.class), 3, new SimpleFileVisitor<>() {
                            @Override
                            public @NonNull FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                                if(MIFOS_PROJECT_MODULE_DOC_PATTERN.matcher(file.toString()).matches()) {
                                    var result = include(settings, projectIdPrefix, file.getParent().getParent().getFileName() + "-", "doc", "doc/build.gradle", file);

                                    if(result.isPresent()) {
                                        projectPluginIds.put(result.get(), "org.mifos.convention.project.docs");

                                        return FileVisitResult.SKIP_SIBLINGS;
                                    }
                                }

                                return FileVisitResult.CONTINUE;
                            }
                        });
                    }
                } catch (IOException e) {
                    log.error("Error traversing directory: ", e);
                }
            } else {
                break;
            }

            if(!projectPluginIds.isEmpty()) {
                settings.getGradle().beforeProject(project -> {
                    if(projectPluginIds.containsKey(project.getName())) {
                        project.getPlugins().apply(projectPluginIds.get(project.getName()));
                    }
                });
            }
        }
    }
}
