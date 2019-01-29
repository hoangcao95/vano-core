package vn.vano.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.yotel.admin.jpa.SysParam;
import vn.yotel.commons.bo.GenericBo;

import java.util.List;

public interface SysParamService extends GenericBo<SysParam, Long> {
    Page<SysParam> findAllSysParam(Pageable pageable);
}
