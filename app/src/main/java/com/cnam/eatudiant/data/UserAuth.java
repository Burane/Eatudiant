package com.cnam.eatudiant.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserAuth {
    private String login;
    private String password;
}
