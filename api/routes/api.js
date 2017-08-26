var express = require('express');
var router = express.Router();

var _ = require('lodash');

router.get('*', function (req, res, next) {
  res.setHeader("Access-Control-Allow-Origin", "*");
  next();
})

/* GET home page. */
router.get('/', function(req, res, next) {
  var arr = [],
      len = 50;
  for (var i = 0; i < len; i++) {
    arr.push({'title': '我是数据'})
  }
  var newArr = _.chunk(arr, 2);
  var obj = {"name": "xu"}
  res.send(newArr)
});

router.get('/music', function(req, res, next) {
  var query = req.query;
  var pageNo = query.pageNo || 1,
      pageSize = query.pageSize || 10;
  var arr = [],
      len = 30;
  for (var i = 0; i < len; i++) {
    arr.push({
    "addr": "海淀区西四环北路（我是第" + (i+1) + "条）",
    "cityId": 1,
    "dis": "https://p0.duyao001.com/image/common/4c0db4828730fe1a8c05f5fb0134d5ef.jpg",
    "distance": "9.9km",
    "follow": 0,
    "id": 13054,
    "lat": 39.94469,
    "lng": 116.27137,
    "mark": 0,
    "nm": "万画影城(四季青店)",
    "poiId": 41066531,
    "price": 28,
    "promotion": {},
    "referencePrice": "0",
    "score": 864.28577,
    "sellPrice": "28",
    "shopId": 23325693,
    "tag": {
      "allowRefund": 1,
      "buyout": 0,
      "deal": 0,
      "endorse": 1,
      "sell": 1,
      "snack": 0
    },
    "showPrice": true})
  }
  var newArr = _.chunk(arr, pageSize);

  res.send({
    data: {
      list: newArr[pageNo - 1] || [],
      pageNo: pageNo,
      pageSize: pageSize, 
      total: len,
      totalPages: newArr.length
    },
    errorCode: 0,
    message: 'message'
  })
});

module.exports = router;
