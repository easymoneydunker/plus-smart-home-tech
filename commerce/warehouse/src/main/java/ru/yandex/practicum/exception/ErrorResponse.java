package ru.yandex.practicum.exception;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private Throwable cause;
    private List<StackTraceElement> stackTrace;
    private String httpStatus;
    private String userMessage;
    private String message;
    private List<Throwable> suppressed;
    private String localizedMessage;

    public ErrorResponse(Throwable cause, List<StackTraceElement> stackTrace, String httpStatus, String userMessage, String message, List<Throwable> suppressed, String localizedMessage) {
        this.cause = cause;
        this.stackTrace = stackTrace;
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
        this.message = message;
        this.suppressed = suppressed;
        this.localizedMessage = localizedMessage;
    }

    public ErrorResponse() {
    }

    public Throwable getCause() {
        return this.cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public List<StackTraceElement> getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(List<StackTraceElement> stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getUserMessage() {
        return this.userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Throwable> getSuppressed() {
        return this.suppressed;
    }

    public void setSuppressed(List<Throwable> suppressed) {
        this.suppressed = suppressed;
    }

    public String getLocalizedMessage() {
        return this.localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ErrorResponse other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$cause = this.getCause();
        final Object other$cause = other.getCause();
        if (!Objects.equals(this$cause, other$cause)) return false;
        final Object this$stackTrace = this.getStackTrace();
        final Object other$stackTrace = other.getStackTrace();
        if (!Objects.equals(this$stackTrace, other$stackTrace))
            return false;
        final Object this$httpStatus = this.getHttpStatus();
        final Object other$httpStatus = other.getHttpStatus();
        if (!Objects.equals(this$httpStatus, other$httpStatus))
            return false;
        final Object this$userMessage = this.getUserMessage();
        final Object other$userMessage = other.getUserMessage();
        if (!Objects.equals(this$userMessage, other$userMessage))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (!Objects.equals(this$message, other$message)) return false;
        final Object this$suppressed = this.getSuppressed();
        final Object other$suppressed = other.getSuppressed();
        if (!Objects.equals(this$suppressed, other$suppressed))
            return false;
        final Object this$localizedMessage = this.getLocalizedMessage();
        final Object other$localizedMessage = other.getLocalizedMessage();
        return Objects.equals(this$localizedMessage, other$localizedMessage);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $cause = this.getCause();
        result = result * PRIME + ($cause == null ? 43 : $cause.hashCode());
        final Object $stackTrace = this.getStackTrace();
        result = result * PRIME + ($stackTrace == null ? 43 : $stackTrace.hashCode());
        final Object $httpStatus = this.getHttpStatus();
        result = result * PRIME + ($httpStatus == null ? 43 : $httpStatus.hashCode());
        final Object $userMessage = this.getUserMessage();
        result = result * PRIME + ($userMessage == null ? 43 : $userMessage.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $suppressed = this.getSuppressed();
        result = result * PRIME + ($suppressed == null ? 43 : $suppressed.hashCode());
        final Object $localizedMessage = this.getLocalizedMessage();
        result = result * PRIME + ($localizedMessage == null ? 43 : $localizedMessage.hashCode());
        return result;
    }

    public String toString() {
        return "ErrorResponse(cause=" + this.getCause() + ", stackTrace=" + this.getStackTrace() + ", httpStatus=" + this.getHttpStatus() + ", userMessage=" + this.getUserMessage() + ", message=" + this.getMessage() + ", suppressed=" + this.getSuppressed() + ", localizedMessage=" + this.getLocalizedMessage() + ")";
    }
}