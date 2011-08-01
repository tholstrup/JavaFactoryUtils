package info.tholstrup.utils.factory;

import java.lang.reflect.InvocationHandler;

/**
 * The base factory from which other abstract factories are derived.
 */
public abstract class BaseFactory<T>
{
    private boolean createNewInstanceEachTime;
    private Class<? extends T> instanceClass;

    protected boolean defaultCreateNewInstanceEachTime;
    protected Class<? extends T> defaultImplementationClass;

    /**
     * @return An array of {@link InvocationHandler}s which have a constructor that takes a single object. It is also expected that the
     *         invocation handler will call through to the underlying proxied class (cannot be null).
     */
    protected abstract Class[] getInvocationHandlers ();

    protected final Class<? extends T> getInstanceClass ()
    {
        if (instanceClass == null)
        {
            instanceClass = defaultImplementationClass;
        }
        return instanceClass;
    }

    /**
     * Allows you to override the default implementing class should the need arise.
     * 
     * @param instanceClass
     */
    public final void overrideInstanceClass (Class<? extends T> instanceClass)
    {
        this.instanceClass = instanceClass;
    }

    protected final boolean isCreateNewInstanceEachTime ()
    {
        return createNewInstanceEachTime;
    }

    protected final void setCreateNewInstanceEachTime (boolean createNewInstanceEachTime)
    {
        this.createNewInstanceEachTime = createNewInstanceEachTime;
    }

    protected void resetFactoryDefaults ()
    {
        instanceClass = defaultImplementationClass;
        createNewInstanceEachTime = defaultCreateNewInstanceEachTime;
    }
}
