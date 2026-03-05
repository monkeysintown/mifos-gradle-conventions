package org.mifos.convention.project.base;

import org.gradle.api.initialization.Settings;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public final class MifosProjectUtils {
    private static final Logger log = Logging.getLogger(MifosProjectUtils.class);

    private MifosProjectUtils() {}

    public static Optional<Object> getProperty(Map<String, Object> properties, String propertyNameTemplate, int counter) {
        return Optional.ofNullable(properties.get(propertyNameTemplate.formatted(counter)));
    }

    public static Optional<String> include(Settings settings, String projectIdPrefix, String projectIdMiddle, String projectIdSuffix, String fileEndsWith, Path file) {
        String projectId = null;

        // log.error("Path: {} - ({} | {})", file, Files.isRegularFile(file), file.endsWith(fileEndsWith));

        if(Files.isRegularFile(file) && file.endsWith(fileEndsWith)) {
            projectId = projectIdPrefix + projectIdMiddle + projectIdSuffix;

            settings.include(":" + projectId);
            settings.project(":" + projectId).setProjectDir(file.getParent().toFile());
        }

        return Optional.ofNullable(projectId);
    }
}
