package ucd.fs.dto;

import lombok.Data;
import ucd.fs.model.User;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}