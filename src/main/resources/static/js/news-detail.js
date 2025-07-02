//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data:{
        input:'',//搜索输入框内容
        userToken:{},//用户信息
        isLogin:false,//是否登录
        news:{},
        drawer:false,//抽屉
        suggestions:[],//
        noRead:null,//未读消息
        checkedSuggestion:{}

    },
    methods:{
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
        //获取url中附带的参数
        getNewsIdByUrl(){
            var currentUrl = window.location.href;
            let url = new URL(currentUrl);
            let id = url.searchParams.get("id");
            return id;
        },
        //获取新闻详细
        getNewsDetail(){
            axios.get('/news/detail',{
                params:{
                    id:this.getNewsIdByUrl()
                }
            }).then(response => {
                this.news=response.data
            }).catch(error => {
                console.error('/scenery/detail请求失败:', error);
            });
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
        this.getNewsDetail()
        this.getAllSuggestion()
    },
    computed: {

    }



});

