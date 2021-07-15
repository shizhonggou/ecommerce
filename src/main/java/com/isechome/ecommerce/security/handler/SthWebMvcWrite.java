package com.isechome.ecommerce.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.isechome.ecommerce.security.RespBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SthWebMvcWrite {
    public void writeToWeb(HttpServletResponse response, RespBean respBean) throws IOException {
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
