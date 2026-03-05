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
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_FLOW_BUSINESS_LOGIC_MODULES;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_FLOW_MODULES;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_BOOT_MODULE_FLOW_PATTERN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BASE_PACKAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CLASSIFIER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_MODULE_FOLDER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_ADAPTER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_CORE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_FLOW;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_SUFFIX_MAPPING;
import static org.mifos.convention.project.base.MifosProjectUtils.getProperty;
import static org.mifos.convention.project.base.MifosProjectUtils.include;

public class MifosProjectBootFlowLayoutPlugin implements Plugin<Settings> {
    private static final Logger log = Logging.getLogger(MifosProjectBootFlowLayoutPlugin.class);

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

                try {
                    if(Files.exists(modulesBasePath)) {
                        Files.walkFileTree(modulesBasePath, EnumSet.noneOf(FileVisitOption.class), 5, new SimpleFileVisitor<>() {
                            @Override
                            public @NonNull FileVisitResult visitFile(@NotNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                                var found = false;

                                for(var module : MIFOS_PROJECT_BOOT_FLOW_MODULES) {
                                    if(MIFOS_PROJECT_BOOT_MODULE_FLOW_PATTERN.matcher(file.toString()).matches()) {
                                        var result = include(settings, projectIdPrefix, file.getParent().getParent().getParent().getParent().getFileName() + "-flow-" + file.getParent().getParent().getFileName(), "-" + module, "flow/" + file.getParent().getParent().getFileName() + "/" + module + "/build.gradle", file);

                                        if(result.isPresent()) {
                                            projectPluginIds.put(result.get(), "org.mifos.convention.project.boot.%s.flow.%s".formatted(projectClassifier, module));

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
                                            var coreProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + "-flow-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_CORE));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, coreProjectDep);
                                        }

                                        var mainMappingModule = rootModule.resolve("mapping/build.gradle");

                                        if(Files.exists(mainMappingModule)) {
                                            var mainMappingProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_MAPPING));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_API, mainMappingProjectDep);
                                        }
                                    }
                                    if(project.getName().endsWith(MIFOS_PROJECT_SUFFIX_ADAPTER)) {
                                        var mappingModule = parentModule.resolve("mapping/build.gradle");

                                        if(Files.exists(mappingModule)) {
                                            var mappingProjectDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix  + rootModule.getFileName() + MIFOS_PROJECT_SUFFIX_FLOW + "-" + parentModule.getFileName() + MIFOS_PROJECT_SUFFIX_MAPPING));
                                            project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, mappingProjectDep);
                                        }

                                        for(var businessModuleName : MIFOS_PROJECT_BOOT_FLOW_BUSINESS_LOGIC_MODULES) {
                                            var businessModules = rootModule.resolve(businessModuleName);

                                            if(Files.exists(businessModules)) {
                                                try {
                                                    Files.list(businessModules).forEach(path -> {
                                                        var businessModule = path.resolve("build.gradle");

                                                        if(Files.exists(businessModule)) {
                                                            var businessModuleDep = project.getDependencies().project(Map.of("path", ":" + projectIdPrefix + rootModule.getFileName() + "-" + businessModuleName + "-" + businessModule.getFileName() + MIFOS_PROJECT_SUFFIX_CORE));
                                                            project.getDependencies().add(GRADLE_CONFIGURATION_IMPLEMENTATION, businessModuleDep);
                                                        }
                                                    });
                                                } catch (IOException ioe) {
                                                    log.error("Error traversing {} folder.", businessModuleName, ioe);
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    log.error("Error traversing flow directory: ", e);
                }
            }
        }
    }
}
