package hu.vizicsaba.restapidemo.service.exception;

public class RestApiDemoNotFoundException extends RuntimeException {

    public RestApiDemoNotFoundException() {
        super();
    }

    public RestApiDemoNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public RestApiDemoNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}

