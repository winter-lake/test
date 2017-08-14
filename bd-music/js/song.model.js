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
	}
}