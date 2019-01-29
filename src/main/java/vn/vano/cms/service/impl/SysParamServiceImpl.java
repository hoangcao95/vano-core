package vn.vano.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vano.cms.repository.SysParamRepo;
import vn.vano.cms.service.SysParamService;
import vn.yotel.admin.jpa.SysParam;
import vn.yotel.commons.bo.impl.GenericBoImpl;

@Service("paramService")
@Transactional
public class SysParamServiceImpl extends GenericBoImpl<SysParam, Long> implements SysParamService {

    @Autowired
    SysParamRepo sysParamRepo;

    @Override
    public SysParamRepo getDAO() {
        return sysParamRepo;
    }

    @Override
    public Page<SysParam> findAllSysParam(Pageable pageable) {
        return sysParamRepo.getAllSysParam(pageable);
    }
}
