package ch.fhnw.webeng.weighttracker.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusString = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusString == null) {
            throw new IllegalStateException("missing error statusString code");
        }

        int statusCode = Integer.parseInt(statusString.toString());
        HttpStatus status = HttpStatus.valueOf(statusCode);
        model.addAttribute("status", status);

        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            return "errors/404";
        }
        return "errors/error";
    }
}