
package logic.exceptions;

import logic.Strings;

public class ZeroNumberException extends NumberFormatException {

    public ZeroNumberException() {
        super(Strings.ERROR_ZERO);
    }

}