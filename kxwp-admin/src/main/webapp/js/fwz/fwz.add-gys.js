/*添加供应商js*/
var GysMenu = (function () {
    var gysmenu = {
    	/*初始化页面*/
        init:function(){
        	var self=this;
            // 省市区级联
            KXWP.addressSelect("#j_province", '#j_city', '#j_county' , '#j_town');
        },
    }
    return gysmenu;
})();
GysMenu.init();//初始化页面





/*GysMenu.init();//初始化页面
*/

