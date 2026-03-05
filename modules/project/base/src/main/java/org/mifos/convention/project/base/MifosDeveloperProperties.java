/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.convention.project.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Deprecated(forRemoval = true)
@Data
@NoArgsConstructor
final class MifosDeveloperProperties {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String timezone;
    private String url;
    private String organisation;
    private String organisationUrl;
    private List<String> roles;
    private String gpgPublicKey;
}
