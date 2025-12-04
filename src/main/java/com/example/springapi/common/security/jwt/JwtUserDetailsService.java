package com.example.springapi.common.security.jwt;

import java.util.List;

import com.example.springapi.common.security.user.UserAccount;
import com.example.springapi.common.security.user.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * DB からユーザー情報を取得する UserDetailsService 実装。
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

    /**
     * UserAccountRepository
     */
    private final UserAccountRepository userAccountRepository;
    /**
     * JwtUserDetailsServiceのコンストラクタ
     * @param userAccountRepository
     */

    public JwtUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * ユーザー名からユーザー情報を取得
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("Username must not be blank");
        }
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<SimpleGrantedAuthority> authorities = CollectionUtils.isEmpty(userAccount.getRoles())
                ? List.of()
                : userAccount.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        log.debug("Loaded user {} with {} authorities", username, authorities.size());
        return User.withUsername(userAccount.getUsername())
                .password(userAccount.getPassword())
                .authorities(authorities)
                .accountLocked(!userAccount.isEnabled())
                .disabled(!userAccount.isEnabled())
                .build();
    }
}

