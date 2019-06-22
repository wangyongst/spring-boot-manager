package com.spring.boot.manager.utils.excel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ServletUtil {
    private String fileName;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public OutputStream getOut() {
        try {
            return resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ServletUtil(HttpServletResponse resp) {
        this.resp = resp;
    }

    public ServletUtil(String fileName,
                       HttpServletRequest req,
                       HttpServletResponse resp) {
        this.fileName = fileName;
        this.req = req;
        this.resp = resp;
    }

    public void poiExcelServlet() {
        resp.setContentType("application/vnd.ms-excel");
        String contentDisposition = "";
        try {
            if (req.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                contentDisposition = "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1")
                        + "\"";// firefox浏览器
            } else {
                contentDisposition = "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";// IE浏览器
            }
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        resp.setHeader("Content-Disposition", contentDisposition);
        resp.setCharacterEncoding("UTF-8");
    }
}