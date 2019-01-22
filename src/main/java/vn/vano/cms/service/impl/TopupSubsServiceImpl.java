package vn.vano.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.vano.cms.jpa.TopupSubs;
import vn.vano.cms.repository.TopupSubsRepo;
import vn.vano.cms.service.TopupSubsService;
import vn.yotel.commons.bo.GenericBo;
import vn.yotel.commons.bo.impl.GenericBoImpl;

import java.util.Date;
import java.util.List;

@Service(value = "topupSubsService")
public class TopupSubsServiceImpl extends GenericBoImpl<TopupSubs, Long> implements TopupSubsService {

    @Autowired
    TopupSubsRepo topupSubsRepo;

    @Override
    public TopupSubsRepo getDAO() {
        return topupSubsRepo;
    }

    @Override
    public List<TopupSubs> findByMsisdn(String msisdn) {
        return findByMsisdn(msisdn);
    }

    @Override
    public Page<TopupSubs> findByDate(String msisdn, Date fromDate, Date toDate, Pageable pageable) {
        Page<TopupSubs> topupSubsList = topupSubsRepo.findByDate(msisdn, fromDate, toDate, pageable);
        return topupSubsList;
    }

}
