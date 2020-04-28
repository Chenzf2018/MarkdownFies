# 抓取猫眼电影排行榜
* 工具：`requests`，正则

## 抓取分析
抓取的目标站点为`https://maoyan.com/board/4`；进入第二页时，网站变为`https://maoyan.com/board/4?offset=10`；`offset`代表偏移量。

## 抓取首页
```python
import requests


def get_one_page(url):
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Safari/537.36 Edg/80.0.361.109"
    }

    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        return response.text
    return None


def main():
    url = "https://maoyan.com/board/4"
    html = get_one_page(url)
    print(html)


main()
```
返回的结果是一个验证中心：
<div align=center><img src=WebCrawler/美团验证中心.png></div>

一个简单的解决方案是，保存返回的代码为`html`并打开，手动滑动滑块进行验证！

再运行代码，可以成功获取首页的源代码，接着进行页面解析！

## 利用正则进行页面解析
在开发者模式下的`Network`监听组件中查看源代码，不要在`Elements`选项卡中直接查看源码，因为那里的惊码可能经过`JavaScript`操作而与原始请求不同：
<div align=center><img src=WebCrawler/猫眼首页解析.png></div>

可以看到，一部电影信息对应的源代码是一个`dd`节点：
```html
<dd>
                        <i class="board-index board-index-1">1</i>
    <a href="/films/1203" title="霸王别姬" class="image-link" data-act="boarditem-click" data-val="{movieId:1203}">
      <img src="//s3plus.meituan.net/v1/mss_e2821d7f0cfe4ac1bf9202ecf9590e67/cdn-prod/file:5788b470/image/loading_2.e3d934bf.png" alt="" class="poster-default" />
      <img data-src="https://p0.meituan.net/movie/ce4da3e03e655b5b88ed31b5cd7896cf62472.jpg@160w_220h_1e_1c" alt="霸王别姬" class="board-img" />
    </a>
    <div class="board-item-main">
      <div class="board-item-content">
              <div class="movie-item-info">
        <p class="name"><a href="/films/1203" title="霸王别姬" data-act="boarditem-click" data-val="{movieId:1203}">霸王别姬</a></p>
        <p class="star">
                主演：张国荣,张丰毅,巩俐
        </p>
<p class="releasetime">上映时间：1993-07-26</p>    </div>
    <div class="movie-item-number score-num">
<p class="score"><i class="integer">9.</i><i class="fraction">5</i></p>        
    </div>

      </div>
    </div>

                </dd>
                <dd>
                        <i class="board-index board-index-2">2</i>
    <a href="/films/1297" title="肖申克的救赎" class="image-link" data-act="boarditem-click" data-val="{movieId:1297}">
      <img src="//s3plus.meituan.net/v1/mss_e2821d7f0cfe4ac1bf9202ecf9590e67/cdn-prod/file:5788b470/image/loading_2.e3d934bf.png" alt="" class="poster-default" />
      <img data-src="https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c" alt="肖申克的救赎" class="board-img" />
    </a>
    <div class="board-item-main">
      <div class="board-item-content">
              <div class="movie-item-info">
        <p class="name"><a href="/films/1297" title="肖申克的救赎" data-act="boarditem-click" data-val="{movieId:1297}">肖申克的救赎</a></p>
        <p class="star">
                主演：蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿
        </p>
<p class="releasetime">上映时间：1994-09-10(加拿大)</p>    </div>
    <div class="movie-item-number score-num">
<p class="score"><i class="integer">9.</i><i class="fraction">5</i></p>        
    </div>

      </div>
    </div>

                </dd>
```
因此，可以用正则表达式来提取这里面的信息。

首先提取它的排名信息`<i class="board-index board-index-2">2</i>`，可将`board-index`作为标志位：

正则表达式：`<dd>.*?board-index.*?>(.*?)</i>`

随后提取电影的图片`<img data-src="https://p0.meituan.net/movie/ce4da3e03e655b5b88ed31b5cd7896cf62472.jpg@160w_220h_1e_1c" alt="霸王别姬" class="board-img" />`：

正则表达式：`<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)"`

接着提取电影的名称`<p class="name"><a href="/films/1203" title="霸王别姬" data-act="boarditem-click" data-val="{movieId:1203}">霸王别姬</a></p>`：


正则表达式：`<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)".*?name.*?<a.*?>(.*?)</a>`

再提取主演、上映时间、评分
```html
<p class="star">
                主演：张国荣,张丰毅,巩俐
        </p>
<p class="releasetime">上映时间：1993-07-26</p>    </div>
    <div class="movie-item-number score-num">
<p class="score"><i class="integer">9.</i><i class="fraction">5</i></p>        
    </div>
```

正则表达式：`<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)".*?name.*?<a.*?>(.*?)</a>.*?star.*?>(.*?)</p>.*?releasetime.*?>(.*?)</p>.*?integer.*?>(.*?)</i>.*?fraction.*?>(.*?)</i>.*?</dd>`

调用`findall()`方法提取出所有的内容：
```python
def parse_one_page(html):
    pattern = re.compile('<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)".*?name.*?<a.*?>(.*?)</a>.*?star.*?>(.*?)</p>.*?releasetime.*?>(.*?)</p>.*?integer.*?>(.*?)</i>.*?fraction.*?>(.*?)</i>.*?</dd>',
                         re.S)
    items = re.findall(pattern, html)
    print(items)
```
结果为：
```
[('1', 'https://p0.meituan.net/movie/ce4da3e03e655b5b88ed31b5cd7896cf62472.jpg@160w_220h_1e_1c', '霸王别姬', '\n                主演：张国荣,张丰毅,巩俐\n        ', '上映时间：1993-07-26', '9.', '5'), 
......
('10', 'https://p0.meituan.net/movie/1f0d671f6a37f9d7b015e4682b8b113e174332.jpg@160w_220h_1e_1c', '喜剧之王', '\n                主演：周星驰,莫文蔚,张柏芝\n        ', '上映时间：1999-02-13(中国香港)', '9.', '1')]
```

对得到的列表结果进行处理，遍历提取结果并生成字典：
```python
def parse_one_page(html):
    pattern = re.compile('<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)".*?name.*?<a.*?>(.*?)</a>.*?star.*?>(.*?)</p>.*?releasetime.*?>(.*?)</p>.*?integer.*?>(.*?)</i>.*?fraction.*?>(.*?)</i>.*?</dd>',
                         re.S)
    items = re.findall(pattern, html)

    for item in items:
        yield {
            'index': item[0],
            'image': item[1],
            'title': item[2],
            'actor':item[3].strip(),
            'time': item[4],
            'score': item[5] + item[6]
        }


def main():
    url = "https://maoyan.com/board/4"
    html = get_one_page(url)
    # print(html)
    for item in parse_one_page(html):
        print(item)
```

输出结果：
```
{'index': '1', 'image': 'https://p0.meituan.net/movie/ce4da3e03e655b5b88ed31b5cd7896cf62472.jpg@160w_220h_1e_1c', 'title': '霸王别姬', 'actor': '主演：张国荣,张丰毅,巩俐', 'time': '上映时间：1993-07-26', 'score': '9.5'}
{'index': '2', 'image': 'https://p0.meituan.net/movie/283292171619cdfd5b240c8fd093f1eb255670.jpg@160w_220h_1e_1c', 'title': '肖申克的救赎', 'actor': '主演：蒂姆·罗宾斯,摩根·弗里曼,鲍勃·冈顿', 'time': '上映时间：1994-09-10(加拿大)', 'score': '9.5'}
......
```

## 写入文件
通过`JSON`库的`dumps()`方法实现字典的序列化，并指定`ensure_ascii`参数为`False`，这样可以保证输出结果是中文形式而不是`Unicode`编码。

```python
def write_to_file(contents):
    with open('result.txt', 'a', encoding='utf-8') as f:
        f.write(json.dumps(contents, ensure_ascii=False) + '\n')
```

## 整合代码
```python
import requests
from requests.exceptions import RequestException
import re
import json
import time


def get_one_page(url):
    try:
        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"
        }

        response = requests.get(url, headers=headers)
        if response.status_code == 200:
            return response.text
        return None

    except RequestException:
        return 'Exception'


def parse_one_page(html):
    pattern = re.compile('<dd>.*?board-index.*?>(.*?)</i>.*?<img data-src="(.*?)".*?name.*?<a.*?>(.*?)</a>.*?star.*?>(.*?)</p>.*?releasetime.*?>(.*?)</p>.*?integer.*?>(.*?)</i>.*?fraction.*?>(.*?)</i>.*?</dd>',
                         re.S)
    items = re.findall(pattern, html)

    for item in items:
        yield {
            'index': item[0],
            'image': item[1],
            'title': item[2],
            'actor':item[3].strip(),
            'time': item[4],
            'score': item[5] + item[6]
        }


def write_to_file(contents):
    with open('result.txt', 'a', encoding='utf-8') as f:
        f.write(json.dumps(contents, ensure_ascii=False) + '\n')


def main(offset):
    url = "https://maoyan.com/board/4?offset=" + str(offset)
    html = get_one_page(url)
    # print(html)
    for item in parse_one_page(html):
        print(item)
        write_to_file(item)


if __name__ == '__main__':
    for i in range(10):
        main(offset= i * 10)
        time.sleep(2)
```