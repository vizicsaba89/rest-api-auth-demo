package hu.vizicsaba.restapidemo.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserResponse {

    private long id;

    private boolean active;

    private String address;

    private boolean created;

    private LocalDateTime createdAt;

    private boolean deleted;

    private LocalDateTime deletedAt;

    private String deletedFlag;

    private String email;

    private LocalDateTime lastLogin;

    private String name;

    private String phone;

    private long settlementId;

    private boolean updated;

    private LocalDateTime updatedAt;

    private String userType;

    private String userName;

    private String settlementsBySettlementId;

    private long userByCreatedId;

    private long userByDeletedId;

    private long userByUpdatedId;

    private List<UserRolesResponse> userRoles;

}
