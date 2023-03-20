package com.cyn.web.old;

import com.alibaba.fastjson.JSON;
import com.cyn.pojo.Disease;
import com.cyn.service.DiseaseService;
import com.cyn.service.impl.DiseaseServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllServlet")
public class SelectAllServlet extends HttpServlet {
    private DiseaseService diseaseService=new DiseaseServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Disease> diseases = diseaseService.selectAll();
        //转为json
        String jsonString = JSON.toJSONString(diseases);
        //写数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
