package cn.jeeweb.common.sms.disruptor;

import cn.jeeweb.common.sms.data.SmsResult;

import java.io.Serializable;

public interface SmsHandlerCallBack extends Serializable {
    void onResult(SmsResult smsResult);
}
