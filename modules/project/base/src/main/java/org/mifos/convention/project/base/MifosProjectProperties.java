package org.mifos.convention.project.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
final class MifosProjectProperties {
    private String groupId;
    private String description;
    private String licenseName;
    private String licenseUrl;
    private String homepage;
    private String bugTracker;
    private String contact;
    private String inceptionYear;
    private String vendor;
    private String copyright;
    private String scmUrl;
    private String scmConnectionMain;
    private String scmConnectionDeveloper;
    private String contribute;
    private String documentation;
    private String donation;
    private String faq;
    private String help;
    private String translate;
}
