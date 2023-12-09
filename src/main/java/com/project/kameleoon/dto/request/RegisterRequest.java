package com.project.kameleoon.dto.request;

import com.project.kameleoon.validation.UniqueEmail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@UniqueEmail
public class RegisterRequest {

    @NotNull
    @Size(min = 2, max = 64)
    @Pattern(regexp = "^[\\p{L}\\d\\s_-]*$", message = "{api.validation.name.message}")
    private String name;

    @NotNull
    @Pattern(regexp = "^[\\w-]+@[a-zA-Z]+\\.[a-zA-Z]+$", message = "{api.validation.email.message}")
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{api.validation.password.message}")
    private String password;
}
