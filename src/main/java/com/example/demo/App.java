package com.example.demo;

import static org.springframework.web.cors.CorsConfiguration.ALL;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.bean.Persons;
import com.example.demo.repository.PersonsRepository;

@SpringBootApplication
public class App implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	/**
	 * 允许vue http 跨域访问springmvc controller api
	 * @return
	 */
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALL)
                        .allowedMethods(ALL)
                        .allowedHeaders(ALL)
                        .allowCredentials(true);
            }
        };
    }
	
	@Autowired
	private PersonsRepository personRepository;

	@Override
	public void run(String... args) throws Exception {
		//initPersonTable();
	}

	/**
	 * 初始化Persons 表格
	 */
	public void initPersonTable() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0; i<15; i++) {
			Persons p = new Persons();
			p.setEmail("12345"+i+"@qq.com");
			p.setCreate_datetime(dateFormat.format(new Date()));
			p.setPhone("798374"+i);
			p.setSex(i%2==0?"男":"女");
			p.setUsername("text"+i);
			p.setZone("D8");
			personRepository.save(p);
		}
	}
}
