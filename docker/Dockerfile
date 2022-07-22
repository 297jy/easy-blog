FROM centos:7
MAINTAINER www.ctnrs.com

#安装nodejs和npm
ADD node-v16.14.0-linux-x64.tar.gz .
#添加easy_blog后端jar包
ADD easy_blog.jar .
#添加pm2管理easy_blog的进程文件
ADD easy_blog_run.js .
#添加到Hexo框架代码到容器里
ADD myblog.tar.gz .
#添加nginx到容器里
ADD nginx-1.22.0.tar.gz .
#添加Vue管理界面前端代码到容器里
ADD dist.tar.gz .
#添加nginx配置文件到容器中
ADD nginx.conf .
#添加easy-blog配置文件
ADD system_config /etc

RUN yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo \
    && yum install -y gcc gcc-c++ make \
        openssl-devel pcre-devel gd-devel \
        iproute net-tools telnet wget curl \
    && yum install lsof -y \
    && yum clean all \
    && rm -rf /var/cache/yum/* \
    #安装java环境
    && yum -y install java-1.8.0-openjdk* \
    #安装centos7中文环境
    && yum install kde-l10n-Chinese -y \
    && yum install glibc-common -y \
    && localedef -c -f UTF-8 -i zh_CN zh_CN.utf8 \
    && export LANG=zh_CN.UTF-8 \
    && echo "export LANG=zh_CN.UTF-8" >> /etc/locale.conf

ENV LANG=zh_CN.UTF-8
ENV LC_ALL=zh_CN.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0
ENV JRE_HOME=$JAVA_HOME/jre
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib
ENV PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
ENV PATH=/node-v16.14.0-linux-x64/bin:$PATH

WORKDIR /myblog
RUN  npm config set registry http://registry.npm.taobao.org \
    && npm install -g npm@8.15.0 \
    && npm install -g hexo-cli \
    # && npm install hexo-server --save \
    # && npm install 				\
    # && npm install hexo-deployer-git --save \
    # && npm install hexo-migrator-rss --save \
    # && npm install hexo-asset-image --save	\
    # && npm install hexo-wordcount --save 	\
    # && npm install hexo-generator-sitemap --save 		\
    # && npm install hexo-generator-baidu-sitemap --save 	\
    # && npm install hexo-helper-live2d --save   \
    && npm install pm2 -g

WORKDIR /nginx-1.22.0

RUN ./configure --prefix=/usr/local/nginx \
    --with-http_ssl_module \
    --with-http_stub_status_module \
    && make -j 4 && make install  \
    && rm -rf /usr/local/nginx/html/* \
#做软连接
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mv -f /dist /usr/local/nginx/html/dist

#设置环境变量
ENV PATH $PATH:/usr/local/nginx/sbin
#拷贝
COPY nginx.conf /usr/local/nginx/conf/nginx.conf
#CMD ["systemctl", "stop", "firewalld.service"]

#暴露端口
EXPOSE 80 8000 8009

#指定工作目录
WORKDIR /myblog

CMD pm2 start run.js \
    && pm2 start /easy_blog_run.js \
    && nginx -g "daemon off;"
#运行这个镜像，默认执行的命令
#CMD ["nginx", "-g", "daemon off;"]
