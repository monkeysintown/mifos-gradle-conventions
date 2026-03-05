/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base.extension;

import org.gradle.api.Named;
import org.gradle.api.provider.Property;
import org.jspecify.annotations.NonNull;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_BUG_TRACKER;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTACT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_CONTRIBUTE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_COPYRIGHT;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_DESCRIPTION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_DONATION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_FAQ;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_GROUP_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_HELP;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_HOMEPAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_JDK_VERSION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_LICENSE_NAME;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_LICENSE_URL;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_ORGANISATION_ID;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_SPRING_BOOT_VERSION;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_TRANSLATE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_VENDOR;

public abstract class MifosProject implements Named {
    private static final String PROJECT_MAIN = "__main__";
    private final String name;

    public MifosProject() {
        this(PROJECT_MAIN);
    }

    @Inject
    public MifosProject(String name) {
        this.name = name;
        getJdkVersion().set(MIFOS_PROJECT_DEFAULT_JDK_VERSION);
        getSpringBootVersion().set(MIFOS_PROJECT_DEFAULT_SPRING_BOOT_VERSION);
        getOrganisationId().set(MIFOS_PROJECT_DEFAULT_ORGANISATION_ID);
        getGroupId().set(MIFOS_PROJECT_DEFAULT_GROUP_ID);
        getDescription().set(MIFOS_PROJECT_DEFAULT_DESCRIPTION + (PROJECT_MAIN.equals(name) ? "" : ":" + name));
        getLicenseName().set(MIFOS_PROJECT_DEFAULT_LICENSE_NAME);
        getLicenseUrl().set(MIFOS_PROJECT_DEFAULT_LICENSE_URL);
        getHomepage().set(MIFOS_PROJECT_DEFAULT_HOMEPAGE);
        getBugTracker().set(MIFOS_PROJECT_DEFAULT_BUG_TRACKER);
        getContact().set(MIFOS_PROJECT_DEFAULT_CONTACT);
        getInceptionYear().set(LocalDate.now().getYear() + "");
        getVendor().set(MIFOS_PROJECT_DEFAULT_VENDOR);
        getCopyright().set(MIFOS_PROJECT_DEFAULT_COPYRIGHT);
        // getScmUrl().set(MIFOS_PROJECT_DEFAULT_SCM_URL);
        // getScmConnectionMain().set(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_MAIN);
        // getScmConnectionDeveloper().set(MIFOS_PROJECT_DEFAULT_SCM_CONNECTION_DEVELOPER);
        getContribute().set(MIFOS_PROJECT_DEFAULT_CONTRIBUTE);
        getDonation().set(MIFOS_PROJECT_DEFAULT_DONATION);
        getFaq().set(MIFOS_PROJECT_DEFAULT_FAQ);
        getHelp().set(MIFOS_PROJECT_DEFAULT_HELP);
        getTranslate().set(MIFOS_PROJECT_DEFAULT_TRANSLATE);
    }

    public abstract Property<String> getJdkVersion();
    public abstract Property<String> getSpringBootVersion();
    public abstract Property<String> getOrganisationId();
    public abstract Property<String> getGroupId();
    public abstract Property<String> getDescription();
    public abstract Property<String> getLicenseName();
    public abstract Property<String> getLicenseUrl();
    public abstract Property<String> getHomepage();
    public abstract Property<String> getBugTracker();
    public abstract Property<String> getContact();
    public abstract Property<String> getInceptionYear();
    public abstract Property<String> getVendor();
    public abstract Property<String> getCopyright();
    public abstract Property<String> getScmUrl();
    public abstract Property<String> getScmConnectionMain();
    public abstract Property<String> getScmConnectionDeveloper();
    public abstract Property<String> getContribute();
    public abstract Property<String> getDocumentation();
    public abstract Property<String> getDonation();
    public abstract Property<String> getFaq();
    public abstract Property<String> getHelp();
    public abstract Property<String> getTranslate();

    @Override
    public @NonNull String getName() {
        return name;
    }
}
