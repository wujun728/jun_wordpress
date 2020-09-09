package cn.jeeweb.web.modules.oss.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.modules.oss.entity.Attachment;
import cn.jeeweb.web.modules.oss.mapper.AttachmentMapper;
import cn.jeeweb.web.modules.oss.service.IAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.gov.gzst.oss.service.impl
* @title: 附件管理服务实现
* @description: 附件管理服务实现
* @author: 王存见
* @date: 2018-04-25 14:25:54
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("attachmentService")
public class AttachmentServiceImpl  extends CommonServiceImpl<AttachmentMapper,Attachment> implements IAttachmentService {

}