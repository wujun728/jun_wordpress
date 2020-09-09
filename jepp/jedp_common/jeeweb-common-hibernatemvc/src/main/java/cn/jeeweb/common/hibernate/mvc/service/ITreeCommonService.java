package cn.jeeweb.common.hibernate.mvc.service;

import cn.jeeweb.common.mvc.entity.tree.TreeNode;

import java.io.Serializable;

public interface ITreeCommonService<T extends Serializable & TreeNode<ID>, ID extends Serializable>
		extends ICommonService<T> {

}