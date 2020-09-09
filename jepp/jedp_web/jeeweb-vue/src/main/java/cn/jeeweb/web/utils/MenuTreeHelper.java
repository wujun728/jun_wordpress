package cn.jeeweb.web.utils;



import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.web.modules.sys.entity.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 树排序
 *
 * @author 王存见
 *
 */
public class MenuTreeHelper implements Serializable {

    private static final long serialVersionUMenu = 2444638065060902858L;

    private List<Menu> menus;

    public static  MenuTreeHelper create() {
        MenuTreeHelper treeSortUtil = new MenuTreeHelper();
        return treeSortUtil;
    }

    /**
     * 获得根节点
     */
    private List<MenuTreeNode> getTopNodes() {
        List<MenuTreeNode> list = new ArrayList<MenuTreeNode>();
        for (Menu treeable : menus) {
            if (treeable.isRoot()) {
                list.add(new MenuTreeNode(treeable));
            }
        }
        return list;
    }

    /**
     * 解析根节点
     *
     * @param node
     */
    private void parseSubNode(MenuTreeNode node) {
        List<MenuTreeNode> newTreeNodes = new ArrayList<MenuTreeNode>();
        for (Menu treeable : menus) {
            if (!ObjectUtils.isNullOrEmpty(treeable.getParentId()) && treeable.getParentId().equals(node.getNodeId())) {
                MenuTreeNode vueTreeNode = new MenuTreeNode(treeable);
                newTreeNodes.add(vueTreeNode);
                parseSubNode(vueTreeNode);
                node.setChildren(newTreeNodes);
            }
        }
    }

    public List<MenuTreeNode> sort(List<?> menus) {
        this.menus = (List<Menu>) menus;
        List<MenuTreeNode> rootNodes = getTopNodes();
        for (MenuTreeNode rootNode : rootNodes) {
            parseSubNode(rootNode);
        }
        this.menus.clear();
        return rootNodes;
    }

    /**
     * 这里可以优化一下，可以配置子节点的key,以及可以修改
     */
    public static class MenuTreeNode extends HashMap<String, Object> {
        private final static String PAREND_Menu_KEY="MenuTreeNode_parendId";

        public Object getNodeId() {
            return this.get(PAREND_Menu_KEY);
        }


        public MenuTreeNode(Menu menu) {
            //配置Menu,用于匹配
            this.put(PAREND_Menu_KEY,menu.getId());
            putObject(menu);
        }

        //设置节点
        public MenuTreeNode putObject(Menu menu) {
            put("path",menu.getPath());
            put("url",menu.getUrl());
            put("redirect",menu.getRedirect());
            put("name",menu.getId());
            put("component",menu.getComponent());
            put("type",menu.getType());
            //设置meta
            Map<String,Object> meta = new HashMap<>();
            meta.put("title",menu.getName());
            meta.put("icon",menu.getIcon());
            if (menu.getCacheable()==null){
                menu.setCacheable(true);
            }
            meta.put("noCache",!menu.getCacheable());
            meta.put("requireAuth",menu.getRequireAuth());
            meta.put("permission",menu.getPermission());
            put("meta",meta);
            return this;
        }

        public void setChildren(List<MenuTreeNode> children) {
            this.put("children",children);
        }
    }
}


