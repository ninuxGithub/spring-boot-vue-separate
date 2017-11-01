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
	
	
### 效果
![img](https://github.com/ninuxGithub/spring-boot-vue-separate/blob/master/pic.png)
	
	


	