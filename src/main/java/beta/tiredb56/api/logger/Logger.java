package beta.tiredb56.api.logger;

import beta.tiredb56.api.annotations.LoggerAnnotation;

public class Logger {

    public boolean error = getClass().getAnnotation(LoggerAnnotation.class).error();

}
