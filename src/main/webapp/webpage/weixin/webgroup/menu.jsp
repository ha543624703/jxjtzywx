<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${menuName}</title>
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
  		<link type="text/css" rel="stylesheet" href="template/cms/default/css/weixin_cms.css" />
	    <script type='text/javascript' src='plug-in/jquery/jquery-1.8.3.js'></script>
	    <script type="text/javascript">
	    	var rows = 5;//每页显示记录数
	    	var page = 1;//当前页
	    	var total = 0;//总记录数
	    	var field = "id,title,columnId,summary,createDate,imageName,imageHref,accountid,";//字段
	    	var sort = "createDate";
	    	var order = "desc";
	    	var columnId = '${menu}';//当前栏目id
	    	var $moreBtn = $("#j-viewmore");//显示更多按钮
	    	$(function(){
	    		
	    	    //返回顶部按钮逻辑
	    	    $('#j-gotop').on("click", function () {
	    	        window.scrollTo(0, 0); 
	    	    });
	    	    $("#j-viewmore").on("click",function(){
	    	    	loadData();
		    	});
	    	    $("#j-viewmore").click();
		    });
		    //数据加载
		    function loadData(){
				if ($("#j-viewmore").hasClass("disabled")) {
					return;
				}
				$("#j-viewmore").html("努力加载中…").addClass("disabled");
	    		$.ajax({
	                type: "POST",
	                url: "cmsController.do?getArticleList",
	                data: {columnid:columnId, rows:rows,page:page},
	                dataType: "json",
	                success: function(data){
	                	var mapValue = eval("("+data+")");
	                	var total = mapValue.totalCount;
	                	var value = mapValue.list;
	                	for (var i=0; i<value.length; i++)
	    				{
                        	console.log("article",value[i]);
                        	setData(value[i]);
	    				}
           		        if ((page * rows) >= total){
           		        	$("#j-viewmore").html("已显示全部").addClass("disabled");
           					$moreBtn.off('click');
           				} else {
           					page++;
           					$("#j-viewmore").html("查看更多").removeClass("disabled");
           				}
                	}
	            });
			}
			//数据填充展示
			function setData(article){
				var html = "";
				html= '<section class="w-goods " mon="action=click&amp;type=item">'
					+ '			<a href="cmsController.do?goArticle&menu='+columnId+'&articleid='+article.id+'" class="clearfix">'
					+ '				<ul>'
					+ '					<li class="title">'
					+ '						<span class="store ">'+formatCSTDate(article.update_time,'yyyy-MM-dd hh:mm:ss')+'</span><span class="item-name">'+article.title+'</span>'
					+ '					</li>'
					+ '					<li class="info">'
					+ '						'+article.summary+''
					+ '					</li>'
					+ '				</ul>'
					+ '			</a>'
					+ '</section>'
				$("#j-goods-container").append(html);
			}
					//格式化CST日期的字串
		      function formatCSTDate(strDate,format){
		        return formatDate(new Date(strDate*1000),format);
		      }
			//格式化日期,
   		   function formatDate(date,format){
		        var paddNum = function(num){
		          num += "";
		          return num.replace(/^(\d)$/,"0$1");
		        }
		        //指定格式字符
		        var cfg = {
		           yyyy : date.getFullYear() //年 : 4位
		          ,yy : date.getFullYear().toString().substring(2)//年 : 2位
		          ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
		          ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
		          ,d  : date.getDate()   //日 : 如果1位的时候不补0
		          ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
		          ,hh : date.getHours()  //时
		          ,mm : date.getMinutes() //分
		          ,ss : date.getSeconds() //秒
		        }
		        format || (format = "yyyy-MM-dd hh:mm:ss");
		        return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
     	 } 
	    </script>
	</head>
	<body>
		<header class="w-header" mon="type=header">
			<a class="arrow-wrap" href="javascript:history.back()" mon="content=back">
			<span class="arrow-left"></span>
			</a>
			
			<a href="http://www.jeewx.com/" class="home" mon="content=home"></a>
			<div class="text">${menuName}</div>
		</header>
		<article class="p-list" mon="action=click">
		<div id="j-goods-container">
		</div>

		<div class="w-viewmore clearfix" mon="action=click">
			<a id="j-viewmore" href="javascript:;" class="op-btn more"
				mon="type=more">查看更多</a>
			<a href="javascript:;" id="j-gotop" class="op-btn gotop"
				mon="type=gotop">回到顶部</a>
		</div>
		</article>
	</body>
</html>