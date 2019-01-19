package vn.vano.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vano.cms.repository.AuthUserRoleRepo;
import vn.vano.cms.service.AuthUserRoleService;

import java.util.List;

@Service(value = "authUserRoleService")
public class AuthUserRoleServiceImpl implements AuthUserRoleService {

    @Autowired
    private AuthUserRoleRepo authUserRoleRepo;

    @Override
    public List<Long> findRoleIdByUserId(Long id) {
        return authUserRoleRepo.getRoleIdByUserId(id);
    }
}
