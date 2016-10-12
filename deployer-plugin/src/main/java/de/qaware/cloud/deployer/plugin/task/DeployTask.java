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
package de.qaware.cloud.deployer.plugin.task;

import de.qaware.cloud.deployer.commons.Deployer;
import de.qaware.cloud.deployer.commons.error.EnvironmentConfigException;
import de.qaware.cloud.deployer.commons.error.ResourceConfigException;
import de.qaware.cloud.deployer.commons.error.ResourceException;
import de.qaware.cloud.deployer.plugin.environment.Environment;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static de.qaware.cloud.deployer.plugin.logging.PluginMessageBundle.PLUGIN_MESSAGE_BUNDLE;
import static de.qaware.cloud.deployer.plugin.task.ExtendedExceptionMessageUtil.createExtendedMessage;

/**
 * Represents a task which deploys one specified environment.
 */
public class DeployTask extends BaseSingleEnvironmentTask {

    /**
     * The logger of this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployTask.class);

    /**
     * Deploys the environment with the specified id.
     *
     * @throws ResourceException          If a error during resource interaction with the backend occurs.
     * @throws ResourceConfigException    If a error during config creation/parsing occurs.
     * @throws EnvironmentConfigException If an error during environment parsing/creation occurs.
     */
    @TaskAction
    public void deploy() throws ResourceException, ResourceConfigException, EnvironmentConfigException {
        // Setup environment
        setupEnvironment();

        // Retrieve necessary data
        Environment environment = getEnvironment();
        Deployer deployer = environment.getDeployer();
        List<File> files = environment.getFiles();

        // Deploy resources
        LOGGER.info(PLUGIN_MESSAGE_BUNDLE.getMessage("DEPLOYER_PLUGIN_MESSAGES_DEPLOYING_ENVIRONMENT_STARTED", environment.getId()));
        try {
            deployer.deploy(files);
        } catch (ResourceConfigException e) {
            throw new ResourceConfigException(createExtendedMessage(environment, e.getMessage()), e);
        } catch (ResourceException e) {
            throw new ResourceException(createExtendedMessage(environment, e.getMessage()), e);
        }
        LOGGER.info(PLUGIN_MESSAGE_BUNDLE.getMessage("DEPLOYER_PLUGIN_MESSAGES_DEPLOYING_ENVIRONMENT_DONE", environment.getId()));
    }
}
