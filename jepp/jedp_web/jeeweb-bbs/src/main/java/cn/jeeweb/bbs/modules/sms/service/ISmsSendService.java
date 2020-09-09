package cn.jeeweb.bbs.modules.sms.service;


import java.util.Map;

/**
 * @Title: 短信模版
 * @Description: 短信模版
 * @author 王存见
 * @date 2018-09-14 10:50:52
 * @version V1.0
 *
 */
public interface ISmsSendService {

	void send(String phone, String code, Map<String, Object> datas);

	void send(String[] phones, String code, Map<String, Object> datas);

	void send(String eventId, String phone, String code, Map<String, Object> datas);

	void send(String eventId, String[] phones, String code, Map<String, Object> datas);


}
