package com.webspider;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownLoadFile {
	/**
	 * ���� url ����ҳ����������Ҫ�������ҳ���ļ��� ȥ���� url �з��ļ����ַ�
	 * 
	 * @param url
	 * @param contentType
	 * @return
	 */
	public String getFileNameByUrl(String url, String contentType) {

		// remove http://
		url = url.substring(7);
		// text/html����
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		} else {
			// application/pdf����
			return url.replaceAll("[\\?/:*|<>\"]", "_")
					+ contentType.substring(url.lastIndexOf("/") + 1);
		}
	}

	/**
	 * ������ҳ�ֽ����鵽�����ļ� filePath ΪҪ������ļ�����Ե�ַ
	 */
	private void saveToLoc(byte[] data, String filePath) {

		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					new File(filePath)));
			for (int i = 0; i < data.length; i++) {
				out.write(data[i]);
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** ����URLָ�����ҳ */
	public String downloadFile(String url) {
		String filePath = null;
		// ʵ����httpclient�������ò���
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				5000);
		// ����getmethod�������ò���
		GetMethod getMethod = new GetMethod(url);
		// ����get����ʱ5s ???
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// �����������Դ���
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// ִ��http ��get����
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			// �жϷ��ʵ�״̬��
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("method failed" + getMethod.getStatusLine());
				filePath = null;
			}
			// ����http��Ӧ����
			byte[] responseBody = getMethod.getResponseBody(); // ��ȡΪ�ֽ�����
			// ������ҳURL���ɱ���ʱ���ļ���
			filePath = "h:\\spider\\"
					+ getFileNameByUrl(url, getMethod.getResponseHeader(
							"Content-Type").getValue());
			saveToLoc(responseBody, filePath);
		} catch (HttpException e) {
			// �����������쳣��������Э�鲻�Ի��߷��ص�����������
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return filePath;
		
	}
}
