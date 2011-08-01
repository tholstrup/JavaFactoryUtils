package info.tholstrup.utils.code.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Provides convenience method to create proxied objects.
 */
public class ProxyAssistant
{

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[] {};

    /**
     * 
     * @param instanceClass
     *            The Class which is to be instantiated (cannot be null).
     * @param invocationHandlers
     *            An array of {@link FactoryInvocationHandler}s which have a constructor that takes a single object. It is also expected
     *            that the invocation handler will call through to the underlying proxied class (cannot be null).
     * @return The proxied instance of the class wrapped with the given {@link InvocationHandler}s.
     */
    public static Object createProxiedInstance (Class instanceClass, Class[] invocationHandlers)
    {
        return createProxiedInstance(instanceClass, EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY, invocationHandlers);
    }

    /**
     * 
     * @param instanceClass
     *            The Class which is to be instantiated (cannot be null).
     * @param parameterTypes
     *            The constructor parameter types (cannot be null).
     * @param parameters
     *            The constructor parameters (cannot be null).
     * @param invocationHandlers
     *            An array of {@link FactoryInvocationHandler}s which have a constructor that takes a single object. It is also expected
     *            that the invocation handler will call through to the underlying proxied class.
     * @return The proxied instance of the class wrapped with the given {@link InvocationHandler}s.
     */
    public static <T> T createProxiedInstance (Class<T> instanceClass, Class[] parameterTypes, Object[] parameters,
            Class[] invocationHandlers)
    {
        T instance = null;

        try
        {
            Constructor<T> constructor = instanceClass.getConstructor(parameterTypes);
            instance = constructor.newInstance(parameters);

            for (int i = 0; i < invocationHandlers.length; i++)
            {
                Constructor<T> invocationHandlerConstructor = invocationHandlers[i].getConstructor(new Class[] { Object.class });
                InvocationHandler handler = (InvocationHandler) invocationHandlerConstructor.newInstance(new Object[] { instance });
                instance = (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), handler);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return instance;
    }

}
