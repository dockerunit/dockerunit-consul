package com.github.dockerunit.discovery.consul;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConsulRegistrator {

    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    private final DockerClient client;
    private final Map<String, ContainerTracker> trackers = new HashMap<>();
    private final Map<String, ConsulService> services = new HashMap<>();

    private final int pollingPeriod;
    private final  HttpClient httpClient;
    private final String host;
    private final int port;
    private final ObjectWriter objectWriter;
    private final ConsulServiceFactory svcFactory;


    public ConsulRegistrator(DockerClient client, int pollingPeriod, String consulHost, int consulPort) {
        this.client = client;
        this.pollingPeriod = pollingPeriod;
        this.host = consulHost;
        this.port = consulPort;
        httpClient = HttpClientBuilder.create().build();
        objectWriter = new ObjectMapper().writer().forType(ConsulService.class);
        svcFactory = new ConsulServiceFactory(client);
    }

    public void trackContainer(String containerId) {
        Optional<ContainerTracker> tracker = Optional.ofNullable(trackers.get(containerId));
        trackers.put(containerId, tracker.orElse(
                new ContainerTracker(client, containerId, pollingPeriod, this::onDetectHook, this::onDestroyHook)));
    }

    private void onDetectHook(Container container) {
        ConsulService svc = svcFactory.createSvc(container.getId());
        registerSvc(svc);
        services.put(container.getId(), svc);
    }

    private void onDestroyHook(Container container) {
        if(container != null && services.containsKey(container.getId())) {
            deregisterSvc(services.get(container.getId()));
            trackers.remove(container.getId());
            services.remove(container.getId());
        }
    }

    private void registerSvc(ConsulService svc) {
        String errorMessage = "Could not register container " + svc.getContainerId() + " on Consul.";
        try {
            executePut("/v1/agent/service/register", objectWriter.writeValueAsString(svc), errorMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(errorMessage, e);
        }
    }

    private void deregisterSvc(ConsulService svc) {
        String errorMessage = "Could not deregister container " + svc.getContainerId() + " from Consul.";
        executePut("/v1/agent/service/deregister/" + svc.getId(), null, errorMessage);
    }

    private void executePut(String endpoint, String body, String errorMessage) {
        HttpPut put = new HttpPut("http://" + host + ":" + port + endpoint);
        put.setHeader(ACCEPT, APPLICATION_JSON);
        put.setHeader(CONTENT_TYPE, APPLICATION_JSON);

        if(body != null) {
            try {
                put.setEntity(new StringEntity(body));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(errorMessage, e);
            }
        }

        final HttpResponse response;
        try {
            response = httpClient.execute(put);
        } catch (Exception e) {
            throw new RuntimeException(errorMessage, e);
        }
        if(response == null || response.getStatusLine().getStatusCode() > 299) {
            throw new RuntimeException(errorMessage);
        }
    }

}
