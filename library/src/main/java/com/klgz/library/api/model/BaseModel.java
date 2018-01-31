package com.klgz.library.api.model;

/**
 * Created by asus on 2016/3/10.
 */
public class BaseModel<T> {

    private T data;
    private Status status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return status.isSuccess();
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
