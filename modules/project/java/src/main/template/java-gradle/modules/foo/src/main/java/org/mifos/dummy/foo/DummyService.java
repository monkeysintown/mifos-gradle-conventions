/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.dummy.foo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyService {
    String greet(String message) {
        return "Hello: " + message;
    }
}
