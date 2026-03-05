package org.mifos.conventions.cli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class MifosTemplate {
    private String templateId;
    private String groupId;
    private String description;
    private String author;
    private Map<String, MifosTemplateParameter> parameters;
    private String layout;
    private Map<String, MifosTemplateModule> modules;
}
