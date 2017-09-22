/**
 * Created by liqiao on 8/10/15.
 */

/**
 * _config comes from server-side template. see views/index.jade
 */
dd.config({
			agentId : _config.agentid,
			corpId : _config.corpId,
			timeStamp : _config.timeStamp,
			nonceStr : _config.nonceStr,
			signature : _config.signature,
			jsApiList : [ 'runtime.info', 'biz.contact.choose',
					'device.notification.confirm', 'device.notification.alert',
					'device.notification.prompt', 'biz.ding.post',
					'biz.util.openLink' ]
		});


dd.ready(function() {



	dd.runtime.permission.requestAuthCode({
		corpId : _config.corpId,
		onSuccess : function(info) {
//			alert('authcode: ' + info.code);
			$.ajax({
				url : 'mLoginController.do?dingtalkLogin&code=' + info.code + '&corpid='
						+ _config.corpId,
				type : 'GET',
				success : function(data, status, xhr) {
			
					window.location.href ='mLoginController.do?dinglogin';
					return;
					var info = JSON.parse(data);

					//document.getElementById("userName").innerHTML = info.name;
					//document.getElementById("userId").innerHTML = info.userid+","+info.mobile;
					
					// 图片
////					if(info.avatar.length != 0){
////			            var img = document.getElementById("userImg");
////			            img.src = info.avatar;
////			                      img.height = '100';
////			                      img.width = '100';
////			          }

				},
				error : function(xhr, errorType, error) {
					logger.e("yinyien:" + _config.corpId);
					alert(errorType + ', ' + error);
				}
			});

		},
		onFail : function(err) {
			alert('fail: ' + JSON.stringify(err));
		}
	});
});

dd.error(function(err) {
	alert('dd error: ' + JSON.stringify(err));
});
