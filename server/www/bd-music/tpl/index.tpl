<script id="index_tpl" type="text/html">
		{{each list as value i}}
		<div class="tj-song-item">
            <a href="javascript:;">
                <div class="song-img">
                    <img src="{{value.pic_big}}">
                    <span class="iconfont icon-play">&#xe601;</span>
                </div>
                <div class="song-name">{{vlaue.info}}</div>
            </a>
        </div>
		{{/each}}
</script>