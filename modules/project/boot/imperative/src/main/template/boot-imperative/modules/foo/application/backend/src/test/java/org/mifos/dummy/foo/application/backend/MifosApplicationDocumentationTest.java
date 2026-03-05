/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.dummy.foo.application.backend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class MifosApplicationDocumentationTest {
    @Test
    @SuppressWarnings("java:S2699")
    void createModuleDocumentation() {
        ApplicationModules modules = ApplicationModules.of(Main.class);
        new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
    }
}
