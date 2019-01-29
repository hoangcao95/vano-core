package vn.vano.cms.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.vano.cms.bean.SysParamBean;
import vn.vano.cms.common.Constants;
import vn.vano.cms.service.SysParamService;
import vn.yotel.admin.jpa.SysParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/sys")
public class SysParamController {

    @Autowired
    SysParamService sysParamService;

    Logger logger = Logger.getLogger(SysParamController.class);

    @RequestMapping(value = "/sysParam-list.html", method = RequestMethod.GET)
    public String listSysParam(Model model,
                               Pageable pageable){
        logger.debug("BEGIN::sysParam-list.html");
        try {
            String not_found_message = "";
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE);
            Page<SysParam> page = sysParamService.findAllSysParam(_pageable);
            if(page.getContent().size() == 0){
                not_found_message = Constants.NOT_FOUND_MESSAGE;
            }
            //if (redirectAttributes.getFlashAttributes().equals("successMessage")){
            //    model.addAttribute("successMessage", Constants.SysParam.ADD_SYS_SUCCESS);
            //}
            model.addAttribute("page", page);
            model.addAttribute("not_found_message", not_found_message);
        }catch (Exception ex){
            logger.error("", ex);
        }
        logger.debug("BEGIN::sysParam-list.html");
        return "sys/sysParam_list";
    }

    @RequestMapping(value = "/initSysParam-add.html", method = RequestMethod.GET)
    public ModelAndView showFormAddSysParam(){
        logger.debug("BEGIN::sysParam-add.html");
        ModelAndView model = new ModelAndView("/sys/sysParam_add");
        try{
            SysParamBean sysParamBean = new SysParamBean();
            model.addObject("sys", sysParamBean);
        }catch (Exception ex){
            logger.error("", ex);
        }
        logger.debug("END::sysParam-add.html");
        return model;
    }

    @RequestMapping(value = "/sysParam-add.html", method = {RequestMethod.POST})
    public String AddSysParam(@ModelAttribute(value = "sys") SysParamBean sysParamBean,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              Pageable pageable){
        logger.debug("BEGIN::sysParam-add.html");
        try {
            if(sysParamBean != null){
                SysParam sysParam = new SysParam();
                sysParam.setKey(sysParamBean.getKey());
                sysParam.setValue(sysParamBean.getValue());
                sysParam.setType(sysParamBean.getType());


                Calendar calendar = Calendar.getInstance();
                sysParam.setCreatedDate(calendar.getTime());

                sysParam.setStatus(Constants.STATUS_ACTIVITY);
                sysParamService.create(sysParam);
                redirectAttributes.addFlashAttribute("successMessage", Constants.SysParam.ADD_SYS_SUCCESS);
            }
            return "redirect:/sys/sysParam-list.html";
        }catch (Exception ex){
            logger.error("", ex);
            getListSysParam(model, pageable);
            model.addAttribute("successMessage", Constants.SysParam.ADD_SYS_ERROR);
        }
        logger.debug("BEGIN::sysParam-add.html");
        return "/sys/sysParam_list";
    }

    @RequestMapping(value = "/sysParam-edit.html/{id}", method = RequestMethod.GET)
    public ModelAndView showFormEditSysParam(@PathVariable("id") Long id){
        logger.debug("BEGIN::sysParam-edit.html");
        ModelAndView model = new ModelAndView("/sys/sysParam_update");
        SysParam sysParam = sysParamService.findOne(id);
        try{
            SysParamBean sysParamBean = new SysParamBean();
            sysParamBean.setId(id.toString());
            sysParamBean.setKey(sysParam.getKey());
            sysParamBean.setValue(sysParam.getValue());
            sysParamBean.setType(sysParam.getType());
            sysParamBean.setCreatedDate(sysParam.getCreatedDate().toString());

            model.addObject("sys", sysParamBean);
        }catch (Exception ex){
            logger.error("", ex);
        }
        logger.debug("END::sysParam-edit.html");
        return model;
    }

    @RequestMapping(value = "/sysParam-edit.html", method = RequestMethod.POST)
    public String EditSysParam(@ModelAttribute(value = "sys") SysParamBean sysParamBean,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               Pageable pageable){
        logger.debug("BEGIN::sysParam-edit.html");
        try{
            SysParam sysParam = new SysParam();
            sysParam.setId(Long.parseLong(sysParamBean.getId()));
            if (sysParamBean != null){
                sysParam.setKey(sysParamBean.getKey());
                sysParam.setValue(sysParamBean.getValue());
                sysParam.setType(sysParamBean.getType());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date _createDate = dateFormat.parse(sysParamBean.getCreatedDate());
                sysParam.setCreatedDate(_createDate);

                Calendar calendar = Calendar.getInstance();
                sysParam.setModifiedDate(calendar.getTime());
                sysParamService.update(sysParam);
                redirectAttributes.addFlashAttribute("successMessage", Constants.SysParam.UPDATE_SYS_SUCCESS);
            }
            return "redirect:/sys/sysParam-list.html";
        }catch (Exception ex){
            logger.error("", ex);
            getListSysParam(model, pageable);
            model.addAttribute("successMessage", Constants.SysParam.UPDATE_SYS_ERROR);
        }
        logger.debug("END::sysParam-edit.html");
        return "/sys/sysParam_list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteSysParam(@PathVariable("id") String id,
                                 Model model,
                                 Pageable pageable){
        logger.debug("BEGIN::delete");
        try {
            sysParamService.deleteById(Long.parseLong(id));
            getListSysParam(model, pageable);
            model.addAttribute("successMessage", Constants.SysParam.DELETE_SYS_SUCCESS);
        }catch (Exception ex){
            logger.error("", ex);
            getListSysParam(model, pageable);
            model.addAttribute("successMessage", Constants.SysParam.DELETE_SYS_ERROR);
        }
        logger.debug("END::delete");

        return "sys/sysParam_list";
    }

    private void getListSysParam(Model model,
                                 Pageable pageable){
        logger.debug("BEGIN::getListSysParam");
        try {
            String not_found_message = "";
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE);
            Page<SysParam> page = sysParamService.findAllSysParam(_pageable);
            if(page.getContent().size() == 0){
                not_found_message = Constants.NOT_FOUND_MESSAGE;
            }
            //if (redirectAttributes.getFlashAttributes().equals("successMessage")){
            //    model.addAttribute("successMessage", Constants.SysParam.ADD_SYS_SUCCESS);
            //}
            model.addAttribute("page", page);
            model.addAttribute("not_found_message", not_found_message);
        }catch (Exception ex){
            logger.error("", ex);
        }
        logger.debug("BEGIN::getListSysParam");
    }
}
