# 微头条

个人开发的仿今日头条的消息和资讯的分享和发布的应用

## 登录和注册

![](https://user-gold-cdn.xitu.io/2020/4/1/171363d5ecf010af?w=1080&h=2280&f=jpeg&s=171621)

![](https://user-gold-cdn.xitu.io/2020/4/1/171363de49067775?w=1080&h=2280&f=jpeg&s=95753)

![](https://user-gold-cdn.xitu.io/2020/4/1/17136450695c9b23?w=1080&h=2280&f=jpeg&s=250068)

## 文章

![](https://user-gold-cdn.xitu.io/2020/4/1/1713644317840c73?w=1080&h=2280&f=jpeg&s=666701)
![](https://user-gold-cdn.xitu.io/2020/4/1/171363e926c3a97f?w=1080&h=2280&f=jpeg&s=919246)

![](https://user-gold-cdn.xitu.io/2020/4/1/171363eddeadf26e?w=1080&h=2280&f=jpeg&s=485873)

## 视频


![](https://user-gold-cdn.xitu.io/2020/4/1/17136418825a44e5?w=1080&h=2280&f=jpeg&s=1156809)

![](https://user-gold-cdn.xitu.io/2020/4/1/171363f6cd35f2bd?w=1080&h=2280&f=jpeg&s=423412)

![](https://user-gold-cdn.xitu.io/2020/4/1/171364206adf9072?w=360&h=762&f=gif&s=1696304)

## 我的

![](https://user-gold-cdn.xitu.io/2020/4/1/1713648512176e75?w=1080&h=2280&f=jpeg&s=575327)

![](https://user-gold-cdn.xitu.io/2020/4/1/17136490a794bc79?w=1080&h=2280&f=jpeg&s=75395)

![](https://user-gold-cdn.xitu.io/2020/4/1/17136493ca55d831?w=1080&h=2280&f=jpeg&s=157805)

![](https://user-gold-cdn.xitu.io/2020/4/1/171364a1e0658756?w=1080&h=2280&f=jpeg&s=340037)

![](https://user-gold-cdn.xitu.io/2020/4/1/171364a5426e0413?w=1080&h=2280&f=jpeg&s=187915)


![](https://user-gold-cdn.xitu.io/2020/4/1/171364ab102aebd6?w=1080&h=2280&f=jpeg&s=261035)

## 图片选择和裁剪

![](https://user-gold-cdn.xitu.io/2020/4/1/17136477c062c0ce?w=360&h=762&f=gif&s=2272071)

## 敏感词检测

![](https://user-gold-cdn.xitu.io/2020/4/1/171364bd43db9ae2?w=1080&h=2280&f=jpeg&s=472532)

![](https://user-gold-cdn.xitu.io/2020/4/1/171364b688f2a228?w=1080&h=2280&f=jpeg&s=186714)

## 功能

- 富文本编辑，实现图片、文本、链接以及一二三级标题的混排，利用RecyclerView的多布局实现富文本的增加和删除，自定义本地图片选择器实现对图片的选择
- 图片显示，使用自定义ImageView，实现图片的放大、缩小、移动、保存，以及大图片的区块显示，解决了自定义ImageView移动图片和viewPager的滑动冲突
- 图片裁剪，利用自定义view实现图片圆形和矩形裁剪和保存的功能
- 敏感词过滤，通过AC自动机实现对评论和文章进行敏感词搜索，并反馈给用户让用户就行修改

技术栈:

- 自定义View和ViewGroup
- 属性动画
- RecyclerView的多布局
- Glide、Retrofit、OkHtttp
- MVVM



