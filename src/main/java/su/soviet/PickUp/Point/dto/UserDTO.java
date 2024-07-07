package su.soviet.PickUp.Point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import su.soviet.PickUp.Point.model.Role;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String password;
    private Set<Role> roles;
}
