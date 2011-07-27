package info.tholstrup.utils.factory;

import info.tholstrup.utils.code.injection.ProxyAssistant;

/**
 * An abstract factory for items that have only a no argument constructor.
 * <p />
 * This factory provide some Spring-like functionality including caching, InvcationHandler injection (for Aspect Oriented Programming purposes), and
 * plays nice with the EasyMock mock testing framework.
 * <p />
 * Consumers should additionally take a look at the eclipse templates in the etc/eclipse_templates directory of this projects source package.<br/>
 * They should also take a look at the EasyMock testing framework and see if it would benefit their project.  See http://www.easymock.org/
 */
public abstract class BaseNoArgFactory extends BaseFactory {

	private Object instance;

	/**
	 * Internal method which retrieves an instance of the class (which may come from the cache).
	 * <p />
	 * This method uses the underscore naming convention so that overriding classes can define a strongly typed getInstance() method of their own.
	 * 
	 * @return An instance of the implementing class.
	 */
	protected final Object _getInstance() {
		if (isCreateNewInstanceEachTime()) {
			return getNewInstance();
		}
		return getCachedInstance();
	}

	/**
	 * Back door method for testing purposes. It will switch the factory over to a cached version and will always return the instance provided until
	 * the resetFactoryDefaults method is called. (I know it sucks having this as a public method but that's life)
	 * <p />
	 * If this is in non-test code you need to find the person who did it and slap them... then fix the code.
	 * 
	 * @param instance
	 *            The overriding instance.
	 */
	public final void overrideCachedInstance(Object instance) {
		this.instance = instance;
		setCreateNewInstanceEachTime(false);
	}

	/**
	 * Resets the factory to it's initial state, this includes clearing the cache. There should be no reason to use this method unless you are also
	 * using the overrideStaticInstance method.
	 */
	public void resetFactoryDefaults() {
		super.resetFactoryDefaults();
		instance = null;
	}

	/**
	 * @return The cached instance if one exists, otherwise a new instance is created and returned.
	 */
	private final Object getCachedInstance() {
		if (instance == null) {
			instance = getNewInstance();
		}
		return instance;
	}

	/**
	 * @return Creates and returns a new instance of the instanceClass.
	 */
	private final Object getNewInstance() {
		return ProxyAssistant.createProxiedInstance(getInstanceClass(), getInvocationHandlers());
	}

}