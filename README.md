Q&A


1.文件名中有空格
==>已解决
2.下载中文文件名乱码！！
==>已解决
3.下载文件名中有+
==>没有解决------------------------------------------
基本想法是 ServletActionContext.getRequest().getParameter("downloadFile");
在这之前即在js中将提交的参数先进行处理。

4.文件名中有中文？没有问题！！，英文?,命名就不允许
5.FF和Chrome下载问题
==>也已经解决,编码问题
==>另外之前感觉IE下载的时候总会打开两次也是这里的原因，xls等就会给出只读什么的提示（你打开两次了嘛）

-----
继续发现问题。。。
6.	// Servlet的默认字符集是ISO-8859-1！！！
	// 但是可以配置Tomcat的server.xml中的URIEncoding为UTF-8就不需要先转码了
	具体见 cn.zhuqi.web.actions.FileDownloadFullAction的execute()方法
	
7. 如果是修改图片资源或者css,js文件等，下一次看效果必须清空缓存，否则有可能没有改变效果。
（PS:图片后缀也区分大小写例如head.jpg，使用head.JPG就访问不到）

-----------
8.分页的实现
目前使用的使用的是jQuery的datatable, 缺点是数据集合不能太大,这里可以基本满足要求。
区别不是很明显啊,可以使用。。

9.文件上传后信息的获取
目前思路是重新对文件命名，直接以资源的ID号来区别,方便管理。
===>有问题，目前的ID号是在文件上传后才确定的，也就是先上传的文件，然后对表的插入操作。
修改方案：
(1)目前使用的是albumAction 和fileuploadAction,分开处理了,可以考虑合并，但是效果不好，没有swfupload的效果了。
(2)考虑在fileuploadAction中返回json数据，然后填入表单数据中，然后提交到albumAction中进行处理。
-----
优先考虑方案(2)，毕竟效果好看，就是复杂了一点。
关键==>fileuploadAction中返回json数据==>搞定

暂时回到8，分页问题用jQuery的那个局限性比较大，列属性的控制比较麻烦，资料有比较少，看不过来了，以后再研究吧，现在使用分页Model。
其实可以使用PageModel类的，但是尝试一下新用法。自定义tag............
-------
效果还不错(有小BUG,结果集为空时页面数量好像是错的,界面不是太好看)。。。看看最后要什么吧。。。

10.基础理论研究告一段落，现在开始正式工作流jbpm4.4
------------
首先集成请假实例的使用角色控制（PS：原实例使用的用户名检测控制。）
===>user中加上roleId属性。

===================================================================================
自己写的东西明显架构不成熟，因此直接先学习一段时间(PS:2-3天) 2013.6.19 14:41
-----------
重新开始吧。
11.组织结构


--------------------------------------------------------
看得还是不明白。。
直接跳转流程这部分吧，先实现了再说。
12.流程
例子:
审批实例：
第一步：创建审批流程中各相关实体类，并创建数据库表
第二步：根据对用例的分析，初步建立工程审批的相关接口
第三步:实现接口
	-集成JBPM到OA系统
	
	
-------------------
13.权限这块就不做了，全部用现成的东西。。
14. Hibernate 问题
public void update(User user, int[] roleIds) {
		// a different object with the same identifier value was already
		// associated with the session。
		// 在hibernate中同一个session里面有了两个相同标识但是是不同实体。
		// PS:使用merge代替update
		// 出现的原因是当前用户更新个人信息的时候,若是管理员更新他人信息就不会出错.
		getSession().merge(user);
		// 删除旧的关联
		String hql = "select ur from UsersRoles ur join ur.user u where u.id = ?";
		List<UsersRoles> urs = getSession().createQuery(hql)
				.setParameter(0, user.getId()).list();

		for (UsersRoles ur : urs) {
			getSession().delete(ur);
		}

		// 建立新的关联
		updateUsersRoles(user, roleIds);
	}
------------------
15.ok,架子搭上了，开始工作流workflow.

----------------
工作流上传没有问题，好像是插入OA系统出错了，接下来处理这个问题。。

---------------
16. 1013-6-24 15:25 发布流程成功，并实现了流程的文件流程图查看，和流程定义的删除..
------------------
17.继续与OA系统实际项目进行集成，实现业务逻辑。

------------
18.要抓紧时间了。。。但要忙而不乱.
------------
19.第一期的后台逻辑基本实现，接下来组合界面
-------------------------
20.界面部分建议使用Chrome或者FireFox，否则郁闷死。
------------------
21.文件的管理是一大模块，暂时先不做了，先确定工作流没有问题。。
----------------
22.工作流暂时没有问题。主要是工作流中的角色控制。
----------------
23.工作流发布修正完成。接下来实现22中的工作流设计.
------------------
24.修正工作流的审批时候的BUG
--------------------
25.注意单元测试，比较结果，还要注意事务！！！！！！！！！！！！！！！！
特别是flowToNextStep！！！！！！！！！！！
=========注意获取任务的不同。。
-----------------------
26.已办任务的显示+审批时间 V完成
--------------------------
27.正式开始实现T2任务
(PS：任务分布)
=====================
1、	工程受理阶段的工作流管理(T1)。
2、	工程中的所有文件管理(T2)，主要涉及到工作流中每个模块需要上传下载那些文件的控制，比如在客户代表录入工程信息的时候需要上传的文件是15个，这些应该可以控制(或增加，或减少，或修改)。
3、	工程中所有涉及的界面设计和维护(T3),主要是前台。
4、	工程中所有涉及的文件上传和下载操作(T4)。
5、	工程中所有涉及的表单域的校验和管理(T5)（PS：目前的安排是做前台的校验，后台的校验看有没有时间）。例如录入工程信息界面，工程名称不可为空，面积必须是数字（这些必须实现），再比如管理员分配账号时，密码必须6位以上(这些选择实现)等等。
6、	临时用电工程的工作流设计以及管理实现，后台实现(T6)。
7、	正式用电工程的工作流设计以及管理实现，后台实现(T7)。
8、	财务的工作流设计以及管理实现，后台实现(T8)。
9、	中后期（临时开始）的前后台集成(T9)。
10、	系统的集成和控制(T10)。
=====================
28.继续完成T2，主要完成每个节点的文件管理
----------
29.已经完成28，接下来实现具体工程文件的上传等管理。。
--------------
30.完成了文件上传下载，也实现了与工程的关联，接下来实现具体的后面查看的内容...
--------------
31.OK,继续完成...上午整理之前的代码，下午正式开始。
-------------
32.项目一期基本完成，主要还剩下未上传文件的补充上传和各个页面的链接显示(哪些人能看到哪些菜单。。是审批还是分配编号，还是签订协议)
---------
33.32的后半部分完成，其实就剩下修改工程了。。
----------
34.开始修改工作流图，完成整个流程图
--------------
35.five操作有问题。。。，查看原因。。
--------------
36.全部工作流的框架已经搭建完成，
	接下来还剩下
	流程功能列表访问,
	七库集成,最后的Excel大表的输出，
	还有各个界面的具体填充。。
--------------------------
37.七库基本完成，还剩高亮显示。。
【注】签订协议之前的工程历史状态不正确，需检查。。。
--------------------------
38.37七库完成，高亮显示也完成。【注】也完成
继续完成工程的列表访问
---------------
39.删了com.springsource.org.jruby-1.4.0.jar，记录一下，防止误删
-------------
40.列表访问已完成，excel导出demo已经完成，具体情况具体实现。
考虑到最后的Excel大表的数据输出，所以project增加部分字段（待定。。）另外每次付款都要记录一下，故记录一下model
-------------
41.付款模型已建立。
-------------
42.增加工程信息修改,基本完成工程受理的修改。。剩下修改文件。。
-----------
43.文件修改已经完成，销户也已经修改完成，剩下各个页面的修改操作.
-------------
44.完成fid修改，还剩财务和之后合同之类的修改。。
-------------
45.完成财务信息的修改，目标完成财务文件和合同文件之类的修改。。
-------------
46.所有的修改已经完成，接下来主要实现Excel大表和页面的修改
-------------------
47.短消息基本完成，。，。还剩3个index
-------------------
48.完善了短消息模块，只剩下回复+发送消息
-------------------
49.短消息模块完成
-------------
50.大表??????????????
------------
51.完善了message，流程图ok,文件查看部分修改完成，大表待完成。。。
---------------
52.文件查看、下载修改完成，文件格式待完善,大表,密码和个人信息修改,IE下<%=basePath %>
------------------
53.文件格式已完善
------------------
54.IE下<%=basePath %>已完善
------------------
55.个人信息修改完成,  merge!!!!!!!!!!!!!!!
------------------
56.密码修改也完成...
------------------
57.还剩一个大表Excel,其实还有ID生成器
------------------
58.Excel表的属性
-----------------
59.财务信息管理完成，部分Excel属性添加完成，剩下Excel属性的修改,即ZSequence
-----------------
60.ZSequence排序有点小问题
-----------------
61.viewPic有小问题，工程完成后无法查看图片,修改完成,不用修改定义图片，到具体使用的时候看看改过的有没有问题。。
-----------------
62.大表生成完成，
给出下载链接,
给出下载链接,
给出下载链接,
给出下载链接,
给出下载链接,
给出下载链接,
为防止所有表单的重复提交，需做一下修改，然后还剩下ID生成器的修改
------------------
63.Excel大表全部完成,现在完善submit,基本完成
------------------
64.ID生成器的修改,先完成密码的md5加密（没网,做不了~~）
------------------
65.密码md5加密成功~~
------------------
66.ID生成器的修改继续...(先完善了zsequence)
已经完成每年初始为1的修正,现在完成规则的添加以及修改
------------------
67.ID生成器修改完毕，接下来是测试阶段，实际编写阶段已经结束...
------------------------------------------------
测试工程...
1.修改ID规则添加的错误（错误的也能够添加进系统...） -- 2013_8_4_14_00
....
2.要记得修改工程进度这块....
....
3.修改了ActionResource分配的一个小问题，现继续完成2...
....
4.修改了用户管理界面的修改功能，首先判断当前人员是否已经分配账号,若没有抛出异常~~
		(不能使用load，此项目使用的都是延迟加载，只有在实际使用到这个user的时候才会在数据库中加载这个对象，
		因此在action中判断user是否为空无法抛出异常，所以使用get方法
 ----	对于Hibernate get方法，Hibernate会确认一下该id对应的数据是否存在，首先在session缓存中查找，
		然后在二级缓存中查找，还没有就查询数据库，数据库中没有就返回null。)
....
5.完善2，结果发现不用修改了，施工进度表是分开的文件，只要依次添加3个进度文件即可~~
------------------
测试工程告一段落，进入下一测试阶段~~

---------------------------------------
综合测试阶段：
1.修改了财务报表的一些问题,但是IE无法下载，FF下载后文件没有后缀。。(测试环境，IE type="file"显示有问题,正常没有问题，FF不考虑兼容了)=>测试结果全部兼容，没有问题
2.接下来修改全部的下载javascript:void(0);目测修改完毕，待查~~
3.查找工程支持工程编号的模糊搜索
4.修改了TXT文件在线浏览的乱码bug
=======================
1.工程受理（收取款项可修改）                    	V
2.工程受理1.2.15删除                            	V
3.图形化查看修改 			    V
=======================
2.登陆界面用户名密码 			V
1.审核意见主界面显示			V
3.						V
8.主界面待办任务列表显示			V
9.正式用电可研文件相关保留后两项	V
13.删除临时用电开工报告			V
15.删除临时用电竣工验收单和竣工报告书	V
--------------------------------------
100.每天的待办任务做成邮箱一样的未读邮件(同时提供主页面的显式提醒)	V
101.修改了待办任务无法分页的bug   					V
102.查看工程不需要点击按钮了，直接点击那一栏即可 				V
103.财务代表角色已经可以分配了，具体如何分配直接给出即可			V
--------------------------------------
待办任务：
16,14,11,10,6,7 =>修改设计图纸，待确定
4.文件上传后可修改，可跳过待定。。。
想要使用文件服务器 ----------不好实现啊
---------------------------------------

		














--------------------------------------
PS:
1.修改Mysql数据库密码
UPDATE mysql.user SET password=PASSWORD('新密码') WHERE user='root';
FLUSH PRIVILEGES;

2.建立数据库ptfee
CREATE DATABASE `ptfee` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

3.修改mysql的字符集
	->3.1查看
		show variables like 'collation_%';
		show variables like 'character_set_%';
	->3.2修改,全部设置成utf8
		通过MySQL命令行修改:(PS:这个是有问题的，还能暂时修改。。)
			set character_set_client=utf8;

			set character_set_connection=utf8;

			set character_set_database=utf8;

			set character_set_results=utf8;

			set character_set_server=utf8;
			
			set character_set_system=utf8;
			
			=====

			set collation_connection=utf8_general_ci;

			set collation_database=utf8_general_ci;

			set collation_server=utf8_general_ci;

---------
4.删除表
DELETE FROM table1
TRUNCATE TABLE table1


------------------
5.Tomcat设置
	5.1、首先是：java.lang.OutOfMemoryError: Java heap space
		解释：
		Heap size 设置
		JVM堆的设置是指java程序运行过程中JVM可以调配使用的内存空间的设置.JVM在启动的时候会自动设置
		Heap size的值，其初始空间(即-Xms)是物理内存的1/64，最大空间(-Xmx)是物理内存的1/4。可以利用
		JVM提供的-Xmn -Xms -Xmx等选项可进行设置。Heap size 的大小是Young Generation 和Tenured
		Generaion 之和。
		提示：在JVM中如果98％的时间是用于GC且可用的Heap size 不足2％的时候将抛出此异常信息。
		提示：Heap Size 最大不要超过可用物理内存的80％，一般的要将-Xms和-Xmx选项设置为相同，而-Xmn为
		1/4的-Xmx值。
		解决方法：
			手动设置Heap size
		修改TOMCAT_HOME/bin/catalina.bat，在“echo "Using CATALINA_BASE: $CATALINA_BASE"”上面加
		入以下行：
		set JAVA_OPTS=%JAVA_OPTS% -server -Xms800m -Xmx800m -XX:MaxNewSize=256m
		或修改catalina.sh
		在“echo "Using CATALINA_BASE: $CATALINA_BASE"”上面加入以下行：
		JAVA_OPTS="$JAVA_OPTS -server -Xms800m -Xmx800m -XX:MaxNewSize=256m"
	5.2、其次是：java.lang.OutOfMemoryError: PermGen space
		原因：
		PermGen space的全称是Permanent Generation space,是指内存的永久保存区域，这块内存主要是被JVM存
		放Class和Meta信息的,Class在被Loader时就会被放到PermGen space中，它和存放类实例(Instance)的
		Heap区域不同,GC(Garbage Collection)不会在主程序运行期对PermGen space进行清理，所以如果你的应用
		中有很CLASS的话,就很可能出现PermGen space错误，这种错误常见在web服务器对JSP进行pre compile的
		时候。如果你的WEB APP下都用了大量的第三方jar, 其大小超过了jvm默认的大小(4M)那么就会产生此错误信
		息了。
		解决方法：
		手动设置MaxPermSize大小
		修改TOMCAT_HOME/bin/catalina.bat（Linux下为catalina.sh），在“echo "Using CATALINA_BASE:
		$CATALINA_BASE"”上面加入以下行：
		set JAVA_OPTS=%JAVA_OPTS% -server -XX:PermSize=128M -XX:MaxPermSize=512m
		catalina.sh下为：
		JAVA_OPTS="$JAVA_OPTS -server -XX:PermSize=128M -XX:MaxPermSize=512m"
	===========================
	设置好像有问题，暂时不动。。
				
		
		在项目进行过程中开展有效的监督和控制，消除了多头管理存在的盲区，实现全过程无缝电子监管。实施配套费管控体系，可以及时了解各项配套费工程进展状态，有效实现工作的追踪和掌控，通过对各个流程的统计，分析每个环节的工作效率，工程由原来的粗放管理转变为标准化管理，提升管理精益化水平。各项流程的管控由职能管理转变为业务流程管理，通过流程再造后，改变了以往由于部门职能管理界面不同对业务流转环节的条块分割，使得业务在部门之间流转更加顺畅，由单部门业务处理转变为多专业业务协同处理，充分考虑不同部门的不同管理需求，提高了信息共享度。实现了前端业务执行到财务反映、财务控制、决策分析的横向贯通，实现了预算编制源于业务预测、财务预算控制向前端业务关口前移。通过流程优化，为根据资产负债率预控目标确定投资能力，根据投资能力确定投资规模，有效控制公司经营风险。
		
		
-----------------------------------------------
Navicat 导入数据报错 --- 1153 - Got a packet bigger than 'max_allowed_packet' bytes
解决方法：
在MySQL安装目录下找到文件my.ini，搜索[mysqld]，在其下面添加一句话
max_allowed_packet=400M


修改步骤:
1.设置一下各个职位的权限，使得除客户代表以外的都能够【工程管理】
2.修改两个职位的名称+【服务】两字
3.admin发布包ptfee1027_back.zip
4.使用Navicat修改t_zactivity
5.在Myeclipse中运行ActivityYanShiTest中的testDeploy_10_28

---------------------


更优美的方法：
1.备份原来的t_user和t_party
2.ptfee_2013_11_2.sql



=======================================
发现一个大错误BUG............ V
2013.12.2修复完成
=======================================

每个界面加上工程名称........................ V
======================================= 


吉林省配套费项目修改意见汇总
一、正式用电施工合同，文件上传   ....V
二、60%进度，100%进度，文件上传  V
三、关于流程节点改进(上传文件后，同时录入相关信息) V
概算
预算
决算
设计
监理
金额
时间
单位
①	是否只包括：概算、预算、决算、设计和监理5个模块？
②	这5个模块是否每一个都包括“金额”、“时间”、“单位”三个属性？
③	每一个模块的“金额”、“时间”、“单位”三个属性分别加到流程的哪里？这个需要明确，不然无法具体修改。
④	“临时/正式用电施工合同”里面是否只包括以下三项：
“合同金额”、“签订日期”和“计划开工/竣工日期”？
⑤	最后形成的Excel表中是否需要加入这几项？（需求给的Excel表中没有这些项目，确认一下最后形成的Excel表中到底需要哪些项目，这部分需求不明确，最好以文件的形式列出来，这样可以更好更快地完成修改，不用改完后再重新修改。）

四、上传的扫描件是多个 V
五、文件查看预览，又需要直接调用本地软件打开相应的文档（与之前的要求不一致） V
六、关于付款的百分比
之前项目里付款的百分比分别为：30%，30%，20%，15%，5%；①每次缴费的百分比需要固定调整设定为其它百分比，还是需要设定为可以灵活调整的？之前给的需求说的都是固定不变的。
七、正式用电其他费用借款，文件上传  .....V
八、组织临时用电工程设计|监理招标，中标通知书2个 V
九、允许.docx文件的上传  V
十、关于“待办任务”预览提醒
在刚登陆系统的“待办任务”方面，“待办任务”那个提醒框上面是否需要额外显示以下两项？
①	额外显示“任务时间”；
②	额外显示“任务发送人”。

================
还剩下六、十.....
======================
十 V
-------
六

=============
十的BUG自己实现处理,注意project删除...TaskInfo

=========
TaskInfo taskInfo = taskInfoService.getPAUTaskInfo(proId,
							activity.getId(), user.getId());
错了,去掉最后的userId;

------------------------------------------------------------------
十终于修改完毕.....
=======================
六改完一半。。。。
修改图即可。。。
-----------------------------
修改完毕 
=======2013、12、5 0:49