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
public class UserRequest {

    private String address;

    private String email;

    private String emailToken;

    private String name;

    private String phone;

    private String userType;

    private String userName;

    private Long settlementId;

    private String settlementsBySettlementId;

    List<String> roles;

}
