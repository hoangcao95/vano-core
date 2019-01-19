package vn.vano.cms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.jping.*;
import vn.vano.cms.bean.CommandBean;
import vn.vano.cms.thread.StreamGobbler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
    private static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Locale locale, Model model,
                        @ModelAttribute("cmd") @Valid CommandBean cmd) {
        LOG.debug("BEGIN::index.html");
        try {

            model.addAttribute("cmd", cmd);
        } catch (Exception ex) {

        }

        LOG.debug("END::index.html");
        return "/home/index";
    }

    @RequestMapping(value = "/command.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String command(Locale locale, Model model,
                          @ModelAttribute("cmd") @Valid CommandBean cmd,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request) {
        LOG.debug("BEGIN::command.html");
        try {
            LOG.info("SEND COMMAND: " + cmd.getCommandCode());
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                builder.command("cmd.exe", "/c", cmd.getCommandCode());
            } else {
                builder.command("sh", "-c", cmd.getCommandCode());
            }
            builder.directory(new File(System.getProperty("user.home")));
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), LOG::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;


            PingArguments arguments = new PingArguments.Builder().url("google.com").timeout(5000).count(2).bytes(32).build();

            PingResult results = Ping.ping(arguments, Ping.Backend.UNIX);


            LOG.info("TTL: " + results.ttl());
            LOG.info("RTT Minimum: " + results.rtt_min());
            LOG.info("Received : " + results.received());

            try {
                Socket socket = new Socket("localhost", 8090);
                LOG.info("Port 8090 is open");
            } catch (Exception ex) {
                LOG.info("Port 8090 is close");
            }

            SystemInfo info = new SystemInfo();
            LOG.info(info.MemInfo());
            LOG.info(info.DiskInfo());



            try {
                Socket socket = new Socket("localhost", 8091);
                LOG.info("Port 8091 is open");
            } catch (Exception ex) {
                LOG.info("Port 8091 is close");
            }


            for (PingRequest ping : results.getRequests()) {
                LOG.info(ping.toString());
            }

        } catch (Exception ex) {

        }
        model.addAttribute("cmd", cmd);
        LOG.debug("END::command.html");
        return "redirect:/home/index.html";
    }

}
