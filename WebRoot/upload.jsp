<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'upload.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<style type="text/css">
		#progressBar{width: 400px;height: 12px;background: #FFFFFF;border: 1px solid #000000;padding: 1px;}
		#progressBarItem{width: 30%;height: 100%;background: #FF0000;}
		#div{size: 12px;}
	</style>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  </head>
  <body>
    <br/>
    <br/>
    <br/>
    	<iframe name="upload_iframe" width="0" height="0"></iframe>
    	<form action="upload.do" method="post" enctype="multipart/form-data" target="upload_iframe" onsubmit="showState();">
    		上传文件一：<input type="file" name="file1" id="file1"/><br/><br/>
    		上传文件二：<input type="file" name="file2" id="file2"/><br/><br/>
    		<input type="submit" value="上传"/>
    	</form>
    	上传进度条：<br/>
    	<div id="progressBar" style="display: none;"><div id="progressBarItem"></div></div>
    	<div id="statusinfo"></div>
 	<script type="text/javascript">
 		var _finished=true;//标志是否上传结束
 		var tid;
 		function showState(){
 			_finished=false;
 			$("#progressBar").css("display","block");
 			$("#progressBarItem").css("width","1%");
 			$("input[type=submit]").attr("disabled",true);
			tid=setInterval(requestStatus,1000);
 		}
		function requestStatus(){
 			if(_finished){clearInterval(tid);return;}//上传结束则返回
 			$.get("upload.do",function(data){
 				debug(data);//显示debug信息
 				var ss=data.split("||");//处理上传进度
 				$("#progressBarItem").css("width",ss[0]+"%");
 				$("#statusinfo").html("已完成百分比："+ss[0]+"%<br/>已完成数(M)："+ss[1]+"<br/>文件总长度："+ss[2]+"<br/>传输速度(K)："+ss[3]+"<br/>已用时间(s)："+ss[4]+"<br/>估计总时间："+ss[5]+"<br/>估计剩余时间："+ss[6]+"<br/>正在上传第几个文件："+ss[7]);
 				if(ss[1]==ss[2]){
 					_finished=true;
 					//$("#statusinfo").html("<br/><br/><br/>上传已完成！");
 					$("input[type=submit]").attr("disabled",false);
 				}
 			});
 		}
 		function debug(obj){
 			var div=document.createElement("DIV");
 			div.innerHTML="[debug]："+obj;
 			document.body.appendChild(div);
 			
 		}
 	</script>
  </body>
</html>
