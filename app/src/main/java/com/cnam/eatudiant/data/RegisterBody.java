package com.cnam.eatudiant.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterBody {
    private String login;
    private String password;
    private String mail;
}
