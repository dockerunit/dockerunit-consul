package com.github.dockerunit.discovery.consul.annotation.impl;

import static com.github.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_DEFAULT;
import static com.github.dockerunit.discovery.consul.ConsulDiscoveryConfig.DOCKER_BRIDGE_IP_PROPERTY;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerunit.discovery.consul.ContainerUtils;
import com.github.dockerunit.discovery.consul.annotation.UseConsulDns;
import com.github.dockerunit.annotation.ExtensionInterpreter;
import com.github.dockerunit.internal.ServiceDescriptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UseConsulDnsExtensionInterpreter implements ExtensionInterpreter<UseConsulDns> {

    @Override
    public CreateContainerCmd build(ServiceDescriptor sd, CreateContainerCmd cmd, UseConsulDns t) {

        Optional<String> dnsIp = Optional.ofNullable(ContainerUtils.extractBridgeIpAddress(ContainerUtils.getConsulContainer()
                .getNetworkSettings()).get());

        List<String> dnsList = Arrays.asList(Optional.ofNullable(cmd.getHostConfig().getDns()).orElse(new String[0]));
        dnsList.add(dnsIp.orElse(System.getProperty(DOCKER_BRIDGE_IP_PROPERTY, DOCKER_BRIDGE_IP_DEFAULT)));

        HostConfig hc = cmd.getHostConfig()
                .withDns(dnsList);
        return cmd.withHostConfig(hc);
    }

}
