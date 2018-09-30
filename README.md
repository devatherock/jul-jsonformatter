[![CircleCI](https://img.shields.io/circleci/project/github/devaprasadh/jul-jsonformatter/master.svg)](https://circleci.com/gh/devaprasadh/jul-jsonformatter)
[![Download](https://api.bintray.com/packages/devatherock/jul-jsonformatter/jul-jsonformatter/images/download.svg) ](https://bintray.com/devatherock/jul-jsonformatter/jul-jsonformatter/_latestVersion)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jul-jsonformatter&metric=ncloc)](https://sonarcloud.io/dashboard?id=jul-jsonformatter)
[![Coverage Status](https://coveralls.io/repos/github/devaprasadh/jul-jsonformatter/badge.svg?branch=master)](https://coveralls.io/github/devaprasadh/jul-jsonformatter?branch=master)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jul-jsonformatter&metric=bugs)](https://sonarcloud.io/dashboard?id=jul-jsonformatter)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jul-jsonformatter&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=jul-jsonformatter)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
# jul-jsonformatter
[Formatter](https://docs.oracle.com/javase/7/docs/api/java/util/logging/Formatter.html) to log `java.util.logging(JUL)` messages as JSON, compatible with [Logstash](https://www.elastic.co/products/logstash)

## Usage
- Include any one of [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5), [Jackson](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.9.7) or [json-simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple/1.1.1) libraries in classpath
- Include a `logging.properties` file in classpath with `io.github.devatherock.json.formatter.JSONFormatter` as the formatter

```
handlers=java.util.logging.ConsoleHandler

java.util.logging.ConsoleHandler.level=INFO
java.util.logging.ConsoleHandler.formatter=io.github.devatherock.json.formatter.JSONFormatter
```

The path to the logging.properties file can also be passed as JVM arg 
like `-Djava.util.logging.config.file=/path/to/logging.properties`

## Samples
Each line in the generated log will be a JSON. Sample one line message:
```json
{
    "@timestamp": "2018-09-30T08:00:17.632-05:00",
    "logger_name": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "level": "INFO",
    "thread_name": "Test worker",
    "class": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "method": "testSimpleMessage",
    "message": "First message"
}
```

Multi-line message:
```json
{
    "@timestamp": "2018-09-30T08:00:17.654-05:00",
    "logger_name": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "level": "INFO",
    "thread_name": "Test worker",
    "class": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "method": "testMessageWithNewLine",
    "message": "Next message\n"
}
```

Message with exception:
```json
{
    "@timestamp": "2018-09-30T08:33:58.394-05:00",
    "logger_name": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "level": "SEVERE",
    "thread_name": "Test worker",
    "class": "io.github.devatherock.json.formatter.helpers.GSONFormatterTest",
    "method": "testMessageWithExceptionWithExceptionMessage",
    "message": "Test message with exception",
    "exception": {
        "exception_class": "java.lang.RuntimeException",
        "exception_message": "test exception",
        "stack_trace": "java.lang.RuntimeException: test exception\n\tat com.devaprasadh.json.formatter.helpers.GSONFormatterTest.testMessageWithExceptionWithExceptionMessage(GSONFormatterTest.java:115)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)\n\tat org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)\n\tat org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)\n\tat org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)\n\tat org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)\n\tat org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)\n\tat org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)\n\tat org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)\n\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)\n\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)\n\tat org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)\n\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)\n\tat org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)\n\tat org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:27)\n\tat org.junit.runners.ParentRunner.run(ParentRunner.java:363)\n\tat org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.runTestClass(JUnitTestClassExecutor.java:106)\n\tat org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:58)\n\tat org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:38)\n\tat org.gradle.api.internal.tasks.testing.junit.AbstractJUnitTestClassProcessor.processTestClass(AbstractJUnitTestClassProcessor.java:66)\n\tat org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.processTestClass(SuiteTestClassProcessor.java:51)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:35)\n\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)\n\tat org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch(ContextClassLoaderDispatch.java:32)\n\tat org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:93)\n\tat com.sun.proxy.$Proxy2.processTestClass(Unknown Source)\n\tat org.gradle.api.internal.tasks.testing.worker.TestWorker.processTestClass(TestWorker.java:109)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:35)\n\tat org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)\n\tat org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:155)\n\tat org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:137)\n\tat org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:404)\n\tat org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:63)\n\tat org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:46)\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n\tat org.gradle.internal.concurrent.ThreadFactoryImpl$ManagedThreadRunnable.run(ThreadFactoryImpl.java:55)\n\tat java.lang.Thread.run(Thread.java:748)\n"
    }
}
```