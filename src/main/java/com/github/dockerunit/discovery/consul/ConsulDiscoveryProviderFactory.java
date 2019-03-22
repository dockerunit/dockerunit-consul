package com.github.dockerunit.discovery.consul;

import com.github.dockerunit.discovery.DiscoveryProvider;
import com.github.dockerunit.discovery.DiscoveryProviderFactory;

public class ConsulDiscoveryProviderFactory implements DiscoveryProviderFactory {

	@Override
	public DiscoveryProvider getProvider() {
		return new ConsulDiscoveryProvider();
	}

}
