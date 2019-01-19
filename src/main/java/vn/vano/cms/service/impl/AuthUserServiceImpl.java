package vn.vano.cms.service.impl;

//import vn.vano.cms.service.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.vano.cms.repository.UserRepo;
import vn.vano.cms.service.AuthUserService;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.model.AuthUserModel;
import vn.yotel.commons.bo.impl.GenericBoImpl;

import java.util.List;

@Service(value = "userService")
public class AuthUserServiceImpl extends GenericBoImpl<AuthUser, Long> implements AuthUserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserRepo getDAO() {
        return this.userRepo;
    }

    @Override
    public Page<AuthUser> findAllAuthUser(Pageable pageable) {
        return userRepo.getAllAuthUser(pageable);
    }

    @Override
    public AuthUser findByUsername(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public Page<AuthUser> findUsers(String s, Pageable pageable) {
        return userRepo.findUsers(s, pageable);
    }

    @Override
    public List<AuthUser> findAllUsers(String s, Byte aByte, Byte aByte1) {
        return userRepo.findAllUsers(s, aByte, aByte1);
    }

    @Override
    public Page<AuthUserModel> listUsers(String s, String s1, String s2, int i, Pageable pageable) {
        return null;
    }

    @Override
    public List<Long> findRolesByUserId(Long userId) {
        return null;
    }

    @Override
    public AuthUser findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public AuthUser findOne(Long id) {
        return (AuthUser) this.getDAO().findOne(id);
    }
}
