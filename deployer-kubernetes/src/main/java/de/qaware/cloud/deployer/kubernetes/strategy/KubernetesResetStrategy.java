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
package de.qaware.cloud.deployer.kubernetes.strategy;

import de.qaware.cloud.deployer.commons.error.ResourceException;
import de.qaware.cloud.deployer.commons.resource.Resource;
import de.qaware.cloud.deployer.commons.strategy.BaseDeletionStrategy;
import de.qaware.cloud.deployer.kubernetes.resource.base.KubernetesResource;
import de.qaware.cloud.deployer.kubernetes.resource.namespace.NamespaceResource;
import de.qaware.cloud.deployer.kubernetes.resource.namespace.NamespaceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static de.qaware.cloud.deployer.kubernetes.logging.KubernetesMessageBundle.KUBERNETES_MESSAGE_BUNDLE;

/**
 * Implements the reset strategy. Meaning that the whole namespace is deleted before the deployment.
 */
class KubernetesResetStrategy extends BaseDeletionStrategy implements KubernetesStrategy {

    /**
     * The logger of this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KubernetesResetStrategy.class);

    /**
     * Deploys the list of resources.
     *
     * @param resources The resources to deploy.
     * @throws ResourceException If an error during deployment occurs.
     */
    private static void deployResources(List<KubernetesResource> resources) throws ResourceException {
        LOGGER.info(KUBERNETES_MESSAGE_BUNDLE.getMessage("DEPLOYER_KUBERNETES_MESSAGE_DEPLOYING_RESOURCES_STARTED"));
        for (Resource resource : resources) {
            LOGGER.info(KUBERNETES_MESSAGE_BUNDLE.getMessage("DEPLOYER_KUBERNETES_MESSAGE_CREATING_SINGLE_RESOURCE", resource));
            resource.create();
        }
        LOGGER.info(KUBERNETES_MESSAGE_BUNDLE.getMessage("DEPLOYER_KUBERNETES_MESSAGE_DEPLOYING_RESOURCES_DONE"));
    }

    @Override
    public void deploy(NamespaceResource namespaceResource, List<KubernetesResource> resources) throws ResourceException {
        // 1. Delete the old namespace
        NamespaceUtil.safeDeleteNamespace(namespaceResource);

        // 2. Create the new namespace
        NamespaceUtil.safeCreateNamespace(namespaceResource);

        // 3. Create resources in the namespace
        deployResources(resources);
    }

    @Override
    public void delete(NamespaceResource namespaceResource, List<KubernetesResource> resources) throws ResourceException {
        // Delete the complete namespace
        NamespaceUtil.safeDeleteNamespace(namespaceResource);
    }
}
