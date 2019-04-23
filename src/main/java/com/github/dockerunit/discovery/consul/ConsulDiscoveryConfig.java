package com.github.dockerunit.discovery.consul;

import com.github.dockerunit.annotation.Use;

import static com.github.dockerunit.discovery.consul.ConsulDiscoveryConfig.CONSUL_CONTAINER_NAME;

@Use(service=ConsulDescriptor.class, containerPrefix=CONSUL_CONTAINER_NAME)
public class ConsulDiscoveryConfig {

	public static final String DOCKER_BRIDGE_IP_PROPERTY = "docker.bridge.ip";
	public static final String DOCKER_BRIDGE_IP_DEFAULT = "172.17.42.1";
	public static final String DOCKER_HOST_PROPERTY = "docker.host";
	public static final String SERVICE_DISCOVERY_TIMEOUT = "service.discovery.timeout";
	public static final String SERVICE_DISCOVERY_TIMEOUT_DEFAULT = "30";
	public static final String CONSUL_POLLING_PERIOD = "consul.polling.period";
	public static final String CONSUL_POLLING_PERIOD_DEFAULT = "1";
	public static final String CONSUL_DNS_ENABLED_PROPERTY = "consul.dns.enabled";
	public static final String CONSUL_DNS_ENABLED_DEFAULT = "true";
	public static final String CONSUL_CONTAINER_NAME = "consul";

}
