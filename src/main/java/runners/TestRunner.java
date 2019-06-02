package runners;

import annotation.After;
import annotation.Before;
import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public static void run (final String className) throws Exception {
        System.out.println("Start TestRunner");
        Class clazz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        run(clazz);
    }

    private static Map<Class<? extends Annotation>, List<Method>> getMethods(final Class<?> testClass) throws Exception {
        Map<Class<? extends Annotation>, List<Method>> methods = new HashMap<Class<? extends Annotation>, List<Method>>();
        methods.put(Before.class, new ArrayList<Method>());
        methods.put(Test.class, new ArrayList<Method>());
        methods.put(After.class, new ArrayList<Method>());

        //разберем по аннотациям - наберем скоуп @test, @before и @after
        for (Method method: testClass.getDeclaredMethods()) {

        if (method.isAnnotationPresent(Before.class)) {
            System.out.println("we find Before annotation. Method:" + method.getName());
            methods.get(Before.class).add(method);
        }

        if (method.isAnnotationPresent(Test.class)) {
            System.out.println("we find Test annotation. Method:" + method.getName());
            methods.get(Test.class).add(method);
        }

        if (method.isAnnotationPresent(After.class)) {
            System.out.println("we find After annotation. Method:" + method.getName());
            methods.get(After.class).add(method);
        }
        }
        return methods;
    }

    public static void run (final Class<?> testClass) throws Exception {
        Map<Class<? extends Annotation>, List<Method>> methods = getMethods(testClass);
        if (!methods.isEmpty()) {
            for (Method testMethod: methods.get(Test.class)) {
                Object instance = testClass.getConstructor(null).newInstance();
                System.out.println("we have methods scoup. Create instance:" + instance.toString());
                //@Before
                invokeAnnotationMethod(methods, Before.class, instance);
                //@Test
                System.out.println("try to invoke " + Test.class + " from " + instance.toString());
                try {
                    testMethod.invoke(instance);
                    System.out.println("Method " + testMethod.getName() + " sucsses!");
                } catch(Exception ex) {
                    System.out.println("Exception when invoke method "  + testMethod.getName());
                }
                //@After
                invokeAnnotationMethod(methods, After.class, instance);
            }
        }
        System.out.println("End TestRunner");
    }

    private static void invokeAnnotationMethod(Map<Class<? extends Annotation>, List<Method>> methods, Class<? extends Annotation> annotation, Object instance) {
        methods.get(annotation).stream().forEach(
                p -> {
                    try {
                        System.out.println("try to invoke " + annotation.toString() + " from " + instance.toString());
                        p.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
