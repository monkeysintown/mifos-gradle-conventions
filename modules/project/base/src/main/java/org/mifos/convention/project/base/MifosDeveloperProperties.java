package org.mifos.convention.project.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
