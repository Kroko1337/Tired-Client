package beta.tiredb56.api.logger.impl;

import beta.tiredb56.api.annotations.LoggerAnnotation;

@LoggerAnnotation()

public enum SuccessLog {

    SUCCESS_LOG;

    public void doLog(String success) {
        System.out.println("[SUCCESS] " + success);
    }

}
