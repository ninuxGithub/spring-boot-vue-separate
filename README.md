### 感谢
	感谢作者：Boyle
	from：https://github.com/boylegu/SpringBoot-vue
	大部分都是来自于Boyle的项目
	
	感谢作者：janessssss
	https://github.com/janessssss/vuejs-element
	借用了user  curd 的demo
	
	感谢作者：lin-xin
	https://github.com/lin-xin/vue-manage-system

### 目的
	练习spring-boot 整合vue
	
	
### 运行
	启动boot项目在8000端口
	
	在cmd命令行到front目录先安装vue环境（npm install,当然前提是 安装了nodejs）
	然后运行front,在命令行中：npm dev run 
	
	
	boyle 的github 也有介绍请参考：https://github.com/boylegu/SpringBoot-vue


### 需要注意的地方
	踩坑1：PageNumber：对jpa查询后的number进行了修正（默认是0 ，而前端页面需要的是1）
	采坑2：在User类中，如果创建时间使用create_time，下划线的方式连接变量，那采用jpa根据字段排序的时候是无法区分的，
	改用驼峰式的命名就好啦
	
	
	和这个项目配套的前段项目可以是本项目的front
	也可以是：https://github.com/ninuxGithub/vue-front-end

### jpa分页
重点：分页对象的repository要继承JpaSpecificationExecutor
```java
	public Page<T> queryPagination(Integer page, Integer perPage, String sortType, Map<String, Object> params,
			Class<?> beanClass) {
		if (page == null) {
			page = 1;
		}
		Sort sort = null;
		Direction direction = Direction.ASC;
		if (StringUtils.isBlank(sortType)) {
			sort = new Sort(direction, "id");
		} else {
			if (sortType.startsWith("-")) {
				direction = Direction.DESC;
				sortType = sortType.substring(1);
			}
			sort = new Sort(direction, sortType);
		}
		// 创建pageable对象
		Pageable pageable = new PageRequest(page - 1, perPage == null ? maxPerPage : perPage, sort);
		// 开始获取分页的JpaSpecificationExecutor
		JpaSpecificationExecutor<T> bean = (JpaSpecificationExecutor<T>) springUtil.getBean(beanClass);
		logger.info("sort field :{} queryPagination run...", sortType);
		// 开始分页
		Page<T> pagination = bean.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicatList = new ArrayList<>();
				for (String field : params.keySet()) {
					Object value = params.get(field);
					if (null != value && StringUtils.isNotBlank(value.toString())) {
						//有些字段是不可以采用like的 例如Double ...
						predicatList.add(cb.like(root.get(field), "%" + value + "%"));
					}
				}
				Predicate[] arrayPredicates = new Predicate[predicatList.size()];
				return cb.and(predicatList.toArray(arrayPredicates));
			}
		}, pageable);
		// 对分页的PageNumber 进行调整
		return new PageNumber<>(pagination.getContent(), pageable, pagination.getTotalElements());
	}
```	

### element-ui fileupload
文件上组件的使用，以及spring-boot 后台对文件上传的处理

### vue 组件的变量传递，组件的通信
可以参考StudentTable.vue ,SearchStudent.vue, 重点在
 'keywords': 'filterResultData'
 收到vue的监控，如果搜索的变量发送了改变，那么就会触发filterResultData，进行搜索返回分页的结果
 再通过Even发送给StudentTable.vue组件，实现了变量的传递，从而实现了组件的通信
 
 
### filter,interceptor,listener 注解实现的方式
filter:
	采用注解@Order(value = Ordered.HIGHEST_PRECEDENCE)@WebFilter(urlPatterns = "/*", filterName = "apiFilter") , 实现接口javax.servlet.Filter

interceptor:
	需要实现接口：org.springframework.web.servlet.HandlerInterceptor，并且在webmvconfig 中的addInterceptors方法中注入拦截器

listener:
	javax.servlet.http.HttpSessionListener--session级别（经过采坑，requestMapping 方法需要有HttpSession的入参，才会得到监听）
	javax.servlet.ServletContextListener--servlet容器配置
	javax.servlet.ServletRequestListener--request级别的监听
	另外还有一个重要的点就是需要在applicaton中加入注解@ServletComponentScan,扫描组件
	
### axois 发送post get
```javascript
	//get
	axios.get(this.url,{params:this.filter}).then((response) =>{
		this.students = response.data.content;
	}).catch((response)=>{
		this.$message.error('message');
	});
   		
   	//axios post 需要借助qs 将参数sringify
    var qs = require('qs');
    axios.post('/api/changeColor',qs.stringify({'color':color})).then(function(response) {

    }).catch(function(error) {

    });	
   		
```
	
### 效果
![img](https://github.com/ninuxGithub/spring-boot-vue-separate/blob/master/pic.png)



##Nginx 配置文件

	#Nginx所用用户和组，window下不指定
	#user  niumd niumd;
	#工作的子进程数量（通常等于CPU数量或者2倍于CPU）
	worker_processes  2;
	#错误日志存放路径
	#error_log  logs/error.log;
	#error_log  logs/error.log  notice;
	error_log  logs/error.log  info;
	#指定pid存放文件
	pid        logs/nginx.pid;
	events {
		#使用网络IO模型linux建议epoll，FreeBSD建议采用kqueue，window下不指定。
		#use epoll;
		#允许最大连接数
		worker_connections 1024;
	}
	http {
	    include       mime.types;
		default_type  application/octet-stream;
		#定义日志格式
		#log_format main '$remote_addr - $remote_user [$time_local] $request '
		#'"$status" $body_bytes_sent "$http_referer" '
		#'"$http_user_agent" "$http_x_forwarded_for"';
		#access_log off;
		access_log  logs/access.log;
	    client_header_timeout 3m;
	    client_body_timeout 3m;
	    send_timeout 3m;
	    client_header_buffer_size 1k;
	    large_client_header_buffers 4 4k;
	    sendfile on;
	    tcp_nopush on;
	    tcp_nodelay on;
		#keepalive_timeout 75 20;
	    include gzip.conf;
	    upstream backend_server {
			#根据ip计算将请求分配各那个后端tomcat，许多人误认为可以解决session问题，其实并不能。
			#同一机器在多网情况下，路由切换，ip可能不同
			#ip_hash;
	        server localhost:8000 weight=5;
			server localhost:9000 weight=5;
	    }
	
	    #访问nginx自带的页面nginx首页地址：http://www.nginxserver.com:8080/
	    server {
	    	listen 8080;
	        server_name  www.nginxserver.com;
	        index index.html index.htm;
	        root html;
	        location /{
	    
	        }
	    }
	
	    #通过代理访问后台的服务器：http://www.nginxserver.com/
	    server {
	        listen 80;
	        server_name  www.nginxserver.com;
	        #index index.html index.htm;
	        #root html;
			
	        location / {
	            #proxy_connect_timeout 3;
	            #proxy_send_timeout 30;
	            #proxy_read_timeout 30;
				
	            proxy_pass http://backend_server;
				
				#proxy config
	            proxy_redirect          off;
				proxy_set_header        Host $host:$server_port;
				proxy_set_header        X-Real-IP $remote_addr;
				proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
	            client_max_body_size    10m;
				client_body_buffer_size 128k;
				proxy_connect_timeout   300;
				proxy_send_timeout      300;
				proxy_read_timeout      300;
				proxy_buffer_size       4k;
				proxy_buffers           4 32k;
				proxy_busy_buffers_size 64k;
				proxy_temp_file_write_size 64k;
	        }
	    }
	}
	


server_name  www.nginxserver.com 需要配置到host里面	


	