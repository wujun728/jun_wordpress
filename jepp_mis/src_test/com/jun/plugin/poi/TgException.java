/**   
 * @Title: TgException.java 
 * @package com.sysmanage.common.tools.core 
 * @Description: 
 * @author zhangfeng 13940488705@163.com 
 * @date 2011-8-9 上午10:57:26 
 * @version V1.0   
 */

package com.jun.plugin.poi;

/**
 * @ClassName: TgException
 * @Description: 标准异常类
 * @author zhangfeng 13940488705@163.com
 * @date 2011-8-9 上午10:57:26
 * 
 */
public class TgException extends Exception {
	private static final long serialVersionUID = 1L;

	public TgException() {
		super();
	}

	public TgException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TgException(String arg0) {
		super(arg0);
	}

	public TgException(Throwable arg0) {
		super(arg0);
	}
}
