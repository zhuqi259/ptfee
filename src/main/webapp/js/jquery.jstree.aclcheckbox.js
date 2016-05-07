
/**
 * 提供jsTree的一个插件：aclcheckbox，主要功能：
 * 在树节点上提供如下状态的显示：
 * 未定义 - 正常显示
 * 拒绝 - checkbox打叉
 * 允许 - checkbox打勾
 * 继承拒绝 - checkbox打叉，同时背景为绿色
 * 继承允许 - checkbox打勾，同时背景为绿色
 */
(function ($) {
	$.jstree.plugin("aclcheckbox", {
		__init : function () {
			this.select_node = this.deselect_node = this.deselect_all = $.noop;
			this.get_selected = this.get_checked;

			this.get_container()
				.bind("open_node.jstree create_node.jstree clean_node.jstree", $.proxy(function (e, data) { 
						this._prepare_checkboxes(data.rslt.obj);
					}, this))
				.bind("loaded.jstree", $.proxy(function (e) {
						this._prepare_checkboxes();
						//插入CSS引用
						var tmp;
						var url = "acl/style/acl.css";
						if(document.createStyleSheet) {
							try { tmp = document.createStyleSheet(url); } catch (e) { }
						}
						else {
							tmp			= document.createElement('link');
							tmp.rel		= 'stylesheet';
							tmp.type	= 'text/css';
							tmp.media	= "all";
							tmp.href	= url;
							document.getElementsByTagName("head")[0].appendChild(tmp);
						}
					}, this))
				.delegate("a", "click.jstree", $.proxy(function (e) {
						//if(this._get_node(e.target).hasClass("jstree-checked")) { this.uncheck_node(e.target); }
						//else { this.check_node(e.target); }
						if(this._get_node(e.target).hasClass("jstree-permit")) { this.deny_node(e.target); }
						else { this.permit_node(e.target); }
						if(this.data.ui) { this.save_selected(); }
						if(this.data.cookies) { this.save_cookie("select_node"); }
						e.preventDefault();
					}, this));
		},
		__destroy : function () {
			this.get_container().find(".jstree-checkbox").remove();
		},
		_fn : {
			_prepare_checkboxes : function (obj) {
				obj = !obj || obj == -1 ? this.get_container() : this._get_node(obj);
				var c, _this = this, t;
				obj.each(function () {
					t = $(this);
					//c = t.is("li") && t.hasClass("jstree-checked") ? "jstree-checked" : "jstree-unchecked";
					//t.find("a").not(":has(.jstree-checkbox)").prepend("<ins class='jstree-checkbox'>&#160;</ins>").parent().not(".jstree-checked, .jstree-unchecked").addClass(c);
					c = t.is("li") && t.hasClass("jstree-permit") ? "jstree-permit" : "jstree-normal";
					t.find("a").not(":has(.jstree-checkbox)").prepend("<ins class='jstree-checkbox'>&#160;</ins>").parent().not(".jstree-permit, .jstree-normal").addClass(c);
				});
				if(obj.is("li")) { this._repair_state(obj); }
				else { obj.find("> ul > li").each(function () { _this._repair_state(this); }); }
			},
			change_state : function (obj, state) {
				obj = this._get_node(obj);
				state = (state === false || state === true) ? state : obj.hasClass("jstree-checked");
				if(state) { obj.find("li").andSelf().removeClass("jstree-checked jstree-undetermined").addClass("jstree-unchecked"); }
				else { 
					obj.find("li").andSelf().removeClass("jstree-unchecked jstree-undetermined").addClass("jstree-checked"); 
					if(this.data.ui) { this.data.ui.last_selected = obj; }
					this.data.aclcheckbox.last_selected = obj;
				}
				obj.parentsUntil(".jstree", "li").each(function () {
					var $this = $(this);
					if(state) {
						if($this.children("ul").children(".jstree-checked, .jstree-undetermined").length) {
							$this.parentsUntil(".jstree", "li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
							return false;
						}
						else {
							$this.removeClass("jstree-checked jstree-undetermined").addClass("jstree-unchecked");
						}
					}
					else {
						if($this.children("ul").children(".jstree-unchecked, .jstree-undetermined").length) {
							$this.parentsUntil(".jstree", "li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
							return false;
						}
						else {
							$this.removeClass("jstree-unchecked jstree-undetermined").addClass("jstree-checked");
						}
					}
				});
				if(this.data.ui) { this.data.ui.selected = this.get_checked(); }
				this.__callback(obj);
			},
			change_permit : function (obj, state) {//在允许与拒绝之间切换,state=true表示拒绝，state=false表示允许
				obj = this._get_node(obj);
				state = (state === false || state === true) ? state : obj.hasClass("jstree-permit");
				
				//拒绝
				if(state) { obj.removeClass("jstree-permit jstree-normal").addClass("jstree-deny"); }
				
				//允许
				else { 
					obj.removeClass("jstree-deny jstree-normal").addClass("jstree-permit"); 
					if(this.data.ui) { this.data.ui.last_selected = obj; }
					this.data.aclcheckbox.last_selected = obj;
				}
				//父节点
				obj.parentsUntil(".jstree", "li").each(function () {
					var $this = $(this);
					if(state) {//拒绝
						//if($this.children("ul").children(".jstree-checked, .jstree-undetermined").length) {
						//	$this.parentsUntil(".jstree", "li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
						//	return false;
						//}
						//else {
						//	$this.removeClass("jstree-checked jstree-undetermined").addClass("jstree-unchecked");
						//}
					}
					else {//允许
						//if($this.children("ul").children(".jstree-deny").length) {
						//	$this.parentsUntil(".jstree", "li").andSelf().removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
						//	return false;
						//}
						//else {
							$this.removeClass("jstree-deny jstree-normal").addClass("jstree-permit");
						//}
					}
				});
				if(this.data.ui) { this.data.ui.selected = this.get_checked(); }
				this.__callback(obj);
			},
			check_node : function (obj) {
				this.change_state(obj, false);
			},
			uncheck_node : function (obj) {
				this.change_state(obj, true);
			},
			permit_node : function (obj) { //允许某个节点（并自动选中允许其所有父节点）
				obj = this._get_node(obj);
				obj.removeClass("jstree-deny jstree-normal jstree-extends").addClass("jstree-permit");
				obj.parentsUntil(".jstree", "li").each(function () {
					var $this = $(this);
					$this.removeClass("jstree-deny jstree-normal jstree-extends").addClass("jstree-permit");
				});
			},
			set_permit_node : function (obj) { //设置允许某个节点，其父节点保持状态不变
				obj = this._get_node(obj);
				obj.removeClass("jstree-deny jstree-normal jstree-extends").addClass("jstree-permit");
			},
			deny_node : function (obj) { //拒绝某个节点
				obj = this._get_node(obj);
				obj.removeClass("jstree-permit jstree-normal jstree-extends").addClass("jstree-deny");
			},
			cancel_node : function (obj) { //取消某个节点
				obj = this._get_node(obj);
				obj.removeClass("jstree-deny jstree-permit jstree-extends").addClass("jstree-normal");
			},
			extends_node : function (obj) { //设置某个节点为继承
				obj = this._get_node(obj);
				//obj.removeClass("jstree-deny jstree-permit").addClass("jstree-normal jstree-extends");
				obj.addClass("jstree-extends");
			},
			permit_all : function (node) { //允许某个节点以及下面的所有子节点（如果参数为空，则表示允许所有的节点）
				var _this = this;
				if(!node){
					this.get_container().find("li").each(function () {
						_this.permit_node(this, false);
					});
				}else{
					node.find("li").andSelf().each(function () {
						_this.permit_node(this, false);
					});
				}
			},
			deny_all : function (node) { //拒绝所有的节点
				var _this = this;
				if(!node){
					this.get_container().find("li").each(function () {
						_this.deny_node(this, false);
					});
				}else{
					node.find("li").andSelf().each(function () {
						_this.deny_node(this, false);
					});
				}
			},
			cancel_all : function (node) { //取消所有的节点
				var _this = this;
				if(!node){
					this.get_container().find("li").each(function () {
						_this.cancel_node(this, false);
					});
				}else{
					node.find("li").andSelf().each(function () {
						_this.cancel_node(this, false);
					});
				}
			},
			extends_all : function (node) { //继承所有的节点
				var _this = this;
				if(!node){
					this.get_container().find("li").each(function () {
						_this.extends_node(this, false);
					});
				}else{
					node.find("li").andSelf().each(function () {
						_this.extends_node(this, false);
					});
				}
			},
			get_all_auths_node : function(){ //获得所有定义了权限的节点（允许、拒绝或继承）
				return this.get_container().find("li.jstree-permit, li.jstree-deny, li.jstree-extends");
			},
			check_all : function () {
				var _this = this;
				this.get_container().children("ul").children("li").each(function () {
					_this.check_node(this, false);
				});
			},
			uncheck_all : function () {
				var _this = this;
				this.get_container().children("ul").children("li").each(function () {
					_this.change_state(this, true);
				});
			},

			is_checked : function(obj) {
				obj = this._get_node(obj);
				return obj.length ? obj.is(".jstree-checked") : false;
			},
			get_checked : function (obj) {
				obj = !obj || obj === -1 ? this.get_container() : this._get_node(obj);
				return obj.find("> ul > .jstree-checked, .jstree-undetermined > ul > .jstree-checked");
			},
			get_unchecked : function (obj) { 
				obj = !obj || obj === -1 ? this.get_container() : this._get_node(obj);
				return obj.find("> ul > .jstree-unchecked, .jstree-undetermined > ul > .jstree-unchecked");
			},

			show_checkboxes : function () { this.get_container().children("ul").removeClass("jstree-no-checkboxes"); },
			hide_checkboxes : function () { this.get_container().children("ul").addClass("jstree-no-checkboxes"); },

			_repair_state : function (obj) {
//				obj = this._get_node(obj);
//				if(!obj.length) { return; }
//				var a = obj.find("> ul > .jstree-checked").length,
//					b = obj.find("> ul > .jstree-undetermined").length,
//					c = obj.find("> ul > li").length;
//
//				if(c === 0) { if(obj.hasClass("jstree-undetermined")) { this.check_node(obj); } }
//				else if(a === 0 && b === 0) { this.uncheck_node(obj); }
//				else if(a === c) { this.check_node(obj); }
//				else { 
//					obj.parentsUntil(".jstree","li").removeClass("jstree-checked jstree-unchecked").addClass("jstree-undetermined");
//				}
			},
			reselect : function () {
				if(this.data.ui) { 
					var _this = this,
						s = this.data.ui.to_select;
					s = $.map($.makeArray(s), function (n) { return "#" + n.toString().replace(/^#/,"").replace('\\/','/').replace('/','\\/'); });
					this.deselect_all();
					$.each(s, function (i, val) { _this.check_node(val); });
					this.__callback();
				}
			}
		}
	});
})(jQuery);
