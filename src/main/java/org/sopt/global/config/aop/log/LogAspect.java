package org.sopt.global.config.aop.log;

import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

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
        String id = joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName();
        StopWatch stopWatch = new StopWatch(id);
        stopWatch.start();
        try {

            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            if (log.isInfoEnabled()) {
                log.info("▶ {} 소요시간 {} ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());
            }
        }
    }

    @Around("controllerMethods()")
    public Object logControllerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs != null && log.isInfoEnabled()) {
            HttpServletRequest raw = attrs.getRequest();
            ContentCachingRequestWrapper request = wrapIfNecessary(raw);

            String uri = decode(request.getRequestURI());
            String httpMethod = request.getMethod();
            String params = extractParams(request);
            String body = extractBody(request);

            log.info("[{}] {}", httpMethod, uri);
            log.info("    handler = {}.{}",
                    joinPoint.getSignature().getDeclaringType().getSimpleName(),
                    joinPoint.getSignature().getName());
            log.info("    params  = {}", params);
            if (!body.isEmpty()) {
                log.info("    body    = {}", body);
            }
        }

        return joinPoint.proceed();
    }

    private String extractParams(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String safe = name.replaceAll("\\.", "_");
            json.put(safe, req.getParameter(name));
        }
        return json.toString();
    }

    private String decode(String uri) {
        return URLDecoder.decode(uri, StandardCharsets.UTF_8);
    }

    private String extractBody(ContentCachingRequestWrapper req) {
        byte[] buf = req.getContentAsByteArray();
        if (buf.length == 0) return "";
        try {
            return new String(buf, req.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private ContentCachingRequestWrapper wrapIfNecessary(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        }
        return new ContentCachingRequestWrapper(request);
    }
}
