package com.webspider;

import java.util.Set;


public class MySpider {

	/**
	 * 使用种子初始化URL队列
	 * 
	 * @param seeds
	 */
	private void initSpiderWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	/**
	 * 抓取过程
	 */
	public void spidering(String[] seeds) {
		// 定义过滤器，提取以http://www.lietu.com开头的链接
		LinkFilter filter = new LinkFilter() {

			public boolean accept(String url) {
				if (!url.startsWith("http://www.baidu.com")) {
					return false;
				} else {
					return true;
				}
			}
		};
		// 初始化URL队列
		initSpiderWithSeeds(seeds);
		// 循环条件，待抓取的链接不空，抓取的网页不多于1000个
		while (!LinkQueue.unVisitedUrlIsEmpty()
				&& LinkQueue.getVisitedUrlNum() <= 1000) {
			// 队列头URL出列
			String visitUrl = (String) LinkQueue.unVisitedDeQueue();
			if (visitUrl == null)
				continue;
			DownLoadFile downLoad = new DownLoadFile();
			// 下载网页
			downLoad.downloadFile(visitUrl);
			// URL以放入已访问的URL中
			LinkQueue.addVisitedUrl(visitUrl);
			// 提取出下载网页中的URL
			Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
			// 新的未访问的URL加入到队列
			for (String link : links) {
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}

	// main 方法入口
	public static void main(String[] args) {
		MySpider crawler = new MySpider();
		crawler.spidering(new String[] { "http://www.baidu.com" });
	}
}
