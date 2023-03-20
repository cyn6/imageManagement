package com.cyn.web.old;

import com.alibaba.fastjson.JSON;
import com.cyn.pojo.Disease;
import com.cyn.service.DiseaseService;
import com.cyn.service.impl.DiseaseServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectByServlet")
public class SelectByServlet extends HttpServlet {
    private DiseaseService diseaseService=new DiseaseServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Disease disease = JSON.parseObject(s, Disease.class);
        System.out.println(disease);
        List<Disease> diseases = diseaseService.selectByCropDiseaseName(disease);
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
