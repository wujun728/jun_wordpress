/**

 @Name: 产品结算

 */

layui.define(['fly', 'element'], function(exports){

    var $ = layui.jquery;
    var layer = layui.layer;
    var util = layui.util;
    var laytpl = layui.laytpl;
    var form = layui.form;
    var laypage = layui.laypage;
    var fly = layui.fly;

    var element = layui.element;
    var upload = layui.upload;

    var flow = layui.flow;
    var table = layui.table;

    var THIS = 'layui-this';


    //监听订单提交
    form.on('submit(orderPay)', function(obj){
        var field = obj.field;
        if($('#FLY-bill-agreement')[0] && !field.agreement){
            layer.msg('您必须同意产品服务协议，才可授权');
            return false;
        }
    });

    //查看协议
    $('#FLY-bill-agreement').on('click', function(){
        layer.open({
            type: 1
            ,title: 'layui 付费产品服务协议（LPPL）'
            ,area: ['310px', '450px']
            ,content: ['<div class="layui-text" style="padding: 5px 15px; font-size: 12px;">'
                ,'<ul>'
                ,'<li>1. 授权 jeeweb 官方任意周边付费产品均可获得我们的 VIP 认证，用户可通过Fly社区的授权帐号下载到产品。</li>'
                ,'<li>2. 授权者可将授权后的产品用于任意符合国家法律法规的应用平台，并且不受域名和项目数量限制。</li>'
                ,'<li>3. 授权者如果使用 jeeweb 付费产品开发的商业项目（如ERP、CMS等），需保留授权产品的源码头注释和出处。</li>'
                ,'<li>4. 授权者务必尊重知识产权，严格保证不恶意传播产品源码、不得直接对授权的产品本身进行二次转售或倒卖、不得对授权的产品进行简单包装后声称为自己的产品等。否则我们有权利收回产品授权，并根据事态轻重追究相应法律责任。</li>'
                ,'<li>5. 授权 jeeweb 官方的源码类付费产品，不支持退款。</li>'
                ,'<li>6. 我们有义务为授权者提供有效期内的产品下载、更新和维护，一旦过期，授权者无法享有相应权限。终身授权则不受限制。</li>'
                ,'<li>7. jeeweb.cn 拥有最终解释权</li>'
                ,'</ul>'
                ,'</div>'].join('')
            ,shadeClose: true
        });
    });


    var active = {
        change: function(othis){
            var goods_id = [];
            if(othis.hasClass(THIS)){
                if(othis.data('required')){
                    return;
                } else {
                    othis.removeClass(THIS)
                }
            } else {
                othis.addClass(THIS).siblings().removeClass(THIS);
            }

            $('.fly-form-btn.'+ THIS +'[data-type="change"]').each(function(){
                goods_id.push($(this).data('id'));
            });

            fly.json('/product/getProduct', {
                version: goods_id[0], authorizationPeriod: goods_id[1]
            }, function(res){
                if(res.code === 0){
                    return location.href = '/product/order/'+ res.product.id
                }else{
                    layer.msg('未找到参评');
                }
                if(goods_id.length === 0){
                    $('#LAY_price').html('￥ 0');
                }
            }, {type: 'post'})
        }
    }


    $('.fly-form-btn').on('click', function(){
        var othis = $(this)
            ,type = othis.data('type');
        active[type] && active[type].call(this, othis);
    });

    exports('bill', {});

});