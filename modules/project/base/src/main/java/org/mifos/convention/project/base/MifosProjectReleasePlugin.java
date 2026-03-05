/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

// NOTE: eventually delete this, JReleaser is only usable as a separate command line tool due to outdated dependencies
@Deprecated
public class MifosProjectReleasePlugin implements Plugin<Project> {
    private static final Logger log = Logging.getLogger(MifosProjectReleasePlugin.class);

    @Override
    public void apply(Project project) {
//        Map<String, ?> properties = project.getProperties();
//
//        var mifosDevelopers = new ArrayList <MifosDeveloperProperties>();
//        Map<String, ?> mifosDeveloperProperties = properties.entrySet()
//                .stream()
//                .filter(entry -> entry.getKey().startsWith(MIFOS_DEVELOPERS_PROPERTY_PREFIX))
//                .collect(Collectors.toMap(
//                    Map.Entry::getKey,
//                    Map.Entry::getValue
//                ));
//
//        for(int developerNum=0; developerNum < MIFOS_DEVELOPER_MAX_SIZE; developerNum++) {
//            var devUsername = Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_USERNAME_TEMPLATE));
//
//            if(devUsername.isPresent()) {
//                var d = new MifosDeveloperProperties();
//                d.setUsername(devUsername.get().toString());
//                d.setEmail(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_EMAIL_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setFirstname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_FIRSTNAME_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setLastname(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_LASTNAME_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setTimezone(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_TIMEZONE_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_URL_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setOrganisation(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setOrganisationUrl(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ORGANISATION_URL_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setGpgPublicKey(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_GPG_PUBLIC_KEY_TEMPLATE)).map(Object::toString).orElse(MIFOS_EMPTY));
//                d.setRoles(Optional.ofNullable(mifosDeveloperProperties.get(MIFOS_DEVELOPERS_PROPERTY_ROLES_TEMPLATE)).map(Object::toString).or(() -> Optional.of(MIFOS_EMPTY)).map(o -> Arrays.asList(o.split(","))).get());
//
//                mifosDevelopers.add(d);
//            } else {
//                // we are done, no more developers listed
//                break;
//            }
//        }
//
//        var mifosProject = new MifosProjectProperties();
//        mifosProject.setGroupId(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_GROUP_ID)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_GROUP_ID));
//        mifosProject.setDescription(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_DESCRIPTION)).map(Object::toString).orElse(""));
//        mifosProject.setLicenseName(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_NAME)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_NAME));
//        mifosProject.setLicenseUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_LICENSE_URL)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_LICENSE_URL));
//        mifosProject.setHomepage(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_HOMEPAGE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_HOMEPAGE));
//        mifosProject.setBugTracker(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_BUG_TRACKER)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_BUG_TRACKER));
//        mifosProject.setContact(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_CONTACT)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_CONTACT));
//        mifosProject.setInceptionYear(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_INCEPTION_YEAR)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_INCEPTION_YEAR));
//        mifosProject.setVendor(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_VENDOR)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_VENDOR));
//        mifosProject.setCopyright(Optional.ofNullable(properties.get(MIFOS_PROJECTS_CURRENT_PROPERTY_COPYRIGHT)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_COPYRIGHT));
//
//        // only set globally for the whole git repository
//        mifosProject.setScmUrl(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_URL)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_URL));
//        mifosProject.setScmConnectionMain(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_MAIN)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN));
//        mifosProject.setScmConnectionDeveloper(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_SCM_CONNECTION_DEVELOPER)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER));
//        mifosProject.setContribute(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_CONTRIBUTE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_CONTRIBUTE));
//        mifosProject.setDocumentation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DOCUMENTATION)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_DOCUMENTATION));
//        mifosProject.setDonation(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_DONATION)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_DONATION));
//        mifosProject.setFaq(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_FAQ)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_FAQ));
//        mifosProject.setHelp(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_HELP)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_HELP));
//        mifosProject.setTranslate(Optional.ofNullable(properties.get(MIFOS_PROJECTS_PROPERTY_TRANSLATE)).map(Object::toString).orElse(MIFOS_PROJECT_DEFAULT_TRANSLATE));
//
//        project.setGroup(mifosProject.getGroupId());
//        project.setDescription(mifosProject.getDescription());
//
//        project.getPlugins().apply(JReleaserPlugin.class);
//
//        project.getExtensions().configure(JReleaserExtension.class, jreleaser -> {
//            jreleaser.getGitRootSearch().set(true);
//
//            // project
//            jreleaser.project(releaseProject -> {
//                releaseProject.getAuthors().set(mifosDevelopers.stream().map(MifosDeveloperProperties::getUsername).toList());
//                releaseProject.getLicense().set(mifosProject.getLicenseName());
//                releaseProject.links(link -> {
//                    link.getHomepage().set(mifosProject.getHomepage());
//                    link.getBugTracker().set(mifosProject.getBugTracker());
//                    link.getContact().set(mifosProject.getContact());
//                    link.getContribute().set(mifosProject.getContribute());
//                    link.getDocumentation().set(mifosProject.getDocumentation());
//                    link.getDonation().set(mifosProject.getDonation());
//                    link.getFaq().set(mifosProject.getFaq());
//                    link.getHelp().set(mifosProject.getHelp());
//                    link.getTranslate().set(mifosProject.getTranslate());
//                });
//                releaseProject.getInceptionYear().set(mifosProject.getInceptionYear());
//                releaseProject.getVendor().set(mifosProject.getVendor());
//                releaseProject.getCopyright().set(mifosProject.getCopyright());
//            });
//
//            // catalog
//            jreleaser.catalog(catalog -> {
//                catalog.sbom(sbom -> sbom.syft(syft -> {
//                    syft.getActive().set(Active.ALWAYS);
//                    syft.pack(pack -> pack.getEnabled().set(true));
//                }));
//                catalog.swid(swid -> {
//                    var mifos = swid.create("mifos");
//                    mifos.getActive().set(Active.ALWAYS);
//                });
//            });
//
//            // signing
//            jreleaser.signing(signing -> signing.pgp(pgp -> {
//                pgp.getActive().set(Active.ALWAYS);
//                pgp.getArmored().set(true);
//            }));
//        });
//
//        project.getPlugins().apply(JdksPlugin.class);
    }
}
