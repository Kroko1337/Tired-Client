package beta.tiredb56.api.logger.impl;

import beta.tiredb56.api.annotations.LoggerAnnotation;

@LoggerAnnotation(error = true)

public enum ErrorLog {

    ERROR_LOG;

    public void doLog(String error) {
        System.out.println("[ERROR] " + error);
    }

}
