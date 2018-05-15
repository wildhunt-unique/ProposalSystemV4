/* 王海涛 2017.9.26 主界面控制js */
$(function startOn() {
	/* 开始运行加载函数 */
	/* 重写layout padding为0，保证移动端自适应 */
	$(".layout").css({
		"paddingLeft" : 0,
		"paddingRight" : 0
	});
});

$(function inputTip() {
	/* 输入框文字提示效果_绑定 */
	inputTipIn();
	inputTipOut();
});

function inputTipIn() {
	/* 输入提示进入控制函数 */
	$(".login_input>input").bind("focusin", function() {
		/* 登录输入框绑定焦点进入事件 */
		var $parent_area = $(this).parent();
		var $label = $parent_area.children("label");
		var $symble = $parent_area.children(".login__symble_1");
		var $input = $parent_area.children("input");
		/* 输入框提示字获得焦点动画 */
		$label.stop().animate({
			/* properties */
			fontSize : "15px",
			top : 0
		},
			{
				/* options */
				duration : 150
			});
		/* 输入框边框颜色改变 */
		$parent_area.parent().css("borderColor", "#346d89");
		/* 图标颜色改变 */
		$symble.css("color", "#346d89");
	});
}

function inputTipOut() {
	/* 输入提示退出函数 */
	$(".login_input>input").bind("focusout", function() {
		/* 输入框提示字失去焦点动画 */
		var $parent_area = $(this).parent();
		var $label = $parent_area.children("label");
		var $symble = $parent_area.children(".login__symble_1");
		var $input = $parent_area.children("input");
		var screen_height = window.innerHeight;
		var screen_width = window.innerWidth;
		/* 失去焦点动画 */
		if ($input.val() === "") {
			/* 输入框为空则恢复提示文字位置 */
			if (screen_height > 620 && screen_width > 430) {
				$label.stop().animate({
					/* properties */
					fontSize : "20px",
					top : "24px"
				},
					{
						/* options */
						duration : 150
					});
			} else {
				$label.stop().animate({
					/* properties */
					fontSize : "16px",
					top : "24px"
				},
					{
						/* options */
						duration : 150
					});
			}
		}
		;
		/* 输入框边框颜色改变 */
		$parent_area.parent().css("borderColor", "#8aadbe");
		/* 图标颜色改变 */
		$symble.css("color", "rgba(10, 92, 159, 0.50)");
	});
}

$(function inputPopovers() {
	/* 输入框_Popovers提示 */
	$("#input_popovers__1").popover({
		animate : true,
		trigger : 'hover',
		container : 'body',
		placement : 'bottom',
		content : '用户名是分配账户时的工号'
	});
	$("#input_popovers__2").popover({
		animate : true,
		trigger : 'hover',
		container : 'body',
		placement : 'bottom',
		content : '忘记密码？请联系管理员'
	});
})

/*随机更换壁纸函数*/
$(function changeTheme() {
	var reg = /\d/;
	var themeClass = "theme";
	var index = Math.random() * 10;
	index = index.toString();
	themeClass += reg.exec(index);
	$("body").removeClass("theme0")
		.removeClass("theme1")
		.removeClass("theme2")
		.removeClass("theme3")
		.removeClass("theme4")
		.removeClass("theme5")
		.removeClass("theme6")
		.removeClass("theme7")
		.removeClass("theme8")
		.removeClass("theme9");
	if (window.innerHeight >= 620 && window.innerWidth >= 430) {
		$("body").addClass(themeClass);
	};
})

$(function addBindEvent() {
	//绑定注册按钮事件
	$("#signup_button").bind("click", function() {
		window.open("https://www.apple.com", "_self");
	});

	//绑定版权信息按钮事件
	$("#copyright__link").bind("click", function() {
		$('.bd-example-modal-sm').modal();
	});

	//绑定登录按钮事件
	$("#login_button").bind("click", function(event) {
		event.preventDefault();

		$('#submit_button').html("登&nbsp;&nbsp;&nbsp;&nbsp;陆&nbsp;&nbsp;&nbsp;&nbsp;中&nbsp;...");

		//调用登录验证函数
		$.ajax({
			url : "login.do",
			type : "POST",
			data : {
				user_name : $("#user_name").val(),
				user_password : $("#user_password").val()
			},
			success : function(data, status) {
				isLogin(data);
			},
			error : function(e) {
                $('#submit_button').html("登&nbsp;&nbsp;&nbsp;&nbsp;录");
				alert("请检查网络连接！！");
				window.clearInterval(timer);
			}
		});

	});
})
function isLogin(data) {
	if (data == "true") {
        $("form").submit();
	} else {
        $('#submit_button').html("登&nbsp;&nbsp;&nbsp;&nbsp;录");
		$("#login-error").stop(true).fadeIn({
			duration : 0,
			complete : function autoClose() {
				$("#login-error").delay(3200).fadeOut();
			}
		});
	}
}
function loginFailTip() {
	//登录失败提示，调用函数弹出登录失败提示
	$("#login-error").stop(true).fadeIn({
		duration : 0,
		complete : function autoClose() {
			$("#login-error").delay(3200).fadeOut();
		}
	});
}

function loginValidate() {
	//登录信息合法性验证
	var $userName = $("#user_name");
	var $userPassword = $("#user_password");
	if ($userName.val() == "") {
		alert("请输入用户名");
		return false;
	}
	if ($userPassword.val() == "") {
		alert("请输入密码");
		return false;
	}
	return true;
}

/*
    提交表单函数：
    $("#user_login").submit();
*/