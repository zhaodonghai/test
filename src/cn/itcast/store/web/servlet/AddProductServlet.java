package cn.itcast.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.utils.UploadUtils;

public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		Product product=new Product();
		Map<String,String> map=new HashMap<String,String>();
		try {
			//三行代码获取集合
			DiskFileItemFactory fac=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(fac);
			List<FileItem> list=upload.parseRequest(request);
			//遍历集合
			for(FileItem item:list){
				if(item.isFormField()){
					//普通项  *_普通项获取其中数据放到Map中
					map.put(item.getFieldName(), item.getString());
				}else{
					//上传项
					//获取原始文件名称
					String fname=item.getName();
					//根据文件原始名称,创建新文件名
					String newFileName=UploadUtils.getUUIDName(fname);
					//获取到存放图片根目录真实路径
					String rootPath=getServletContext().getRealPath("/products/3");
					//#_创建随机8层目录
					String path01=UploadUtils.getDir(newFileName);
					File ranDir=new File(rootPath+path01);
					if(!ranDir.exists()){
						ranDir.mkdirs();
					}
					//#_创建文件
					File file=new File(ranDir,newFileName);
					if(!file.exists()){
						file.createNewFile();
					}
					//#_流对接,通过item.getInputStream();将图片数据输出到文件中
					OutputStream os=new FileOutputStream(file);
					InputStream is=item.getInputStream();
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					
					//#_设置product对象图片路径
					product.setPimage("products/3"+path01+newFileName);
				}
			}

			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			
			ProductService ProductService=new ProductServiceImp();
			ProductService.saveProduct(product);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/admin/product/list.jsp");
	}

}