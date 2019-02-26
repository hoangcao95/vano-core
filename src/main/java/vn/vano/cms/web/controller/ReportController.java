package vn.vano.cms.web.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.vano.cms.common.Constants;
import vn.vano.cms.common.IGsonBase;
import vn.vano.cms.config.RestCallApp;
import vn.vano.cms.config.RestMessage;
import vn.vano.cms.jpa.TopupSubs;
import vn.yotel.commons.context.AppContext;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/report")
public class ReportController implements IGsonBase {
    Logger logger = Logger.getLogger(ReportController.class);
    @Autowired
    RestCallApp restCallApp;

    @RequestMapping(value = "/promotion-sum.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String postPromotionSum(ModelMap modelMap, HttpServletRequest request,
                                   @RequestParam(required = false, value = "from_date", defaultValue = "") String fromDate,
                                   @RequestParam(required = false, value = "to_date", defaultValue = "") String toDate,
                                   Pageable pageable) {
        Date _fromDate = new Date();
        Date _toDate = new Date();
        Page<Object> page = null;
        try {
            logger.info("postPromotionSum::BEGIN");
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
            restMessage = restCallApp.postPromotionSum(new SimpleDateFormat("yyyyMMdd").format(_fromDate), new SimpleDateFormat("yyyyMMdd").format(_toDate), "" + pageable.getPageNumber());

            List<Object> lst = (ArrayList<Object>) restMessage.getData();
            String total = "";

            if (lst == null) {
                lst = new ArrayList<>();
                total = "0";
            } else {
                total = restMessage.getMessage();
            }
            page = new PageImpl<Object>(lst, pageable, Integer.parseInt(total));
            modelMap.addAttribute("page", page);
            logger.info("postPromotionSum::END");
        }catch (Exception ex){
            logger.error("", ex);
        }
        modelMap.addAttribute("from_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
        modelMap.addAttribute("to_date", new SimpleDateFormat("yyyy-MM-dd").format(_toDate));
        return "report/promotion_sum";
    }

    @RequestMapping(value = "/reportListAward.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String postReportListAward(Model model, HttpServletRequest request,
                                      @RequestParam(required = false, value = "from_date", defaultValue = "") String fromDate,
                                      @RequestParam(required = false, value = "to_date", defaultValue = "") String toDate,
                                      @RequestParam(required = false, value = "phone", defaultValue = "") String phone) {
        logger.info("reportListAward::BEGIN");
        try {
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
            restMessage = restCallApp.reportListAward(new SimpleDateFormat("yyyyMMdd").format(_fromDate), phone);
            if(restMessage.getData() != null) {
                model.addAttribute("listData", restMessage.getData());
            }
            model.addAttribute("result", null);
            model.addAttribute("from_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("to_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("phone", phone);
        } catch (Exception ex){
            logger.error("", ex);
        }
        logger.info("reportListAward::END");
        return "report/award_list";
    }

    @RequestMapping(value = "/reportControlMonth.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String postReportControlMonth(Model model, HttpServletRequest request,
                                         @RequestParam(required = false, value = "from_date", defaultValue = "") String fromDate,
                                         @RequestParam(required = false, value = "to_date", defaultValue = "") String toDate) {
        logger.info("reportControlMonth::BEGIN");
        try {
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
            restMessage = restCallApp.reportControlMonth(new SimpleDateFormat("yyyyMMdd").format(_fromDate), new SimpleDateFormat("yyyyMMdd").format(_toDate));

            model.addAttribute("listData", restMessage.getData());
            model.addAttribute("result", null);
            model.addAttribute("from_date", new SimpleDateFormat("yyyy-MM-dd").format(_fromDate));
            model.addAttribute("to_date", new SimpleDateFormat("yyyy-MM-dd").format(_toDate));

        }catch (Exception ex) {
            logger.error("", ex);
        }
        logger.info("reportControlMonth::END");
        return "report/controlMonth_list";
    }
}
