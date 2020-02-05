[![CircleCI](https://img.shields.io/circleci/build/gh/dockerunit/dockerunit-consul/master.svg?style=flat)](https://circleci.com/gh/dockerunit/dockerunit-consul/tree/master)
&nbsp;
[![Codacy](https://img.shields.io/codacy/coverage/22445615da7d47e8b50e17f937ca9b49.svg?style=flat)](https://app.codacy.com/project/dockerunit/dockerunit-consul/dashboard)
&nbsp;
[![Coverity](https://img.shields.io/coverity/scan/18572.svg?style=flat)](https://scan.coverity.com/projects/dockerunit-dockerunit-consul)
&nbsp;
[![License](https://img.shields.io/github/license/dockerunit/dockerunit-consul.svg?style=flat)](https://choosealicense.com/licenses/apache-2.0/)

[![Codacy](https://img.shields.io/codacy/grade/22445615da7d47e8b50e17f937ca9b49.svg?style=flat&label=codacy)](https://app.codacy.com/project/dockerunit/dockerunit-consul/dashboard)
&nbsp;
[![BetterCodeHub](https://bettercodehub.com/edge/badge/dockerunit/dockerunit-consul?branch=master)](https://bettercodehub.com/)
&nbsp;
[![LGTM](https://img.shields.io/lgtm/grade/java/github/dockerunit/dockerunit-consul.svg?style=flat&label=lgtm)](https://lgtm.com/projects/g/dockerunit/dockerunit-consul/context:java)
&nbsp;
[![LGTM](https://img.shields.io/lgtm/alerts/github/dockerunit/dockerunit-consul.svg?style=flat&label=lgtm)](https://lgtm.com/projects/g/dockerunit/dockerunit-consul/alerts)

[![Maven](https://img.shields.io/maven-central/v/com.github.dockerunit/dockerunit-consul.svg?style=flat)](https://search.maven.org/search?q=g:com.github.dockerunit%20AND%20a:dockerunit-consul&core=gav)
&nbsp;
[![Javadoc](https://javadoc.io/badge/com.github.dockerunit/dockerunit-consul.svg)](https://www.javadoc.io/doc/com.github.dockerunit/dockerunit-consul)
&nbsp;
[![Nexus](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.dockerunit/dockerunit-consul.svg?style=flat)](https://oss.sonatype.org/index.html#nexus-search;gav~com.github.dockerunit~dockerunit-consul~~~)

[![Discord](https://img.shields.io/discord/587583543081959435.svg?style=flat)](https://discordapp.com/channels/587583543081959435/587583543081959437)

This module allows you to configure the discovery phase for the testing of your services.
Service Discovery is necessary for dockerunit to start and for your tests to execute successfully.

Usage (set `dockerunit.consul.version` property to the version you intend to use):
```xml
<dependency>
  <groupId>com.github.dockerunit</groupId>
  <artifactId>dockerunit-consul</artifactId>
  <version>${dockerunit.version}</version>
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
that you have set in the corresponding `@Svc` annotation.

The last annotation can be better explained with an example.

Say you have two services: `service-a` and `service-b`.
Our test only talks to `service-a` which is a client of `service-b`.
Both services listen on port `8080`.

Hereafter the dockerunit descriptors for the two services:

```java
@Svc(name="service-a", image="service-a-image")
@WebHealthCheck(port=8080)
@PublishPort(host=8080, container=8080)
@UseConsulDns
public class DescriptorForA{}

@Named("service-b")
@Image("service-b-image")
@WebHealthCheck(port=8080)
public class DescriptorForB{}
```
- Because `service-a` is using `@UseConsulDns`, it can reference `service-b`
by using the `service-b.service.consul` name.
- `service-b` is not declaring any `@PublishPort` because our test does not need to talk to it.
Only `service-a` will talk to it, but it can do it by using the container IP to which
the `service-b.service.consul` name resolves to.
