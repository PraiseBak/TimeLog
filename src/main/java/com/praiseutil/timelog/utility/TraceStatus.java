package com.praiseutil.timelog.utility;

import java.util.Objects;

public class TraceStatus {
    private final TraceId traceId;
    private final long startTimeMs;
    private final String message;

    public TraceStatus(TraceId traceId, long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraceStatus that = (TraceStatus) o;
        return startTimeMs == that.startTimeMs && Objects.equals(traceId, that.traceId) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traceId, startTimeMs, message);
    }
}

