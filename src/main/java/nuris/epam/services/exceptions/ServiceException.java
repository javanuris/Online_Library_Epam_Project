package nuris.epam.services.exceptions;

/**
 * Created by User on 20.03.2017.
 */
public class ServiceException extends Exception {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
