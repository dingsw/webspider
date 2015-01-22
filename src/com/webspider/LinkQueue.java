package com.webspider;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class LinkQueue {
	// �ѷ��ʵ�URL��ַ
	private static Set<String> visitedUrl = new HashSet<String>();

	// δ���ʵ�URL��ַ
	private static Queue<String> unVisitedUrl = new PriorityQueue<String>();

	// ���URL����
	public static Queue getUnVisitedUrl() {
		return unVisitedUrl;
	}

	// ��ӵ����ʹ���URL������
	public static void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	// �Ƴ����ʹ���URL
	public static void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	// δ���ʵ�URL����
	public static Object unVisitedDeQueue() {
		return unVisitedUrl.poll();
	}

	// ��֤ÿ��URLֻ������һ��
	public static void addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("")
				&& !unVisitedUrl.contains(url) && !visitedUrl.contains(url)) {
			unVisitedUrl.add(url);
		}
	}
	
	//����Ѿ����ʵ�URL��Ŀ
	public static int getVisitedUrlNum(){
		return visitedUrl.size();
	}
	
	//�ж�δ���ʵ�URL�����Ƿ��ǿ�
	public static boolean unVisitedUrlIsEmpty(){
		return unVisitedUrl.isEmpty();
	}
}
