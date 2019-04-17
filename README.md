dockerunit-consul - A Consul based discovery provider for Dockerunit
===========================================================================

This module allows you to configure the discovery phase for the testing of your services.
Service Discovery is necessary for dockerunit to start and for your tests to execute successfully. 

Usage (set `dockerunit.consul.version` property to the version you intend to use):
```xml
<dependency>
  <groupId>com.github.dockerunit</groupId>
  <artifactId>dockerunit-consul</artifactId>
  <version>${dockerunit.consul.version}</version>
  <scope>test</scope>
</dependency>
```


Discovery configuration can be specified by means of the following Java annotations:

- `@TCPHealthCheck`: A basic health-check that probes your container to check if it's 
able to accept TCP connections.
- `@WebHealthCheck`: Health-check for HTTP/HTTPS services. Consul will consider the health-check as passing only 
when it returns a 200 HTTP status code.
- `@UseConsulDns`: Injects the Consul container IP as a DNS server for your container(s). This allows 
your services to reference other services by using their `dockerunit name`, which is the value 
that you have set in the corresponding `@Named` annotation.

The last annotation can be better explained with an example.

Say you have two services: `service-a` and `service-b`.
Our test only talks to `service-a` which is a client of `service-b`.
Both services listen on port `8080`.
 
Hereafter the dockerunit descriptors for the two services:

```java
@Named("service-a")
@Image("service-a-image")
@WebHealthCheck(port=8080)
@PortBinding(exposedPort=8080, hostPort=8080)
@UseConsulDns
public class DescriptorForA{}

@Named("service-b")
@Image("service-b-image")
@WebHealthCheck(port=8080)
public class DescriptorForB{}
```
- Because `service-a` is using `@UseConsulDns`, it can reference `service-b` 
by using the `service-b.service.consul` name.
- `service-b` is not declaring any `@PortBinding` because our test does not need to talk to it.
Only `service-a` will talk to it, but it can do it by using the container IP to which 
the `service-b.service.consul` name resolves to.  
