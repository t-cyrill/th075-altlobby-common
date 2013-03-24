package jp.dip.th075altlobby.imo.Exception;

import java.io.IOException;

public class InvalidDataLengthException extends IOException {
    public InvalidDataLengthException(String string) {
        super(string);
    }

    /**
     *
     **/
    private static final long serialVersionUID = 1L;
}