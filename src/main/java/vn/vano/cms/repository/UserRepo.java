package vn.vano.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import vn.yotel.admin.jpa.AuthUser;

import java.util.List;

public interface UserRepo extends vn.yotel.admin.repository.AuthUserRepo{
    @Query(value = " SELECT a FROM AuthUser a ", nativeQuery = false)
    public Page<AuthUser> getAllAuthUser(Pageable pageable);
}
