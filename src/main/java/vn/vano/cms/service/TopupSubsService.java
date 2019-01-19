package vn.vano.cms.service;

import vn.vano.cms.jpa.TopupSubs;
import vn.yotel.commons.bo.GenericBo;

import java.util.List;

public interface TopupSubsService extends GenericBo<TopupSubs, Long>{
    List<TopupSubs> findAll();
    List<TopupSubs> findByMsisdn(String msisdn);
    List<TopupSubs> findByDate(String msisdn, String fromDate, String toDate);
}
