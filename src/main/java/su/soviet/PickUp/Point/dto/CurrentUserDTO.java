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
public class CurrentUserDTO {
    private Long id;
    private String name;
    private Set<Role> roles;
}
