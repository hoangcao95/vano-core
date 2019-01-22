package vn.vano.cms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.jping.*;
import vn.vano.cms.bean.CommandBean;
import vn.vano.cms.bean.TopupBean;
import vn.vano.cms.common.Constants;
import vn.vano.cms.common.IGsonBase;
import vn.vano.cms.common.PhoneUtils;
import vn.vano.cms.jpa.TopupSubs;
import vn.vano.cms.service.TopupSubsService;
import vn.vano.cms.thread.StreamGobbler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/topup")
public class TopupController implements IGsonBase{
    private static Logger LOG = LoggerFactory.getLogger(TopupController.class);
    private final String URL_TOPUP = "http://fastshare.mobifone.vn/fapi/fpay.jsp?action=share2&vendor=X&mtcode=topupok&msisdnB={0}&amount={1}&cpid={2}&service={3}&note={4}";
    private final String CPID = "CS90";

    @Autowired
    TopupSubsService topupSubsService;

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Locale locale, Model model) {
        LOG.debug("BEGIN::index.html");
        try {
            model.addAttribute("topup", new TopupBean());
        } catch (Exception ex) {

        }

        LOG.debug("END::index.html");
        return "/topup/index";
    }

    @RequestMapping(value = "/by-file.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String byFile(Locale locale, Model model,
                        @ModelAttribute("topup") @Valid TopupBean topup) {
        LOG.debug("BEGIN::index.html");
        try {

            model.addAttribute("topup", topup);
        } catch (Exception ex) {

        }

        LOG.debug("END::index.html");
        return "/topup/by-file";
    }

    @RequestMapping(value = "/topup.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String command(Locale locale, Model model,
                          @ModelAttribute("topup") @Valid TopupBean topup,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request) {
        LOG.debug("BEGIN::topup.html");
        LOG.debug(GSON.toJson(topup));
        String msisdn = PhoneUtils.normalizeMsIsdn(topup.getMsisdn());
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        TopupSubs topupSubs = new TopupSubs();
        topupSubs.setMsisdn("0".concat(msisdn));
        topupSubs.setCreatedDate(new Date());
        topupSubs.setServiceCode(topup.getServiceCode());
        topupSubs.setNote(topup.getNote());
        try {
            String urlTopup = MessageFormat.format(URL_TOPUP, topup.getMsisdn(), topup.getAmount(), CPID, topup.getServiceCode(), topup.getNote());
            URL url = new URL(urlTopup);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");

            is = httpConnection.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            String result = sb.toString().trim();
            if(result.startsWith("OK")) {
                topupSubs.setTopupResult("SUCCESS");
                //Thanh cong
                model.addAttribute("successMessage",
                        "TOPUP " + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(Long.parseLong(topup.getAmount())) + " thành công cho TB " + topup.getMsisdn());
            } else {
                topupSubs.setTopupResult("ERROR");
                //Khong thanh cong
                model.addAttribute("successMessage", "ERROR: TOPUP không thành công");
            }
        } catch (Exception ex) {
            topupSubs.setTopupResult("EXCEPTION");
            model.addAttribute("successMessage", "ERROR: Có lỗi xảy ra khi TOPUP");
        }
        topupSubsService.create(topupSubs);
        model.addAttribute("topup", topup);
        LOG.debug("END::topup.html");
        return "/topup/index";
    }

    @RequestMapping(value = "/clear.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String clear(Locale locale, Model model) {
        LOG.debug("BEGIN::clear.html");
        try {
            model.addAttribute("topup", new TopupBean());
        } catch (Exception ex) {

        }
        LOG.debug("END::clear.html");
        return "/topup/index";
    }

    @RequestMapping(value = "/topup-list.html", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('SADMIN')")
    public String searchTopup(Model model,
                              @RequestParam(value = "msisdn", defaultValue = "", required = false) String msisdn,
                              @RequestParam(value = "from_date", defaultValue = "", required = false) String fromDate,
                              @RequestParam(value = "to_date", defaultValue = "", required = false) String toDate,
                              Pageable pageable,
                              HttpServletRequest request) {
        try{
            String not_found_message = "";
            LOG.info("searchSms::BEGIN");
            if (msisdn.equalsIgnoreCase("")) {
                if (request.getSession().getAttribute("msisdn") != null) {
                    msisdn = (String) request.getSession().getAttribute("msisdn");
                }
            } else {
                request.getSession().setAttribute("msisdn", msisdn);
            }
            Date _fromDate = new Date();
            Date _toDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                _fromDate = sdf.parse(fromDate);
                _toDate = sdf.parse(toDate);
            } catch (Exception e) {
                Calendar calendar = Calendar.getInstance();
                _toDate = new Date();
                calendar.setTime(_toDate);
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                _fromDate = calendar.getTime();
                fromDate = sdf.format(_fromDate);
                toDate = sdf.format(_toDate);
            }

            Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE);
            Page<TopupSubs> page = topupSubsService.findByDate(msisdn, _fromDate, _toDate, _pageable);
            if (page.getContent().size() == 0) {
                not_found_message = Constants.NOT_FOUND_MESSAGE;
            }
            model.addAttribute("msisdn", msisdn);
            model.addAttribute("page", page);
            model.addAttribute("not_found_message", not_found_message);
            model.addAttribute("from_date",  new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("to_date",  new SimpleDateFormat("yyyy-MM-dd").format(_toDate));
        } catch(Exception ex){
            LOG.error("", ex);
        }
        return "/topup/topup_list";
    }
}
