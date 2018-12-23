package kmiecik.michal.earningscalculator.domain.errorhandling;

public class AppError {

    private final ErrorReason errorReason;
    private final String message;

    public AppError(ErrorReason errorReason, String message) {
        this.errorReason = errorReason;
        this.message = message;
    }

    public ErrorReason getErrorReason() {
        return errorReason;
    }

    public String getMessage() {
        return message;
    }
}
