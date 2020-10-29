package com.sun.ts.tests;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * Only run tests from the bottom class to mimic the TCK
 */
public class TckExtention implements InvocationInterceptor {

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext)
            throws Throwable {
        Method method = invocationContext.getExecutable();
        Class<?> klass = invocationContext.getTargetClass();
        if (method.getDeclaringClass() != klass) {
            invocation.skip();
        } else {
            invocation.proceed();
        }
    }
}
