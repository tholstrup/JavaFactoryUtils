package info.tholstrup.utils.code.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An invocation handler which times method calls and logs the time to the debug level logger.
 */
public class TimingHandler extends FactoryInvocationHandler
{

    private Logger logger;
    protected Object delegate;

    /**
     * @param delegate
     *            This object which will be wrapped with timing log messages.
     */
    public TimingHandler(Object delegate)
    {
        this.delegate = delegate;
        logger = LoggerFactory.getLogger(getWrappedClass());
    }

    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result = null;
        Date timeStart = new Date();
        Class wrappedClass = getWrappedClass();
        try
        {
            result = method.invoke(delegate, args);
        }
        catch (InvocationTargetException e)
        {
            throw e.getTargetException();
        }
        finally
        {
            if (logger.isDebugEnabled())
            {
                Date timeEnd = new Date();

                StringBuffer buffer = new StringBuffer();
                buffer.append(wrappedClass.getName());
                buffer.append(".");
                buffer.append(method.getName());
                buffer.append(" took ");
                buffer.append((timeEnd.getTime() - timeStart.getTime()));
                buffer.append(" milliseconds to complete.");

                logger.debug(buffer.toString());
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