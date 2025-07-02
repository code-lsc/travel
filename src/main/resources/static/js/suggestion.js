//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data:{
        input:'',//搜索输入框内容
        userToken:{},//用户信息
        isLogin:false,//是否登录
        page:['index.html','scenery.html','strategy.html','news.html','suggestion.html'],
        form:{title:'',type: '',options: [],content:'',name:'',phone:''},
        rules: {
            title: [
                { required: true, message: '请输入标题', trigger: 'blur' },
            ],
            type: [
                { required: true, message: '请选择类型', trigger: 'change' },
            ],
            options: [
                { required:true,validator: (rule, value, callback, source, optionss) => {
                        if (value.length === 0) {
                            callback(new Error('请选择投诉内容'));
                        } else {
                            callback();
                        }
                    }, trigger: 'change'},
            ],
            content: [
                { required: true, message: '请输入内容', trigger: 'blur' },
            ],
            name: [
                { required: true, message: '请输入姓名', trigger: 'blur' },
                { pattern: /^([\u4e00-\u9fa5]+|[a-zA-Z]+)$/, message: '姓名必须为中文或英文', trigger: 'blur' }
            ],
            phone: [
                { required: true, message: '请输入联系方式', trigger: 'blur' },
                { pattern: /^(1\d{10})$|^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/, message: '联系方式格式不正确', trigger: 'blur' },
            ],
        },
        drawer:false,//抽屉
        suggestions:[],//
        noRead:null,//未读消息
        checkedSuggestion:{}
    },
    methods:{
        //菜单栏触发
        selectMenu(index){
            var rootPath = window.location.origin+'/';
            if (index!=1)
                rootPath+='site/'
            window.location.href = rootPath+this.page[index-1]
        },
        select(item) {
            this.selected = item;
        },
        onTypeChange(value) {
            if (value === '0' || value === '1') {
                this.$set(this.form, 'options', [])
            } else {
                this.$delete(this.form, 'options')
            }
        },
        //提交表单
        onSubmit() {
            if (!this.isLogin){
                this.$message.error('您还未登录')
                return
            }
            this.$refs.form.validate(valid => {
                if (valid) {
                    if (this.form.type==='0'){
                        let s='['
                        for (let i = 0; i < this.form.options.length; i++) {
                            s=s+this.form.options[i];
                            if (i+1==this.form.options.length){
                                s+=']'
                            }else {
                                s+=','
                            }
                        }
                        this.form.options=s;
                        this.form.type=0;
                    }else {
                        this.form.type=1;
                    }
                    console.log(this.form)
                    axios.post('/suggestion/add',this.form).then(response => {
                            if (response.data){
                                this.$message.success('发送成功');
                                this.form={}
                            }else {
                                this.$message.error('发送失败');
                            }

                    }).catch(error => {
                        console.error('/scenery/detail请求失败:', error);
                    });
                }
            });
        },
        //获取用户信息
        getUserInfo(){
            axios.get('/user/info').then(response => {
                if (response.data) {
                    this.userToken = response.data
                    this.isLogin=true
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        handlePersonalCenter() {
            // 处理个人中心的逻辑
            location.href='/site/setting.html'
        },
        handleMessage() {
            // 处理消息的逻辑
            this.drawer=true
        },
        handleLogout() {
            // 处理退出的逻辑
            axios.get('/user/logout').then(response => {
                if (response.data===true)
                    location.reload()
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        handleCommand(command) {
            // 根据 command 的值来执行不同的逻辑
            switch (command) {
                case 'center':
                    this.handlePersonalCenter();
                    break;
                case 'message':
                    this.handleMessage();
                    break;
                case 'logout':
                    this.handleLogout();
                    break;
                default:
                    break;
            }
        },
        getAllSuggestion(){
            axios.get('/suggestion/uid').then(response => {
                if (response.data) {
                    console.log(response.data)
                    this.suggestions=response.data
                    let count=0
                    for (let i = 0; i < this.suggestions.length; i++) {
                        if (this.suggestions[i].reply.status===0)
                            count++
                    }
                    if (count>0)
                        this.noRead=count
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        viewReply(index,row){
            this.checkedSuggestion=row
            if (row.reply.status===1)
                return
            row.reply.status=1
            axios.get('/suggestion/reply/read',{
                params:{
                    id:row.reply.id
                }
            }).then(response => {
                this.noRead--
                if (this.noRead===0)
                    this.noRead=null
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        }


    },
    mounted(){
        this.getUserInfo()
        this.getAllSuggestion()
    },
    computed: {

    }



});

