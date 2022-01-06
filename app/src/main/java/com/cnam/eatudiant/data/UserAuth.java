package com.cnam.eatudiant.data;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserAuth {

    @NonNull private String username;
    @NonNull private String password;
}
