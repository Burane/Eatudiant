package com.cnam.eatudiant.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserAuth {

    @NonNull private String login;
    @NonNull private String password;
}
