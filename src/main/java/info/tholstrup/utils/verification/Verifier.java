package info.tholstrup.utils.verification;

import java.util.Collection;

public class Verifier
{

    /**
     * If o is null a runtime excpetion is thrown.
     * 
     * @param o
     *            The object to test for null.
     * @throws IllegalArgumentException
     *             if object o is null.
     */
    public static void verifyNotNull (Object o)
    {
        verifyNotNull(o, null);
    }

    /**
     * If o is null a runtime excpetion is thrown.
     * 
     * @param o
     *            The object to test for null.
     * @param message
     *            The message to display if o is null.
     * @throws IllegalArgumentException
     *             if object o is null.
     */
    public static void verifyNotNull (Object o, String message)
    {
        if (o == null)
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * If s is null or empty a runtime excpetion is thrown.
     * 
     * @param s
     *            The string which should be non-null and non-empty.
     * @throws IllegalArgumentException
     *             if s is null or empty.
     */
    public static void verifyNotEmpty (String s)
    {
        verifyNotEmpty(s, null);
    }

    /**
     * If s is null or empty a runtime excpetion is thrown.
     * 
     * @param s
     *            The string which should be non-null and non-empty.
     * @param message
     *            The message to display if s is null or empty.
     * @throws IllegalArgumentException
     *             if s is null or empty.
     */
    public static void verifyNotEmpty (String s, String message)
    {
        if (s == null || s.length() < 0)
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * If c is null or empty a runtime excpetion is thrown.
     * 
     * @param c
     *            The collection which should be non-null and non-empty.
     * @throws IllegalArgumentException
     *             if c is null or empty.
     */
    public static void verifyNotEmpty (Collection c)
    {
        verifyNotEmpty(c, null);
    }

    /**
     * If c is null or empty a runtime excpetion is thrown.
     * 
     * @param c
     *            The collection which should be non-null and non-empty.
     * @param message
     *            The message to display if c is null or empty.
     * @throws IllegalArgumentException
     *             if c is null or empty.
     */
    public static void verifyNotEmpty (Collection c, String message)
    {
        if (c == null || c.isEmpty())
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * If b is false a runtime excpetion is thrown.
     * 
     * @param b
     *            The boolean which should be true.
     * @param message
     *            The message to display if b is false.
     * @throws IllegalArgumentException
     *             if b is false.
     */
    public static void verifyTrue (boolean b, String message)
    {
        if (!b)
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * If b is false a runtime excpetion is thrown.
     * 
     * @param b
     *            The boolean which should be true.
     * @throws IllegalArgumentException
     *             if b is false.
     */
    public static void verifyTrue (boolean b)
    {
        if (!b)
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * If b is true a runtime excpetion is thrown.
     * 
     * @param b
     *            The boolean which should be false.
     * @param message
     *            The message to display if b is true.
     * @throws IllegalArgumentException
     *             if b is true.
     */
    public static void verifyFalse (boolean b, String message)
    {
        if (b)
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * If b is true a runtime excpetion is thrown.
     * 
     * @param b
     *            The boolean which should be false.
     * @throws IllegalArgumentException
     *             if b is true.
     */
    public static void verifyFalse (boolean b)
    {
        if (b)
        {
            throw new IllegalArgumentException();
        }
    }
}
