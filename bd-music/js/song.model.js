//所有页面的接口文档
var SongModel = {};


SongModel.index = {
	//今日推荐
	tj : function(){
		return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.getRecommandSongList&song_id=877578&num=6',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
	},
    //新歌速递
    newSong : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=9',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    /*音乐榜单*/
    //热歌榜
    hotSong : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=2&size=5',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    //新歌榜
    NewSong : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=25&size=5',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    //King榜
    kingSong : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=23&size=5',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    //热门歌单
    hotList : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=24&size=6',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    //mv歌单
    mvList : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=11&size=6',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    },
    audio : function(){
        return $.ajax({
            url: 'http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid='+toLocation()+'',
            type: 'get',
            contentType:"application/json",
            dataType: 'jsonp'
        })
    }
}


function toLocation(){
    var url = location.href;
    var str = url.split('#')[1];
    return str;
}
