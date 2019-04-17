# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [semantic versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Changed

- Bump docker-java to 3.1.1
- Rewritten discovery to utilise containers internal IPs and ports
- Renamed `@EnableConsul` to `@TCPHealthCheck` and its property `exposedPort` to `port`
- Renamed property `exposedPort` in `@WebHealthCheck` to `port`
- `@UseConsulDns` now sets the Consul container IP (instead of the Docker bridge one) as primary DNS for your containers and this makes it usable on MacOS 


### Removed

- Goodbye to gliderlabs/registrator
- Goodbye to `qzagarese` in package names :-) 

## 1.0.1 [01 Mar 2019]

### Added

- New boolean flag `-Dconsul.dns.enabled` to disable consul DNS (fixes [#12](https://github.com/qzagarese/dockerunit/issues/12)) 

## 1.0.0 [06 Feb 2019]

_Initial Release_
