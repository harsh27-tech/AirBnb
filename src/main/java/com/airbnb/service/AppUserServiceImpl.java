package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl {
    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public AppUser createUser( AppUser user){
        String hashpw=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10));
        user.setPassword(hashpw);

        return appUserRepository.save(user);
    }

    public String verifyLogin(LoginDto logindto) {
        //Optional<AppUser> opUser = appUserRepository.findByEmailOrUsername((logindto.getUsername());
        Optional<AppUser> opUser = appUserRepository.findByEmailOrUsername(logindto.getUsername(),logindto.getUsername());
        if(opUser.isPresent()) {
            AppUser appUser = opUser.get();
            if (BCrypt.checkpw(logindto.getPassword(), appUser.getPassword())) {
                return jwtService.generateToken(appUser);
            }
        }
        return  null;
    }
}
