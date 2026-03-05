/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base.extension;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

@Slf4j
public abstract class MifosExtension {
    private final MifosProject project;
    private final NamedDomainObjectContainer<MifosProject> projects;
    private final NamedDomainObjectContainer<MifosDeveloper> developers;

    @Inject
    public MifosExtension(ObjectFactory objects) {
        this.project = objects.newInstance(MifosProject.class, "__main__");
        this.projects = objects.domainObjectContainer(MifosProject.class, name -> objects.newInstance(MifosProject.class, name));
        this.developers = objects.domainObjectContainer(MifosDeveloper.class, name -> objects.newInstance(MifosDeveloper.class, name));
    }

    public abstract Property<MifosProject> getProject();
    public abstract NamedDomainObjectContainer<MifosProject> getProjects();
    public abstract NamedDomainObjectContainer<MifosDeveloper> getDevelopers();

    public void project(Action<? super MifosProject> action) {
        action.execute(project);
    }
}
