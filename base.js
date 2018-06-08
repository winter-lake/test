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

















































/*2.0.0*/
let ecDo = {
//***************字符串模块**************************/
    //去除空格  type 1-所有空格  2-前后空格  3-前空格 4-后空格
    //trim('  1235asd',1)
    //result：1235asd
    trim(str, type) {
        switch (type) {
            case 1:
                return str.replace(/\s+/g, "");
            case 2:
                return str.replace(/(^\s*)|(\s*$)/g, "");
            case 3:
                return str.replace(/(^\s*)/g, "");
            case 4:
                return str.replace(/(\s*$)/g, "");
            default:
                return str;
        }
    },
    /*type
     1:首字母大写
     2：首字母小写
     3：大小写转换
     4：全部大写
     5：全部小写
     * */
    //changeCase('asdasd',1)
    //result：Asdasd
    changeCase(str, type) {
        function ToggleCase(str) {
            let itemText = ""
            str.split("").forEach(item => {
                if (/^([a-z]+)/.test(item)) {
                    itemText += item.toUpperCase();
                } else if (/^([A-Z]+)/.test(item)) {
                    itemText += item.toLowerCase();
                } else {
                    itemText += item;
                }
            });
            return itemText;
        }

        switch (type) {
            case 1:
                return str.replace(/\b\w+\b/g, function (word) {
                    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();

                });
            case 2:
                return str.replace(/\b\w+\b/g, function (word) {
                    return word.substring(0, 1).toLowerCase() + word.substring(1).toUpperCase();
                });
            case 3:
                return ToggleCase(str);
            case 4:
                return str.toUpperCase();
            case 5:
                return str.toLowerCase();
            default:
                return str;
        }
    },
    //字符串循环复制
    //repeatStr(str->字符串, count->次数)
    //repeatStr('123',3)
    //"result：123123123"
    repeatStr(str, count) {
        return str.repeat(count);
    },
    //字符串替换(字符串,要替换的字符或者正则表达式（不要写g）,替换成什么)
    //ecDo.replaceAll('这里是上海，中国第三大城市，广东省省会，简称穗，','上海','广州')
    //result："这里是广州，中国第三大城市，广东省省会，简称穗，"
    replaceAll(str, AFindText, ARepText) {
        let raRegExp = new RegExp(AFindText, "g");
        return str.replace(raRegExp, ARepText);
    },
    //字符替换*
    //replaceStr(字符串,字符格式, 替换方式,替换的字符（默认*）)
    replaceStr(str, regArr, type = 0, ARepText = '*') {
        let regtext = '',
            Reg = null,
            replaceText = ARepText;
        //replaceStr('18819322663',[3,5,3],0)
        //result：188*****663
        //repeatStr是在上面定义过的（字符串循环复制），大家注意哦
        if (regArr.length === 3 && type === 0) {
            regtext = '(\\w{' + regArr[0] + '})\\w{' + regArr[1] + '}(\\w{' + regArr[2] + '})'
            Reg = new RegExp(regtext);
            let replaceCount = this.repeatStr(replaceText, regArr[1]);
            return str.replace(Reg, '$1' + replaceCount + '$2')
        }
        //replaceStr('asdasdasdaa',[3,5,3],1)
        //result：***asdas***
        else if (regArr.length === 3 && type === 1) {
            regtext = '\\w{' + regArr[0] + '}(\\w{' + regArr[1] + '})\\w{' + regArr[2] + '}'
            Reg = new RegExp(regtext);
            let replaceCount1 = this.repeatStr(replaceText, regArr[0]);
            let replaceCount2 = this.repeatStr(replaceText, regArr[2]);
            return str.replace(Reg, replaceCount1 + '$1' + replaceCount2)
        }
        //replaceStr('1asd88465asdwqe3',[5],0)
        //result：*****8465asdwqe3
        else if (regArr.length === 1 && type === 0) {
            regtext = '(^\\w{' + regArr[0] + '})'
            Reg = new RegExp(regtext);
            let replaceCount = this.repeatStr(replaceText, regArr[0]);
            return str.replace(Reg, replaceCount)
        }
        //replaceStr('1asd88465asdwqe3',[5],1,'+')
        //result："1asd88465as+++++"
        else if (regArr.length === 1 && type === 1) {
            regtext = '(\\w{' + regArr[0] + '}$)'
            Reg = new RegExp(regtext);
            let replaceCount = this.repeatStr(replaceText, regArr[0]);
            return str.replace(Reg, replaceCount)
        }
    },
    //检测字符串
    //checkType('165226226326','phone')
    //result：false
    //大家可以根据需要扩展
    checkType(str, type) {
        switch (type) {
            case 'email':
                return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(str);
            case 'phone':
                return /^1[3|4|5|7|8][0-9]{9}$/.test(str);
            case 'tel':
                return /^(0\d{2,3}-\d{7,8})(-\d{1,4})?$/.test(str);
            case 'number':
                return /^[0-9]$/.test(str);
            case 'english':
                return /^[a-zA-Z]+$/.test(str);
            case 'text':
                return /^\w+$/.test(str);
            case 'chinese':
                return /^[\u4E00-\u9FA5]+$/.test(str);
            case 'lower':
                return /^[a-z]+$/.test(str);
            case 'upper':
                return /^[A-Z]+$/.test(str);
            default:
                return true;
        }
    },
    //检测密码强度
    //checkPwd('12asdASAD')
    //result：3(强度等级为3)
    checkPwd(str) {
        let nowLv = 0;
        if (str.length < 6) {
            return nowLv
        }
        if (/[0-9]/.test(str)) {
            nowLv++
        }
        if (/[a-z]/.test(str)) {
            nowLv++
        }
        if (/[A-Z]/.test(str)) {
            nowLv++
        }
        if (/[\.|-|_]/.test(str)) {
            nowLv++
        }
        return nowLv;
    },
    //随机码
    //count取值范围2-36

    //randomWord(10)
    //result："2584316588472575"

    //randomWord(14)
    //result："9b405070dd00122640c192caab84537"

    //randomWord(36)
    //result："83vhdx10rmjkyb9"

    randomWord(count) {
        return Math.random().toString(count).substring(2);
    },

    //查找字符串
    //let strTest='sad44654blog5a1sd67as9dablog4s5d16zxc4sdweasjkblogwqepaskdkblogahseiuadbhjcibloguyeajzxkcabloguyiwezxc967'
    //countStr(strTest,'blog')
    //result：6
    countStr(str, strSplit) {
        return str.split(strSplit).length - 1
    },
    //过滤字符串(html标签，表情，特殊字符)
    //字符串，替换内容（special-特殊字符,html-html标签,emjoy-emjoy表情,word-小写字母，WORD-大写字母，number-数字,chinese-中文），要替换成什么，默认'',保留哪些特殊字符
    //如果需要过滤多种字符，type参数使用,分割，如下栗子
    //过滤字符串的html标签，大写字母，中文，特殊字符，全部替换成*,但是特殊字符'%'，'?'，除了这两个，其他特殊字符全部清除
    //let str='asd    654a大蠢sasdasdASDQWEXZC6d5#%^*^&*^%^&*$\\"\'#@!()*/-())_\'":"{}?<div></div><img src=""/>啊实打实大蠢猪自行车这些课程';
    // ecDo.filterStr(str,'html,WORD,chinese,special','*','%?')
    //result："asd    654a**sasdasd*********6d5#%^*^&*^%^&*$\"'#@!()*/-())_'":"{}?*****************"
    filterStr(str, type, restr = '', spstr) {
        let typeArr = type.split(','), _str = str;
        for (let i = 0, len = typeArr.length; i < len; i++) {
            //是否是过滤特殊符号
            let pattern;
            if (typeArr[i] === 'special') {
                let regText = '$()[]{}?\|^*+./\"\'+';
                //是否有哪些特殊符号需要保留
                if (spstr) {
                    let _spstr = spstr.split(""), _regText = "[^0-9A-Za-z\\s";
                    for (let j = 0, len1 = _spstr.length; j < len1; j++) {
                        if (regText.indexOf(_spstr[j]) === -1) {
                            _regText += _spstr[j];
                        }
                        else {
                            _regText += '\\' + _spstr[j];
                        }
                    }
                    _regText += ']'
                    pattern = new RegExp(_regText, 'g');
                }
                else {
                    pattern = new RegExp("[^0-9A-Za-z\\s]", 'g')
                }

            }
            switch (typeArr[i]) {
                case 'special':
                    _str = _str.replace(pattern, restr);
                    break;
                case 'html':
                    _str = _str.replace(/<\/?[^>]*>/g, restr);
                    break;
                case 'emjoy':
                    _str = _str.replace(/[^\u4e00-\u9fa5|\u0000-\u00ff|\u3002|\uFF1F|\uFF01|\uff0c|\u3001|\uff1b|\uff1a|\u3008-\u300f|\u2018|\u2019|\u201c|\u201d|\uff08|\uff09|\u2014|\u2026|\u2013|\uff0e]/g, restr);
                    break;
                case 'word':
                    _str = _str.replace(/[a-z]/g, restr);
                    break;
                case 'WORD':
                    _str = _str.replace(/[A-Z]/g, restr);
                    break;
                case 'number':
                    _str = _str.replace(/[0-9]/g, restr);
                    break;
                case 'chinese':
                    _str = _str.replace(/[\u4E00-\u9FA5]/g, restr);
                    break;
            }
        }
        return _str;
    },
    //格式化处理字符串
    //ecDo.formatText('1234asda567asd890')
    //result："12,34a,sda,567,asd,890"
    //ecDo.formatText('1234asda567asd890',4,' ')
    //result："1 234a sda5 67as d890"
    //ecDo.formatText('1234asda567asd890',4,'-')
    //result："1-234a-sda5-67as-d890"
    formatText(str, size = 3, delimiter = ',') {
        let regText = '\\B(?=(\\w{' + size + '})+(?!\\w))';
        let reg = new RegExp(regText, 'g');
        return str.replace(reg, delimiter);
    },
    //找出最长单词 (Find the Longest word in a String)
    //longestWord('Find the Longest word in a String')
    //result：7
    //longestWord('Find|the|Longest|word|in|a|String','|')
    //result：7
    longestWord(str, splitType = /\s+/g) {
        let _max = 0, _item = '';
        let strArr = str.split(splitType);
        strArr.forEach(item => {
            if (_max < item.length) {
                _max = item.length;
                _item = item;
            }
        });
        return {el: _item, max: _max};
    },
    //句中单词首字母大写 (Title Case a Sentence)
    //这个我也一直在纠结，英文标题，即使是首字母大写，也未必每一个单词的首字母都是大写的，但是又不知道哪些应该大写，哪些不应该大写
    //ecDo.titleCaseUp('this is a title')
    //"This Is A Title"
    titleCaseUp(str, splitType = /\s+/g) {
        let strArr = str.split(splitType),
            result = "";
        strArr.forEach(item => {
            result += this.changeCase(item, 1) + ' ';
        });
        return this.trim(result, 4)
    },
//***************字符串模块End**************************/
//***************数组模块**************************/
    //数组去重
    removeRepeatArray(arr) {
        // return arr.filter(function (item, index, self) {
        //     return self.indexOf(item) === index;
        // });
        //es6
        return [...new Set(arr)]
    },
    //数组顺序打乱
    upsetArr(arr) {
        return arr.sort(() => {
            return Math.random() - 0.5
        });
    },

    //数组最大值
    //这一块的封装，主要是针对数字类型的数组
    maxArr(arr) {
        return Math.max.apply(null, arr);
    },
    //数组最小值
    minArr(arr) {
        return Math.min.apply(null, arr);
    },

    //这一块的封装，主要是针对数字类型的数组
    //数组求和
    sumArr(arr) {
        return arr.reduce((pre, cur) =>pre + cur)
    },

    //数组平均值,小数点可能会有很多位，这里不做处理，处理了使用就不灵活了！
    covArr(arr) {
        return this.sumArr(arr) / arr.length;
    },
    //从数组中随机获取元素
    randomOne(arr) {
        return arr[Math.floor(Math.random() * arr.length)];
    },

    //回数组（字符串）一个元素出现的次数
    //getEleCount('asd56+asdasdwqe','a')
    //result：3
    //getEleCount([1,2,3,4,5,66,77,22,55,22],22)
    //result：2
    getEleCount(obj, ele) {
        let num = 0;
        for (let i = 0, len = obj.length; i < len; i++) {
            if (ele === obj[i]) {
                num++;
            }
        }
        return num;
    },

    //返回数组（字符串）出现最多的几次元素和出现次数
    //arr, rank->长度，默认为数组长度，ranktype，排序方式，默认降序
    //返回值：el->元素，count->次数
    //getCount([1,2,3,1,2,5,2,4,1,2,6,2,1,3,2])
    //result：[{"el":"2","count":6},{"el":"1","count":4},{"el":"3","count":2},{"el":"4","count":1},{"el":"5","count":1},{"el":"6","count":1}]
    //默认情况，返回所有元素出现的次数
    //getCount([1,2,3,1,2,5,2,4,1,2,6,2,1,3,2],3)
    //传参（rank=3），只返回出现次数排序前三的
    //result：[{"el":"2","count":6},{"el":"1","count":4},{"el":"3","count":2}]
    //getCount([1,2,3,1,2,5,2,4,1,2,6,2,1,3,2],null,1)
    //传参（ranktype=1,rank=null），升序返回所有元素出现次数
    //result：[{"el":"6","count":1},{"el":"5","count":1},{"el":"4","count":1},{"el":"3","count":2},{"el":"1","count":4},{"el":"2","count":6}]
    //getCount([1,2,3,1,2,5,2,4,1,2,6,2,1,3,2],3,1)
    //传参（rank=3，ranktype=1），只返回出现次数排序（升序）前三的
    //result：[{"el":"6","count":1},{"el":"5","count":1},{"el":"4","count":1}]
    getCount(arr, rank, ranktype) {
        let obj = {}, k, arr1 = []
        //记录每一元素出现的次数
        for (let i = 0, len = arr.length; i < len; i++) {
            k = arr[i];
            if (obj[k]) {
                obj[k]++;
            } else {
                obj[k] = 1;
            }
        }
        //保存结果{el-'元素'，count-出现次数}
        for (let o in obj) {
            arr1.push({el: o, count: obj[o]});
        }
        //排序（降序）
        arr1.sort(function (n1, n2) {
            return n2.count - n1.count
        });
        //如果ranktype为1，则为升序，反转数组
        if (ranktype === 1) {
            arr1 = arr1.reverse();
        }
        let rank1 = rank || arr1.length;
        return arr1.slice(0, rank1);
    },

    //得到n1-n2下标的数组
    //getArrayNum([0,1,2,3,4,5,6,7,8,9],5,9)
    //result：[5, 6, 7, 8, 9]
    //getArrayNum([0,1,2,3,4,5,6,7,8,9],2) 不传第二个参数,默认返回从n1到数组结束的元素
    //result：[2, 3, 4, 5, 6, 7, 8, 9]
    getArrayNum(arr, n1, n2) {
        return arr.slice(n1, n2);
    },

    //筛选数组
    //删除值为'val'的数组元素
    //removeArrayForValue(['test','test1','test2','test','aaa'],'test','%')
    //result：["aaa"]   带有'test'的都删除
    //removeArrayForValue(['test','test1','test2','test','aaa'],'test')
    //result：["test1", "test2", "aaa"]  //数组元素的值全等于'test'才被删除
    removeArrayForValue(arr, val, type) {
        return arr.filter(item=>type ? item.indexOf(val) === -1 : item !== val)
    },
    //获取对象数组某些项
    //let arr=[{a:1,b:2,c:9},{a:2,b:3,c:5},{a:5,b:9},{a:4,b:2,c:5},{a:4,b:5,c:7}]
    //getOptionArray(arr,'a,c')
    //result：[{a:1,c:9},{a:2,c:5},{a:5,c:underfind},{a:4,c:5},{a:4,c:7}]
    //getOptionArray(arr,'b',1)
    //result：[2, 3, 9, 2, 5]
    getOptionArray(arr, keys) {
        let newArr = [];
        if (!keys) {
            return arr
        }
        let _keys = keys.split(','), newArrOne = {};
        //是否只是需要获取某一项的值
        if (_keys.length === 1) {
            for (let i = 0, len = arr.length; i < len; i++) {
                newArr.push(arr[i][keys])
            }
            return newArr;
        }
        for (let i = 0, len = arr.length; i < len; i++) {
            newArrOne = {};
            for (let j = 0, len1 = _keys.length; j < len1; j++) {
                newArrOne[_keys[j]] = arr[i][_keys[j]]
            }
            newArr.push(newArrOne);
        }
        return newArr
    },
    //排除数组某些项
    //let arr=[{a:1,b:2,c:9},{a:2,b:3,c:5},{a:5,b:9},{a:4,b:2,c:5},{a:4,b:5,c:7}]
    //filterOptionArray(arr,'a')
    //result：[{b:2,c:9},{b:3,c:5},{b:9},{b:2,c:5},{b:5,c:7}]
    //filterOptionArray(arr,'a,c')
    //result：[{b:2},{b:3},{b:9},{b:2},{b:5}]
    filterOptionArray(arr, keys) {
        let newArr = [];
        let _keys = keys.split(','), newArrOne = {};
        for (let i = 0, len = arr.length; i < len; i++) {
            newArrOne = {};
            for (let key in arr[i]) {
                //如果key不存在排除keys里面,添加数据
                if (_keys.indexOf(key) === -1) {
                    newArrOne[key] = arr[i][key];
                }
            }
            newArr.push(newArrOne);
        }
        return newArr
    },
    //对象数组的排序
    //let arr=[{a:1,b:2,c:9},{a:2,b:3,c:5},{a:5,b:9},{a:4,b:2,c:5},{a:4,b:5,c:7}]
    //ecDo.arraySort(arr,'a,b')a是第一排序条件，b是第二排序条件
    //result：[{"a":1,"b":2,"c":9},{"a":2,"b":3,"c":5},{"a":4,"b":2,"c":5},{"a":4,"b":5,"c":7},{"a":5,"b":9}]
    arraySort(arr, sortText) {
        if (!sortText) {
            return arr
        }
        let _sortText = sortText.split(',').reverse(), _arr = arr.slice(0);
        for (let i = 0, len = _sortText.length; i < len; i++) {
            _arr.sort((n1, n2) => {
                return n1[_sortText[i]] - n2[_sortText[i]]
            })
        }
        return _arr;
    },
    //数组扁平化
    steamroller(arr) {
        let newArr = [], _this = this;
        for (let i = 0; i < arr.length; i++) {
            if (Array.isArray(arr[i])) {
                // 如果是数组，调用(递归)steamroller 将其扁平化
                // 然后再 push 到 newArr 中
                newArr.push.apply(newArr, _this.steamroller(arr[i]));
            } else {
                // 不是数组直接 push 到 newArr 中
                newArr.push(arr[i]);
            }
        }
        return newArr;
    },
    //另一种写法
    //steamroller([1,2,[4,5,[1,23]]])
    //[1, 2, 4, 5, 1, 23]
    /*
     * i=0 newArr.push(arr[i])  [1]
     * i=1 newArr.push(arr[i])  [1,2]
     * i=2 newArr = newArr.concat(steamroller(arr[i]));  执行到下面
     * 第一次i=2进入后 i=0, newArr.push(arr[i]);  [4]
     * 第一次i=2进入后 i=1, newArr.push(arr[i]);  [4，5]
     *  * i=2 newArr = newArr.concat(steamroller(arr[i]));  执行到下面
     * 第二次i=2进入后 i=0, newArr.push(arr[i]);  [1]
     * 第二次i=2进入后 i=1, newArr.push(arr[i]);  [1，23]  执行到下面
     * 第二次循环完，回到第一次进入后  newArr = newArr.concat(steamroller(arr[i]));  [4,5].concat([1,23])   [4,5,1,23]
     * 然后回到第一次   [1,2].concat([4,5,1,23])
     */
    //  steamroller(arr) {
    //      let newArr = [];
    //      for (let i = 0; i < arr.length; i++) {
    //          if (Array.isArray(arr[i])) {
    //              // 如果是数组，调用(递归)steamroller 将其扁平化
    //              // 然后再 push 到 newArr 中
    //              newArr = newArr.concat(steamroller(arr[i]));
    //          } else {
    //              // 不是数组直接 push 到 newArr 中
    //              newArr.push(arr[i]);
    //          }
    //      }
    //      return newArr;
    //  },
//***************数组模块END**************************/

//***************对象及其他模块**************************/
    //适配rem
    getFontSize(_client) {
        let doc = document,
            win = window;
        let docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                let clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                //如果屏幕大于750（750是根据我效果图设置的，具体数值参考效果图），就设置clientWidth=750，防止font-size会超过100px
                if (clientWidth > _client) {
                    clientWidth = _client
                }
                //设置根元素font-size大小
                docEl.style.fontSize = 100 * (clientWidth / _client) + 'px';
            };
        //屏幕大小改变，或者横竖屏切换时，触发函数
        win.addEventListener(resizeEvt, recalc, false);
        //文档加载完成时，触发函数
        doc.addEventListener('DOMContentLoaded', recalc, false);
    },
    //到某一个时间的倒计时
    //getEndTime('2017/7/22 16:0:0')
    //result："剩余时间6天 2小时 28 分钟20 秒"
    getEndTime(endTime) {
        let startDate = new Date(); //开始时间，当前时间
        let endDate = new Date(endTime); //结束时间，需传入时间参数
        let t = endDate.getTime() - startDate.getTime(); //时间差的毫秒数
        let d = 0,
            h = 0,
            m = 0,
            s = 0;
        if (t >= 0) {
            d = Math.floor(t / 1000 / 3600 / 24);
            h = Math.floor(t / 1000 / 60 / 60 % 24);
            m = Math.floor(t / 1000 / 60 % 60);
            s = Math.floor(t / 1000 % 60);
        }
        return `剩余时间${d}天${h}小时${m}分钟${s}秒"`;
    },
    //随进产生颜色
    randomColor() {
        //randomNumber是下面定义的函数
        //写法1
        //return 'rgb(' + this.randomNumber(255) + ',' + this.randomNumber(255) + ',' + this.randomNumber(255) + ')';

        //写法2
        return '#' + Math.random().toString(16).substring(2).substr(0, 6);

        //写法3
        //let color='#',_index=this.randomNumber(15);
        //for(let i=0;i<6;i++){
        //color+='0123456789abcdef'[_index];
        //}
        //return color;
    },
    //随机返回一个范围的数字
    randomNumber(n1, n2) {
        //randomNumber(5,10)
        //返回5-10的随机整数，包括5，10
        if (arguments.length === 2) {
            return Math.round(n1 + Math.random() * (n2 - n1));
        }
        //randomNumber(10)
        //返回0-10的随机整数，包括0，10
        else if (arguments.length === 1) {
            return Math.round(Math.random() * n1)
        }
        //randomNumber()
        //返回0-255的随机整数，包括0，255
        else {
            return Math.round(Math.random() * 255)
        }
    },
    //设置url参数
    //setUrlPrmt({'a':1,'b':2})
    //result：a=1&b=2
    setUrlPrmt(obj) {
        let _rs = [];
        for (let p in obj) {
            if (obj[p] != null && obj[p] != '') {
                _rs.push(p + '=' + obj[p])
            }
        }
        return _rs.join('&');
    },
    //获取url参数
    //getUrlPrmt('segmentfault.com/write?draftId=122000011938')
    //result：Object{draftId: "122000011938"}
    getUrlPrmt(url) {
        url = url ? url : window.location.href;
        let _pa = url.substring(url.indexOf('?') + 1),
            _arrS = _pa.split('&'),
            _rs = {};
        for (let i = 0, _len = _arrS.length; i < _len; i++) {
            let pos = _arrS[i].indexOf('=');
            if (pos == -1) {
                continue;
            }
            let name = _arrS[i].substring(0, pos),
                value = window.decodeURIComponent(_arrS[i].substring(pos + 1));
            _rs[name] = value;
        }
        return _rs;
    },

    //现金额大写转换函数
    //upDigit(168752632)
    //result："人民币壹亿陆仟捌佰柒拾伍万贰仟陆佰叁拾贰元整"
    //upDigit(1682)
    //result："人民币壹仟陆佰捌拾贰元整"
    //upDigit(-1693)
    //result："欠人民币壹仟陆佰玖拾叁元整"
    upDigit(n) {
        let fraction = ['角', '分', '厘'];
        let digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
        let unit = [
            ['元', '万', '亿'],
            ['', '拾', '佰', '仟']
        ];
        let head = n < 0 ? '欠人民币' : '人民币';
        n = Math.abs(n);
        let s = '';
        for (let i = 0; i < fraction.length; i++) {
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
        }
        s = s || '整';
        n = Math.floor(n);
        for (let i = 0; i < unit[0].length && n > 0; i++) {
            let p = '';
            for (let j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[n % 10] + unit[1][j] + p;
                n = Math.floor(n / 10);
            }
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
            //s = p + unit[0][i] + s;
        }
        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
    },
    //清除对象中值为空的属性
    //filterParams({a:"",b:null,c:"010",d:123})
    //Object {c: "010", d: 123}
    filterParams(obj) {
        let _newPar = {};
        for (let key in obj) {
            if ((obj[key] === 0 || obj[key] === false || obj[key]) && obj[key].toString().replace(/(^\s*)|(\s*$)/g, '') !== '') {
                _newPar[key] = obj[key];
            }
        }
        return _newPar;
    },
    //数据类型判断
    //ecDo.istype([],'array')
    //true
    //ecDo.istype([])
    //'[object Array]'
    istype(o, type) {
        switch (type.toLowerCase()) {
            case 'string':
                return Object.prototype.toString.call(o) === '[object String]';
            case 'number':
                return Object.prototype.toString.call(o) === '[object Number]';
            case 'boolean':
                return Object.prototype.toString.call(o) === '[object Boolean]';
            case 'undefined':
                return Object.prototype.toString.call(o) === '[object Undefined]';
            case 'null':
                return Object.prototype.toString.call(o) === '[object Null]';
            case 'function':
                return Object.prototype.toString.call(o) === '[object Function]';
            case 'array':
                return Object.prototype.toString.call(o) === '[object Array]';
            case 'object':
                return Object.prototype.toString.call(o) === '[object Object]';
            case 'nan':
                return isNaN(o);
            case 'elements':
                return Object.prototype.toString.call(o).indexOf('HTML') !== -1
            default:
                return Object.prototype.toString.call(o)
        }
    },

    //手机类型判断
    browserInfo(type) {
        switch (type) {
            case 'android':
                return navigator.userAgent.toLowerCase().indexOf('android') !== -1
            case 'iphone':
                return navigator.userAgent.toLowerCase().indexOf('iphone') !== -1
            case 'ipad':
                return navigator.userAgent.toLowerCase().indexOf('ipad') !== -1
            case 'weixin':
                return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1
            default:
                return navigator.userAgent.toLowerCase()
        }
    },
    //函数节流
    // let count=0;
    // function fn1(){
    //     count++;
    //     console.log(count)
    // }
    // //100ms内连续触发的调用，后一个调用会把前一个调用的等待处理掉，但每隔200ms至少执行一次
    // document.onmousemove=delayFn(fn1,100,200)
    delayFn(fn, delay, mustDelay) {
        let timer = null;
        let t_start;
        return function () {
            let context = this, args = arguments, t_cur = +new Date();
            //先清理上一次的调用触发（上一次调用触发事件不执行）
            clearTimeout(timer);
            //如果不存触发时间，那么当前的时间就是触发时间
            if (!t_start) {
                t_start = t_cur;
            }
            //如果当前时间-触发时间大于最大的间隔时间（mustDelay），触发一次函数运行函数
            if (t_cur - t_start >= mustDelay) {
                fn.apply(context, args);
                t_start = t_cur;
            }
            //否则延迟执行
            else {
                timer = setTimeout(() => {
                    fn.apply(context, args);
                }, delay);
            }
        };
    },
//***************对象及其他模块END**************************/
//***************cookie模块*******************************/
    //设置cookie
    setCookie(name, value, iDay) {
        let oDate = new Date();
        oDate.setDate(oDate.getDate() + iDay);
        document.cookie = name + '=' + value + ';expires=' + oDate;
    },
    //获取cookie
    getCookie(name) {
        let arr = document.cookie.split('; ');
        for (let i = 0; i < arr.length; i++) {
            let arr2 = arr[i].split('=');
            if (arr2[0] == name) {
                return arr2[1];
            }
        }
        return '';
    },
    //删除cookie
    removeCookie(name) {
        this.setCookie(name, 1, -1);
    },
//***************cookie模块END*******************************/
//***************DOM模块*******************************/
    //检测对象是否有哪个类名
    hasClass(obj, classStr) {
        if (obj.className && this.trim(obj.className, 1) !== "") {
            let arr = obj.className.split(/\s+/); //这个正则表达式是因为class可以有多个,判断是否包含
            return (arr.indexOf(classStr) === -1) ? false : true;
        }
        else {
            return false;
        }

    },
    //添加类名
    addClass(obj, classStr) {
        if ((this.istype(obj, 'array') || this.istype(obj, 'elements')) && obj.length >= 1) {
            for (let i = 0, len = obj.length; i < len; i++) {
                if (!this.hasClass(obj[i], classStr)) {
                    obj[i].className += " " + classStr;
                }
            }
        }
        else {
            if (!this.hasClass(obj, classStr)) {
                obj.className += " " + classStr;
            }
        }
    },
    //删除类名
    removeClass(obj, classStr) {
        if ((this.istype(obj, 'array') || this.istype(obj, 'elements')) && obj.length > 1) {
            for (let i = 0, len = obj.length; i < len; i++) {
                if (this.hasClass(obj[i], classStr)) {
                    let reg = new RegExp('(\\s|^)' + classStr + '(\\s|$)');
                    obj[i].className = obj[i].className.replace(reg, '');
                }
            }
        }
        else {
            if (this.hasClass(obj, classStr)) {
                let reg = new RegExp('(\\s|^)' + classStr + '(\\s|$)');
                obj.className = obj.className.replace(reg, '');
            }
        }
    },
    //替换类名("被替换的类名","替换的类名")
    replaceClass(obj, newName, oldName) {
        this.removeClass(obj, oldName);
        this.addClass(obj, newName);
    },
    //获取兄弟节点
    //ecDo.siblings(obj,'#id')
    siblings(obj, opt) {
        let a = []; //定义一个数组，用来存o的兄弟元素
        let p = obj.previousSibling;
        while (p) { //先取o的哥哥们 判断有没有上一个哥哥元素，如果有则往下执行 p表示previousSibling
            if (p.nodeType === 1) {
                a.push(p);
            }
            p = p.previousSibling //最后把上一个节点赋给p
        }
        a.reverse() //把顺序反转一下 这样元素的顺序就是按先后的了
        let n = obj.nextSibling; //再取o的弟弟
        while (n) { //判断有没有下一个弟弟结点 n是nextSibling的意思
            if (n.nodeType === 1) {
                a.push(n);
            }
            n = n.nextSibling;
        }
        if (opt) {
            let _opt = opt.substr(1);
            let b = [];//定义一个数组，用于储存过滤a的数组
            if (opt[0] === '.') {
                b = a.filter(item => item.className === _opt);
            }
            else if (opt[0] === '#') {
                b = a.filter(item => item.id === _opt);
            }
            else {
                b = a.filter(item => item.tagName.toLowerCase() === opt);
            }
            return b;
        }
        return a;
    },
    //设置样式
    css(obj, json) {
        for (let attr in json) {
            obj.style[attr] = json[attr];
        }
    },
    //设置HTML内容
    html(obj) {
        if (arguments.length === 1) {
            return obj.innerHTML;
        } else if (arguments.length === 2) {
            obj.innerHTML = arguments[1];
        }
    },
    //设置HTML内容
    text(obj) {
        if (arguments.length === 1) {
            return obj.innerHTML;
        } else if (arguments.length === 2) {
            obj.innerHTML = this.filterStr(arguments[1], 'html');
        }
    },
    //显示隐藏
    show(obj) {
        let blockArr = ['div', 'li', 'ul', 'ol', 'dl', 'table', 'article', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'hr', 'header', 'footer', 'details', 'summary', 'section', 'aside', '']
        if (blockArr.indexOf(obj.tagName.toLocaleLowerCase()) === -1) {
            obj.style.display = 'inline';
        }
        else {
            obj.style.display = 'block';
        }
    },
    hide(obj) {
        obj.style.display = "none";
    },
    /* 封装ajax函数
     * @param {string}obj.type http连接的方式，包括POST和GET两种方式
     * @param {string}obj.url 发送请求的url
     * @param {boolean}obj.async 是否为异步请求，true为异步的，false为同步的
     * @param {object}obj.data 发送的参数，格式为对象类型
     * @param {function}obj.success ajax发送并接收成功调用的回调函数
     * @param {function}obj.error ajax发送失败或者接收失败调用的回调函数
     */
    //  ajax({
    //  	type:'get',
    //  	url:'xxx',
    //  	data:{
    //  		id:'111'
    //  	},
    //  	success:function(res){
    //  		console.log(res)
    //  	}
    //  })
    ajax(obj){
        obj = Object.assign({
            type: 'POST',
            url: '',
            async: true,
            data: null,
            success() {},
            error() {}
        }, obj);
        obj.type = obj.type.toUpperCase();
        let xmlHttp = null;
        if (XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        } else {
            xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
        }
        let params = [];
        for (let key in obj.data) {
            params.push(key + '=' + obj.data[key]);
        }
        let postData = params.join('&');
        if (obj.type.toUpperCase() === 'POST') {
            xmlHttp.open(obj.type, obj.url, obj.async);
            xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8');
            xmlHttp.send(postData);
        } else if (obj.type.toUpperCase() === 'GET') {
            xmlHttp.open(obj.type, `${obj.url}?${postData}`, obj.async);
            xmlHttp.send(null);
        }
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                obj.success(xmlHttp.responseText);
            } else {
                obj.error(xmlHttp.responseText);
            }
        };
    },
    //图片没加载出来时用一张图片代替
    aftLoadImg(obj, url, errorUrl, cb) {
        let oImg = new Image(), _this = this;
        oImg.src = url;
        oImg.onload = function () {
            obj.src = oImg.src;
            if (cb && _this.istype(cb, 'function')) {
                cb(obj);
            }
        }
        oImg.onerror = function () {
            obj.src = errorUrl;
            if (cb && _this.istype(cb, 'function')) {
                cb(obj);
            }
        }
    },
    //图片滚动懒加载
    //@className {string} 要遍历图片的类名
    //@num {number} 距离多少的时候开始加载 默认 0
    //比如，一张图片距离文档顶部3000，num参数设置200，那么在页面滚动到2800的时候，图片加载。不传num参数就滚动，num默认是0，页面滚动到3000就加载
    //html代码
    //<p><img data-src="lawyerOtherImg.jpg" class="load-img" width='528' height='304' /></p>
    //<p><img data-src="lawyerOtherImg.jpg" class="load-img" width='528' height='304' /></p>
    //<p><img data-src="lawyerOtherImg.jpg" class="load-img" width='528' height='304' /></p>....
    //data-src储存src的数据，到需要加载的时候把data-src的值赋值给src属性，图片就会加载。
    //详细可以查看testLoadImg.html

    //window.onload = function() {
    //	loadImg('load-img',100);
    //	window.onscroll = function() {
    //		loadImg('load-img',100);
    //		}
    //}
    loadImg(className = 'ec-load-img', num = 0, errorUrl = null) {
        let oImgLoad = document.getElementsByClassName(className);
        for (let i = 0, len = oImgLoad.length; i < len; i++) {
            //如果图片已经滚动到指定的高度
            if (document.documentElement.clientHeight + document.documentElement.scrollTop > oImgLoad[i].offsetTop - num && !oImgLoad[i].isLoad) {
                //记录图片是否已经加载
                oImgLoad[i].isLoad = true;
                //设置过渡，当图片下来的时候有一个图片透明度变化
                oImgLoad[i].style.cssText = "transition: ''; opacity: 0;";
                if (oImgLoad[i].dataset) {
                    this.aftLoadImg(oImgLoad[i], oImgLoad[i].dataset.src, errorUrl, function (o) {
                        //添加定时器，确保图片已经加载完了，再把图片指定的的class，清掉，避免重复编辑
                        setTimeout(()=>{
                            if (o.isLoad) {
                                this.removeClass(o, className);
                                o.style.cssText = "";
                            }
                        }, 1000)
                    });
                } else {
                    this.aftLoadImg(oImgLoad[i], oImgLoad[i].getAttribute("data-src"), errorUrl, function (o) {
                        //添加定时器，确保图片已经加载完了，再把图片指定的的class，清掉，避免重复编辑
                        setTimeout(()=>{
                            if (o.isLoad) {
                                this.removeClass(o, className);
                                o.style.cssText = "";
                            }
                        }, 1000)
                    });
                }
                (function (i) {
                    setTimeout(()=>{
                        oImgLoad[i].style.cssText = "transition:all 1s; opacity: 1;";
                    }, 16)
                })(i);
            }
        }
    },
    //创建正则字符
    createKeyExp(strArr) {
        let str = "";
        for (let i = 0; i < strArr.length; i++) {
            if (i !== strArr.length - 1) {
                str = str + strArr[i] + "|";
            } else {
                str = str + strArr[i];
            }
        }
        return "(" + str + ")";
    },
    //关键字加标签（多个关键词用空格隔开）
    //ecDo.findKey('守侯我oaks接到了来自下次你离开快乐吉祥留在开城侯','守侯 开','i')
    //"<i>守侯</i>我oaks接到了来自下次你离<i>开</i>快乐吉祥留在<i>开</i>城侯"
    findKey(str, key, el = 'span') {
        let arr = null, regStr = null, content = null, Reg = null;
        arr = key.split(/\s+/);
        //alert(regStr); //    如：(前端|过来)
        regStr = this.createKeyExp(arr);
        content = str;
        //alert(Reg);//        /如：(前端|过来)/g
        Reg = new RegExp(regStr, "g");
        //过滤html标签 替换标签，往关键字前后加上标签
        content = content.replace(/<\/?[^>]*>/g, '')
        return content.replace(Reg, "<" + el + ">$1</" + el + ">");
    },
//***************DOM模块END*******************************/
};



var haorooms =
(function(){
    //创建一个独立的对象，注入所有的方法，包括你想抛出去和不想抛出去的
    var tool = {
        AAAA:function(){},
        BBBB:function(){
            console.log("我只想内部使用，不想给别人用");
        }
    };

    /*
    * 该对象承载所有需要抛出去的对象
    *   1.该对象中的方法可以自己写
    *   2.该对象中的方法可以注入（例子中的tempObj.tool.AA）
    *   3.该对象也可以选择性抛出给使用者需要的方法，也可以隐藏（tool.BBBB）
    * */
    var tempObj ={
        //reader为一些初始化需要的操作，有时候会有注册事件等，或者一些预操作
        reader:function(){
        },
        //注入所有的选择器，方便选择器变化，直接修改该对象中的选择器，而不需要全局去更改
        selector:{
            mySelector:"#mySelector",  //原密码
        },
        //注入所有的接口地址，方便接口变化可以进行，快速变更，不需要全局找引用的对象
        interface:{
            loginUrl:"",
        },
        //注入page中所有的事件，统一管理，建议命名规范：事件_命名，例 click_login
        registerEle:{
            click_login:function(){
                //注册单击事件
            }
        },
        //注入所有ajax请求，页面所有请求，将在这里统一管理，建议命名规范：ajax_命名，例 ajax_login
        /*
        * 该请求中有2种方案,看需求使用
        *  1.不公用一个请求方案
        *  2.公用一个请求，但是回调处理不一样
        * */
        ajaxRequest:{
            //不公用一个请求方案
            ajax_login:function(){
                $.post("","",function(data){
                    tempObj.callback.call_login(data);
                });
            },
            //会有多个业务公用这个请求
            ajax_login_T:function(callback){
                //所有接口地址从interface中获取，callback中tempObj.callback中处理
                $.post("","",callback);
            },
        },
        //处理所有回调函数，针对一个请求，处理一个回调
        callback:{
            //不共用请求处理回调
            call_login:function(data){
                //处理回调
            },
            //公用请求处理回调
            call_login_T:function(){
                var temp = function(){

                };
                tempObj.ajaxRequest.ajax_login_T(temp);
            }
        },
        //所有使用的工具类，如果每个项目都单独的unit.js或者common.js等存放一些公共方法的，这里可以不使用
        // PS:这里存放的只是仅针对于这个页面处理的一些tool，一般没必要抛出去，不过看业务而定
        tool:{
            A:function(){
                console.log("我是自己写的方法");
            },
            AA:tool.AAAA,    //这是我想抛出去给别人用的东西
        },
        //临时缓存存放区域，仅针对本页面，如果跨页面请存放cookie或者localstorage等
        //主要解决有时候会使用页面控件display来缓存当前页面的一些数据
        temp:{

        },
        /*
        * 业务使用区域，针对每个特别的业务去串上面所有的一个个原子
        *   因为上面所有的方法，只是做一件事，这边可以根据业务进行串服务，很简单的
        * */
        firm:{

        }
    };
    /*
    * 闭包抛出去的方法
    * */
    var outputObj =function(){
        //首先执行reader方法，初始化一些操作，比如注册事件啥啥啥的
        tempObj.reader();
        /*
        * 抛出给别人使用的对象
        *   想给别人看和使用的东西，可以注入tempObj对象，就像tool中的AA的方式
        *   不想给别人看和使用的东西，就像内部tool对象中的BBBB方法，你内部可以使用，外部是无法引用的
        * */
        return tempObj;
    }

    //抛出你希望抛出去的对象，因为你掌控了所有，哈哈。
    return new outputObj();
})();




极简方式实现类与继承
var Animal = {
	createFn:function(){
		var dog = {}
		dog.name = '小花'
		dog.handle = function(){
			console.log('人类的好朋友')
		}
		return dog
	}
}
var Dog = {
	createFn:function(){
		var dog1 = Animal.createFn();
		dog1.handle1 = function(){
			console.log('最好的朋友')
		}
		return dog1;
	}
}//继承了Animal的方法与属性

//原型链继承
子类的原型等于父类的实例

//类似继承
function Parent(age){
    this.name = ['mike','jack','smith'];
    this.age = age;
}

function Child(age){
    Parent.call(this,age);
}
var test = new Child(21);
alert(test.age);//21
alert(test.name);//mike,jack,smith
test.name.push('bill');
alert(test.name);//mike,jack,smith,bill

//组合继承
function Parent(age){
    this.name = ['mike','jack','smith'];
    this.age = age;
}
Parent.prototype.run = function () {
    return this.name  + ' are both' + this.age;
};
function Child(age){
    Parent.call(this,age);//对象冒充，给超类型传参
}
Child.prototype = new Parent();//原型链继承
var test = new Child(21);//写new Parent(21)也行
alert(test.run());//mike,jack,smith are both21

//原型式继承

通过Object.create()方式来创建新的类。老式浏览器不支持
function obj(o){
     function F(){}
     F.prototype = o;
     return new F();
 }


//闭包学习
function A(){
    function B(){
       console.log("Hello haorooms!");
    }
    return B;
}
var c = A();
c();//Hello haorooms!
(1)定义了一个普通函数A

(2)在A中定义了普通函数B

(3)在A中返回B（确切的讲，在A中返回B的引用）

(4)执行A(),把A的返回结果赋值给变量 c

(5)执行 c()
当一个内部函数被其外部函数之外的变量引用时，就形成了一个闭包。


