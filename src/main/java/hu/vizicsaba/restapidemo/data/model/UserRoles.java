package hu.vizicsaba.restapidemo.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "USER_ROLES")
@Entity
public class UserRoles implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    private String roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @Id
    private User user;

}
