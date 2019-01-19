package vn.vano.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.vano.cms.jpa.TopupSubs;

import java.util.List;

public interface AuthUserRoleRepo extends JpaRepository<TopupSubs, Long>{

    @Query(value = " SELECT a.role_id FROM auth_user_role a WHERE a.user_id = :userId ", nativeQuery = true)
    List<Long> getRoleIdByUserId(@Param("userId") Long userId);
}
