package info.tholstrup.utils.factory;

import java.util.Arrays;

import info.tholstrup.utils.verification.Verifier;

/**
 * Represents the parameter types and parameters for a method or constructor. It simply holds parameter types and parameters and implements the equals
 * and hashCode methods so that methods or constructors with the same method/constructor signature will equate. This class is immutable.
 */
public class MethodSignature {

	private Class[] parameterTypes;
	private Object[] parameters;
	private int numberOfParams;
	private int hashCode;

	/**
	 * @param parameterTypes
	 *            Must be non-null and contain the same number of elements as parameters.
	 * @param parameters
	 *            Must be non-null and contain the same number of elements as parameterTypes.
	 */
	public MethodSignature(Class[] parameterTypes, Object[] parameters) {
		Verifier.verifyTrue(parameterTypes.length == parameters.length);
		Verifier.verifyNotNull(parameters);
		Verifier.verifyNotNull(parameterTypes);

		this.numberOfParams = parameters.length;
		this.parameters = parameters;
		this.parameterTypes = parameterTypes;
		// This class is immutable so we can cache the hashCode to save processing power.
		this.hashCode = computeHashCode();
	}

	/**
	 * @return The array (in order) of parameters.
	 */
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * @return The array (in order) of parameter types.
	 */
	public Class[] getParameterTypes() {
		return parameterTypes;
	}

	/**
	 * @return The number of parameters and parameter types as the class guarantees they are equal.
	 */
	public int getNumberOfParams() {
		return numberOfParams;
	}

	public int hashCode() {
		return hashCode;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MethodSignature other = (MethodSignature) obj;
		if (numberOfParams != other.numberOfParams) {
			return false;
		}
		if (!Arrays.equals(parameterTypes, other.parameterTypes)) {
			return false;
		}
		if (!Arrays.equals(parameters, other.parameters)) {
			return false;
		}
		return true;
	}

	/**
	 * @return The hash code for this object.
	 */
	private int computeHashCode() {
		// You might worry about integer overflow here but it is ok... jvm spec does not throw an error it just drops off the most significant
		// bits (like rolling over the odometer on your car or your high-score on pac-man)
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + numberOfParams;
		result = PRIME * result + MethodSignature.computeHashCode(parameterTypes);
		result = PRIME * result + MethodSignature.computeHashCode(parameters);
		return result;
	}

	/**
	 * Helper method which computes a hash code for
	 * 
	 * @return The hash code for the given array.
	 */
	private static int computeHashCode(Object[] array) {
		final int PRIME = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (int index = 0; index < array.length; index++) {
			result = PRIME * result + (array[index] == null ? 0 : array[index].hashCode());
		}
		return result;
	}
}