package info.tholstrup.utils.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.tholstrup.utils.code.injection.ProxyAssistant;

/**
 * 
 * An abstract factory for items that have parameterized constructors.
 * <p />
 * This factory provide some Spring-like functionality including caching, InvcationHandler injection (for Aspect Oriented Programming
 * purposes), and plays nice with the EasyMock mock testing framework.
 * <p />
 * If caching is enabled this class will by default key the cache by {@link MethodSignature} objects. If you wish to provide a different
 * caching key you may override the {@link BaseParameterizedFactory#getCacheKey(Class[], Object[])} method in this class. If you need to
 * override the key using other parameters you will need to create your own factory.
 * <p />
 * Consumers should additionally take a look at the eclipse templates in the etc/eclipse_templates directory of this projects source
 * package.<br/>
 * They should also take a look at the EasyMock testing framework and see if it would benefit their project. See http://www.easymock.org/
 * 
 * @see MethodSignature
 */
public abstract class BaseParameterizedFactory<T> extends BaseFactory
{

    /**
     * Empty class array for noArg constructors. Prevents unnecessary object creation.
     */
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[] {};
    /**
     * Empty Object array for noArg constructors. Prevents unnecessary object creation.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

    protected Class[] debugInvocationHandlers = EMPTY_CLASS_ARRAY;
    protected Class[] traceInvocationHandlers = EMPTY_CLASS_ARRAY;
    protected Logger log = LoggerFactory.getLogger(BaseParameterizedFactory.class);

    /**
     * Weak hash map prevents the cache from growing without bound, may want to replace with a better cache but this will work for now. Some
     * suggestion might be a SoftHashMap of which there are freely available implementations on the net and more sophisticated LRU and LFU
     * caches which may have implementations available.
     * <p />
     * It should be noted that the current default key implementation (MethodSignature) is discarded as soon as the key is placed in the map
     * so if frequent garbage collections occur we will lose all caching benefits in a WeakHashMap implementation.
     */
    private Map<MethodSignature, T> cache = Collections.synchronizedMap(new WeakHashMap());

    /**
     * 
     * @param parameterTypes
     * @param parameters
     * @return An object which properly implements the equals and hashCode methods. Should be overridden if the default implementation is
     *         insufficient for your purposes.
     */
    protected MethodSignature getCacheKey (Class[] parameterTypes, Object[] parameters)
    {
        return new MethodSignature(parameterTypes, parameters);
    }

    public T getInstance ()
    {
        return _getInstance(null, null);
    }

    public T getInstance (Class[] parameterTypes, Object[] parameters)
    {
        return _getInstance(parameterTypes, parameters);
    }

    /**
     * 
     * Internal method which retrieves an instance of the class (which may come from the cache).
     * <p />
     * This method uses the underscore naming convention so that overriding classes can define a strongly typed getInstance() method of
     * their own.
     * 
     * @param parameterTypes
     * @param parameters
     * @return An instance of the implementing class.
     */
    protected final T _getInstance (Class[] parameterTypes, Object[] parameters)
    {
        if (isCreateNewInstanceEachTime())
        {
            return getNewInstance(parameterTypes, parameters);
        }
        return getStaticInstance(parameterTypes, parameters);
    }

    /**
     * Back door method for testing purposes. It will switch the factory over to a cached version and will always return the instance
     * provided for the given parameter list until the resetFactoryDefaults method is called. (I know it sucks having this as a public
     * method but that's life)
     * <p />
     * If this is in non-test code you need to find the person who did it and slap them... then fix the code.
     * 
     * @param parameterTypes
     *            The parameter types for which the given instance will be cached.
     * @param parameters
     *            The parameter list for which the given instance will be cached.
     * 
     * @param instance
     *            The overriding instance.
     */
    public void overrideCachedInstance (Class[] parameterTypes, Object[] parameters, T instance)
    {
        // make the cache a normal hashMap so that it does not get garbage collected.
        cache = new HashMap();
        setCreateNewInstanceEachTime(false);
        MethodSignature cacheKey = getCacheKey(parameterTypes, parameters);
        cache.put(cacheKey, instance);
    }

    /**
     * Resets the factory to it's initial state, this includes clearing the cache. There should be no reason to use this method unless you
     * are also using the overrideStaticInstance method.
     */
    @Override
    public void resetFactoryDefaults ()
    {
        super.resetFactoryDefaults();
        // reset the cache to a weakHashMap because if overrideCachedInstance was called it was replaced with a hashMap.
        cache.clear();
    }

    private final T getNewInstance (Class[] parameterTypes, Object[] parameters)
    {
        return (T) ProxyAssistant.createProxiedInstance(getInstanceClass(), parameterTypes, parameters, getInvocationHandlers());
    }

    private final T getStaticInstance (Class[] parameterTypes, Object[] parameters)
    {
        MethodSignature cacheKey = getCacheKey(parameterTypes, parameters);

        Object cacheHit = cache.get(cacheKey);
        if (cacheHit != null)
        {
            return (T) cacheHit;
        }

        T newInstance = getNewInstance(parameterTypes, parameters);
        cache.put(cacheKey, newInstance);
        return newInstance;
    }

    /**
     * @return Classes which implement InvoactionHandler and have a constructor which takes Object as its only parameter (may be empty but
     *         cannot be null).
     */
    @Override
    protected Class[] getInvocationHandlers ()
    {
        if (log.isTraceEnabled())
        {
            return traceInvocationHandlers;
        }
        else if (log.isDebugEnabled())
        {
            return debugInvocationHandlers;
        }
        return EMPTY_CLASS_ARRAY;
    }

}
