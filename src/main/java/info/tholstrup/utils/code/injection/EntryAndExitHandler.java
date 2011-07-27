package info.tholstrup.utils.code.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.tholstrup.utils.verification.Verifier;

/**
 * An invocation handler which prints a message to the debug log before the method is entered and after the method has exited.
 */
public class EntryAndExitHandler extends FactoryInvocationHandler
{

    private Logger logger;
    protected Object delegate;

    /**
     * @param delegate
     *            This object which will be wrapped with entry and exit log messages.
     */
    public EntryAndExitHandler(Object delegate)
    {
        Verifier.verifyNotNull(delegate);
        this.delegate = delegate;
        logger = LoggerFactory.getLogger(getWrappedClass());
    }

    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result = null;
        boolean exceptionThrown = false;
        Class wrappedClass = getWrappedClass();
        StringBuffer loggerBuffer = new StringBuffer();
        Exception thrownException = null;
        try
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Entering method ");
            buffer.append(wrappedClass.getName());
            buffer.append(".");
            buffer.append(method.getName());
            if (args == null)
            {
                buffer.append(" with no args ");
            }
            else
            {
                buffer.append(" with the following args ");
                buffer.append(args.toString());
            }
            logger.debug(buffer.toString());

            result = method.invoke(delegate, args);
        }
        catch (InvocationTargetException e)
        {
            exceptionThrown = true;
            thrownException = e;
            throw e.getTargetException();
        }
        finally
        {
            if (logger.isDebugEnabled())
            {

                loggerBuffer.append("Exiting method ");
                loggerBuffer.append(wrappedClass.getName());
                loggerBuffer.append(".");
                loggerBuffer.append(method.getName());
                if (exceptionThrown)
                {
                    loggerBuffer.append(" (Exception returned).");
                }
                else
                {
                    loggerBuffer.append(" (returned normally).");
                }
                if (thrownException != null)
                {
                    logger.debug(loggerBuffer.toString(), thrownException);
                }
                else
                {
                    logger.debug(loggerBuffer.toString());
                }
            }
        }
        return result;
    }

    @Override
    public Object getDelegate ()
    {
        return delegate;
    }
}
