package vn.vano.cms.web.controller;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
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

    @RequestMapping(value = "/topup-upload.html", method = RequestMethod.GET)
    public String showFormUploadAsset() {
        return "topup/topup_upload";
    }

    @RequestMapping(value = "/topup-upload.html", method = RequestMethod.POST)
    public String uploadFileExcelAsset(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {
        try {
            //tạo file xlsx
            Calendar calendar = Calendar.getInstance();
            String today = calendar.getTime().getTime() + "";
            String fileInput = file.getOriginalFilename();
            XSSFWorkbook xssfWorkbook1 = new XSSFWorkbook();
            XSSFSheet sheet1 = xssfWorkbook1.createSheet(fileInput);
            int rowNum = 0;
            Row firstRow = sheet1.createRow(rowNum++);

            //ghi dữ liệu từ file upload vào file xlsx vừa tạo
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                TopupBean topupBean = new TopupBean();
                XSSFRow row = sheet.getRow(i);
                DataFormatter formatter = new DataFormatter();

                topupBean.setMsisdn(formatter.formatCellValue(row.getCell(1)));
                topupBean.setServiceCode(formatter.formatCellValue(row.getCell(2)));
                topupBean.setTopupResult(formatter.formatCellValue(row.getCell(3)));
                topupBean.setCreatedDate(formatter.formatCellValue(row.getCell(4)));

                System.out.println(topupBean.getMsisdn() + " " + topupBean.getServiceCode() + " " + topupBean.getTopupResult() + " "
                        + topupBean.getCreatedDate());

                XSSFRow rows = sheet1.createRow(rowNum++);
                Cell cell1 = rows.createCell(0);
                cell1.setCellValue(topupBean.getMsisdn());
                Cell cell2 = rows.createCell(1);
                cell2.setCellValue(topupBean.getServiceCode());
                Cell cell3 = rows.createCell(2);
                cell3.setCellValue(topupBean.getTopupResult());
                Cell cell4 = rows.createCell(3);
                cell4.setCellValue(topupBean.getCreatedDate());
            }
            try {
                FileOutputStream outputStream = new FileOutputStream("D:/vano/vano_project/vano-core/src/main/webapp/WEB-INF/templates/upload/" + today + fileInput);
                xssfWorkbook1.write(outputStream);
                xssfWorkbook1.close();
            }catch (Exception ex){
                LOG.error("", ex);
                redirectAttributes.addFlashAttribute("errorMessage", "Lưu file " + today + fileInput + "thất bại");
            }
            //đọc file và insert dữ liệu vào bảng
            try{
                FileInputStream excelFile = new FileInputStream("D:/vano/vano_project/vano-core/src/main/webapp/WEB-INF/templates/upload/" + today + fileInput);
                XSSFWorkbook xssfWorkbook2 = new XSSFWorkbook(excelFile);
                XSSFSheet sheet2 = xssfWorkbook2.getSheetAt(0);
                for (int i = 1; i < sheet2.getPhysicalNumberOfRows(); i++) {
                    TopupBean topupBean = new TopupBean();
                    XSSFRow row = sheet2.getRow(i);
                    DataFormatter formatter = new DataFormatter();

                    topupBean.setMsisdn(formatter.formatCellValue(row.getCell(0)));
                    topupBean.setServiceCode(formatter.formatCellValue(row.getCell(1)));
                    topupBean.setTopupResult(formatter.formatCellValue(row.getCell(2)));
                    topupBean.setCreatedDate(formatter.formatCellValue(row.getCell(3)));

                    System.out.println(topupBean.getMsisdn() + " " + topupBean.getServiceCode() + " " + topupBean.getTopupResult() + " "
                            + topupBean.getCreatedDate());

                    TopupSubs topupSubs = new TopupSubs();
                    topupSubs.setMsisdn(topupBean.getMsisdn());
                    topupSubs.setServiceCode(topupBean.getServiceCode());
                    topupSubs.setTopupResult(topupBean.getTopupResult());

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date _createDate = dateFormat.parse(topupBean.getCreatedDate());
                    topupSubs.setCreatedDate(_createDate);

                    topupSubsService.create(topupSubs);
                    redirectAttributes.addFlashAttribute("successMessage", Constants.INSERT_SUCCESS_MESSAGE);
                }
            }catch (Exception ex){
                redirectAttributes.addFlashAttribute("errorMessage", "Không đọc được file " + today + fileInput);
                LOG.error("", ex);
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", Constants.INSERT_FILE_ERROR_MESSAGE);
        }
        return "redirect:/topup/topup-upload.html";
    }

    @RequestMapping(value = "/downloadTopup", method = RequestMethod.GET)
    public void downloadTopup(HttpServletResponse response) throws IOException {
        try {
            File file = ResourceUtils.getFile("D:/vano/vano_project/vano-core/src/main/webapp/WEB-INF/templates/download/Topup.xlsx");
            byte[] data = FileUtils.readFileToByteArray(file);
            // Thiết lập thông tin trả về
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
            response.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

//    File file = ResourceUtils.getFile("D:/vano/vano_project/vano-core/src/main/webapp/WEB-INF/templates/download/demo.txt");
