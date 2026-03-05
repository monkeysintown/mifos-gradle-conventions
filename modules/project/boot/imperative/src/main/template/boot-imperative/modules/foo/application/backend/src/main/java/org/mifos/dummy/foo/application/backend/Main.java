/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.dummy.foo.application.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;

/** Lorem Ipsum... */
@Slf4j
@Modulith(
        systemName = "Mifos Dummy - Foo - Backend",
        useFullyQualifiedModuleNames = true,
        additionalPackages = {"org.mifos.dummy.foo"})
@SpringBootApplication
public class Main {
    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
