package vn.vano.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.vano.cms.jpa.CoreService;
import vn.vano.cms.jpa.TopupSubs;
import vn.yotel.commons.bo.GenericBo;
import java.util.List;

public interface CoreServiceService extends GenericBo<CoreService, Long>{
    Page<CoreService> findAllCoreService(String code, String name, Pageable pageable);

    CoreService findCoreServiceById(Long id);
}
