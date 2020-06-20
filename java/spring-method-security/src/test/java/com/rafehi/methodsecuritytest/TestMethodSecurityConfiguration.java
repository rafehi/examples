package com.rafehi.methodsecuritytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TestMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
  private ApplicationContext applicationContext;
  private PermissionEvaluator evaluator;

  @Autowired
  public TestMethodSecurityConfiguration(ApplicationContext applicationContext, PermissionEvaluator evaluator) {
    this.applicationContext = applicationContext;
    this.evaluator = evaluator;
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(evaluator);
    expressionHandler.setApplicationContext(applicationContext);
    return expressionHandler;
  }
}
