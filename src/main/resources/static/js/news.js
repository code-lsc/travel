//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data:{
        input:'',//搜索输入框内容
        userToken:{},//用户信息
        isLogin:false,//是否登录
        listLoading:false,
        total:0,//新闻总数
        page:['index.html','scenery.html','strategy.html','news.html','suggestion.html'],
        news:[],
        currentPage:1,/*当前页*/
        isSearch:false,
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
        //搜索内容
        search(){
            if (this.input===''){
                location.reload()
                return
            }
            this.isSearch=true
            this.searchNews(0,10)
        },
        select(item) {
            this.selected = item;
        },
        //分页
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            if (this.isSearch){
                this.searchNews((val-1)*10,10)
            }else {
                this.getAllNews((val-1)*10,10)
            }

        },
        handleRowClick(row) {
            location.href='/site/news-detail.html?id='+row.id
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
        //获取新闻总数
        getAllCount(){
            axios.get('/news/count').then(response => {
                if (response.data){
                    this.total=response.data
                }
            }).catch(error => {
                console.error('/news/count请求失败:', error);
            });
        },
        //获取所有新闻
        //获取新闻总数
        getAllNews(page,size){
            axios.get('/news/all',{
                params:{
                    page:page,
                    size:size
                }
            }).then(response => {
                if (response.data){
                    this.news=response.data
                }
            }).catch(error => {
                console.error('/news/count请求失败:', error);
            });
        },
        searchNews(page,size){
            axios.get('/news/search', {
                params: {
                    key:'%'+this.input+'%',
                    page:page,
                    size:size
                }
            }).then(response => {
                if (response.data) {
                    this.total=response.data.count
                    this.news=response.data.news
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
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
        this.getAllCount()
        this.getAllNews(0,10)
        this.getAllSuggestion()
    },
    computed: {

    }



});

