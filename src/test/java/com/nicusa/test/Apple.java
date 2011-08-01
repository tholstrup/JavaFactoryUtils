package com.nicusa.test;

import org.slf4j.LoggerFactory;

import info.tholstrup.utils.code.injection.EntryAndExitHandler;
import info.tholstrup.utils.code.injection.TimingHandler;
import info.tholstrup.utils.factory.BaseParameterizedFactory;

public class Apple
{
    private String color;
    private String type;
    private String etc;

    private Apple()
    {
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getEtc ()
    {
        return etc;
    }

    public void setEtc (String etc)
    {
        this.etc = etc;
    }

    /**
     * Factory for the Apple interface.
     */
    public static class AppleFactory extends BaseParameterizedFactory<Apple>
    {
        private static AppleFactory factoryInstance = new AppleFactory();
        private static Class clazz = Apple.class;

        /**
         * Private constructor to prevent direct instanciation.
         */
        private AppleFactory()
        {
            log = LoggerFactory.getLogger(clazz);
            // false if you want the instances to be cached.
            defaultCreateNewInstanceEachTime = true;
            defaultImplementationClass = clazz;

            // Defaults: You can delete if you don't want any AOP functionality.
            debugInvocationHandlers = new Class[] { TimingHandler.class };
            traceInvocationHandlers = new Class[] { TimingHandler.class, EntryAndExitHandler.class };
        }

        /**
         * @return A concrete implementation of the Apple interface.
         */
        public static Apple getNewInstance ()
        {
            return factoryInstance.getInstance();
        }
    }
}