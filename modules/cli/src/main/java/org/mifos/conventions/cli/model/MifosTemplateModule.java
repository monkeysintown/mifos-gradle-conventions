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
public class MifosTemplateModule {
    private String name;
    private Map<String, MifosTemplateParameter> parameters;
}
