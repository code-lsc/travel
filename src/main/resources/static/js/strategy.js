//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data:{
        input:'',//搜索输入框内容
        isLogin:false,//登录状态
        userToken:{},//用户信息
        loading: true,//骨架屏加载
        currentDate: '2021-06-01',
        countTotal:2,
        count:2,
        listLoading:false,
        selected: null,//推荐，人气，星级选项
        page:['index.html','scenery.html','strategy.html','news.html','suggestion.html'],
        div:'',
        scenery:[],//风景列表
        checkedScenery:'',//被选中的风景
        strategys:[],//所有攻略信息
        isLoad:true,
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
            this.isLoad=false
            this.strategys.length=0
            this.loading=true
            if (this.input===''){
                location.reload()
            }else{
                axios.get('/strategy/search',{
                    params: {
                        key:'%'+this.input+'%'
                    }
                }).then(response => {
                    var datas=response.data
                    datas.sort((a, b) => {
                        // 先比较点赞数量
                        if (b.likeNumber !== a.likeNumber) {
                            return b.likeNumber - a.likeNumber;
                        }
                        // 如果点赞数量相同，则比较发布日期
                        return new Date(b.createTime) - new Date(a.createTime);
                    });
                    console.log(datas)
                    if (datas){
                        var arr1=[]
                        for (var i = 0; i < datas.length; i++) {
                            arr1.push(datas[i])
                            if (arr1.length === 3) {
                                this.strategys.push(arr1);
                                arr1 = []; // 重置 arr1
                            }
                        }
                        if (arr1.length>0){
                            this.strategys.push(arr1)
                        }
                    }
                }).catch(error => {
                    console.error('scenery/hotScenery请求失败:', error);
                });
            }
            this.loading=false


        },
        //旅游推荐的菜单选择
        tripTypeMenu(index){
            this.loading=true
            this.count=2
            setTimeout(function () {
                IndexVue.loading=false
            },500)
        },
        //加载骨架屏
        loadingView(){
            this.loading=false
        },
        load () {
            if (this.count>=this.countTotal || !this.isLoad)
                return
            this.listLoading = true
            setTimeout(() => {
                this.getAllStrategy(this.count*3)
                this.count += 2
                if (this.count>=this.countTotal)
                    this.count=this.countTotal
                this.listLoading = false
            }, 1000)

        },
        select(item) {
            this.selected = item;
        },
        //查询所有风景名
        selectAllSceneryName(){
            axios.get('/scenery/name').then(response => {
                    // 将字符串数组转换为el-option期望的格式
                    this.scenery=response.data.map(item => ({ label: item, value: item }));
            }).catch(error => {
                console.error('scenery/name请求失败:', error);
            });
        },
        //选择风景后
        checkedSceneryFunction(value){
            this.loading=true
            this.strategys.length=0
            this.getAllStrategy(0)
            setTimeout(()=>{
                this.loading=false;
            },500)
        },
        toEditor(){
            if (!this.isLogin){
                this.$message.error('您还未登录')
                return
            }
            sessionStorage.setItem('title', '发布攻略');
            window.location.href='/site/editor.html'
        },
        //获取攻略总数
        getStrategyCounts(){
            axios.get('/strategy/count',{
                params: {
                    sceneryName:this.checkedScenery
                }
            }).then(response => {
                this.countTotal=Math.ceil(response.data/3)
            }).catch(error => {
                console.error('scenery/countAll请求失败:', error);
            });
        },
        //获取攻略信息
        getAllStrategy(pages){
            axios.get('/strategy/all',{
                params: {
                    page: pages,
                    size:6,
                    sceneryName:this.checkedScenery}
            }).then(response => {
                var datas=response.data
                datas.sort((a, b) => {
                    // 先比较点赞数量
                    if (b.likeNumber !== a.likeNumber) {
                        return b.likeNumber - a.likeNumber;
                    }
                    // 如果点赞数量相同，则比较发布日期
                    return new Date(b.createTime) - new Date(a.createTime);
                });
                console.log(datas)
                if (datas){
                    var arr1=[]
                    var arr2=[]
                    for (var i = 0; i < datas.length; i++) {
                        if (i<3){
                            arr1.push(datas[i])
                        }else {
                            arr2.push(datas[i])
                        }
                    }
                    if (arr1.length>0){
                        this.strategys.push(arr1)
                    }
                    if (arr2.length>0){
                        this.strategys.push(arr2)
                    }
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });


        },
        //点赞
        clickLike(strategy){
            if (!this.isLogin){
                this.$message.error('您还未登录');
                return
            }
            strategy.isLike=!strategy.isLike
            if (strategy.isLike){
                strategy.likeNumber++
            }else {
                strategy.likeNumber--
            }
            axios.get('/strategy/like',{
                params: {
                    id:strategy.id,
                    type:0}
            }).then(response => {}).catch(error => {
                console.error('/strategy/like请求失败:', error);
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
        this.loadingView()
        this.getUserInfo()
        this.getStrategyCounts()
        this.selectAllSceneryName()
        this.getAllStrategy(0)
        this.getAllSuggestion()


    },
    computed: {
        noMore () {
            return this.count >= this.countTotal
        },
        disabled () {
            return this.listLoading || this.noMore
        }
    }



});

window.addEventListener('scroll', function() {
    /*if (IndexVue.count>=13)
        return*/

    //变量scrollTop是滚动条滚动时，距离顶部的距离
    let scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
    //变量windowHeight是可视区的高度
    let windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
    //变量scrollHeight是滚动条的总高度
    let scrollHeight = document.documentElement.scrollHeight||document.body.scrollHeight;
    let dataArr = []
    //滚动条到底部的条件

    if(scrollHeight-Math.floor(scrollTop + windowHeight)<2){
        IndexVue.load()
    }

});