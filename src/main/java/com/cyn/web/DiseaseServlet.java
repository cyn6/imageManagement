package com.cyn.web;

import com.alibaba.fastjson.JSON;
import com.cyn.pojo.Disease;
import com.cyn.pojo.PageBean;
import com.cyn.service.DiseaseService;
import com.cyn.service.impl.DiseaseServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet("/disease/*")
public class DiseaseServlet extends BaseServlet {
    private DiseaseService diseaseService=new DiseaseServiceImpl();

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //[1,2,3,4]
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        System.out.println(s); //[1,2,]
        int[] ids = JSON.parseObject(s, int[].class);
//        System.out.println(ids); [I@4e9bd061
        diseaseService.deleteByids(ids);
        response.getWriter().write("success");//响应成功，或setStatus200
    }

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Disease> diseases = diseaseService.selectAll();
        //转为json
        String jsonString = JSON.toJSONString(diseases);
        //写数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);

        PageBean<Disease> pageBean = diseaseService.selectByPage(currentPage, pageSize);

        //转为json
        String jsonString = JSON.toJSONString(pageBean);
        //写数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //url参数获取
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);

        //获取请求体中参数
        request.setCharacterEncoding("utf-8");
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Disease disease = JSON.parseObject(s, Disease.class);
//        System.out.println(disease);
        PageBean<Disease> pageBean = diseaseService.selectByPageAndCondition(currentPage, pageSize, disease);

        //转为json
        String jsonString = JSON.toJSONString(pageBean);
        //写数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonString);
    }


    public void selectBy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        Disease disease = JSON.parseObject(s, Disease.class);

        List<Disease> diseases = diseaseService.selectByCropDiseaseName(disease);
        //转为json
        String jsonString = JSON.toJSONString(diseases);
        //写数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //通知客户端以utf-8编码进行解析
        response.setContentType("text/html;charset=utf-8");
        //数据库以UTF-8编码解析数据
        request.setCharacterEncoding("UTF-8");

        /*from表单是用二进制流的方式上传的，所以用普通的方法根本得不到它的值的*/
//      String cropName = request.getParameter("cropName");

//        String cropName=null;
//        String diseaseName=null;
//
        Disease disease = new Disease();

        //判断请求是否为multipar请求
        if(!ServletFileUpload.isMultipartContent(request))
        {
            throw new RuntimeException("当前请求不支持文件上传");
        }
        //为基于磁盘的文件项创建一个FileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置临时文件的边界值，大于该值时，上传文件会先保存在临时文件中，否则，上传文件将直接写入到内存
        //单位：字节，设置边界值1M
        factory.setSizeThreshold(1024 * 1024);
        //设置文件临时储存
        String temppath=this.getServletContext().getRealPath("/temp");
        File temp=new File(temppath);
        factory.setRepository(temp);
        //创建一个新的文件上传处理程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置每一个item的头部字符编码，其可以解决文件名中文乱码问题；
        upload.setHeaderEncoding("UTF-8");
        //设置单个文件的最大边界值(这里是5M)
        upload.setFileSizeMax(1024*1024*5);
        //设置一次上传所有文件总和的最大值(对上传多个文件起作用,这里最大为20M)
        upload.setSizeMax(1024*1024*20);
        //解析请求,获取所有的item
        try {
            //
            //调用ServletFileUpload.parseRequest方法解析request对象，
            //得到一个保存了所有上传内容的List对象。
            List <FileItem> items = upload.parseRequest(request);
            //遍历
            String cropName = items.get(1).getString("UTF-8");
            String diseaseName = items.get(2).getString("UTF-8");
//            for(FileItem item:items){
            FileItem item=items.get(0);
            //若item为普通表单项,,很奇怪，这里不会发生
//                if(item.isFormField()){
//                    //获取表单中属性名称
//                    System.out.println(item);
//                    String fieldName = item.getFieldName();
//                    if("cropName".equals(fieldName)){
//                        //获取表单属性的值
//                        cropName=item.getString("UTF-8");
//                    }
//                    if("diseaseName".equals(fieldName)){
//                        //获取表单属性的值
//                        diseaseName=item.getString("UTF-8");
//                    }

            //若item为文件表单项
//                }else{
            //获取文件大小
            long size=item.getSize();
            //获取文件类型
            String contentType = item.getContentType();
            //获取上传文件原始名字
            String fileName = item.getName();
            System.out.println("文件大小："+size);
            System.out.println("文件的类型："+contentType);
            //System.out.println("文件的名称："+fileName);
            //获取文件名,处理获取到的上传文件的文件名的路径部分，只保留文件名部分
            if(fileName.contains("\\"))
            {
                //如果包含则截取字符串
                fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
            }
            //设置文件名，因为同名的文件会覆盖，所以要修饰文件名，设置毫秒+文件名
            fileName=System.currentTimeMillis()+fileName;
            System.out.println("文件的名称："+fileName);
            //获取输入流,其有上传文件的内容
            InputStream inputStream = item.getInputStream();

            //String parent=this.getServletContext().getRealPath("./images");
            String path="C:\\Users\\chenyanan\\Desktop\\imageManagement\\imageRoot";
            File dirFile=new File(path);
            if(!dirFile.exists()){
                //创建多级目录mkdirs()
                dirFile.mkdir();
            }
            //创建目录文件，将来用于保存上传文件
            File file = new File(path, fileName);
            //创建文件输出流
            OutputStream os=new FileOutputStream(file);
            //把输入流中的数据写入到输出流
            int len=0;
            byte[] buf=new byte[1024];
            while((len=inputStream.read(buf))!=-1){
                os.write(buf, 0, len);
            }
            //图片上传到之后的路径
//                    path=path+"\\"+fileName;
            disease.setImagePath(fileName);
            disease.setCropName(cropName);
            disease.setDiseaseName(diseaseName);

            System.out.print(disease);

            //调用add()方法
            diseaseService.add(disease);
            os.close();
            inputStream.close();
            //删除临时文件
            item.delete();
            response.setStatus(200);
//                }
//            }

        } catch (FileUploadException e) {

            e.printStackTrace();
        }
    }
}
