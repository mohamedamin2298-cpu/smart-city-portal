package project.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.backend.enums.Role;
@Getter
@AllArgsConstructor
public class ProfileResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
}