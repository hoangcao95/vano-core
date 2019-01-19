package vn.vano.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.vano.cms.jpa.TopupSubs;

import java.util.List;

public interface TopupSubsRepo extends JpaRepository<TopupSubs, Long> {

    @Query(value = "SELECT a FROM TopupSubs a ", nativeQuery = false)
    List<TopupSubs> findAll();

    @Query(value = "SELECT a FROM TopupSubs a WHERE a.msisdn = :p_msisdn ", nativeQuery = false)
    List<TopupSubs> findByMsisdn(@Param("p_msisdn") String msisdn);

    @Query(value = "SELECT a FROM TopupSubs a WHERE (a.msisdn = :p_msisdn OR '' = :p_msisdn) " +
            "       AND a.createdDate >= :p_fromDate AND a.createdDate <= :p_toDate ", nativeQuery = false)
    List<TopupSubs> findByDate(@Param("p_msisdn") String msisdn,
                               @Param("p_fromDate") String fromDate,
                               @Param("p_toDate") String toDate);
}
