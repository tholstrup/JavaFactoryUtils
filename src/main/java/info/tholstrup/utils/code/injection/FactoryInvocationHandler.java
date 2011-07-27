package info.tholstrup.utils.code.injection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * A specialized InvocationHandler which provides a means to reach the ultimately wrapped class if a class is wrapped with several
 * FactoryInvocationHandler.
 */
public abstract class FactoryInvocationHandler implements InvocationHandler
{
    /**
     * @return The instance that this InvocationHandler wraps.
     */
    public abstract Object getDelegate ();

    /**
     * Recursively finds the wrapped class. If the delegate is another FactoryInvocationHandler this class will continue to dig down until
     * it reaches a class which is not an FactoryInvocationHandler but will only be able to return $Proxy if a plain InvocationHandler is
     * reached.
     * 
     * @return The wrapped class.
     */
    public Class getWrappedClass ()
    {
        if (Proxy.isProxyClass(getDelegate().getClass()))
        {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(getDelegate());
            if (invocationHandler instanceof FactoryInvocationHandler)
            {
                return ((FactoryInvocationHandler) invocationHandler).getWrappedClass();
            }
        }
        return getDelegate().getClass();
    }
}