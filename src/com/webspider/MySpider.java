package com.webspider;

import java.util.Set;


public class MySpider {

	/**
	 * ʹ�����ӳ�ʼ��URL����
	 * 
	 * @param seeds
	 */
	private void initSpiderWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	/**
	 * ץȡ����
	 */
	public void spidering(String[] seeds) {
		// �������������ȡ��http://www.lietu.com��ͷ������
		LinkFilter filter = new LinkFilter() {

			public boolean accept(String url) {
				if (!url.startsWith("http://www.baidu.com")) {
					return false;
				} else {
					return true;
				}
			}
		};
		// ��ʼ��URL����
		initSpiderWithSeeds(seeds);
		// ѭ����������ץȡ�����Ӳ��գ�ץȡ����ҳ������1000��
		while (!LinkQueue.unVisitedUrlIsEmpty()
				&& LinkQueue.getVisitedUrlNum() <= 1000) {
			// ����ͷURL����
			String visitUrl = (String) LinkQueue.unVisitedDeQueue();
			if (visitUrl == null)
				continue;
			DownLoadFile downLoad = new DownLoadFile();
			// ������ҳ
			downLoad.downloadFile(visitUrl);
			// URL�Է����ѷ��ʵ�URL��
			LinkQueue.addVisitedUrl(visitUrl);
			// ��ȡ��������ҳ�е�URL
			Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
			// �µ�δ���ʵ�URL���뵽����
			for (String link : links) {
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}

	// main �������
	public static void main(String[] args) {
		MySpider crawler = new MySpider();
		crawler.spidering(new String[] { "http://www.baidu.com" });
	}
}
