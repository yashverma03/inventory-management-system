package com.app.modules.users.dto;

import com.app.common.annotations.IsEnum;
import com.app.modules.users.enums.UserRoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO for creating a new user account")
public class CreateUserDTO {

    @Schema(description = "User email address (must be unique)", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "User's first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 2, maxLength = 50)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Schema(description = "User's last name (optional)", example = "Doe", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50)
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Schema(description = "User password (will be hashed before storage)", example = "SecurePassword123!", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 6, format = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "User role in the system", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {
            "ADMIN", "MANAGER" })
    @NotNull(message = "Role is required")
    @IsEnum(value = UserRoleEnum.class)
    private UserRoleEnum role;
}
