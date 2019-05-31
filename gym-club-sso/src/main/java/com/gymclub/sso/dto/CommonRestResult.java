package com.gymclub.sso.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/04/11 00:15.
 * Description :
 */
@Data
public class CommonRestResult implements Serializable {
    private static final long serialVersionUID = -8965948882721460674L;
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;
    public static final int FAILURE = 400;

    private int code;
    private String message;
    private Object data;

    private CommonRestResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CommonRestResult ok() {
        RestResponseBuilder builder = new RestResponseBuilder(SUCCESS);
        return builder.withMessage("success").build();
    }

    public static CommonRestResult ok(Object data) {
        RestResponseBuilder builder = new RestResponseBuilder(SUCCESS);
        return builder.withMessage("success").withData(data).build();
    }

    public static CommonRestResult error() {
        RestResponseBuilder builder = new RestResponseBuilder(ERROR);
        return builder.withMessage("error").build();
    }

    public static CommonRestResult error(String message) {
        RestResponseBuilder builder = new RestResponseBuilder(ERROR);
        return builder.withMessage(message).build();
    }

    public static CommonRestResult failure(String message) {
        return new RestResponseBuilder(FAILURE).withMessage(message).build();
    }

    public static IBuilder builder(int code) {
        return new RestResponseBuilder(code);
    }

    public interface IBuilder {

        IBuilder withMessage(String message);

        IBuilder withData(Object data);

        CommonRestResult build();
    }

    private static class RestResponseBuilder implements IBuilder {
        private final int code;
        private String message;
        private Object data;

        public RestResponseBuilder(int code) {
            this.code = code;
        }

        @Override
        public IBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public IBuilder withData(Object data) {
            this.data = data;
            return this;
        }

        @Override
        public CommonRestResult build() {
            return new CommonRestResult(
                    this.code,
                    this.message,
                    this.data);
        }
    }
}
