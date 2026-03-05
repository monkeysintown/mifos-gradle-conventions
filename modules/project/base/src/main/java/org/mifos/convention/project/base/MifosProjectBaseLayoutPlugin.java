/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_EMAIL_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_FIRSTNAME_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_GPG_PUBLIC_KEY_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_LASTNAME_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_URL_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_PREFIX;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_ROLES_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_TIMEZONE_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_URL_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPERS_PROPERTY_USERNAME_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_DEVELOPER_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_EMPTY;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_BUG_TRACKER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_CONTACT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_COPYRIGHT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_DESCRIPTION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_GROUP_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_HOMEPAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_INCEPTION_YEAR;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_NAME;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_CURRENT_PROPERTY_VENDOR;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_BUG_TRACKER_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_CONTACT_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_CONTRIBUTE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_COPYRIGHT_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_DESCRIPTION_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_DOCUMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_DONATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_FAQ;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_GROUP_ID_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_HELP;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_HOMEPAGE_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_INCEPTION_YEAR_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_LICENSE_NAME_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_LICENSE_URL_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_TRANSLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_VENDOR_TEMPLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BUG_TRACKER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTACT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTRIBUTE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_COPYRIGHT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_DESCRIPTION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_DOCUMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_DONATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_FAQ;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_GROUP_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_HELP;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_HOMEPAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_LICENSE_NAME;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_LICENSE_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_SCM_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_TRANSLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_VENDOR;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_MAX_SIZE;
import static org.mifos.convention.project.base.MifosProjectUtils.getProperty;

public class MifosProjectBaseLayoutPlugin implements Plugin<Settings> {
    private static final Logger log = Logging.getLogger(MifosProjectBaseLayoutPlugin.class);

    @Override
    public void apply(Settings settings) {
        settings.dependencyResolutionManagement(resolutionManagement -> {
            resolutionManagement.repositories(repositories -> {
                repositories.mavenLocal();
                repositories.mavenCentral();
                repositories.gradlePluginPortal();
                repositories.maven(repository -> {
                    repository.setUrl("https://central.sonatype.com/repository/maven-snapshots");
                });
                repositories.maven(repository -> {
                    repository.setUrl("https://mifos.jfrog.io/artifactory/mifosx-gradle-local");
                });
            });
        });
        settings.getBuildscript().repositories(repositories -> {
            repositories.mavenLocal();
            repositories.mavenCentral();
            repositories.gradlePluginPortal();
            repositories.maven(repository -> {
                repository.setUrl("https://central.sonatype.com/repository/maven-snapshots");
            });
            repositories.maven(repository -> {
                repository.setUrl("https://mifos.jfrog.io/artifactory/mifosx-gradle-local");
            });
        });

        settings.getBuildscript().configurations(configurations -> {
            configurations.configureEach(configuration -> {
                configuration.resolutionStrategy(resolutionStrategy -> {
                    resolutionStrategy.dependencySubstitution(dependencySubstitutions -> {
                        dependencySubstitutions.substitute(dependencySubstitutions.module("com.burgstaller:okhttp-digest:1.10"))
                                .using(dependencySubstitutions.module("io.github.rburgst:okhttp-digest:1.21"))
                                .because("okhttp-digest only version 1.21 is available on Maven Central. Old version was on JCenter, which asciidoctor-gradle-plugin depends on transitively through simplified-jruby-gradle-plugin via http-builder-ng-okhttp");
                    });
                });
            });
        });
        settings.getBuildscript().getConfigurations().forEach(configuration -> configuration.getResolutionStrategy().force("org.eclipse.jgit:org.eclipse.jgit:6.10.1.202505221210-r"));

        var properties = settings.getExtensions().getExtraProperties().getProperties();

        var mifosDevelopers = new ArrayList <MifosDeveloperProperties>();
        var mifosDeveloperProperties = properties.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(MIFOS_DEVELOPERS_PROPERTY_PREFIX))
                // .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));

        for(int developerNum=0; developerNum < MIFOS_DEVELOPER_MAX_SIZE; developerNum++) {
            var devUsername = Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_USERNAME_TEMPLATE));

            if(devUsername.isPresent()) {
                var d = new MifosDeveloperProperties();
                d.setUsername(devUsername.get().toString());
                d.setEmail(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_EMAIL_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setFirstname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_FIRSTNAME_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setLastname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_LASTNAME_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setTimezone(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_TIMEZONE_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_URL_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setOrganisation(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setOrganisationUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_URL_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setGpgPublicKey(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_GPG_PUBLIC_KEY_TEMPLATE)).orElse(MIFOS_EMPTY).toString());
                d.setRoles(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ROLES_TEMPLATE)).or(() -> Optional.of(MIFOS_EMPTY)).map(o -> Arrays.asList(o.toString().split(","))).get());

                mifosDevelopers.add(d);
            } else {
                // we are done, no more developers listed
                break;
            }
        }

        settings.getGradle().beforeProject(project -> {
            var mifosProject = new MifosProjectProperties();
            mifosProject.setGroupId(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_GROUP_ID)).orElse(MIFOS_PROJECT_DEFAULT_GROUP_ID).toString());
            mifosProject.setDescription(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_DESCRIPTION)).orElse("").toString());
            mifosProject.setLicenseName(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_NAME)).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_NAME).toString());
            mifosProject.setLicenseUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_URL)).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_URL).toString());
            mifosProject.setHomepage(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_HOMEPAGE)).orElse(MIFOS_PROJECT_DEFAULT_HOMEPAGE).toString());
            mifosProject.setBugTracker(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_BUG_TRACKER)).orElse(MIFOS_PROJECT_DEFAULT_BUG_TRACKER).toString());
            mifosProject.setContact(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_CONTACT)).orElse(MIFOS_PROJECT_DEFAULT_CONTACT).toString());
            mifosProject.setInceptionYear(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_INCEPTION_YEAR)).orElse(MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR).toString());
            mifosProject.setVendor(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_VENDOR)).orElse(MIFOS_PROJECT_DEFAULT_VENDOR).toString());
            mifosProject.setCopyright(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_COPYRIGHT)).orElse(MIFOS_PROJECT_DEFAULT_COPYRIGHT).toString());

            // only set globally for the whole git repository
            mifosProject.setScmUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_URL)).orElse(MIFOS_PROJECT_DEFAULT_SCM_URL).toString());
            mifosProject.setScmConnectionMain(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN)).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN).toString());
            mifosProject.setScmConnectionDeveloper(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER)).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER).toString());
            mifosProject.setContribute(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_CONTRIBUTE)).orElse(MIFOS_PROJECT_DEFAULT_CONTRIBUTE).toString());
            mifosProject.setDocumentation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DOCUMENTATION)).orElse(MIFOS_PROJECT_DEFAULT_DOCUMENTATION).toString());
            mifosProject.setDonation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DONATION)).orElse(MIFOS_PROJECT_DEFAULT_DONATION).toString());
            mifosProject.setFaq(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_FAQ)).orElse(MIFOS_PROJECT_DEFAULT_FAQ).toString());
            mifosProject.setHelp(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_HELP)).orElse(MIFOS_PROJECT_DEFAULT_HELP).toString());
            mifosProject.setTranslate(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_TRANSLATE)).orElse(MIFOS_PROJECT_DEFAULT_TRANSLATE).toString());

            for(int projectNum=0; projectNum < MIFOS_PROJECT_MAX_SIZE; projectNum++) {
                var projectIdPrefix = getProperty(properties, MIFOS_PROJECTS_PROPERTY_ID_TEMPLATE, projectNum);

                if(projectIdPrefix.isPresent()) {
                    if(project.getName().startsWith(projectIdPrefix.get().toString())) {
                        mifosProject.setGroupId(getProperty(properties, MIFOS_PROJECTS_PROPERTY_GROUP_ID_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_GROUP_ID).toString());
                        mifosProject.setDescription(getProperty(properties, MIFOS_PROJECTS_PROPERTY_DESCRIPTION_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_DESCRIPTION).toString());
                        mifosProject.setLicenseName(getProperty(properties, MIFOS_PROJECTS_PROPERTY_LICENSE_NAME_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_NAME).toString());
                        mifosProject.setLicenseUrl(getProperty(properties, MIFOS_PROJECTS_PROPERTY_LICENSE_URL_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_URL).toString());
                        mifosProject.setHomepage(getProperty(properties, MIFOS_PROJECTS_PROPERTY_HOMEPAGE_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_HOMEPAGE).toString());
                        mifosProject.setBugTracker(getProperty(properties, MIFOS_PROJECTS_PROPERTY_BUG_TRACKER_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_BUG_TRACKER).toString());
                        mifosProject.setContact(getProperty(properties, MIFOS_PROJECTS_PROPERTY_CONTACT_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_CONTACT).toString());
                        mifosProject.setInceptionYear(getProperty(properties, MIFOS_PROJECTS_PROPERTY_INCEPTION_YEAR_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR).toString());
                        mifosProject.setVendor(getProperty(properties, MIFOS_PROJECTS_PROPERTY_VENDOR_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_VENDOR).toString());
                        mifosProject.setCopyright(getProperty(properties, MIFOS_PROJECTS_PROPERTY_COPYRIGHT_TEMPLATE, projectNum).orElse(MIFOS_PROJECT_DEFAULT_COPYRIGHT).toString());

                        project.setGroup(mifosProject.getGroupId());
                        project.setDescription(mifosProject.getDescription());

                        // log.error("Project: {} - {}", project.getGroup(), project.getName());
                        // we found the project, don't have to iterate further
                        break;
                    }
                } else {
                    // no more projects defined in gradle.properties
                    break;
                }
            }
        });
    }
}
