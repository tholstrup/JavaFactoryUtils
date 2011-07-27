package com.nicusa.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.tholstrup.utils.code.injection.EntryAndExitHandler;
import info.tholstrup.utils.code.injection.TimingHandler;
import info.tholstrup.utils.factory.BaseParameterizedFactory;

/**
 * Factory for the Apple interface.
 */
public class AppleFactory extends BaseParameterizedFactory
{
    private static AppleFactory factoryInstance = new AppleFactory();
    private static Logger log = LoggerFactory.getLogger(Apple.class);
    private Class[] debugInvocationHandlers = new Class[] { TimingHandler.class };
    private Class[] traceInvocationHandlers = new Class[] { TimingHandler.class, EntryAndExitHandler.class };

    /**
     * Private constructor to prevent direct instanciation.
     */
    private AppleFactory()
    {
    }

    /**
     * @return An instance of this factory.
     */
    public static AppleFactory getFactoryInstance ()
    {
        return factoryInstance;
    }

    /**
     * @return A concrete implementation of the Apple interface.
     */
    public Apple getInstance ()
    {
        return (Apple) _getInstance(new Class[] {}, new Object[] {});
        // TODO create as many of these methods as necessary depending on the different creation method signatures.
    }

    /**
     * @return True if a new instance should be created each time, false if caching should be used.
     */
    @Override
    protected boolean getDefaultCreateNewInstanceEachTime ()
    {
        return true;
    }

    /**
     * @return The implementation class that should be created by the factory.
     */
    @Override
    protected Class getDefaultImplementationClass ()
    {
        return Apple.class;
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