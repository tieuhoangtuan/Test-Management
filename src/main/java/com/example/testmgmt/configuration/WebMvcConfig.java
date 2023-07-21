//package com.example.testmgmt.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private final MyInterceptor myInterceptor;
//
//    @Autowired
//    public WebMvcConfig(MyInterceptor myInterceptor) {
//        this.myInterceptor = myInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(myInterceptor);
//    }
//}
