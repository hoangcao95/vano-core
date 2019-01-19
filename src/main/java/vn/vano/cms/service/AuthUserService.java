package vn.vano.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.commons.bo.GenericBo;

public interface AuthUserService extends GenericBo<AuthUser, Long>, vn.yotel.admin.service.AuthUserService{
    Page<AuthUser> findAllAuthUser(Pageable pageable);
}
