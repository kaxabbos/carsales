package com.carsales.controllers.Main;

import com.carsales.models.Users;
import com.carsales.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Main {
    @Autowired
    protected RepoUsers repoUsers;
    @Autowired
    protected RepoCarts repoCarts;
    @Autowired
    protected RepoCars repoCars;
    @Autowired
    protected RepoComments repoComments;
    @Autowired
    protected RepoIncomes repoIncomes;
    @Autowired
    protected RepoDealerships repoDealerships;
    @Value("${upload.path}")
    protected String uploadPath;

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return repoUsers.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }
}
