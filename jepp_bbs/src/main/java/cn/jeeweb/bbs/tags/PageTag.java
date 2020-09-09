package cn.jeeweb.bbs.tags;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.tags
 * @title:
 * @description: Cache工具类
 * @author: 王存见
 * @date: 2018/8/31 17:37
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */

import cn.jeeweb.beetl.tags.TagSupport;
import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import cn.jeeweb.beetl.tags.exception.BeetlTagException;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.common.utils.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.modules.front.tag
 * @title:
 * @description:分页标签
 * @author: 王存见
 * @date: 2018/1/13 14:37
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Component
@Scope("prototype")
@BeetlTagName("page.pager")
public class PageTag extends TagSupport {
    private String url; // 请求URI
    private String parameters = "";
    private int pageSize = 10; // 每页要显示的记录数
    private int pageNo = 1; // 当前页号
    private int recordCount; // 总记录数

    @SuppressWarnings("unchecked")
    public int doStartTag() throws BeetlTagException {
        int pageCount = (recordCount + pageSize - 1) / pageSize; // 计算总页数

        // 拼写要输出到页面的HTML文本
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"laypage-main\">\r\n");
        if (recordCount == 0) {
            sb.append("<strong>没有可显示的招考信息</strong>\r\n");
        } else {
            // 页号越界处理
            if (pageNo > pageCount) {
                pageNo = pageCount;
            }
            if (pageNo < 1) {
                pageNo = 1;
            }

            // 获取请求中的所有参数
            HttpServletRequest request = ServletUtils.getRequest();
            Enumeration<String> enumeration = request.getParameterNames();
            String name = null; // 参数名
            String value = null; // 参数值
            // 把请求中的所有参数当作隐藏表单域
            while (enumeration.hasMoreElements()) {
                name = enumeration.nextElement();
                value = request.getParameter(name);
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)){
                    continue;
                }
                // 去除页号
                if (name.equals("pageNo")) {
                    if (null != value && !"".equals(value)) {
                        pageNo = Integer.parseInt(value);
                    }
                    continue;
                }
                if (this.url.contains("?")){
                    parameters += "&"+name + "=" + value;
                }else{
                    parameters += "?"+name + "=" + value;
                }
            }

            // 输出统计数据
          /*  sb.append(" 共<strong>").append(recordCount).append("</strong>项")
                    .append(",<strong>").append(pageCount)
                    .append("</strong>页: \r\n");*/

            // 上一页处理
            if (pageNo == 1) {
                /*sb.append("<span class=\"disabled\">« 上一页").append(
                        "</span>\r\n");*/
            } else {
                sb.append("<a href=\""+url+"/")
                        .append((pageNo - 1) + parameters).append("\">« 上一页</a>\r\n");
            }

            // 如果前面页数过多,显示"..."
            int start = 1;
            if (this.pageNo > 4) {
                start = this.pageNo - 1;
                sb.append("<a href=\""+url+"/1"+ parameters+"\">1</a>\r\n");
                sb.append("<a href=\""+url+"/2"+parameters+"\">2</a>\r\n");
                sb.append("<span>…</span>\r\n");
            }
            // 显示当前页附近的页
            int end = this.pageNo + 1;
            if (end > pageCount) {
                end = pageCount;
            }
            for (int i = start; i <= end; i++) {
                if (pageNo == i) { // 当前页号不需要超链接
                    sb.append("<span class=\"laypage-curr\">").append(i)
                            .append("</span>\r\n");
                } else {
                    sb.append("<a href=\""+url+"/").append(i)
                            .append(parameters).append("\">").append(i).append("</a>\r\n");
                }
            }
            // 如果后面页数过多,显示"..."
            if (end < pageCount - 2) {
                sb.append("<span>…</span>\r\n");
            }
            if (end < pageCount - 1) {
                sb.append("<a href=\""+url+"/")
                        .append(pageCount - 1).append(parameters).append("\">")
                        .append(pageCount - 1).append("</a>\r\n");
            }
            if (end < pageCount) {
                sb.append("<a href=\""+url+"/")
                        .append(pageCount).append(parameters).append("\">").append(pageCount)
                        .append("</a>\r\n");
            }

            // 下一页处理
            if (pageNo == pageCount) {
                /*sb.append("<span class=\"disabled\">下一页 »").append(
                        "</span>\r\n");*/
            } else {
                sb.append("<a href=\""+url+"/"+(pageNo + 1)+parameters+"\" class=\"laypage-next\">下一页 »</a>");
            }
        }
        sb.append("</div>\r\n");
        // 把生成的HTML输出到响应中
        try {
            this.ctx.byteWriter.writeString(sb.toString());
        } catch (IOException e) {
            throw new BeetlTagException(e);
        }
        return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}

