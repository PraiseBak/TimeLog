# 개요
김영환님의 스프링부트 강의중 로깅부분을 가져와 모듈화하였습니다. 
다음과 같은 로깅을 하는 모듈입니다.
java 8버전 이상 지원
<img width="775" alt="image" src="https://github.com/PraiseBak/TimeLog/assets/60121346/2d2abb95-3ae8-47c2-ac0d-f214d8b6c643">

# 사용방법

위 소스 코드를 빌드(./gradlew build)하여 ./build/libs 내에 생성된 jar 파일을 사용하면 됩니다.


```
public class TimeLogApplication {
    public static void main(String[] args) {
        TraceLocalLogTrace traceLocalLogTrace = new TraceLocalLogTrace();
        TraceStatus startTraceStatus = traceLocalLogTrace.begin("asdf");
        traceLocalLogTrace.end(startTraceStatus);
    }
}
```


## 스프링부트 내 사용

appconfig 등에서 다음과 같이 Bean을 등록해줍니다

```
@Configuration
public class AppConfig {

    @Bean
    public LogTrace logTrace() {
        return new TraceLocalLogTrace();
    }
}
```


## AOP 등에서 사용
```
@Aspect
@Component
@RequiredArgsConstructor
@Log
public class TimeLogAOP {

    private final LogTrace logTrace;

    @Pointcut("execution(* com.aiddoru.dev.Service..*(..))")
    public void allService() {}

    @Pointcut("execution(* com.aiddoru.dev.Controller..*(..))")
    public void allController() {}

    @Around("allController() || allService()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = logTrace.begin(joinPoint.getSignature().toShortString());
        try {
            Object result = joinPoint.proceed();
            logTrace.end(traceStatus);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}

```
