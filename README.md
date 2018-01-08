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

[参考]
nginx反向代理配置keepalive
keepalive for HTTP - Module ngx_http_core_module
 
2. Tomcat
conf/server.xml：
    <!-- 
        maxThreads：由此连接器创建的最大请求处理线程数，这决定可同时处理的最大并发请求数（默认为200）
        minSpareThreads：保持运行状态的最小线程数（默认为10）
        acceptCount：接收传入的连接请求的最大队列长度（默认队列长度为100）
        
        connectionTimeout：在接收一条连接之后，连接器将会等待请求URI行的毫秒数（默认为60000，60秒）
        maxConnections：在任何给定的时间，服务器能接收和处理的最大连接数（NIO的默认值为10000）
        keepAliveTimeout：在关闭这条连接之前，连接器将等待另一个HTTP请求的毫秒数（默认使用connectionTimeout属性值）
        maxKeepAliveRequests：在该连接被服务器关闭之前，可被流水线化的最大HTTP请求数（默认为100）
        
        enableLookups：启用DNS查询（默认是DNS查询被禁用）
        compression：连接器是否启用HTTP/1.1 GZIP压缩，为了节省服务器带宽
        compressionMinSize：指定输出响应数据的最小大小（默认为2048，2KB）
        compressableMimeType：可使用HTTP压缩的文件类型
        server：覆盖HTTP响应的Server头信息
     -->
    <Connector port="8080" protocol="org.apache.coyote.http11.Http11NioProtocol"
               maxThreads="768"
               minSpareThreads="512"
               acceptCount="128"
               
               connectionTimeout="1000"
               maxConnections="1024"
               keepAliveTimeout="300000"
               maxKeepAliveRequests="768"
               
               enableLookups="false"
               URIEncoding="utf-8"
               redirectPort="8443"
               compression="on" compressionMinSize="1024" compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain,application/json,application/xml"
               server="webserver" />
 [参考]
The HTTP Connector - Tomcat 7 Configuration Reference
 
3. Client
客户端HTTP "Keep-Alive"实现代码，请打开下一行的链接。
KeepAliveHttpClientsTest -> httpclient-x
 
【结果验证】
使用 "sudo netstat -antp | grep 80" 监控与Nginx相关的线程状态


## select , poll , epoll 的区别
[博客链接](https://www.cnblogs.com/Anker/p/3265058.html)


	