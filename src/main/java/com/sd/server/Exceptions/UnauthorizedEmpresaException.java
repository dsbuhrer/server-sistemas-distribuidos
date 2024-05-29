package com.sd.server.Exceptions;

public class UnauthorizedEmpresaException extends Exception{
    public UnauthorizedEmpresaException() {
        super("Usuário não autorizado");
    }

    public UnauthorizedEmpresaException(String message) {
        super(message);
    }

    public UnauthorizedEmpresaException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedEmpresaException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedEmpresaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
