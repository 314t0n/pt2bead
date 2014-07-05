package logic.exceptions;

import logic.Strings;

public class NegativeNumberException extends NumberFormatException {

    public NegativeNumberException() {
        super(Strings.ERROR_NEGATIVE);
    }

}
