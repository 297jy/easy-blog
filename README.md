# easy-blog服务端代码
根据Hexo框架定制的博客网站
## 介绍
### Hexo
Hexo是一款快速、简洁且高效的博客框架，支持Markdown语法，能够在几秒内瞬间完成几百个页面的渲染，
即使用户没接触过前端也能搭建一个美观、简洁的博客网站  
### 不足
- 用户如果对Hexo框架不熟悉，那么配置一个美观、简洁的博客网站可能需要花费很长时间
- 发布文章不方便，没有统一的界面让用户管理文章，需要手动上传文件到服务器
- Markdown语法有学习成本，在文章中插图比较复杂
- 如果想把Hexo部署在用户私有的服务器上，操作比较复杂
## 特点
- 提供Hexo默认主题配置，虽然不一定能让所有人都觉得好看，但至少能做到简洁、不丑
- 提供统一的系统界面，让用户更加方便的管理文章以及一些系统配置
- 提供富文本编辑框，用户不需要学习Markdown语法，就能写一篇完整的文章
- 提供docker镜像，这样用户只需要在私有的服务器上搭建好docker运行环境，就能够很方便的在服务器上部署属于自己的博客网站
## 演示效果
- 博客主页面
![博客主页面.png](https://s2.loli.net/2022/07/23/4ixDSRsYevLO59a.png)
- 文章浏览页面
![文章浏览页面.png](https://s2.loli.net/2022/07/23/nkC5Gs7mw3eLHKS.png)
- 文章管理页面
![文章管理页面.png](https://s2.loli.net/2022/07/23/ae56h7mkdJ83IqN.png)
- 文章编辑页面
![文章编辑页面.png](https://s2.loli.net/2022/07/23/DfRVdqkmtFvEJs4.png)
## 命令
- 启动容器命令:  
sudo docker run --privileged=true -d -v /myblog/source/_posts:/myblog/source/_posts -v /myblog/bak:/myblog/bak  -v /myblog/tmp:/myblog/tmp   -p 80:80 -p 8000:8000 -p 8009:8009 zhuanyi/myblog:1.0
- 进入容器命令:  
sudo docker exec -it 容器ID  bash
- 容器ID查看命令:  
sudo docker ps
- 容器停止命令:  
sudo docker stop 容器ID
- 容器镜像构建命令:  
进入docker目录下  
执行 docker build -t zhuanyi/myblog:1.0 .

