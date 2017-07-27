<div class="modal fade" id="j_price_dialog">
	<div class="modal-dialog">
	<div class="modal-content">
  		<div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    		<h3 class="modal-title tx_b tx_20">修改价格<small class="ml10">订单编号:(<span>{{orderNo}}</span>)</small></h3>
  		</div>
      	<div class="modal-body">
        	<form class="form-inline tx_14 row">
        		<div class="col-sm-12">
	        		<div class="form-group">
	        			<label>
	        				<input type="radio" name="discount_type" value="1" checked v-model="discount_type"> 按折扣修改订单价格(实付金额=应付金额*折扣)
	        			</label>
	        		</div>
        		</div>
        		<div class="col-sm-12 tx_c">
        			<div class="form-group">
					    <input type="number" class="form-control"  placeholder="两位小数" style="width:100px;" v-model="discount" number v-bind:disabled="hasDiscount" value="1">
					</div>
					<div class="form-group">
					    <label>倍 = </label>
					    <input type="number" class="form-control" readonly placeholder="调整后价格" style="width:100px;" value="{{discount_price}}">
					</div>
        		</div>
        		<div class="col-sm-12 mt20">
	        		<div class="form-group">
	        			<label>
	        				<input type="radio" name="discount_type" value="2" v-model="discount_type"> 按金额修改订单价格(实付金额=应付金额-调整金额)
	        			</label>
	        		</div>
        		</div>
        		<div class="col-sm-12 tx_c">
					<div class="form-group">
					    <label class="mr5">调整金额</label>
					    <input type="number" class="form-control" placeholder="两位小数" style="width:100px;" v-model="payAmount" number v-bind:disabled="hasPayamount"><label  class="ml5">元</label>
					</div>
        		</div>
        		<div class="col-sm-12 tx_c tx_18 mt20">
        			<span class="form-control-static"><span class="mr5">调整前</span><b class="tx_red">￥ {{ori_price}}</b></span>
        			<span class="form-control-static ml20"><span class="mr5">调整后</span><b class="tx_red">￥ {{real_price}}</b></span>
        		</div>
        	</form>
      	</div>
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        	<button type="button" class="btn btn-primary j_sure_btn">确定</button>
      	</div>
	</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->