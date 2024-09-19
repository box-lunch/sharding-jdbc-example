package com.formerlunchbox.sharding.jdbc.aop;

import groovy.util.logging.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.formerlunchbox.sharding.jdbc.annotation.Master;

import java.lang.reflect.Method;
import java.util.Objects;


@Slf4j
@Component
@Aspect
public class ShardingAop {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ShardingAop.class);

  @Around("execution(* com.formerlunchbox..service.impl.*.*(..))")
  public Object master(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    Object ret = null;
    log.info(joinPoint.toShortString());
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    Master master = method.getAnnotation(Master.class);
    HintManager hintManager = null;
    try {
      if (Objects.nonNull(master)) {
        HintManager.clear();
        hintManager = HintManager.getInstance();
        hintManager.setWriteRouteOnly();
      }
      ret = joinPoint.proceed(args);
    } catch (Exception ex) {
      log.error("exception error", ex);
    } catch (Throwable ex2) {
      log.error("Throwable", ex2);
    } finally {
      if (Objects.nonNull(master) && Objects.nonNull(hintManager)) {
        hintManager.close();
      }
    }
    return ret;
  }
}
