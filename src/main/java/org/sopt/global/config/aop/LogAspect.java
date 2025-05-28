package org.sopt.global.config.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(* org.sopt..service..*(..))")
    public void businessMethods() {}

    @Pointcut("execution(* org.sopt..controller..*(..))")
    public void controllerMethods() {}

    @Around("businessMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            log.info("▶ [TOTAL] {} 소요시간 {} ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
        }
    }

    @Around("controllerMethods()")
    public Object logControllerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs != null) {
            HttpServletRequest req = attrs.getRequest();
            String uri       = URLDecoder.decode(req.getRequestURI(), StandardCharsets.UTF_8);
            String httpMethod= req.getMethod();
            JSONObject params= extractParams(req);

            log.info("[{}] {}", httpMethod, uri);
            log.info("    handler = {}.{}",
                    joinPoint.getSignature().getDeclaringType().getSimpleName(),
                    joinPoint.getSignature().getName());
            log.info("    params  = {}", params.toString());
        }

        return joinPoint.proceed();
    }

    private JSONObject extractParams(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String safe = name.replaceAll("\\.", "_");
            json.put(safe, request.getParameter(name));
        }

        return json;
    }
}
