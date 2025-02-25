package org.hahn.TicketEase.security;


import org.hahn.TicketEase.dtos.LoginFormDto;
import org.hahn.TicketEase.dtos.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticketEase/auth")
@CrossOrigin("*")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private JwtEncoder jwtEncoder ;


    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginFormDto loginFormDto){

        TokenDto tokenDto = new TokenDto();

       Authentication authentication  = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginFormDto.getUsername(),loginFormDto.getPassword())
       );

        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(loginFormDto.getUsername())
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        tokenDto.setAccessToken(jwt);
        return tokenDto;

    }
}
