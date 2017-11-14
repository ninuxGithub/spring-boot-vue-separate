package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.example.demo.bean.Persons;
import com.example.demo.component.CustomMultipartResolver;
import com.example.demo.repository.PersonsRepository;

@SpringBootApplication
@Configuration
@ServletComponentScan
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 允许vue http 跨域访问springmvc controller api
	 * 
	 * @return
	 */
	// 在前台设置了跨域访问

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// return new WebMvcConfigurerAdapter() {
	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**")
	// .allowedOrigins(ALL)
	// .allowedMethods(ALL)
	// .allowedHeaders(ALL)
	// .allowCredentials(true);
	// }
	// };
	// }


	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CustomMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setMaxInMemorySize(40960);
		resolver.setMaxUploadSize(50 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024
		return resolver;
	}

	@Autowired
	private PersonsRepository personRepository;

	@Override
	public void run(String... args) throws Exception {
		// initPersonTable();
	}

	/**
	 * 初始化Persons 表格
	 */
	public void initPersonTable() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 15; i++) {
			Persons p = new Persons();
			p.setEmail("12345" + i + "@qq.com");
			p.setCreate_datetime(dateFormat.format(new Date()));
			p.setPhone("798374" + i);
			p.setSex(i % 2 == 0 ? "男" : "女");
			p.setUsername("text" + i);
			p.setZone("D8");
			personRepository.save(p);
		}
	}
}
