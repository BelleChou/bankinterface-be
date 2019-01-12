function padLeftZero(str){
	return ('00'+ str).substr(str.length);
}
export default {
	fotmateBirth:function(date){//作用：将20180406 转换为2018-04-06
		let year=date.substring(0,4);
		let month=date.substring(4,6);
		let day=date.substring(6);
		return year+"-"+month+"-"+day;
	},
	formatDate:function(dateName,fmt){//作用：将1415674835697的时间戳转为 yyyy/MM/dd 格式时间
		dateName =(typeof dateName=='string')?parseInt(dateName) : dateName;
		let date=new Date(dateName);
		if(/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
		}
		let o = {
			'M+': date.getMonth() + 1,
			'd+': date.getDate(),
			'h+': date.getHours(),
			'm+': date.getMinutes(),
			's+': date.getSeconds()
		}
		for(let k in o) {
			if(new RegExp(`(${k})`).test(fmt)) {
				let str = o[k] + ''
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str))
			}
		}
		return fmt;
	},
	moneyFormate:function(val){//作用：将123456789.32 => 123,456,789.32 
		val=val.toString().replace(/\$|\,/g,'');
		if(isNaN(val)){val='0'}
		let sign=(val==(val=Math.abs(val)));
		val =Math.floor(val*100+0.50000000001);
		let cents=val%100;
		val=Math.floor(val/100).toString();
		if(cents<10){
			cents='0'+cents
		}
		for(let i =0;i<Math.floor((val.length-(1+i))/3);i++){
			val=val.substring(0,val.length-(4*i+3))+','+val.substring(val.length-(4*i+3));
		}
		return ((sign?'':'-')+val +'.'+cents);
	}
}
