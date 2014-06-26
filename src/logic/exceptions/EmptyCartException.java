package logic.exceptions;

import logic.Strings;

public class EmptyCartException extends Exception {

    public EmptyCartException() {
        super(Strings.ERROR_NO_PRODUCT);
    }
    
}
