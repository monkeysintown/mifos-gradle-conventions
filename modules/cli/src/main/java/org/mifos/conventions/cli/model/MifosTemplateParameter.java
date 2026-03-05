package org.mifos.conventions.cli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class MifosTemplateParameter {
    private String name;
    private String prompt;
    private String type;
    private String value;
}
