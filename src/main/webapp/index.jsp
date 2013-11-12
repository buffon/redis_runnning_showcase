<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="/resource/js/jquery-1.6.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resource/css/base.css"/>
    <style>
        legend{
            font-weight:bold ;
        }
    </style>
    <script language="javascript">
        $(function () {

            function init() {
                $.ajax({
                    type: "get",
                    url: "/user.do/info.do",
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#info").html(data["value"]);
                    }
                });

                $.ajax({
                    type: "get",
                    url: "/user.do/dbsize.do",
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#dbsize").html("当前数据库条数：" + data["value"]);
                    }
                });
            }

            init();

            $("#refresh").click(function () {
                init();
            })

            $("#init").click(function () {
                $("#init_msg").html("running...");
                var res = {number: $("#number").val()};
                $.ajax({
                    type: "post",
                    url: "/user.do/init.do",
                    data: res,
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#init_msg").html("result: " + data["value"] + " | cost ms: " + data["pass"]);
                    }
                });
            })


            $("#submit").click(function () {
                var res = {key: $("#set_key").val(), value: $("#set_value").val()};
                $.ajax({
                    type: "post",
                    url: "/user.do",
                    data: res,
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#set_msg").html("result: " + data["value"] + " | cost ms: " + data["pass"]);
                    }
                });
            })

            $("#run").click(function () {
                $("#con_msg").html("running...");
                var res = {number: $("#con_number").val(), operation: $("#con_operation").val()};
                $.ajax({
                    type: "post",
                    url: "/user.do/concurrent.do",
                    data: res,
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#con_msg").html("cost ms: " + data["value"]);
                    }
                });
            })

            $("#get").click(function () {
                var res = {key: $("#get_key").val()};
                $.ajax({
                    type: "get",
                    url: "/user.do",
                    data: res,
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#get_msg").html("result: " + data["value"] + " | cost ms: " + data["pass"]);
                    }
                });
            })

            $("#set_address").click(function () {
                var res = {port: $("#port").val(),ip:$("ip").val()};
                $.ajax({
                    type: "get",
                    url: "/user.do/address.do",
                    data: res,
                    dataType: "json",
                    success: function (data, status, xml) {
                        $("#get_msg").html("result: " + data["value"]);
                    }
                });
            })

        })
    </script>
</head>
<body>

<div id="container">
    <div id="head">
        <h1 style="padding-left: 10px;padding-top: 10px">Redis Manager</h1>
    </div>
    <hr/>
    <div>
        <div style="float: left">
            <fieldset>
                <legend>灌数据</legend>
                <blockquote>
                    <input type="text" id="number" placeholder="请输入初始化条目"/>
                    <input type="button" id="init" value="init"/>
                    <div style="color: #aaaaaa;font-size: 10px">
                        注：key值将从0开始累加到输入的值
                    </div>
                    <div id="init_msg"></div>

                </blockquote>
            </fieldset>
        </div>
        <div>
            <fieldset>
                <legend>服务器</legend>
                <blockquote>
                    <input type="text" id="ip" placeholder="IP"/>
                    <input type="text" id="port" placeholder="PORT"/>
                    <input type="button" id="set_address" value="set"/>
                    <div style="color: #aaaaaa;font-size: 10px">
                        注：服务器启动默认连接的redis服务器地址是localhost:8080
                    </div>
                    <div id="ip_msg"></div>
                </blockquote>
            </fieldset>
        </div>
    </div>
    <div style="clear: left">
        <fieldset>
            <legend>接口</legend>
            <blockquote>
                <div>set方法： POST  http://{ip}:{port}/user.do?key=xxx&value=xxx</div>
                <div>get方法： GET http://{ip}:{port}/user.do?key=xxx</div>
            </blockquote>
        </fieldset>
    </div>
    <div>
        <fieldset>
            <legend>settings info</legend>
            <blockquote>
                <div>
                    <input type="button" value="refresh" id="refresh"/>
                </div>
                <div id="info"></div>
                <div id="dbsize"></div>
            </blockquote>
        </fieldset>
    </div>
    <div>
        <fieldset>
            <legend>并发测试</legend>
            <blockquote>
                <input type="text" id="con_number" placeholder="线程个数"/>
                <input type="text" id="con_operation" placeholder="每个线程执行条数"/>
                <input type="button" id="run" value="run"/>

                <div id="con_msg"></div>
            </blockquote>
        </fieldset>
    </div>

    <div>
        <fieldset>
            <legend>set value</legend>
            <blockquote>
                <input type="text" id="set_key" placeholder="key"/> <br>

                <div style="color: #aaaaaa;font-size: 10px">
                    注：key值是42位，不足则高位不全
                </div>
                <input type="text" id="set_value" placeholder="value"/> <br>

                <div style="color: #aaaaaa;font-size: 10px">
                    注：value值为15位，不足则高位不全
                </div>
                <div id="set_msg"></div>
                <input type="button" id="submit" value="set"/>
            </blockquote>
        </fieldset>
    </div>

    <div>
        <fieldset>
            <legend>get value</legend>
            <blockquote>
                <input type="text" id="get_key" placeholder="key"/>
                <input type="button" id="get" value="get"/>

                <div style="color: #aaaaaa;font-size: 10px">
                    注：key值是42位，不足则高位不全
                </div>

                <div id="get_msg"></div>
            </blockquote>
        </fieldset>
    </div>

</div>

</body>
</html>
