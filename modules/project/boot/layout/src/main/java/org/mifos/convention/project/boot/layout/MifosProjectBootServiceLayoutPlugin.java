/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
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

import static org.mifos.convention.project.base.MifosProjectConstants.GRADLE_CONFIGURATION_API;
import static org.mifos.convention.project.base.MifosProjectConstants.GRADLE_CONFIGURATION_IMPLEMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_BASE_PACKAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_MODULE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_BASE_PACKAGE_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_CLASSIFIER_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_MODULES_PATH_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_STORAGE_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_MODULE_SERVICE_PATTERN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_SERVICE_MODULES;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BASE_PACKAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CLASSIFIER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_MODULE_FOLDER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_STORAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_CORE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_IMPLEMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_MAPPING;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_PERSISTENCE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_SERVICE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_STARTER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_SUPPORT;
import static org.mifos.convention.project.base.MifosProjectUtils.getProperty;
import static org.mifos.convention.project.base.MifosProjectUtils.include;

public class MifosProjectBootServiceLayoutPlugin implements Plugin<Settings> {
    private static final Logger log = Logging.getLogger(MifosProjectBootServiceLayoutPlugin.class);

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
                var projectClassifier = getProperty(properties, MIFOS_PROJECTS_PROPERTY_CLASSIFIER_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_CLASSIFIER);
                var projectStorage = getProperty(properties, MIFOS_PROJECTS_PROPERTY_STORAGE_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_STORAGE).toString();

                try {
                    if(Files.exists(modulesBasePath)) {
                        Files.walkFileTree(modulesBasePath, EnumSet.noneOf(FileVisitOption.class), 5, new SimpleFileVisitor<>() {
                            @Override
                            public @NonNull FileVisitResult visitFile(@NotNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                                var found = false;

                                for(var module : MIFOS_PROJECT_BOOT_SERVICE_MODULES) {
                                    if(MIFOS_PROJECT_BOOT_MODULE_SERVICE_PATTERN.matcher(file.toString()).matches()) {
                                        var result = include(settings, projectIdPrefix, file.getParent().getParent().getParent().getParent().getFileName() + MIFOS_PROJECT_SUFFIX_SERVICE +"-" + file.getParent().getParent().getFileName(), "-" + module, "service/" + file.getParent().getParent().getFileName() + "/" + module + "/build.gradle", file);

                                        if(result.isPresent()) {
                                            projectPluginIds.put(result.get(), "org.mifos.convention.project.boot.%s.service.%s".formatted(projectClassifier, module));

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

                        if(!projectPluginIds.isEmpty()) {
                            settings.getGradle().beforeProject(project -> {
                                if(projectPluginIds.containsKey(project.getName())) {
                                    var currentProjectBasePackage = getProperty(properties, MIFOS_PROJECTS_PROPERTY_BASE_PACKAGE_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_BASE_PACKAGE);

                                    var parentModule = project.getBuildFile().toPath().getParent().getParent();
                                    var rootModule = parentModule.getParent().getParent();

                                    project.getExtensions().getExtraProperties().set(MIFOS_PROJECTS_CURRENT_PROPERTY_BASE_PACKAGE, currentProjectBasePackage);
                                    project.getExtensions().getExtraProperties().set(MIFOS_PROJECTS_CURRENT_PROPERTY_MODULE, parentModule.getFileName());

                                    project.getPlugins().apply(projectPluginIds.get(project.getName()));

                                    if(project.getName().endsWith(MIFOS_PROJECT_SUFFIX_CORE)) {
                                        var mainCoreModule = rootModule.resolve("core/build.gradle");

                                        if(Files.exists(mainCoreModule)) {
                                            var mainCoreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_CORE));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, mainCoreProjectDep);
                                        }
                                    }
                                    if(project.getName().endsWith(MIFOS_PROJECT_SUFFIX_MAPPING)) {
                                        var coreModule = parentModule.resolve("core/build.gradle");

                                        if(Files.exists(coreModule)) {
                                            var coreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_SERVICE + "-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_CORE));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, coreProjectDep);
                                        }

                                        var mainMappingModule = rootModule.resolve("mapping/build.gradle");

                                        if(Files.exists(mainMappingModule)) {
                                            var mainMappingProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_MAPPING));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, mainMappingProjectDep);
                                        }
                                    }
                                    if(project.getName().endsWith(MIFOS_PROJECT_SUFFIX_IMPLEMENTATION)) {
                                        var coreModule = parentModule.resolve("core/build.gradle");

                                        if(Files.exists(coreModule)) {
                                            var coreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_SERVICE + "-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_CORE));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, coreProjectDep);
                                        }

                                        var mappingModule = parentModule.resolve("mapping/build.gradle");

                                        if(Files.exists(mappingModule)) {
                                            var mappingProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_SERVICE + "-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_MAPPING));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, mappingProjectDep);
                                        }

                                        var mainPersistenceModule = rootModule.resolve("persistence", projectStorage,  "build.gradle");

                                        if(Files.exists(mainPersistenceModule)) {
                                            var mainPersistenceProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_PERSISTENCE + "-" + projectStorage));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, mainPersistenceProjectDep);
                                        }
                                    }
                                    if(project.getName().endsWith(MIFOS_PROJECT_SUFFIX_STARTER)) {
                                        var mappingModule = parentModule.resolve("implementation/build.gradle");

                                        if(Files.exists(mappingModule)) {
                                            var mappingProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_SUPPORT + "-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_IMPLEMENTATION));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, mappingProjectDep);
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    log.error("Error traversing service directory: ", e);
                }
            }
        }
    }
}
