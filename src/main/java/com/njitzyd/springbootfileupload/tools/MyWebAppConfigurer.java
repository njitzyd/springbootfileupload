package com.njitzyd.springbootfileupload.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	
	   @Value("${file.staticAccessPath}")
	    private String staticAccessPath;
	  
	   @Value("${file.uploadFolder}")
	    private String uploadFolder;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	//上传的图片在D盘下的test目录下，例如访问路径如：http://localhost:8080/image/bottom_sm.png
    	//http://localhost:8080/image/bottom_sm.png
        //其中temp-rainy表示访问的前缀。"file:D:/test/"是文件真实的存储路径
        registry.addResourceHandler(staticAccessPath).addResourceLocations(uploadFolder);
    }
}
