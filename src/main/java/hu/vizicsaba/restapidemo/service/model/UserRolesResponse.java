package hu.vizicsaba.restapidemo.service.model;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRolesResponse {

    private String roles;

}
