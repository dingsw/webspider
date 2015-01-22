package com.webspider;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LinkQueue {
	// 已访问的URL地址
	private static Set<String> visitedUrl = new HashSet<String>();

	// 未访问的URL地址
	private static Queue<String> unVisitedUrl = new PriorityQueue<String>();

	// 获得URL队列
	public static Queue getUnVisitedUrl() {
		return unVisitedUrl;
	}

	// 添加到访问过的URL队列中
	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	// 移除访问过的URL
	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	// 未访问的URL出列
	public static Object unVisitedDeQueue() {
		return unVisitedUrl.poll();
	}

	// 保证每个URL只被访问一次
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("")
				&& !unVisitedUrl.contains(url) && !visitedUrl.contains(url)) {
			unVisitedUrl.add(url);
		}
	}
	
	//获得已经访问的URL数目
	public static int getVisitedUrlNum(){
		return visitedUrl.size();
	}
	
	//判断未访问的URL队列是否是空
	public static boolean unVisitedUrlIsEmpty(){
		return unVisitedUrl.isEmpty();
	}
}
