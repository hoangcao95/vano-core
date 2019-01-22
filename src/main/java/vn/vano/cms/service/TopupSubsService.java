package vn.vano.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vano.cms.jpa.TopupSubs;
import vn.yotel.commons.bo.GenericBo;

import java.util.Date;
import java.util.List;

public interface TopupSubsService extends GenericBo<TopupSubs, Long>{
    List<TopupSubs> findAll();
    List<TopupSubs> findByMsisdn(String msisdn);
    Page<TopupSubs> findByDate(String msisdn, Date fromDate, Date toDate, Pageable pageable);
}
