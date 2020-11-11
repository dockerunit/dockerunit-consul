# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [semantic versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

## 0.1.1 - (11 Nov 2020)

### Changed

  - Bumped `lombok` to version `1.8.16`
  - Bumped `jackson` to version `2.11.3`
  - Bumped `httpcore` to version `4.4.13`
  - Introduced new system property called `consul.image` for overriding the default DockerHub Consul image
  - Minor formatting changes

## [0.1.0] - (01 Mar 2019)

### Changed

  - Bump docker-java to 3.1.1
  - Rewritten discovery to utilise containers internal IPs and ports
  - Renamed `@EnableConsul` to `@TCPHealthCheck` and its property `exposedPort` to `port`
  - Renamed property `exposedPort` in `@WebHealthCheck` to `port`
  - `@UseConsulDns` now sets the Consul container IP (instead of the Docker bridge one) as primary DNS for your containers and this makes it usable on MacOS 

### Removed

  - Goodbye to gliderlabs/registrator
  - Goodbye to `qzagarese` in package names :-)
  - New boolean flag `-Dconsul.dns.enabled` to disable Consul DNS (fixes [#12](https://github.com/qzagarese/dockerunit/issues/12)) 
