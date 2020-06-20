package com.rafehi.methodsecuritytest.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
  private PermissionEvaluatorProxy permissionEvaluator;
  private ApplicationContext applicationContext;

  @Autowired
  public MethodSecurityConfiguration(
      PermissionEvaluatorProxy permissionEvaluator, ApplicationContext applicationContext) {
    this.permissionEvaluator = permissionEvaluator;
    this.applicationContext = applicationContext;
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();

    expressionHandler.setPermissionEvaluator(permissionEvaluator);
    expressionHandler.setApplicationContext(applicationContext);

    return expressionHandler;
  }

  public class PermissionEvaluatorProxy implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
      return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
      return true;
    }
  }
}
