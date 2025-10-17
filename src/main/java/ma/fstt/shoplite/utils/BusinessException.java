package ma.fstt.shoplite.utils;

import ma.fstt.shoplite.enums.ErrorEnum;

public class BusinessException extends RuntimeException {
    private final ErrorEnum error;

    public BusinessException(ErrorEnum error) {
        super(error.name());
        this.error = error;
    }

    public ErrorEnum getError() {
        return error;
    }
}
