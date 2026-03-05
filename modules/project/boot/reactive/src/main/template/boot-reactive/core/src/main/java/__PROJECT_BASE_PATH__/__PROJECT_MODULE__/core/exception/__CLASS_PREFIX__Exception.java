/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package {{project_base_package}}.{{project_module}}.core.exception;

import org.mifos.common.boot.core.exception.MifosBaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;

public class {{class_prefix}}Exception extends MifosBaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String {{project_name|upper}}_{{module_name|upper}}_MESSAGE_PREFIX = {{class_prefix}}Exception.class.getPackageName();

    public {{class_prefix}}Exception({{class_prefix}}Error error, Throwable cause, Object... args) {
        super({{project_name|upper}}_{{module_name|upper}}_MESSAGE_PREFIX, error, cause, args);
    }

    @Getter
    @RequiredArgsConstructor
    public enum {{class_prefix}}Error implements {{project_name|capitalize}}BaseException.{{project_name|capitalize}}Error {
        NOT_FOUND(100), UNKNOWN(100_000);

        private final int code;
    }
}
