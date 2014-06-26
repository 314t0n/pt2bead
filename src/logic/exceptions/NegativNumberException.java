package logic.exceptions;

import logic.Strings;

public class NegativNumberException extends NumberFormatException {

    public NegativNumberException() {
        super(Strings.ERROR_NEGATIVE);
    }

}
