package vn.vano.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.vano.cms.jpa.CoreService;
import vn.vano.cms.jpa.TopupSubs;

import java.util.List;

@Repository(value = "coreServiceRepo")
public interface CoreServiceRepo extends JpaRepository<CoreService, Long> {

    @Query(value = "SELECT a FROM CoreService a WHERE (:code IS NULL OR :code ='' OR a.code LIKE CONCAT('%', :code, '%'))" +
            "AND (:name IS NULL OR :name ='' OR a.name LIKE CONCAT('%', :name, '%'))" +
            "AND a.status=:status", nativeQuery = false)
    public Page<CoreService> findAllCoreService(@Param("code") String code,
                                          @Param("name") String name,
                                          @Param("status") Integer status,
                                          Pageable pageable);
    @Query(value = "SELECT a FROM CoreService a ", nativeQuery = false)
    public List<CoreService> getAllCoreService();
}
