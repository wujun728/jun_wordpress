package cn.jeeweb.web.config;


import cn.jeeweb.web.modules.sys.service.IDictService;
import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.InitDictable;
import cn.jeeweb.common.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.config
 * @title:
 * @description: 字典初始化
 * @author: 王存见
 * @date: 2018/3/3 15:06
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */

@Configuration
public class DictConfig implements InitDictable {

    /**
     * 数据字典初始化
     * @return
     */
    @Override
    public Map<String, List<Dict>> initDict() {
        Map<String, List<Dict>> dictMap = new HashMap<String, List<Dict>>();
        IDictService dictService= SpringContextHolder.getBean(IDictService.class);
        for (cn.jeeweb.web.modules.sys.entity.Dict dict : dictService.selectDictList()) {
                List<Dict> dictList = dictMap.get(dict.getCode());
                if (dictList != null) {
                    dictList.add(new Dict(dict.getLabel(), dict.getValue()));
                } else {
                    dictMap.put(dict.getCode(),
                            Lists.newArrayList(new Dict(dict.getLabel(), dict.getValue())));
                }
        }
        return dictMap;
    }
}
