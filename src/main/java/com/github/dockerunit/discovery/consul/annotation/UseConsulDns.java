package com.github.dockerunit.discovery.consul.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.dockerunit.annotation.ExtensionMarker;
import com.github.dockerunit.annotation.Svc;
import com.github.dockerunit.discovery.consul.annotation.impl.UseConsulDnsExtensionInterpreter;

/**
 * Tells dockerunit to set consul as primary dns for the containers that are created from
 * the svc descriptor class where this annotation is used.
 * 
 * If svc A is defined using the {@link Svc} annotation as follows
 * {@code @Svc(name = "svc-a") }
 * 
 * then svc B will be able to reference it using name <em> svc-a.service.consul </em>
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@ExtensionMarker(UseConsulDnsExtensionInterpreter.class)
public @interface UseConsulDns { }
