package cn.jeeweb.generator.service;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.generator.entity.TemplateScheme;

import java.io.Serializable;

/**   
 * @Title: 模板方案
 * @Description: 模板方案
 * @author 王存见
 * @date 2017-09-15 15:21:43
 * @version V1.0   
 *
 */
public interface ITemplateSchemeService extends ICommonService<TemplateScheme> {
    /**
     * 复制
     * @param id
     * @return
     */
    boolean copy(Serializable id);

    /**
     * 复制
     * @param id
     * @return
     */
    Response export(Serializable id);

    /**
     * 复制
     * @param json
     * @return
     */
    boolean loadImport(String json);
}

