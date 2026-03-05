
package org.mifos.convention.project.base;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
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
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_CONTRIBUTE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_DOCUMENTATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_DONATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_FAQ;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_HELP;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_SCM_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECTS_PROPERTY_TRANSLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BUG_TRACKER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTACT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTRIBUTE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_COPYRIGHT;
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

public class MifosProjectRootPlugin implements Plugin<Project> {
    private static final Logger log = Logging.getLogger(MifosProjectRootPlugin.class);

    @Override
    public void apply(Project project) {
        Map<String, ?> properties = project.getProperties();

        var mifosDevelopers = new ArrayList <MifosDeveloperProperties>();
        Map<String, ?> mifosDeveloperProperties = properties.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(MIFOS_DEVELOPERS_PROPERTY_PREFIX))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));

        for(int developerNum=0; developerNum < MIFOS_DEVELOPER_MAX_SIZE; developerNum++) {
            var devUsername = Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_USERNAME_TEMPLATE.formatted(developerNum)));

            if(devUsername.isPresent()) {
                var d = new MifosDeveloperProperties();
                d.setUsername(devUsername.get().toString());
                d.setEmail(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_EMAIL_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setFirstname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_FIRSTNAME_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setLastname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_LASTNAME_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setTimezone(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_TIMEZONE_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_URL_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setOrganisation(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setOrganisationUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_URL_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setGpgPublicKey(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_GPG_PUBLIC_KEY_TEMPLATE.formatted(developerNum))).map(Object::toString).orElse(MIFOS_EMPTY));
                d.setRoles(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ROLES_TEMPLATE.formatted(developerNum))).map(Object::toString).or(() -> Optional.of(MIFOS_EMPTY)).map(o -> Arrays.asList(o.split(","))).get());

                mifosDevelopers.add(d);
            } else {
                // we are done, no more developers listed
                break;
            }
        }

        var mifosProject = new MifosProjectProperties();
        mifosProject.setGroupId(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_GROUP_ID)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_GROUP_ID));
        mifosProject.setDescription(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_DESCRIPTION)).map(Object::toString).orElse(""));
        mifosProject.setLicenseName(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_NAME)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_NAME));
        mifosProject.setLicenseUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_URL)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_URL));
        mifosProject.setHomepage(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_HOMEPAGE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_HOMEPAGE));
        mifosProject.setBugTracker(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_BUG_TRACKER)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_BUG_TRACKER));
        mifosProject.setContact(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_CONTACT)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_CONTACT));
        mifosProject.setInceptionYear(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_INCEPTION_YEAR)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR));
        mifosProject.setVendor(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_VENDOR)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_VENDOR));
        mifosProject.setCopyright(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_COPYRIGHT)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_COPYRIGHT));

        // only set globally for the whole git repository
        mifosProject.setScmUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_URL)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_URL));
        mifosProject.setScmConnectionMain(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN));
        mifosProject.setScmConnectionDeveloper(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER));
        mifosProject.setContribute(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_CONTRIBUTE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_CONTRIBUTE));
        mifosProject.setDocumentation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DOCUMENTATION)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_DOCUMENTATION));
        mifosProject.setDonation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DONATION)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_DONATION));
        mifosProject.setFaq(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_FAQ)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_FAQ));
        mifosProject.setHelp(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_HELP)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_HELP));
        mifosProject.setTranslate(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_TRANSLATE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_TRANSLATE));

        project.setGroup(mifosProject.getGroupId());
        project.setDescription(mifosProject.getDescription());

        project.subprojects(subproject -> {
            subproject.setVersion(project.getRootProject().getVersion());
            subproject.getPlugins().apply(MavenPublishPlugin.class);

            subproject.getExtensions().configure(PublishingExtension.class, publishing -> {
                subproject.getPlugins().apply(MavenPublishPlugin.class);

                publishing.publications(publications -> publications.create("mavenJava", MavenPublication.class, publication -> {
                    publication.versionMapping(versionMapping -> {
                        versionMapping.usage("java-api", variantVersionMappingStrategy -> {
                            variantVersionMappingStrategy.fromResolutionOf("runtimeClasspath");
                        });
                    });
                    publication.setGroupId(mifosProject.getGroupId());

                    publication.pom(pom -> {
                        pom.getUrl().set(mifosProject.getHomepage());

                        // license
                        pom.licenses(licenses -> licenses.license(license -> {
                            license.getName().set(mifosProject.getLicenseName());
                            license.getUrl().set(mifosProject.getLicenseUrl());
                        }));

                        pom.developers(developers -> {
                            mifosDevelopers.forEach(mifosDeveloper -> developers.developer(dev -> {
                                dev.getId().set(mifosDeveloper.getUsername());
                                dev.getName().set(mifosDeveloper.getFirstname()  + " " + mifosDeveloper.getLastname());
                                dev.getEmail().set(mifosDeveloper.getEmail());
                                dev.getUrl().set(mifosDeveloper.getUrl());
                                dev.getTimezone().set(mifosDeveloper.getTimezone());
                                dev.getOrganization().set(mifosDeveloper.getOrganisation());
                                dev.getOrganizationUrl().set(mifosDeveloper.getOrganisationUrl());
                                dev.getRoles().set(mifosDeveloper.getRoles());
                            }));
                        });

                        // scm
                        pom.scm(scm -> {
                            scm.getUrl().set(mifosProject.getScmUrl());
                            scm.getConnection().set(mifosProject.getScmConnectionMain());
                            scm.getDeveloperConnection().set(mifosProject.getScmConnectionDeveloper());
                        });
                    });
                }));

                publishing.repositories(repositories -> repositories.maven(maven -> {
                    maven.setName("staging-deploy");
                    maven.setUrl(subproject.getLayout().getBuildDirectory().dir("staging-deploy"));
                }));
            });
        });

        try {
            var rootPath = project.getRootDir().toPath();

            if(!Files.exists(rootPath.resolve(".gitignore"))) {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(MifosProjectRootPlugin.class.getClassLoader().getResourceAsStream("gitignore.txt")), rootPath.resolve(".gitignore").toFile());
            }
            if(!Files.exists(rootPath.resolve(".gitattributes"))) {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(MifosProjectRootPlugin.class.getClassLoader().getResourceAsStream("gitattributes.txt")), rootPath.resolve(".gitattributes").toFile());
            }
        } catch (Exception ioe) {
            log.error("Unable to locate .gitignore file", ioe);
        }

        // log.error("Project: {} - {} - {}", project.getGroup(), project.getName(), mifosProject.getHomepage());
    }
}
