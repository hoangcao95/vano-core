package vn.vano.cms.web.controller;

        import org.apache.log4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.domain.Sort;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.ui.ModelMap;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;
        import vn.vano.cms.bean.ServiceBean;
        import vn.vano.cms.common.Constants;
        import vn.vano.cms.jpa.CoreService;
        import vn.vano.cms.service.AuthUserRoleService;
        import vn.vano.cms.service.AuthUserService;
        import vn.vano.cms.service.CoreServiceService;
        import vn.yotel.admin.jpa.AuthUser;
        import vn.yotel.admin.service.AuthRoleService;

        import javax.validation.Valid;
        import java.util.List;

@Controller
@RequestMapping(value = "/service")
public class CoreServiceController {
    @Autowired
    CoreServiceService coreServiceService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthRoleService authRoleService;
    @Autowired
    AuthUserRoleService authUserRoleService;

    Logger logger = Logger.getLogger(CoreServiceController.class);

    @RequestMapping(value = "/service_list.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('SADMIN')")
    public String searchCoreService(ModelMap modelMap,
                                    @RequestParam(value = "code", defaultValue = "", required = false) String code,
                                    @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                    Pageable pageable) {
        try{
            String not_found_message = "";

            modelMap.addAttribute("code", code);
            modelMap.addAttribute("name", name);

            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "name"));
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
            Page<CoreService> page = coreServiceService.findAllCoreService(code, name, Constants.STATUS_ACTIVITY, _pageable);
            if (page.getContent().size() == 0) {
                not_found_message = Constants.NOT_FOUND_MESSAGE;
            }
            modelMap.addAttribute("page", page);
            modelMap.addAttribute("not_found_message", not_found_message);
        } catch(Exception ex){
            logger.error("", ex);
        }
        return "/services/service_list";
    }

    @RequestMapping(value = "/service_add.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('SADMIN')")
    public String showFormAddAsset(Model model,
                                   ServiceBean serviceBean) {
        serviceBean = new ServiceBean();
        model.addAttribute("service", serviceBean);
        return "services/service_add";
    }

    @RequestMapping(value = "/service_add.html", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('SADMIN')")
    public String addCoreService(Model model,
                                 ModelMap modelMap,
                                 @ModelAttribute(value = "service") ServiceBean serviceBean,
                                 Pageable pageable,
                                 RedirectAttributes redirectAttributes) {
        try {
            if(serviceBean != null) {
                CoreService coreService = new CoreService();

                coreService.setCode(serviceBean.getCode());
                coreService.setName(serviceBean.getName());
                coreService.setDescription(serviceBean.getDescription());
                coreService.setStatus(Constants.STATUS_ACTIVITY);
                coreServiceService.create(coreService);
            }
            searchCoreService(modelMap, null, null, pageable);
            model.addAttribute("successMessage", Constants.CoreService.ADD_SERVICE_SUCCESS);
        } catch (Exception ex){
            logger.error("", ex);
            model.addAttribute("successMessage", Constants.CoreService.ADD_SERVICE_ERROR);
        }
//        return "redirect:/service/service_list";
        return "/services/service_list";
    }

}
