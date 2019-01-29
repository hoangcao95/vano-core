package vn.vano.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.yotel.admin.jpa.SysParam;

import java.util.List;

@Repository("paramRepo")
public interface SysParamRepo extends JpaRepository<SysParam, Long> {
    @Query(value = "SELECT a FROM SysParam a ", nativeQuery = false)
    Page<SysParam> getAllSysParam(Pageable pageable);
}
