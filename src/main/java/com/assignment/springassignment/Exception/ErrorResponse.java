package com.assignment.springassignment.Exception;

public class ErrorResponse {
    int status;
    String Error;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(int status, String error) {
        this.status = status;
        Error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        this.Error = error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", Error='" + Error + '\'' +
                '}';
    }
}
