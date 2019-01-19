package vn.vano.cms.service;

import java.util.List;

public interface AuthUserRoleService {
    List<Long> findRoleIdByUserId(Long id);
}
