package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.bbs.modules.sys.entity.MessageTemplate;
import cn.jeeweb.bbs.modules.sys.entity.User;
import cn.jeeweb.bbs.modules.sys.service.IMessageTemplateService;
import cn.jeeweb.bbs.modules.sys.service.IUserService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.sys.service.IMessageService;
import cn.jeeweb.bbs.modules.sys.entity.Message;
import cn.jeeweb.bbs.modules.sys.mapper.MessageMapper;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.sys.service.impl
* @title: 系统消息服务实现
* @description: 系统消息服务实现
* @author: 王存见
* @date: 2018-09-03 15:10:32
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("messageService")
public class MessageServiceImpl  extends CommonServiceImpl<MessageMapper,Message> implements  IMessageService {

    @Autowired
    private IMessageTemplateService messageTemplateService;
    @Autowired
    private IUserService userService;

    @Override
    public void sendMessage(String uid, String code, Map<String, Object> datas) {
        String[] uids = { uid };
        sendMessage(uids,code,datas);
    }

    @Override
    public void sendMessage(String[] uids, String code, Map<String, Object> datas) {
        MessageTemplate template = messageTemplateService.selectOne(new EntityWrapper<MessageTemplate>().eq("code", code));
        if (datas == null) {
            datas = new HashMap<>();
        }
        if (template == null){
            return ;
        }
        String content = parseContent(StringEscapeUtils.unescapeHtml4(template.getTemplateContent()), datas);
        String title = parseContent(StringEscapeUtils.unescapeHtml4(template.getTemplateTitle()), datas);
        List<Message> messageList = new ArrayList<Message>();
        for (String uid: uids) {
            Message message = new Message();
            message.setRead(0);
            message.setSendDate(new Date());
            message.setTitle(title);
            message.setContent(content);
            message.setReadUid(uid);
            User user= userService.selectById(uid);
            message.setReadUname(user.getRealname());
            messageList.add(message);
        }
        if (messageList.size()>0) {
            insertBatch(messageList);
        }
    }

    private String parseContent(String content,Map<String, Object> dataMap) {
        try {
            String tempname = StringUtils.hashKeyForDisk(content);
            Configuration configuration = new Configuration();
            configuration.setNumberFormat("#");
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate(tempname, content);
            freemarker.template.Template template = new freemarker.template.Template(tempname, new StringReader(content));
            StringWriter stringWriter = new StringWriter();
            template.process(dataMap, stringWriter);
            configuration.setTemplateLoader(stringLoader);
            content = stringWriter.toString();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("模板解析失败");
        }
        return content;
    }

    @Override
    public void read(String id) {
        Message message = selectById(id);
        message.setRead(1);
        message.setReadDate(new Date());
        insertOrUpdate(message);
    }
}