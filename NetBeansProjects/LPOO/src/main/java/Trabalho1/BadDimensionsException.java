package Trabalho1;

/**
 *
 * @Pedro Bertoncelo Figueiredo
 */
public class BadDimensionsException extends Exception
{
    public BadDimensionsException() {
        super("As dimensões das matrizes são incompatíveis.");
    }

    public BadDimensionsException(String message) {
        super(message);
    }

} // BadDimensionsException

