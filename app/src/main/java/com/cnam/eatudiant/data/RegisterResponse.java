package com.cnam.eatudiant.data;

import com.cnam.eatudiant.Exception.ApiException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterResponse {
    private int status;
    private String message;


    public RegisterResponse(int status, String message) throws ApiException {
        this.status = status;
        this.message = message;

        if (status != 200) {
            throw new ApiException("Code: " + status + " message: " + message);
        }

    }
}
