package macaca.client.connection;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

public class HttpConnectionManager {

    PoolingHttpClientConnectionManager cm = null;
    
    public void init() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
    }

    public CloseableHttpClient getHttpClient() {
    	this.init();
    	
    	RequestConfig requestConfig = RequestConfig.custom()  
    	        .setConnectTimeout(60000)  //设置连接超时时间
    	        .setConnectionRequestTimeout(60000)  //设置从connectManager获取Connection超时时间
    	        //.setSocketTimeout(80000) //服务器获取响应数据需要等待的时间
    	        //SocketTimeout设置的超时是指指定时间内服务器端没有反应
    	        .build();
    	
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new HttpRequestRetryHandler() {
					public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
						if (executionCount > 1) {
	                        return false;
                        }
						//System.err.println(exception.getCause().toString());
                        if (exception instanceof org.apache.http.NoHttpResponseException) {
                            System.err.println("NoHttpResponseException: No response from server on call");
                            return true;
                        }
                        return false;
					}
                })
                //.setConnectionTimeToLive(1, TimeUnit.DAYS)
                .build();
        
        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
}
