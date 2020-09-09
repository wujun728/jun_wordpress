/**

 @Name: 用户模块

 */
 
layui.define(['laypage', 'fly', 'element', 'flow','table'], function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var laypage = layui.laypage;
  var fly = layui.fly;
  var flow = layui.flow;
  var element = layui.element;
  var upload = layui.upload;
  var table = layui.table;

  var gather = {}, dom = {
    mine: $('#LAY_mine')
    ,mineview: $('.mine-view')
    ,minemsg: $('#LAY_minemsg')
    ,infobtn: $('#LAY_btninfo')
  };

  //我的相关数据
  var elemUC = $('#LAY_uc'), elemUCM = $('#LAY_ucm');
  gather.minelog = {};
  gather.mine = function(index, type, url){
    var tpl = [
      //求解
      '{{# for(var i = 0; i < d.rows.length; i++){ }}\
      <li>\
        {{# if(d.rows[i].collection_time){ }}\
          <a class="jie-title" href="/jie/{{d.rows[i].id}}/" target="_blank">{{= d.rows[i].title}}</a>\
          <i>{{ d.rows[i].collection_time }} 收藏</i>\
        {{# } else { }}\
          {{# if(d.rows[i].status == 1){ }}\
          <span class="fly-jing layui-hide-xs">精</span>\
          {{# } }}\
          {{# if(d.rows[i].accept >= 0){ }}\
            <span class="jie-status jie-status-ok">已结</span>\
          {{# } else { }}\
            <span class="jie-status">未结</span>\
          {{# } }}\
          {{# if(d.rows[i].status == -1){ }}\
            <span class="jie-status">审核中</span>\
          {{# } }}\
          <a class="jie-title" href="/jie/{{d.rows[i].id}}/" target="_blank">{{= d.rows[i].title}}</a>\
          <i class="layui-hide-xs">{{ layui.util.timeAgo(d.rows[i].time, 1) }}</i>\
          {{# if(d.rows[i].accept == -1){ }}\
          <a class="mine-edit layui-hide-xs" href="/jie/edit/{{d.rows[i].id}}" target="_blank">编辑</a>\
          {{# } }}\
          <em class="layui-hide-xs">{{d.rows[i].hits}}阅/{{d.rows[i].comment}}答</em>\
        {{# } }}\
      </li>\
      {{# } }}'
    ];

    var view = function(res){
      var html = laytpl(tpl[0]).render(res);
      dom.mine.children().eq(index).find('span').html(res.count);
      elemUCM.children().eq(index).find('ul').html(res.rows.length === 0 ? '<div class="fly-msg">没有相关数据</div>' : html);
    };

    var page = function(now){
      var curr = now || 1;
      if(gather.minelog[type + '-page-' + curr]){
        view(gather.minelog[type + '-page-' + curr]);
      } else {
        //我收藏的帖
        if(type === 'collection'){
          var nums = 10; //每页出现的数据量
          fly.json(url, {}, function(res){
            res.count = res.rows.length;

            var rows = layui.sort(res.rows, 'collection_timestamp', 'desc')
            ,render = function(curr){
              var data = []
              ,start = curr*nums - nums
              ,last = start + nums - 1;

              if(last >= rows.length){
                last = curr > 1 ? start + (rows.length - start - 1) : rows.length - 1;
              }

              for(var i = start; i <= last; i++){
                data.push(rows[i]);
              }

              res.rows = data;
              
              view(res);
            };

            render(curr)
            gather.minelog['collect-page-' + curr] = res;

            now || laypage.render({
              elem: 'LAY_page1'
              ,count: rows.length
              ,curr: curr
              ,jump: function(e, first){
                if(!first){
                  render(e.curr);
                }
              }
            });
          });
        } else {
          fly.json('/api/'+ type +'/', {
            page: curr
          }, function(res){
            view(res);
            gather.minelog['mine-jie-page-' + curr] = res;
            now || laypage.render({
              elem: 'LAY_page'
              ,count: res.count
              ,curr: curr
              ,jump: function(e, first){
                if(!first){
                  page(e.curr);
                }
              }
            });
          });
        }
      }
    };

    if(!gather.minelog[type]){
      page();
    }
  };

  if(elemUC[0]){
    layui.each(dom.mine.children(), function(index, item){
      var othis = $(item)
      gather.mine(index, othis.data('type'), othis.data('url'));
    });
  }

  //显示当前tab
  if(location.hash){
    element.tabChange('user', location.hash.replace(/^#/, ''));
  }

  element.on('tab(user)', function(){
    var othis = $(this), layid = othis.attr('lay-id');
    if(layid){
      location.hash = layid;
    }
  });

  //根据ip获取城市
  if($('#L_city').val() === ''){
     $.getScript('http://pv.sohu.com/cityjson?ie=utf-8"', function(){
        $('#L_city').val(returnCitySN.cname||'');
      });
  }

  //搜索
  $('.LAY_search').on('click', function(){
      layer.open({
          type: 1
          ,title: false
          ,closeBtn: false
          //,shade: [0.1, '#fff']
          ,shadeClose: true
          ,maxWidth: 10000
          ,skin: 'fly-layer-search'
          ,content: ['<form action="http://cn.bing.com/search">'
              ,'<input autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="q">'
              ,'</form>'].join('')
          ,success: function(layero){
              var input = layero.find('input');
              input.focus();

              layero.find('form').submit(function(){
                  var val = input.val();
                  if(val.replace(/\s/g, '') === ''){
                      return false;
                  }
                  input.val('site:jeeweb.cn '+ input.val());
              });
          }
      })
  });

  //上传图片
  if($('.upload-img')[0]){
    layui.use('upload', function(upload){
      var avatarAdd = $('.avatar-add');

      upload.render({
        elem: '.upload-img'
        ,url: '/oss/upload/'
        ,size: 50
        ,before: function(){
          avatarAdd.find('.loading').show();
        }
        ,done: function(res){
          if(res.code == 0){
            $.post('/user/avatar/', {
              avatar: res.attachment_list[0].filePath
            }, function(res){
              location.reload();
            });
          } else {
            layer.msg(res.msg, {icon: 5});
          }
          avatarAdd.find('.loading').hide();
        }
        ,error: function(){
          avatarAdd.find('.loading').hide();
        }
      });
    });
  }

  //合作平台
  if($('#LAY_coop')[0]){

    //资源上传
    $('#LAY_coop .uploadRes').each(function(index, item){
      var othis = $(this);
      upload.render({
        elem: item
        ,url: '/api/upload/cooperation/?filename='+ othis.data('filename')
        ,accept: 'file'
        ,exts: 'zip'
        ,size: 30*1024
        ,before: function(){
          layer.msg('正在上传', {
            icon: 16
            ,time: -1
            ,shade: 0.7
          });
        }
        ,done: function(res){
          if(res.code == 0){
            layer.msg(res.msg, {icon: 6})
          } else {
            layer.msg(res.msg)
          }
        }
      });
    });

    //成效展示
    var effectTpl = ['{{# layui.each(d.data, function(index, item){ }}'
    ,'<tr>'
      ,'<td><a href="/u/{{ item.uid }}" target="_blank" style="color: #01AAED;">{{ item.uid }}</a></td>'
      ,'<td>{{ item.authProduct }}</td>'
      ,'<td>￥{{ item.rmb }}</td>'
      ,'<td>{{ item.create_time }}</td>'
      ,'</tr>'
    ,'{{# }); }}'].join('');

    var effectView = function(res){
      var html = laytpl(effectTpl).render(res);
      $('#LAY_coop_effect').html(html);
      $('#LAY_effect_count').html('你共有 <strong style="color: #FF5722;">'+ (res.count||0) +'</strong> 笔合作授权订单');
    };

    var effectShow = function(page){
      fly.json('/cooperation/effect', {
        page: page||1
      }, function(res){
        effectView(res);
        laypage.render({
          elem: 'LAY_effect_page'
          ,count: res.count
          ,curr: page
          ,jump: function(e, first){
            if(!first){
              effectShow(e.curr);
            }
          }
        });
      });
    };

    effectShow();

  }

  //提交成功后刷新
  fly.form['set-mine'] = function(data, required){
    layer.msg('修改成功', {
      icon: 1
      ,time: 1000
      ,shade: 0.1
    }, function(){
      location.reload();
    });
  }

  //帐号绑定
  $('.acc-unbind').on('click', function(){
    var othis = $(this), type = othis.attr('type');
    layer.confirm('整的要解绑'+ ({
      qq_id: 'QQ'
      ,weibo_id: '微博'
    })[type] + '吗？', {icon: 5}, function(){
      fly.json('/api/unbind', {
        type: type
      }, function(res){
        if(res.status === 0){
          layer.alert('已成功解绑。', {
            icon: 1
            ,end: function(){
              location.reload();
            }
          });
        } else {
          layer.msg(res.msg);
        }
      });
    });
  });


  //我的消息
  gather.minemsg = function(){
    var delAll = $('#LAY_delallmsg')
    ,tpl = '{{# var len = d.rows.length;\
    if(len === 0){ }}\
      <div class="fly-none">您暂时没有最新消息</div>\
    {{# } else { }}\
      <ul class="mine-msg">\
      {{# for(var i = 0; i < len; i++){ }}\
        <li data-id="{{d.rows[i].id}}">\
          <blockquote class="layui-elem-quote">{{ d.rows[i].content}}</blockquote>\
          <p><span>{{d.rows[i].time}}</span><a href="javascript:;" class="layui-btn layui-btn-sm layui-btn-danger fly-delete">删除</a></p>\
        </li>\
      {{# } }}\
      </ul>\
    {{# } }}'
    ,delEnd = function(clear){
      if(clear || dom.minemsg.find('.mine-msg li').length === 0){
        dom.minemsg.html('<div class="fly-none">您暂时没有最新消息</div>');
      }
    }
    
    
    /*
    fly.json('/message/find/', {}, function(res){
      var html = laytpl(tpl).render(res);
      dom.minemsg.html(html);
      if(res.rows.length > 0){
        delAll.removeClass('layui-hide');
      }
    });
    */
    
    //阅读后删除
    dom.minemsg.on('click', '.mine-msg li .fly-delete', function(){
      var othis = $(this).parents('li'), id = othis.data('id');
      fly.json('/message/remove/', {
        id: id
      }, function(res){
        if(res.code === 0){
          othis.remove();
          delEnd();
        }
      });
    });

    //删除全部
    $('#LAY_delallmsg').on('click', function(){
      var othis = $(this);
      layer.confirm('确定清空吗？', function(index){
        fly.json('/message/remove/', {
          all: true
        }, function(res){
          if(res.code === 0){
            layer.close(index);
            othis.addClass('layui-hide');
            delEnd(true);
          }
        });
      });
    });

  };

  //监听工具条，我的发帖
  table.on('tool(myPublistListFilter)', function(obj){
      var data = obj.data;
      if(obj.event === 'detail'){
          //location.href = "/posts/"+data.id +"/detail";
          window.open("/posts/"+data.id +"/detail");
      } else if(obj.event === 'del'){
          layer.confirm('确认删除该求解么？', function(index){
              fly.json('/posts/' + data.id +'/delete', {
                  id: ""
              }, function(res){
                  if(res.code === 0){
                      table.reload('myPublistList', {})
                  } else {
                      layer.msg(res.msg);
                  }
              });
              layer.close(index);
          });
      } else if(obj.event === 'edit'){
          window.open("/posts/"+data.id +"/update");
          //location.href = "/posts/"+data.id +"/update";
      }
  });

  dom.minemsg[0] && gather.minemsg();

    //已购产品
    table.render({
        elem: '#LAY_productList'
        ,url: '/product/mine/'
        ,method: 'post'
        ,where: function(){
            var where = {}
                ,alias = layui.data.alias;
            if(alias) where.alias = alias;
            return where;
        }()
        ,cols: [[
            {field: 'name',title: '产品名称', minWidth: 300, templet: function(d){
                var href = d.name || ('/product/'+ d.productId+'/download');
                return '<a href="'+ href +'" target="_blank" class="layui-table-link">'+ d.name + '</a>'
            }}
            ,{field: 'expiryTime', title: '授权有效期', width: 120, templet: function(d){
                return d.expiryTime ? d.expiryTime : '永久';
            }}
            ,{field: 'price', title: '付费金额', width: 120, templet: function(d){
                return '<span style="color: #FF5722;">￥'+ (d.price || 0) +'</span>';
            }}
            ,{title: '操作', width: 100, templet: function(d){
                if(false){
                    return '<a href="/order/bill?itemid='+ d.productId +'" style="color: #FF5722;" target="_blank">重新授权</a>';
                } else {
                    return '<a href="/product/'+ d.productId +'/download" class="layui-table-link" target="_blank">下载</a>';
                }
            }}
        ]]
        ,page: true
        ,text: {
            none: function(){
                var where = {}
                    ,alias = layui.data.alias;
                return alias ? '未查询到你的 '+ alias +' 授权记录' : '您还没有任何产品授权'
            }()
        }
        ,done: function(res, curr){
            //无数据
            if(res.data.length === 0 && curr == 1){

            }
        }
    });


    //我发布的组件
    table.render({
        elem: '#LAY_mySendExtend'
        ,url: '/extend/mine/'
        ,method: 'post'
        ,cols: [[
            {field: 'title', title: '组件', minWidth: 200, templet: '<div><a href="/extend/{{ d.alias }}/" target="_blank" class="layui-table-link">{{ d.title }}</a></div>'}
            ,{field: 'alias', title: '组件模块名', align: 'center', width: 120}
            ,{field: 'status', title: '状态', width: 100, align: 'center', templet: function(d){
                if(d.status == 1){
                    return '<span style="color: #FF5722;">加精</span>';
                } else if(d.status == -1){
                    return '<span style="color: #ccc;">审核中</span>';
                } else {
                    return '<span style="color: #999;">正常</span>'
                }
            }}
            ,{field: 'create_time', title: '发布时间', width: 120, align: 'center', templet: '<div>{{ layui.util.timeAgo(d.create_time, 1) }}</div>'}
            ,{title: '操作', width: 100, align: 'center', templet: function(d){
                return !d.lock ? '<a class="layui-btn layui-btn-xs" href="/extend/release/'+ d.alias +'/" target="_blank">编辑</a>' : ''
            }}
        ]]
        ,page: true
        ,text: {
            none: '您还没有贡献过组件（<a href="/extend/" target="_blank" class="fly-link">前往发布</a>）'
        }
        //,skin: 'line'
    });

  exports('user', null);
  
});