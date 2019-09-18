package com.yetoop.cloud.atlas.common;

import java.util.List;

public class ListUtil {

	public static <E> boolean isEmpty(List<E> list) {
		return list == null || list.isEmpty();
	}
}
