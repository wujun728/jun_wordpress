package cn.jeeweb.common.email.disruptor;


import cn.jeeweb.common.email.data.EmailResult;

public interface EmailHandlerCallBack {
    void onResult(EmailResult emailResult);
}