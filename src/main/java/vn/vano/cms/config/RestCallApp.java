package vn.vano.cms.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestCallApp {
    private static final Logger logger = LoggerFactory.getLogger(RestCallApp.class);
    private String host = "http://app_api.bibibook.vn/vmsbilling";
    private String mtUrl = "/kpi/mt/daily";
    private String prcUrl = "/kpi/prc/daily";
    private String vasgateUrl = "/kpi/vasgate/daily";
    private String bookApiUrl = "/kpi/bibibook/daily";
    private String chargeUrl = "/kpi/chart/daily";
    private String isdnUrl = "/kpi/detectnumber/daily";
    private String errorCodeUrl = "/kpi/test";
    private String smshistoryUrl = "/custcare/kpbt/mt_history.html";
    private String retrySmsUrl = "/custcare/resend_mt.html";
    private String regisSubUrl = "/custcare/resend_mt.html";
    private String calcelSubUrl = "/custcare/resend_mt.html";
    private String listDenyUrl = "/custcare/resend_mt.html";
    private String searchDenyUrl = "/custcare/resend_mt.html";
    private String addDenyUrl = "/custcare/resend_mt.html";
    private String removeDenyUrl = "/custcare/resend_mt.html";
    private String promotionSumUrl = "/custcare/kpbt/new/promotion_sum.html";
    private String promotionLogUrl = "/custcare/kpbt/new/promotion_log.html";
    private String promotionNumberMaxUrl = "/yomiad/promotion_log_numberMax.html";
    private String top10numberUrl = "/yomiad/top10number.html";
    private  String reportControlMonthUrl = "/yomiad/report_control_month.html";
    private  String reportListAwardUrl = "/yomiad/report_list_award.html";

    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    public RestCallApp() {
        this.mapper = new ObjectMapper();
    }

    public RestCallApp(String host, String mtUrl, String prcUrl, String vasgateUrl, String bookApiUrl, String chartUrl,
                       String isdnUrl, String smshistoryUrl, String retrySmsUrl, String regisSubUrl, String calcelSubUrl,
                       String listDenyUrl, String addDenyUrl, String removeDenyUrl, String searchDenyUrl, String promotionSumUrl,
                       String promotionLogUrl, String promotionNumberMaxUrl, String top10numberUrl, String reportControlMonthUrl,
                       String reportListAwardUrl) {
        this.host = host;
        this.mtUrl = mtUrl;
        this.prcUrl = prcUrl;
        this.vasgateUrl = vasgateUrl;
        this.bookApiUrl = bookApiUrl;
        this.chargeUrl = chartUrl;
        this.isdnUrl = isdnUrl;
        this.smshistoryUrl = smshistoryUrl;
        this.retrySmsUrl = retrySmsUrl;
        this.regisSubUrl = regisSubUrl;
        this.calcelSubUrl = calcelSubUrl;
        this.listDenyUrl = listDenyUrl;
        this.addDenyUrl = addDenyUrl;
        this.removeDenyUrl = removeDenyUrl;
        this.searchDenyUrl = searchDenyUrl;
        this.mapper = new ObjectMapper();
        this.promotionSumUrl = promotionSumUrl;
        this.promotionLogUrl = promotionLogUrl;
        this.promotionNumberMaxUrl = promotionNumberMaxUrl;
        this.top10numberUrl = top10numberUrl;
        this.reportControlMonthUrl = reportControlMonthUrl;
        this.reportListAwardUrl = reportListAwardUrl;
    }

    public RestMessage getInfo(String startdate, String enddate, String url) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("startdate", startdate);
        map.add("enddate", enddate);
        this.restTemplate = new RestTemplate();
        String uri = this.host + url;
        String strMessage = (String) this.restTemplate.postForObject(uri, map, String.class, new Object[0]);
//        logger.info(strMessage);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage getSmsHistory(String startdate, String enddate, String msisdn, String type) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("startdate", startdate);
        map.add("enddate", enddate);
        map.add("msisdn", msisdn);
        map.add("type", type);
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.smshistoryUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage sentRetryMT(String mtId) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("mtId", mtId);
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.retrySmsUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage registerSub(String requestId, String phone, String productId, String packageCode) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("request_id", requestId);
        map.add("msisdn", phone);
        map.add("product_id", productId);
        map.add("package_code", packageCode);
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.regisSubUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage calcelSub(String requestId, String phone, String productId, String packageCode) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("request_id", requestId);
        map.add("msisdn", phone);
        map.add("product_id", productId);
        map.add("package_code", packageCode);
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.calcelSubUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage getListDeny() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.listDenyUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage searchDeny(String msisdn) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap();
            map.add("msisdn", msisdn);
            this.restTemplate = new RestTemplate();
            String strMessage = (String) this.restTemplate.postForObject(this.host + this.searchDenyUrl, map, String.class, new Object[0]);
            logger.info("strMessage:" + strMessage);
            RestMessage restMessage = null;
            try {
                restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
            } catch (Exception e) {
                logger.error(e.toString());
            }
            return restMessage;
    }

    public RestMessage addDeny(String msisdn) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("msisdn", msisdn);
        map.add("regex", "dsds");
        map.add("type", "0");
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.addDenyUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage removeDeny(String msisdn) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("msisdn", msisdn);
        map.add("regex", "dsds");
        map.add("type", "0");
        this.restTemplate = new RestTemplate();
        String strMessage = (String) this.restTemplate.postForObject(this.host + this.removeDenyUrl, map, String.class, new Object[0]);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) this.mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return restMessage;
    }

    /**
     * Ham call API lay danh sach cac so trung thuong
     *
     * @param startdate
     * @param enddate
     * @return
     */
    public RestMessage postPromotionSum(String startdate, String enddate, String numPage) {
        RestMessage restMessage = null;
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("startdate", startdate);
            map.add("enddate", enddate);
            map.add("numPage", numPage);
            restTemplate = new RestTemplate();
            String uri = this.host + promotionSumUrl;
            String strMessage = restTemplate.postForObject(uri, map, String.class);
            logger.info(strMessage);
            restMessage = (RestMessage) mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        return restMessage;
    }

    /**
     * Ham tra cuu lich su dat so
     * @param startdate
     * @param enddate
     * @return
     */
    public RestMessage postPromotionLog(String startdate, String enddate, String msisdn, String numPage) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("startdate", startdate);
        map.add("enddate", enddate);
        map.add("msisdn", msisdn);
        map.add("numPage", numPage);
        restTemplate = new RestTemplate();
        String uri = this.host + promotionLogUrl;
        logger.info("postPromotionLog.uri: {}", uri);
        String strMessage = restTemplate.postForObject(uri, map, String.class);
        logger.info("postPromotionLog.message: {}", uri);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage postPromotionNumberMax(String processDate) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("processDate", processDate);
        restTemplate = new RestTemplate();
        String uri = this.host + promotionNumberMaxUrl;
        String strMessage = restTemplate.postForObject(uri, map, String.class);
        logger.info(strMessage);
        RestMessage restMessage = null;
        try {
            restMessage = mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage top10number(String startdate, String enddate) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("fromDate", startdate);
        map.add("toDate", enddate);
        restTemplate = new RestTemplate();
        String uri = this.host + top10numberUrl;
        String strMessage = restTemplate.postForObject(uri, map, String.class);
        logger.info(strMessage);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage reportControlMonth(String startdate, String enddate) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("fromDate", startdate);
        map.add("toDate", enddate);
        restTemplate = new RestTemplate();
        String uri = this.host + reportControlMonthUrl;
        String strMessage = restTemplate.postForObject(uri, map, String.class);
        logger.info(strMessage);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }
        return restMessage;
    }

    public RestMessage reportListAward(String processDate, String msisdn) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("processDate", processDate);
        map.add("msisdn", msisdn);
        restTemplate = new RestTemplate();
        String uri = this.host + reportListAwardUrl;
        String strMessage = restTemplate.postForObject(uri, map, String.class);
        logger.info(strMessage);
        RestMessage restMessage = null;
        try {
            restMessage = (RestMessage) mapper.readValue(strMessage, RestMessage.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
        }
        return restMessage;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMtUrl() {
        return this.mtUrl;
    }

    public void setMtUrl(String mtUrl) {
        this.mtUrl = mtUrl;
    }

    public String getPrcUrl() {
        return this.prcUrl;
    }

    public void setPrcUrl(String prcUrl) {
        this.prcUrl = prcUrl;
    }

    public String getVasgateUrl() {
        return this.vasgateUrl;
    }

    public void setVasgateUrl(String vasgateUrl) {
        this.vasgateUrl = vasgateUrl;
    }

    public String getBookApiUrl() {
        return this.bookApiUrl;
    }

    public void setBookApiUrl(String bookApiUrl) {
        this.bookApiUrl = bookApiUrl;
    }

    public String getChargeUrl() {
        return this.chargeUrl;
    }

    public void setChargeUrl(String chargeUrl) {
        this.chargeUrl = chargeUrl;
    }

    public String getIsdnUrl() {
        return this.isdnUrl;
    }

    public void setIsdnUrl(String isdnUrl) {
        this.isdnUrl = isdnUrl;
    }

    public String getRetrySmsUrl() {
        return this.retrySmsUrl;
    }

    public void setRetrySmsUrl(String retrySmsUrl) {
        this.retrySmsUrl = retrySmsUrl;
    }

    public String getSmshistoryUrl() {
        return this.smshistoryUrl;
    }

    public void setSmshistoryUrl(String smshistoryUrl) {
        this.smshistoryUrl = smshistoryUrl;
    }

    public String getRegisSubUrl() {
        return this.regisSubUrl;
    }

    public void setRegisSubUrl(String regisSubUrl) {
        this.regisSubUrl = regisSubUrl;
    }

    public String getCalcelSubUrl() {
        return this.calcelSubUrl;
    }

    public void setCalcelSubUrl(String calcelSubUrl) {
        this.calcelSubUrl = calcelSubUrl;
    }

    public String getListDenyUrl() {
        return this.listDenyUrl;
    }

    public void setListDenyUrl(String listDenyUrl) {
        this.listDenyUrl = listDenyUrl;
    }

    public String getAddDenyUrl() {
        return this.addDenyUrl;
    }

    public void setAddDenyUrl(String addDenyUrl) {
        this.addDenyUrl = addDenyUrl;
    }

    public String getRemoveDenyUrl() {
        return this.removeDenyUrl;
    }

    public void setRemoveDenyUrl(String removeDenyUrl) {
        this.removeDenyUrl = removeDenyUrl;
    }

    public String getSearchDenyUrl() {
        return this.searchDenyUrl;
    }

    public void setSearchDenyUrl(String searchDenyUrl) {
        this.searchDenyUrl = searchDenyUrl;
    }

    public String getErrorCodeUrl() {
        return this.errorCodeUrl;
    }

    public void setErrorCodeUrl(String errorCodeUrl) {
        this.errorCodeUrl = errorCodeUrl;
    }

    public String getPromotionSumUrl() {
        return promotionSumUrl;
    }

    public void setPromotionSumUrl(String promotionSumUrl) {
        this.promotionSumUrl = promotionSumUrl;
    }

    public String getPromotionLogUrl() {
        return promotionLogUrl;
    }

    public void setPromotionLogUrl(String promotionLogUrl) {
        this.promotionLogUrl = promotionLogUrl;
    }

    public String getPromotionNumberMaxUrl() {
        return promotionNumberMaxUrl;
    }

    public void setPromotionNumberMaxUrl(String promotionNumberMaxUrl) {
        this.promotionNumberMaxUrl = promotionNumberMaxUrl;
    }

    public String getTop10numberUrl() {
        return top10numberUrl;
    }

    public void setTop10numberUrl(String top10numberUrl) {
        this.top10numberUrl = top10numberUrl;
    }

    public String getReportControlMonthUrl() {
        return reportControlMonthUrl;
    }

    public void setReportControlMonthUrl(String reportControlMonthUrl) {
        this.reportControlMonthUrl = reportControlMonthUrl;
    }

    public String getReportListAwardUrl() {
        return reportListAwardUrl;
    }

    public void setReportListAwardUrl(String reportListAwardUrl) {
        this.reportListAwardUrl = reportListAwardUrl;
    }
}
