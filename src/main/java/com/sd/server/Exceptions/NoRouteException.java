package com.sd.server.Exceptions;

public class NoRouteException extends Exception{
    public NoRouteException() {
        super("Rota impossível, ou faltam dados");
    }

    public NoRouteException(String message) {
        super(message);
    }

    public NoRouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRouteException(Throwable cause) {
        super(cause);
    }

    protected NoRouteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
