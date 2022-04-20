package hu.vizicsaba.restapidemo.data.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "USER")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean active;

    @Column(nullable = false)
    private String address;

    @Column
    private boolean created;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private boolean deleted;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedFlag;

    @Column(nullable = false)
    private String email;

    @Column
    private String emailToken;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private String name;

    @Column
    private String nextLoginChangePwd;

    @Column
    private String password;

    @Column
    private boolean passwordExpired;

    @Column
    private String phone;

    @Column
    private Long settlementId;

    @Column(nullable = false)
    private String tempPassword;

    @Column
    private boolean tempPasswordExpired;

    @Column
    private boolean updated;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String userType;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column
    private String settlementsBySettlementId;

    @Column
    private Long userByCreatedId;

    @Column
    private Long userByDeletedId;

    @Column
    private Long userByUpdatedId;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<UserRoles> userRoles;

}
