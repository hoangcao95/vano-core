package vn.vano.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.vano.cms.jpa.CoreService;
import vn.vano.cms.repository.CoreServiceRepo;
import vn.vano.cms.service.CoreServiceService;
import vn.yotel.commons.bo.impl.GenericBoImpl;

@Service
public class CoreServiceServiceImpl extends GenericBoImpl<CoreService,Long> implements CoreServiceService{

    @Autowired
    CoreServiceRepo coreServiceRepo;

    @Override
    public CoreServiceRepo getDAO() {
        return coreServiceRepo;
    }

    @Override
    public Page<CoreService> findAllCoreService(String code, String name, Pageable pageable) {
        return getDAO().findAllCoreService(code, name, pageable);
    }

    @Override
    public CoreService findCoreServiceById(Long id) {
        return coreServiceRepo.getCoreServiceById(id);
    }
}
