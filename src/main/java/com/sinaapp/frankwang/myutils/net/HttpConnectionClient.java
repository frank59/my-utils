package com.sinaapp.frankwang.myutils.net;


import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Http链接取数据工具
 * 
 * @author coldwater
 * 
 */
public class HttpConnectionClient {
	/**
	 * 链接超时
	 */
	private static int DEFAULT_CONNECTION_TIMEOUT = 1000 * 3;
	/**
	 * 传输超时
	 */
	private static int DEFAULT_SO_TIMEOUT = 1000 * 3;
	/**
	 * 最大连接数
	 */
	private static int DEFAULT_CONNECTIONS_MAX_TOTAL = 200;
	/**
	 * 每host最大连接数
	 */
	private static int DEFAULT_CONNECTIONS_MAX_PERHOST = 50;
	// 初始化用到的同步锁
	private final ReentrantLock lock = new ReentrantLock();

	// 注意：这里不能static，因为有用多个httpConnectionClient的情况
	private MultiThreadedHttpConnectionManager connectionManager = null;
	private HttpClient httpClient = null;

	private int connectionTimeOut = DEFAULT_CONNECTION_TIMEOUT;
	private int soTimeOut = DEFAULT_SO_TIMEOUT;
	private int connectionMaxTotal = DEFAULT_CONNECTIONS_MAX_TOTAL;
	private int connectionMaxPerHost = DEFAULT_CONNECTIONS_MAX_PERHOST;
	
	public static HttpConnectionClient I = new HttpConnectionClient();
	
	private HttpConnectionClient(){}
	
	/**
	 * 返回字符集
	 */
	private String codeing = "UTF-8";
	/**
	 * 请求字符集
	 */
	private String charset = null;
	
	private String getCharset(){
		if (charset==null){
			return codeing;
		}else{
			return charset;
		}
	}
	private HttpClient getHttpClient() {
		lock.lock();
		try {
			if (connectionManager == null) {
				connectionManager = new MultiThreadedHttpConnectionManager();
				configure();
			}
			if (httpClient == null) {
				httpClient = new HttpClient(connectionManager);
			}

		} finally {
			lock.unlock();
		}
		return httpClient;
	}

	/**
	 * 配置connectionmanager
	 */
	private void configure() {
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setConnectionTimeout(connectionTimeOut);
		params.setMaxTotalConnections(connectionMaxTotal);
		params.setDefaultMaxConnectionsPerHost(connectionMaxPerHost);
		params.setSoTimeout(soTimeOut);
	}

	/**
	 * 返回http网页内容，使用Get方法提交
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String getContextByGetMethod(String url) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				getCharset());
		GetMethod gm = new GetMethod(url);
		String result = "";
		try {
			client.executeMethod(gm);

			if (gm.getStatusCode() >= 400) {
				throw new HttpException("Connection Error!return Status :"
						+ gm.getStatusCode());
			}
			result = new String(gm.getResponseBody(),codeing);
		} finally {
			gm.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 返回http网页内容，使用Get方法提交
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String getContextByPostMethod(String url, Map<String, String> params) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		// 设置编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				getCharset());
		PostMethod pm = new PostMethod(url);
		String result = "";
		try {
			for(Entry<String, String> entry : params.entrySet()) {
				pm.addParameter(entry.getKey(), entry.getValue());
			}
			client.executeMethod(pm);

			if (pm.getStatusCode() >= 400) {
				throw new HttpException("Connection Error!return Status :"
						+ pm.getStatusCode());
			}
			result = new String(pm.getResponseBody(),codeing);
		} finally {
			pm.releaseConnection();
		}
		return result;
	}
	
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getConnectionMaxTotal() {
		return connectionMaxTotal;
	}

	public void setConnectionMaxTotal(int connectionMaxTotal) {
		this.connectionMaxTotal = connectionMaxTotal;
	}

	public int getConnectionMaxPerHost() {
		return connectionMaxPerHost;
	}

	public void setConnectionMaxPerHost(int connectionMaxPerHost) {
		this.connectionMaxPerHost = connectionMaxPerHost;
	}

	/**
	 * @param codeing
	 *            the codeing to set
	 */
	public void setCodeing(String codeing) {
		this.codeing = codeing;
	}

	/**
	 * @return the codeing
	 */
	public String getCodeing() {
		return codeing;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
