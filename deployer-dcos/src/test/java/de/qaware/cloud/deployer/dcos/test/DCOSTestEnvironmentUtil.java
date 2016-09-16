/*
 * Copyright 2016 QAware GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.qaware.cloud.deployer.dcos.test;

import de.qaware.cloud.deployer.commons.config.cloud.AuthConfig;
import de.qaware.cloud.deployer.commons.config.cloud.EnvironmentConfig;
import de.qaware.cloud.deployer.commons.test.TestEnvironmentUtil;

import java.io.IOException;
import java.util.Map;

public final class DCOSTestEnvironmentUtil {

    // Environment variables.
    private static final String DCOS_URL_ENV = "MARATHON_URL";
    private static final String DCOS_TOKEN_ENV = "MARATHON_TOKEN";

    private DCOSTestEnvironmentUtil() {
    }

    public static EnvironmentConfig createEnvironmentConfig(String updateStrategy) throws IOException {
        // Retrieve env variables
        Map<String, String> environmentVariables = TestEnvironmentUtil.loadEnvironmentVariables(
                DCOS_URL_ENV,
                DCOS_TOKEN_ENV
        );

        // Create environment config.
        EnvironmentConfig environmentConfig = new EnvironmentConfig(environmentVariables.get(DCOS_URL_ENV), updateStrategy);
        environmentConfig.setAuthConfig(new AuthConfig(environmentVariables.get(DCOS_TOKEN_ENV)));
        return environmentConfig;
    }
}
