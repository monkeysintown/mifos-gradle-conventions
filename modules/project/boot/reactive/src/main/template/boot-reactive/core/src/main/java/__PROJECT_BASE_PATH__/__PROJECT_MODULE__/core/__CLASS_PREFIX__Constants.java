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
package {{project_base_package}}.{{project_module}}.core;

import lombok.experimental.UtilityClass;

@UtilityClass
public class {{class_prefix}}Constants {
    public static final String {{project_name|upper}}_{{project_module|upper}}_MESSAGE_BASE = "io/{{project_base_path}}/{{project_module}}/messages";
    public static final String {{project_name|upper}}_{{project_module|upper}}_MIME_TYPE_1_0 = "application/vnd.{{project_name}}.{{project_module}}+json;charset=UTF-8;version=1.0";
    public static final String {{project_name|upper}}_{{project_module|upper}}_ROUTE_BASE = "/{{project_module}}s";
    // TODO: add more REST endpoints here...
    public static final String {{project_name|upper}}_{{project_module|upper}}_ROUTE_CREATE = {{project_name|upper}}_{{project_module|upper}}_ROUTE_BASE + "/create";
    public static final String {{project_name|upper}}_{{project_module|upper}}_TAG = "{{project_module}}s";
}
