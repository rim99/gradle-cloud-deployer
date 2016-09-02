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
package de.qaware.cloud.deployer.marathon.config.resource;

import de.qaware.cloud.deployer.commons.config.resource.BaseResourceConfigFactory;
import de.qaware.cloud.deployer.commons.config.resource.ContentType;
import de.qaware.cloud.deployer.commons.error.ResourceConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MarathonResourceConfigFactory extends BaseResourceConfigFactory<MarathonResourceConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarathonResourceConfigFactory.class);

    public MarathonResourceConfigFactory() {
        super(LOGGER);
    }

    @Override
    public MarathonResourceConfig createConfig(File file) throws ResourceConfigException {
        String filename = file.getName();
        ContentType contentType = retrieveContentType(file);
        String content = readFileContent(file);
        return new MarathonResourceConfig(filename, contentType, content);
    }
}
