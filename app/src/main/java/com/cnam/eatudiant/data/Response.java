package com.cnam.eatudiant.data;

import com.cnam.eatudiant.Exception.ApiException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Response<T> {
    private int status;
    private String message;

    private T datas;

    public Response(int status, String message, T datas) throws ApiException {
        this.status = status;
        this.message = message;

        if (status != 200) {
            throw new ApiException("Code: " + status + " message: " + message);
        }

        this.datas = datas;
    }
}
