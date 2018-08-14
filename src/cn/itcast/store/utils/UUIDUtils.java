package cn.itcast.store.utils;

import java.util.UUID;

public class UUIDUtils {
	/**
	 * �������id
	 * 
	 * @return
	 */
	public static String getId() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static String getId64() {
		return getId() + getId();
	}

	/**
	 * ���������
	 * 
	 * @return
	 */
	public static String getCode() {
		return getId();
	}

	public static void main(String[] args) {
		System.out.println(getCode());
	}
}
