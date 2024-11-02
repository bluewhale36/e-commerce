package com.guncat.ecommerce.users.service;

import com.guncat.ecommerce.common.enums.IsEnabled;
import com.guncat.ecommerce.common.enums.IsLocked;
import com.guncat.ecommerce.users.domain.entity.Users;
import com.guncat.ecommerce.users.domain.entity.UsersRole;
import com.guncat.ecommerce.users.domain.vo.EmailVO;
import com.guncat.ecommerce.users.domain.vo.PasswordVO;
import com.guncat.ecommerce.users.domain.vo.TelVO;
import com.guncat.ecommerce.users.domain.vo.UserIdVO;
import com.guncat.ecommerce.users.dto.RegisterDTO;
import com.guncat.ecommerce.users.enums.Role;
import com.guncat.ecommerce.users.repository.UsersRepository;
import com.guncat.ecommerce.users.repository.UsersRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService_Impl implements IF_UsersService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;
    private final UsersRoleRepository usersRoleRepository;

    @Override
    public void register(RegisterDTO registerDTO) {

        // VO 객체를 통한 정규식 유효성 검사.
        UserIdVO userIdVO = new UserIdVO(registerDTO.getUserId());
        PasswordVO passwordVO = new PasswordVO(registerDTO.getPassword());
        TelVO telVO = new TelVO(registerDTO.getTel());
        EmailVO emailVO = new EmailVO(registerDTO.getEmail());

        // 중복되는 user_code 의 존재 여부 확인
        String newUserCode = UUID.randomUUID().toString();
        while(usersRepository.existsById(newUserCode)) {
            newUserCode = UUID.randomUUID().toString();
        }

        // 회원 Entity Build.
        Users user = Users.builder()
                .userCode(newUserCode)
                .userId(userIdVO.userId())
                .password(passwordEncoder.encode(passwordVO.password()))
                .name(registerDTO.getName())
                .tel(telVO.tel())
                .email(emailVO.email())
                .is_enabled(IsEnabled.ENABLED)
                .is_locked(IsLocked.UNLOCKED)
                .build();

        // 회원 권한 Entity Build.
        UsersRole usersRole = UsersRole.builder()
                .roleCode(UUID.randomUUID().toString())
                .userCode(user.getUserCode())
                .role(Role.REGULAR)
                .build();

        // 회원 정보 INSERT
        usersRepository.save(user);

        // 회원 권한 정보 INSERT
        usersRoleRepository.save(usersRole);
    }

    @Override
    public Long chkDuplicatedUserId(String userId) {
        return usersRepository.countByUserId(userId);
    }

    @Override
    public Long chkDuplicatedEmail(String email) {
        return usersRepository.countByEmail(email);
    }

    @Override
    public String getCurrentUserId() {
        /*
            SecurityContextHolder 는 애플리케이션 전역적으로 현재 로그인한 사용자의 인증 상태를 관리함.
            그 안의 Authentication 객체는 현재 로그인한 사용자의 인증 정보를 담고 있음.
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*
            로그인 하지 않았을 경우 authentication 객체는 null 값이며,
            익명의 사용자일 경우 authentication.isAuthenticated() 값이 false 이다.
         */
        if (authentication != null && authentication.isAuthenticated()) {

            // Authentication 객체 내부 정보 중 인증된 사용자의 주체(사용자, Principal)을 가져옴.
            Object obj = authentication.getPrincipal();

            /*
                Spring Security 의 인증 방식을 사용하지 않을 경우, obj 객체의 타입은 String 일 가능성이 있음.
                -> CastingException 방지.
             */
            if (obj instanceof UserDetails) {
                return ((UserDetails) obj).getUsername();
            }
        }
        return null;
    }


}
