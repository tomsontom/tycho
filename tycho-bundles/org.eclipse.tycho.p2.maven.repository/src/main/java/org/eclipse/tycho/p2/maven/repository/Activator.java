/*******************************************************************************
 * Copyright (c) 2008, 2011 Sonatype Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sonatype Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.tycho.p2.maven.repository;

import java.net.URI;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
    public static final String ID = "org.eclipse.tycho.p2.maven.repository";

    private static BundleContext context;

    private static IProvisioningAgent agent;

    public void start(BundleContext context) throws Exception {
        Activator.context = context;

        ServiceReference<IProvisioningAgentProvider> providerRef = context
                .getServiceReference(IProvisioningAgentProvider.class);
        IProvisioningAgentProvider provider = context.getService(providerRef);
        // TODO this doesn't return the running agent; is this intended?
        agent = provider.createAgent(null); // null == currently running system
        context.ungetService(providerRef);
    }

    public void stop(BundleContext context) throws Exception {
        Activator.context = null;
    }

    public static BundleContext getContext() {
        return context;
    }

    // TODO repositories should not make assumptions on the agent they are loaded by (see callers)
    public static IProvisioningAgent getProvisioningAgent() {
        return agent;
    }

    public static IProvisioningAgent createProvisioningAgent(final URI targetLocation) throws ProvisionException {
        ServiceReference<IProvisioningAgentProvider> serviceReference = context
                .getServiceReference(IProvisioningAgentProvider.class);
        IProvisioningAgentProvider agentFactory = context.getService(serviceReference);
        try {
            return agentFactory.createAgent(targetLocation);
        } finally {
            context.ungetService(serviceReference);
        }
    }
}
