(function(global){
	global.shopCar = {
		initEvent : function(){
			var view = this;
			view.$el.onclick = function(e){
				var e = e || event;
				var target = e.target || e.srcElement;
				if(target.parentNode.className !== 'add_remove') return;//使用事件委托必须判断一下，如果不是我们要的元素return 
				var numberDom = target.parentNode.children[1];//数量dom
				var oneNumber = target.parentNode.children[1].value;
				oneNumber = Number(oneNumber);//转换为数字
				var onePrice = target.parentNode.previousElementSibling.innerText
				 || target.parentNode.previousSibling.innerText;//.innerText;//单价
				var xj = target.parentNode.nextElementSibling
				 || target.parentNode.nextSibling;//小计
				//减的时候
				if(target.innerHTML === '-'){
					if(isNaN(oneNumber) || oneNumber == 0) {
						return false;
					}
					oneNumber--;
					numberDom.value = oneNumber;
					xj.innerText = oneNumber * onePrice;
					//重新渲染底部数据
					view.renderTotal();
				}else if(target.innerHTML === '+'){//加的时候
					oneNumber++;
					numberDom.value = oneNumber;
					xj.innerText = oneNumber * onePrice;
					//重新渲染底部数据
					view.renderTotal();
				}
			}
		},
		//全选单选函数
		checked : function(){
			var view = this;
			//全选
			view.$all_checked.onclick = function(){
				for(var i=0;i<view.$checkbox.length;i++){
					view.$checkbox[i].checked = this.checked;
				}
				view.renderTotal();
			}
			//单选
			for(var i=0;i<view.$checkbox.length;i++){
				view.$checkbox[i].onclick = function(){
					var count = 0;
					for(var i=0;i<view.$checkbox.length;i++){
						if(view.$checkbox[i].checked){
							count++;
						}
					}
					view.$all_checked.checked = (count === view.$checkbox.length);
					view.renderTotal();
				}
			}
		},
		//渲染底部
		renderTotal : function(){
			var view = this;
			var total_num = 0;
			var total_price = 0;
			var goods_item = view.$el.children;
			var goodNum = [];
			var goodPrice = [];
			for(var i=0;i<goods_item.length;i++){
				if(goods_item[i].getElementsByTagName('input')[0].checked === true){
					if(goods_item[i].getElementsByTagName('input')[1].value > 0){
						goodNum.push(goods_item[i].getElementsByTagName('input')[1].value);
					}
					if(goods_item[i].getElementsByClassName('xj')[0].innerHTML){
						goodPrice.push(goods_item[i].getElementsByClassName('xj')[0].innerHTML);
					}
				}
			}
			for(var i=0;i<goodNum.length;i++){
				total_num += Number(goodNum[i]);
			}
			for(var i=0;i<goodPrice.length;i++){
				total_price += Number(goodPrice[i]);
			}
			view.$allNum.innerHTML = total_num;
			view.$allPrice.innerHTML = total_price;
		},
		//初始化页面函数
		init : function(){
			var view = this;
			view.$el = document.getElementsByClassName('goods_list')[0];//找到父级元素进行事件委托自定义属性
			view.$allNum = document.getElementsByClassName('allNum')[0];//总计多少件
			view.$allPrice = document.getElementsByClassName('allPrice')[0];//总计多少件
			view.$all_checked = document.getElementById('all_checked');//全选按钮
			view.$checkbox = view.$el.getElementsByClassName('checkedOne');//全部的checkbox框
			view.initEvent();//调用加减函数
			view.renderTotal();//调用渲染底部数据函数
			view.checked();//调用全选函数	
		}
	}
	global.init = shopCar.init;
})(window)


shopCar.init();


