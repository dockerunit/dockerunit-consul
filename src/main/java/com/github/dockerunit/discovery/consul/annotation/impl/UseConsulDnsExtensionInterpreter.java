package com.github.dockerunit.discovery.consul.annotation.impl;

import static com.github.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_DEFAULT;
import static com.github.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_PROPERTY;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerunit.discovery.consul.annotation.UseConsulDns;
import com.github.dockerunit.annotation.ExtensionInterpreter;
import com.github.dockerunit.internal.ServiceDescriptor;

public class UseConsulDnsExtensionInterpreter implements ExtensionInterpreter<UseConsulDns> {

    @Override
    public CreateContainerCmd build(ServiceDescriptor sd, CreateContainerCmd cmd, UseConsulDns t) {
        HostConfig hc = cmd.getHostConfig()
                .withDns(System.getProperty(DOCKER_BRIDGE_IP_PROPERTY, DOCKER_BRIDGE_IP_DEFAULT));

        return cmd.withHostConfig(hc);
    }

}
