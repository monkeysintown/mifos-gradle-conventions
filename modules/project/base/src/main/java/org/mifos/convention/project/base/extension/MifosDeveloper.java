/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base.extension;

import org.gradle.api.Named;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_HOMEPAGE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_TIMEZONE;
import static org.mifos.convention.project.base.MifosProjectConstants.MIFOS_PROJECT_DEFAULT_VENDOR;

public abstract class MifosDeveloper implements Named {
    private final String name;

    public MifosDeveloper() {
        this(null);
    }

    @Inject
    public MifosDeveloper(String name) {
        this.name = name;
        getUsername().set(name);
        getOrganisation().set(MIFOS_PROJECT_DEFAULT_VENDOR);
        getOrganisationUrl().set(MIFOS_PROJECT_DEFAULT_HOMEPAGE);
        getTimezone().set(MIFOS_PROJECT_DEFAULT_TIMEZONE);
    }

    public abstract Property<String> getUsername();
    public abstract Property<String> getEmail();
    public abstract Property<String> getFirstname();
    public abstract Property<String> getLastname();
    public abstract Property<String> getTimezone();
    public abstract Property<String> getUrl();
    public abstract Property<String> getOrganisation();
    public abstract Property<String> getOrganisationUrl();
    public abstract ListProperty<String> getRoles();
    public abstract Property<String> getGpgPublicKey();
}
