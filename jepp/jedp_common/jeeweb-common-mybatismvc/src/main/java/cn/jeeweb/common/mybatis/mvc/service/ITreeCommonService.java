package cn.jeeweb.common.mybatis.mvc.service;

import cn.jeeweb.common.mvc.entity.tree.TreeNode;
import cn.jeeweb.common.query.data.Queryable;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.io.Serializable;
import java.util.List;

public interface ITreeCommonService<T extends Serializable & TreeNode<ID>, ID extends Serializable>
		extends ICommonService<T> {
	public List<T> selectTreeList(Wrapper<T> wrapper);
	public List<T> selectTreeList(Queryable queryable, Wrapper<T> wrapper);
}