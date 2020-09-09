package cn.jeeweb.common.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;

/**
 *
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @title: SerializationUtils.java
 * @package cn.jeeweb.common.util
 * @description: 序列化工具
 * @author: 王存见
 * @date: 2017年5月22日 下午9:25:14
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */
public class SerializationUtils extends org.apache.commons.lang3.SerializationUtils {

	public static String serializeStr(final Serializable object) {
		if (object == null) {
			return null;
		}
		byte[] objectBytes = serialize(object);
		String hexStr = Hex.encodeHexString(objectBytes);
		return hexStr;
	}

	public static Object deserializeStr(String hexStr) {
		try {
			if (StringUtils.isEmpty(hexStr)) {
				return null;
			}
			return deserialize(Hex.decodeHex(hexStr.toCharArray()));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

}