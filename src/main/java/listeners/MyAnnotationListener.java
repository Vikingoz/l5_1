package listeners;

import annotation.After;
import annotation.Before;
import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyAnnotationListener {

    public static void run (final String className) throws Exception {
        run(Class.forName(className, true, Thread.currentThread().getContextClassLoader()));
    }

    public static void run (final Class<?> testClass) throws Exception {
        System.out.println("Start MyAnnotationListener");

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

        if (!methods.isEmpty()) {
            Object instance = testClass.getConstructor(null).newInstance();
            System.out.println("we have methods scoup. Create instance:" + instance.toString());

            for (Method testMethod: methods.get(Test.class)) {
                //@Before
                invokeAnnotation(methods, Before.class, instance);
                //@Test
                System.out.println("try to invoke " + Test.class + " from " + instance.toString());
                testMethod.invoke(instance);
                //@After
                invokeAnnotation(methods, After.class, instance);
            }
        }
        System.out.println("End MyAnnotationListener");
    }

    private static void invokeAnnotation(Map<Class<? extends Annotation>, List<Method>> methods, Class<? extends Annotation> annotation, Object instance) {
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
