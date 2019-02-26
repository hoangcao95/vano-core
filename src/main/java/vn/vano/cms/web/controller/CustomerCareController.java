package vn.vano.cms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.vano.cms.config.RestCallApp;
import vn.vano.cms.config.RestMessage;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.commons.context.AppContext;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer_care")
public class CustomerCareController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerCareController.class);
    @Autowired
    RestCallApp restCallApp;

    /**
     * Tra cu MO/MT
     * @param model
     * @param request
     * @param fromDate
     * @param toDate
     * @param phone
     * @return
     */
    @RequestMapping(value = "/search-sms.html", method = {RequestMethod.GET})
    public String searchSms(Model model, HttpServletRequest request,
                            @RequestParam(required = false, value = "from_date", defaultValue = "") String fromDate,
                            @RequestParam(required = false, value = "to_date", defaultValue = "") String toDate,
                            @RequestParam(required = false, value = "phone", defaultValue = "") String phone) {
        logger.info("searchSms::BEGIN");
        try {
            if (phone.equalsIgnoreCase("")) {
                if (request.getSession().getAttribute("phone") != null) {
                    phone = (String) request.getSession().getAttribute("phone");
                }
            } else {
                request.getSession().setAttribute("phone", phone);
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
            ////call api get kpi sms
            RestMessage restMessage = new RestMessage();
            if (!phone.equalsIgnoreCase("")) {
                restMessage = restCallApp.getSmsHistory(new SimpleDateFormat("yyyyMMdd").format(_fromDate), new SimpleDateFormat("yyyyMMdd").format(_toDate), phone, "1");
            }
            model.addAttribute("phone", phone);
            model.addAttribute("Datas", restMessage.getData());
            model.addAttribute("result", null);
            model.addAttribute("from_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("to_date", new SimpleDateFormat("yyyy-MM-dd").format(_toDate));
        }catch (Exception e){
            logger.error("", e);
        }
        logger.info("searchSms::END");
        return "customer_care/search_sms";
    }

    @RequestMapping(value = "/promotion-log.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String postPromotionLog(Model model, HttpServletRequest request,
                                   @RequestParam(required = false, value = "from_date", defaultValue = "") String fromDate,
                                   @RequestParam(required = false, value = "to_date", defaultValue = "") String toDate,
                                   @RequestParam(required = false, value = "phone", defaultValue = "") String phone,
                                   Pageable pageable){

        Date _fromDate = new Date();
        Date _toDate = new Date();
        Page<Object> page = null;
        try {
            logger.info("postPromotionLog::BEGIN");
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
            ////call api get kpi sms
            RestMessage restMessage = new RestMessage();
            if (!phone.equalsIgnoreCase("")) {
                restMessage = restCallApp.postPromotionLog(new SimpleDateFormat("yyyyMMdd").format(_fromDate), new SimpleDateFormat("yyyyMMdd").format(_toDate), phone, "" + pageable.getPageNumber());
            }
            List<Object> lst = (ArrayList<Object>) restMessage.getData();
            String total = null;
            if (lst == null) {
                lst = new ArrayList<>();
                total = "0";
            } else {
                total = restMessage.getMessage();
            }
            page = new PageImpl<Object>(lst, pageable, Integer.parseInt(total));
            model.addAttribute("page", page);
            model.addAttribute("from_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("to_date", new SimpleDateFormat("yyyy-MM-dd").format(_toDate));
            logger.info("postPromotionLog::END");
        }catch (Exception e){
            logger.error("", e);
        }
        return "/customer_care/promotion_log";
    }
}
