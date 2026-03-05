package org.mifos.convention.project.boot.layout;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.mifos.convention.project.base.MifosProjectBaseLayoutPlugin;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static org.mifos.convention.project.base.MifosProjectConstants.GRADLE_CONFIGURATION_IMPLEMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_APPLICATION_MAIN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_BASE_PACKAGE_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_CLASSIFIER_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_MODULES_PATH_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_APPLICATION_MODULES;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_MODULE_APPLICATION_PATTERN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BASE_PACKAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CLASSIFIER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_MODULE_FOLDER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectUtils.getProperty;
import static org.mifos.convention.project.base.MifosProjectUtils.include;

public class MifosProjectBootApplicationLayoutPlugin implements Plugin<Settings> {
    private static final Logger log = Logging.getLogger(MifosProjectBootApplicationLayoutPlugin.class);

    @Override
    public void apply(Settings settings) {
        settings.getPlugins().apply(MifosProjectBaseLayoutPlugin.class);

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
                        Files.walkFileTree(modulesBasePath, EnumSet.noneOf(FileVisitOption.class), 4, new SimpleFileVisitor<>() {
                            @Override
                            public @NonNull FileVisitResult visitFile(@NotNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                                var found = false;

                                for(var module : MIFOS_PROJECT_BOOT_APPLICATION_MODULES) {
                                    if(MIFOS_PROJECT_BOOT_MODULE_APPLICATION_PATTERN.matcher(file.toString()).matches()) {
                                        var result = include(settings, projectIdPrefix, file.getParent().getParent().getParent().getFileName() + "-" + file.getParent().getParent().getFileName(), "-" + module, module + "/build.gradle", file);

                                        if(result.isPresent()) {
                                            var projectClassifier = getProperty(properties, MIFOS_PROJECTS_PROPERTY_CLASSIFIER_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_CLASSIFIER);

                                            projectPluginIds.put(result.get(), "org.mifos.convention.project.boot.%s.application.%s".formatted(projectClassifier, module));

                                            found = true;
                                        }
                                    }
                                }

                                if(found) {
                                    return FileVisitResult.SKIP_SUBTREE;
                                }

                                return FileVisitResult.CONTINUE;
                            }
                        });
                    }
                } catch (IOException e) {
                    log.error("Error traversing persistence directory: ", e);
                }
            } else {
                break;
            }

            if(!projectPluginIds.isEmpty()) {
                settings.getGradle().beforeProject(project -> {
                    if(projectPluginIds.containsKey(project.getName())) {
                        var currentProjectBasePackage = getProperty(properties, MIFOS_PROJECTS_PROPERTY_BASE_PACKAGE_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_BASE_PACKAGE);
                        var currentModulePackage = project.getBuildFile().toPath().getParent();

                        var applicationMain = currentProjectBasePackage + "." + currentModulePackage.getParent().getParent().getFileName() + "." + currentModulePackage.getParent().getFileName() + "." + currentModulePackage.getFileName() + ".Main";

                        project.getExtensions().getExtraProperties().set(MIFOS_PROJECTS_CURRENT_PROPERTY_APPLICATION_MAIN, applicationMain);

                        project.getPlugins().apply(projectPluginIds.get(project.getName()));

                        var rootModule = project.getBuildFile().toPath().getParent().getParent().getParent();

                        // rest
                        if(project.getName().endsWith("-rest")) {
                            var starterModule = rootModule.resolve("starter/build.gradle");

                            if(Files.exists(starterModule)) {
                                var starterProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + "-starter"));
                                project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, starterProjectDep);
                            }
                        }

                        // cli, migration
                        if(project.getName().endsWith("-cli") || project.getName().endsWith("-migration")) {
                            var coreModule = rootModule.resolve("core/build.gradle");

                            if(Files.exists(coreModule)) {
                                var coreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + "-core"));
                                project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, coreProjectDep);
                            }
                        }

                        // bff, vaadin, jsf
                        if(project.getName().endsWith("-bff") || project.getName().endsWith("-vaadin") || project.getName().endsWith("-jsf")) {
                            // TODO: include client libs

                            log.error("Application type not ready yet for production development: {}!!!", project.getName());

                            var coreModule = rootModule.resolve("core/build.gradle");

                            if(Files.exists(coreModule)) {
                                var coreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + "-core"));
                                project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, coreProjectDep);
                            }
                        }
                    }
                });
            }
        }
    }
}
