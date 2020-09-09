package cn.jeeweb.web.modules.sys.mapper;

import java.util.List;

import cn.jeeweb.web.modules.sys.entity.Dict;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
	List<Dict> selectDictList();
}