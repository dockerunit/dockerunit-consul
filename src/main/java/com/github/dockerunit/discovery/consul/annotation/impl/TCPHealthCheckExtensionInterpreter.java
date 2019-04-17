package com.github.dockerunit.discovery.consul.annotation.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerunit.annotation.ExtensionInterpreter;
import com.github.dockerunit.discovery.consul.annotation.TCPHealthCheck;
import com.github.dockerunit.internal.ServiceDescriptor;

public class TCPHealthCheckExtensionInterpreter implements ExtensionInterpreter<TCPHealthCheck> {

	private static final String SERVICE_NAME_SUFFIX = "_NAME";
	private static final String SERVICE_PREFIX = "SERVICE_";
	private static final String SERVICE_CHECK_INTERVAL = SERVICE_PREFIX + "CHECK_INTERVAL";
	private static final String SERVICE_CHECK_INITIAL_STATUS = SERVICE_PREFIX + "CHECK_INITIAL_STATUS";
	private static final String SERVICE_CHECK_TCP = SERVICE_PREFIX + "CHECK_TCP";


	@Override
	public CreateContainerCmd build(ServiceDescriptor td, CreateContainerCmd cmd, TCPHealthCheck ec) {
		final String serviceNameEnv = SERVICE_PREFIX + ec.port() + SERVICE_NAME_SUFFIX + "=" + td.getNamed().value();
		final String serviceCheckIntervalEnv = SERVICE_CHECK_INTERVAL + "=" + ec.pollingInterval() + "s";
		final String serviceInitialStatusEnv = SERVICE_CHECK_INITIAL_STATUS + "=" + ec.checkInitialStatus().toString().toLowerCase();
		final String serviceCheckTCP = SERVICE_CHECK_TCP + "=$SERVICE_IP" + ":" + ec.port();
		
		List<String> finalEnv = new ArrayList<>();
		List<String> enableConsulEnv = Arrays.asList(serviceNameEnv, serviceCheckIntervalEnv, serviceInitialStatusEnv, serviceCheckTCP);
		finalEnv.addAll(enableConsulEnv);

		String[] env = cmd.getEnv();
		
		if(env != null) {
			finalEnv.addAll(Arrays.asList(env));
		}
		return cmd.withEnv(finalEnv);
	}

}
