package com.sinitek.demo.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.beans.Introspector;

public final class ReflectUtils {

    private static final Logger log = Logger.getLogger(ReflectUtils.class);

    private static final Class[] NO_CLASSES = new Class[0];

    private static final Class[] OBJECT = new Class[]{Object.class};

    private static final Class[] NO_PARAM = new Class[]{};

    private static final Method OBJECT_EQUALS;

    private static final Method OBJECT_HASHCODE;

    static {
        Method eq;
        Method hash;
        try {
            eq = Object.class.getMethod("equals", ReflectUtils.OBJECT);
            hash = Object.class.getMethod("hashCode", ReflectUtils.NO_PARAM);
        } catch (Exception e) {
            throw new RuntimeException("Could not find Object.equals() or Object.hashCode()", e);
        }
        OBJECT_EQUALS = eq;
        OBJECT_HASHCODE = hash;
    }

    public static boolean overrideEquals(Class clazz) {
        Method equals;
        try {
            equals = clazz.getMethod("equals", ReflectUtils.OBJECT);
        } catch (NoSuchMethodException nsme) {
            return false; // its an interface so we can't really tell
            // anything...
        }
        return !ReflectUtils.OBJECT_EQUALS.equals(equals);
    }

    public static boolean overrideHashCode(Class clazz) {
        Method hashCode;
        try {
            hashCode = clazz.getMethod("hashCode", ReflectUtils.NO_PARAM);
        } catch (NoSuchMethodException nsme) {
            return false; // its an interface so we can't really tell
            // anything...
        }
        return !ReflectUtils.OBJECT_HASHCODE.equals(hashCode);
    }

    public static Object newInstance(Class clazz) throws RuntimeException {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            ReflectUtils.log.warn(e);
            throw new RuntimeException(e);
        }
    }

    public static Object newInstance(String className) throws RuntimeException {
        return ReflectUtils.newInstance(ReflectUtils.classForName(className));
    }

    public static Class classForName(String name) throws RuntimeException {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader.loadClass(name);
            }
        } catch (Throwable t) {
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            ReflectUtils.log.warn(e);
            throw new RuntimeException(e);
        }
    }

    public static Class classForName(String name, String defaultPackage) throws RuntimeException {
        Assert.notNull(name);
        if (name.indexOf(".") < 0) {
            name = defaultPackage + "." + name;
        }
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader.loadClass(name);
            }
        } catch (Throwable t) {
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            ReflectUtils.log.warn(e);
            throw new RuntimeException(e);
        }
    }

    public static Class classForName(String name, Class caller) {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader.loadClass(name);
            }
        } catch (Throwable e) {
        }
        try {
            return Class.forName(name, true, caller.getClassLoader());
        } catch (ClassNotFoundException e) {
            ReflectUtils.log.warn(e);
            throw new RuntimeException(e);
        }
    }

    public static boolean isPublic(Class clazz, Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
    }

    public static Object getConstantValue(String name) {
        Class clazz;
        try {
            clazz = ReflectUtils.classForName(StringUtils.qualifier(name));
        } catch (Throwable t) {
            return null;
        }
        try {
            return clazz.getField(StringUtils.unqualify(name)).get(null);
        } catch (Throwable t) {
            return null;
        }
    }

    public static Constructor getDefaultConstructor(Class clazz) throws RuntimeException {

        if (ReflectUtils.isAbstractClass(clazz))
            return null;

        try {
            Constructor constructor = clazz.getDeclaredConstructor(ReflectUtils.NO_CLASSES);
            if (!ReflectUtils.isPublic(clazz, constructor)) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (NoSuchMethodException nme) {
            throw new RuntimeException("Object class " + clazz.getName()
                    + " must declare a default (no-argument) constructor");
        }

    }

    public static boolean isAbstractClass(Class clazz) {
        int modifier = clazz.getModifiers();
        return Modifier.isAbstract(modifier) || Modifier.isInterface(modifier);
    }

    public static boolean isFinalClass(Class clazz) {
        return Modifier.isFinal(clazz.getModifiers());
    }

    public static Method getMethod(Class clazz, Method method) {
        try {
            return clazz.getMethod(method.getName(), method.getParameterTypes());
        } catch (Exception e) {
            return null;
        }
    }

    public static Method getterMethod(Class clazz, String propertyName) {

        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            // only carry on if the method has no parameters
            if (methods[i].getParameterTypes().length == 0) {
                String methodName = methods[i].getName();

                // try "getObject"
                if (methodName.startsWith("getObject")) {
                    String stdMethod = Introspector.decapitalize(methodName.substring(3));
                    // String testOldMethod = methodName.substring(3);
                    if (stdMethod.equals(propertyName)) {
                        return methods[i];
                    }

                }

                // if not "getObject" then try "is"
                /*
                     * boolean isBoolean =
                     * methods[i].getReturnType().equals(Boolean.class) ||
                     * methods[i].getReturnType().equals(boolean.class);
                     */
                if (methodName.startsWith("is")) {
                    String stdMethod = Introspector.decapitalize(methodName.substring(2));
                    // String testOldMethod = methodName.substring(2);
                    if (stdMethod.equals(propertyName)) {
                        return methods[i];
                    }
                }
            }
        }
        return null;
    }

    public static Method setterMethod(Class<? extends Object> clazz, String propertyName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            // only carry on if the method has no parameters
            if (methods[i].getParameterTypes().length == 1) {
                String methodName = methods[i].getName();

                // try "getObject"
                if (methodName.startsWith("set")) {
                    String stdMethod = Introspector.decapitalize(methodName.substring(3));
                    // String testOldMethod = methodName.substring(3);
                    if (stdMethod.equals(propertyName)) {
                        return methods[i];
                    }
                }
            }
        }
        return null;
    }

    public static Object invokeMethod(Object obj, String methodName, Object[] params) {
        try {
            Class[] paramsClasses = ReflectUtils.getParamsClass(params);
            Method method = ReflectUtils.getMethod(obj, methodName, paramsClasses);
            return method.invoke(obj, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getMethod(Object obj, String methodName, Class[] paramTypes) {
        if (paramTypes == null) {
            paramTypes = new Class[]{};
        }
        Method[] methods = obj.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                Class[] types = methods[i].getParameterTypes();
                if (types.length == paramTypes.length) {
                    for (int j = 0; j < types.length; j++) {
                        if (types[j].isAssignableFrom(paramTypes[j])) {
                            return methods[i];
                        }
                    }
                }

            }
        }
        throw new RuntimeException("method not found!" + obj.getClass().getName() + ":" + methodName);
    }

    public static Class<?>[] getParamsClass(Object[] params) {
        if (params == null || params.length < 1) {
            return null;
        }
        Class[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }
        return classes;
    }

    private ReflectUtils() {
    }
}
