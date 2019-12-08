# http-server
这是一个http服务程序<br/>
http-server是server程序,提供socket连接，http报文处理<br/>
http-test是应用层配置处理的例子，提供业务层处理<br/>
<br/>

#### 1、加入依赖
<pre>
&lt;dependency>
    &lt;groupId>com.mk.server&lt;/groupId>
    &lt;artifactId>http-server&lt;/artifactId>
    &lt;version>1.0&lt;/version>
&lt;/dependency>
</pre>

#### 2、标注启动类型
<pre>
@HttpServerBoot(port = 8000)
public class App 
{
    public static void main(String[] args)
    {
        HttpServer.run(App.class, args);
    }
}
</pre>

#### 3、请求配置例子
<pre>
@RequestMapping(value = "/b")
public class BServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setBody("test b");
    }
}
</pre>
