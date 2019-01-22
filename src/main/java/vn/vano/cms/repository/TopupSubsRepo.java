package vn.vano.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.vano.cms.jpa.TopupSubs;

import java.util.Date;
import java.util.List;

public interface TopupSubsRepo extends JpaRepository<TopupSubs, Long> {

    @Query(value = "SELECT a FROM TopupSubs a ", nativeQuery = false)
    public List<TopupSubs> findAll();

    @Query(value = "SELECT a FROM TopupSubs a WHERE a.msisdn = :p_msisdn ", nativeQuery = false)
    public List<TopupSubs> findByMsisdn(@Param("p_msisdn") String msisdn);

    @Query(value = "SELECT a FROM TopupSubs a WHERE (a.msisdn = :msisdn OR '' = :msisdn) " +
                   "AND a.createdDate >= :fromDate AND a.createdDate <= :toDate " +
                   "ORDER BY a.createdDate DESC ", nativeQuery = false)
    public Page<TopupSubs> findByDate(@Param("msisdn") String msisdn,
                                      @Param("fromDate") Date fromDate,
                                      @Param("toDate") Date toDate,
                                      Pageable pageable);
    }
