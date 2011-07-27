package info.tholstrup.utils.factory;

import java.lang.reflect.InvocationHandler;

/**
 * The base factory from which other abstract factories are derived.
 */
public abstract class BaseFactory {

	private Class instanceClass;
	private boolean createNewInstanceEachTime = getDefaultCreateNewInstanceEachTime();

	/**
	 * @return True if a new instance should be created each time, false if versions should be cached.
	 */
	protected abstract boolean getDefaultCreateNewInstanceEachTime();

	/**
	 * @return The default implementation Class that this factory should return.
	 */
	protected abstract Class getDefaultImplementationClass();

	/**
	 * @return An array of {@link InvocationHandler}s which have a constructor that takes a single object. It is also expected that the invocation
	 *         handler will call through to the underlying proxied class (cannot be null).
	 */
	protected abstract Class[] getInvocationHandlers();

	protected final Class getInstanceClass() {
		if (instanceClass == null) {
			instanceClass = getDefaultImplementationClass();
		}
		return instanceClass;
	}

	/**
	 * Allows you to override the default implementing class should the need arise.
	 * 
	 * @param instanceClass
	 */
	public final void overrideInstanceClass(Class instanceClass) {
		this.instanceClass = instanceClass;
	}

	protected final boolean isCreateNewInstanceEachTime() {
		return createNewInstanceEachTime;
	}

	protected final void setCreateNewInstanceEachTime(boolean createNewInstanceEachTime) {
		this.createNewInstanceEachTime = createNewInstanceEachTime;
	}

	protected void resetFactoryDefaults() {
		instanceClass = getDefaultImplementationClass();
		createNewInstanceEachTime = getDefaultCreateNewInstanceEachTime();
	}
}
