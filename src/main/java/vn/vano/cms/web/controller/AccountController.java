package vn.vano.cms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.vano.cms.bean.AccountBean;
import vn.vano.cms.common.IGsonBase;
import vn.vano.cms.common.MessageContants;
import vn.vano.cms.service.AuthUserRoleService;
import vn.vano.cms.service.AuthUserService;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.jpa.Role;
import vn.yotel.admin.service.AuthRoleService;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/account")
public class AccountController implements IGsonBase{
    private static Logger LOG = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    AuthUserService authUserService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthRoleService authRoleService;
    @Autowired
    AuthUserRoleService authUserRoleService;

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET})
    public String index(Locale locale, Model model, Pageable pageable, RedirectAttributes redirectAttributes) {
        LOG.debug("BEGIN::index.html");
        try {
            List<Role> lstAllRole = authRoleService.findAll();
            model.addAttribute("lstAllRole", lstAllRole);
            model.addAttribute("account", new AccountBean());
            model.addAttribute("ACTION", "ADD");

            getListUser(model, pageable);
        } catch (Exception ex) {

        }
        LOG.debug("END::index.html");
        return "/account/index";
    }

    @RequestMapping(value = "/change_password.html", method = {RequestMethod.GET})
    public String indexChangePass(Locale locale, Model model) {
        LOG.debug("BEGIN::change_password.html");

        LOG.debug("END::change_password.html");
        return "/change_password";
    }

    @RequestMapping(value = "/create.html", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('SADMIN')") //Chi chap nhan cac user co quyen SADMIN moi thuc hien dc chuc nang nay
    public String createAccount(Locale locale, Model model,
                                @ModelAttribute("account") @Valid AccountBean account,
                                RedirectAttributes redirectAttributes,
                                Pageable pageable) {
        try {
            AuthUser user = new AuthUser();
            user.setUserName(account.getUserName());
            user.setFullName(account.getFullName());
            user.setEmail(account.getEmail());
            user.setGender((account.getGender()));
            if(account.getDateOfBirth() != null && !account.getDateOfBirth().isEmpty()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDay = dateFormat.parse(account.getDateOfBirth());
                user.setDateOfBirth(birthDay);
            }

            List<Long> lstRoleId = account.getRoles();
            List<Role> lstRole = new ArrayList<>();
            if(lstRoleId != null && !lstRoleId.isEmpty()) {
                for (Long roleId : lstRoleId) {
                    Role item = authRoleService.findOne(roleId);
                    lstRole.add(item);
                }
            }
            user.setPassword(passwordEncoder.encode(account.getPassword()));
            user.setSalt("5876695f8e4e1811");
            user.setAuthRoles(lstRole);
            user.setStatus(new Byte("1").byteValue());
            authUserService.create(user);
            redirectAttributes.addAttribute("successMessage", MessageContants.Account.ADDNEW_SUCCESS);
            return "redirect:/account/index.html";
        } catch (Exception ex) {
            LOG.error("", ex);
            getListUser(model, pageable);
            model.addAttribute("successMessage", MessageContants.Account.ADDNEW_ERROR);
            model.addAttribute("account", account);
            model.addAttribute("ACTION", "ADD");
        }
        return "/account/index";
    }

    @RequestMapping(value = "/update.html/{id}", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('SADMIN')") //Chi chap nhan cac user co quyen SADMIN moi thuc hien dc chuc nang nay
    public String updateAccount(Locale locale, Model model,
                                @PathVariable("id") Long id,
                                Pageable pageable) {
        try {
            AuthUser user = authUserService.findOne(id);
            List<Role> lstAllRole = authRoleService.findAll();
            model.addAttribute("lstAllRole", lstAllRole);
            if (user != null) {
                AccountBean bean = new AccountBean();
                bean.setUserId(id.toString());
                bean.setUserName(user.getUserName());
                bean.setFullName(user.getFullName());
                bean.setEmail(user.getEmail());
                bean.setDateOfBirth((user.getDateOfBirth().toString()));
                bean.setGender(user.getGender());
                bean.setPassword(passwordEncoder.encode(user.getPassword()));

                List<Long> lstRoldId = authUserRoleService.findRoleIdByUserId(id);
                bean.setRoles(lstRoldId);

                model.addAttribute("account", bean);
                model.addAttribute("ACTION", "UPDATE");

                getListUser(model, pageable);
            }

        } catch (Exception ex) {
            LOG.error("", ex);
            model.addAttribute("successMessage", MessageContants.Account.ADDNEW_ERROR);
//            model.addAttribute("account", account);
            getListUser(model, pageable);
    }
        return "/account/index";
    }

    @RequestMapping(value = "/update.html", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('SADMIN')") //Chi chap nhan cac user co quyen SADMIN moi thuc hien dc chuc nang nay
    public String updateAccounts(Locale locale, Model model,
                                @ModelAttribute("account") @Valid AccountBean account,
                                Pageable pageable) {
        try {
            AuthUser user = authUserService.findOne(Long.parseLong(account.getUserId()));
            user.setUserName(account.getUserName());
            user.setFullName(account.getFullName());
            user.setEmail(account.getEmail());
            if(account.getDateOfBirth() != null && !account.getDateOfBirth().isEmpty()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDay = dateFormat.parse(account.getDateOfBirth());
                user.setDateOfBirth(birthDay);
            }
            user.setGender(account.getGender());
            user.setPassword(account.getPassword());

            List<Long> listRoleId = account.getRoles();
            List<Role> listRole = new ArrayList<>();
            if(listRoleId != null || !listRoleId.isEmpty() ){
                for (Long roleId: listRoleId) {
                    Role role = authRoleService.findOne(roleId);
                    listRole.add(role);
                }
            }
            user.setAuthRoles(listRole);
            authUserService.update(user);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        getListUser(model, pageable);
        return "/account/index";
    }

    @RequestMapping(value = "/delete.html/{id}", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('SADMIN')") //Chi chap nhan cac user co quyen SADMIN moi thuc hien dc chuc nang nay
    public String deleteAccount(Locale locale, Model model,
                                @PathVariable("id") Long id) {
        try {
            AuthUser user = authUserService.findOne(id);
            if (user != null) {
                authUserService.delete(user);
                model.addAttribute("successMessage", MessageContants.Account.DELETE_SUCCESS);
            } else {
                model.addAttribute("successMessage", MessageContants.Account.DELETE_ERROR);
            }
            model.addAttribute("account", new AccountBean());
            return "/account/index";
        } catch (Exception ex) {
            LOG.error("", ex);
            model.addAttribute("successMessage", MessageContants.Account.DELETE_ERROR);
        }
        return "/account/index";
    }

    @RequestMapping(value = "/clear.html", method = {RequestMethod.GET})
    public String clear(Locale locale, Model model) {
        LOG.debug("BEGIN::clear.html");
        try {
//            List<Role> lstAllRole = authRoleService.findAll();
//            model.addAttribute("lstAllRole", lstAllRole);
//            model.addAttribute("account", new AccountBean());
        } catch (Exception ex) {

        }
        LOG.debug("END::clear.html");
        return "redirect:/account/index.html";
    }

    @RequestMapping(value = "/onchange_pass.html", method = {RequestMethod.POST})
    public String changePass(Locale locale, Model model,
                             @RequestParam String username,
                             @RequestParam String old_password,
                             @RequestParam String new_password,
                             @RequestParam String verify_password) {
        LOG.debug("BEGIN::onchange_pass.html");
        try {
            AuthUser _user = authUserService.findByUsername(username);
            if (!passwordEncoder.matches(old_password, _user.getPassword()) || !new_password.equals(verify_password)) {
                model.addAttribute("username", username);
                model.addAttribute("old_password", old_password);
                model.addAttribute("new_password", new_password);
                model.addAttribute("verify_password", verify_password);
                return "/change_password";
            } else {
                String newEncryptedPassword = passwordEncoder.encode(new_password);
                _user.setPassword(newEncryptedPassword);
                authUserService.update(_user);
                return "redirect:/logout.html";
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        LOG.debug("END::onchange_pass.html");
        return "redirect:/logout.html";
    }

    private void getListUser(Model model, Pageable pageable) {
        //Get all user
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "userName"));
        Pageable _pageable = new PageRequest(pageable.getPageNumber(), 10, sort);
        Page<AuthUser> pageAuthUser = authUserService.findAllAuthUser(_pageable);
//            Page<AuthUser> pageAuthUser = new PageImpl<>(lstAllUser);
        model.addAttribute("page", pageAuthUser);
    }
}
