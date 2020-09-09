package cn.jeeweb.generator.mapper;

import cn.jeeweb.generator.entity.Template;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**   
 * @Title: 生成模板数据库控制层接口
 * @Description: 生成模板数据库控制层接口
 * @author jeeweb
 * @date 2017-09-15 15:10:12
 * @version V1.0   
 *
 */
@Mapper
public interface TemplateMapper extends BaseMapper<Template> {
    
}