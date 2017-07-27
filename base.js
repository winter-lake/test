// 编程实现indexOf方法，找到返回下标，找不到返回-1，兼容IE低版本
// 它接收三个参数，数组，要找的元素，起始位置（起始位置不传从0开始）
function arrIndexOf(arr, val, index) {
	var i = typeof(index) === 'undefined' ? 0 : index;
	var len = arr.length;
	for(; i < len; i++) {
		if(arr[i] === val) {
			return i;
		}
	}
	return -1;
}

// 随机数，接收两个参数，最小值，最大值。比如getRandom(2, 6)，返回2到6之间的数，包括2和6
function getRandom(min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
}

// 参数：（数组， 要查找的值）
// 返回这个值在这个数组中出现了多少次
function getEle(arr, ele) {
	var num = 0;
	for(var i = 0; i < arr.length; i++) {
		if(ele === arr[i]) {
			num++;
		}
	}
	return num;
}

// 获取元素到页面的距离，getPos(oDiv3).left代表到左边的距离，getPos(oDiv3).top代表到顶部的距离
function getPos(obj){
	var pos = {left: 0, top: 0};
	while(obj){
		pos.left += obj.offsetLeft;
		pos.top += obj.offsetTop;
		obj = obj.offsetParent;
	}
	return pos;
}

// 看obj是否有classname这个class名，没有返回-1，有返回012下标
function hasClass(obj, classname) {
	// 如果这个元素没有class，直接返回
	if(!obj.className) {
		return -1;
	}
	// 如果这个元素有class，则取得所有的class，拆分成数组，遍历，同classname比较，有同名的，则返回下标，否则返回-1
	var classArray = obj.className.split(' ');
	for(var i = 0, len = classArray.length; i < len; i++) {
		if(classArray[i] === classname) {
			return i;
		}
	}
	return -1;
}

// 给某个元素添加class
function addClass(obj, classname) {
	if(!obj.className) {
		// 元素没有class名，则直接添加
		obj.className = classname;
	} else {
		// 元素有class名，则分两种情况，看有没有给定的classname，如果有，则啥也不干，没有才添加
		if(hasClass(obj, classname) === -1) {
			obj.className += ' ' + classname;
		}
	}
}

// 删除某个元素上的class
function removeClass(obj, classname) {
	var index = hasClass(obj, classname);
	if(index !== -1) {
		// 得有这个classname，才删除
		var arr = obj.className.split(' ');
		arr.splice(index, 1);
		obj.className = arr.join(' ');
	}
}

// 通过classname获取元素，返回元素集合
function getElementsByClassName(parent, classname) {
	var arr = [];
	var elems = parent.getElementsByTagName("*");
	for(var i = 0, len = elems.length; i < len; i++) {
		if(hasClass(elems[i], classname) !== -1) {
			arr.push(elems[i]);
		}
	}
	return arr;
}

//获取ID的方法		如：var oW=id("welcome");
function id(obj) {
	return document.getElementById(obj);
}

// 阻止浏览器的默认行为，接收一个事件对象作为参数
function preventDefault(ev){
	if(ev.preventDefault){
		ev.preventDefault();
	}else{
		ev.returnValue = false;
	}
}

// 阻止冒泡
function stopPropagation(ev){
	if(ev.stopPropagation){
		// 标准浏览器
		ev.stopPropagation();
	}else{
		// 非标准浏览器
		ev.cancelBubble = true;
	}
}


// 鼠标滚轮封装，上：120，下：-120
function getWheelDelta(ev){
	if (ev.wheelDelta) {
		// ie/chrome
		return ev.wheelDelta;
	} else{
		// 火狐
		return -40 * ev.detail;
	}
}

// 事件绑定封装
function bind(obj, ev, fn) {
	if(obj.addEventListener) {
		obj.addEventListener(ev, fn, false);
	} else {
		obj.attachEvent('on' + ev, function() {
			fn.call(obj);
		});
	}
}


function extend(obj1, obj2){
	for(var attr in obj2){
		obj1[attr] = obj2[attr];
	}
}

// 一维数组转二维
Array.prototype.toTwo = function (n){
	var newArr = [].concat(this);
	var newArr1 = [];
	var len = Math.ceil(newArr.length / n);
	for (var i=0; i<len; i++) {
		newArr1.push(newArr.splice(0, n));
	}
	return newArr1;
}

// 参数为：打开方式(get/post)，url地址，传给后端的数据，请求成功的回调
function ajax(method, url, data, success){
	
	// 第一步：创建ajax对象
	var xhr = null;
	if(window.XMLHttpRequest){
		xhr = new XMLHttpRequest();
	}else{
		xhr = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	// 如果是get方式，并且也有数据。数据跟在url?的后面
	if(method === 'get' && data){
		url += '?' + data;
	}
	
	// 第二步：打开方式，地址，是否异步
	xhr.open(method, url, true);
	
	// 第三步：发送。如果是get，直接发送。否则还要告诉后端发的数据类型，数据要做为参数放在send方法中
	if(method === 'get'){
		xhr.send();
	}else{
		xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
		xhr.send(data);
	}
	
	// 第四步：数据的获取
	xhr.onreadystatechange = function (){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				success && success(xhr.responseText);
			}else{
				alert('出错了,Err：' + xhr.status);
			}
		}
	}
	
}
