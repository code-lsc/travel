//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data(){

        return {
            userToken: {},//用户信息
            count:2,//展示风景列表数量
            countTotal:0,//风景列表总数
            scenery:[],//风景对象
            scenerys:[[],[],[],[],[],[],[],[]],//存储8个热门城市已加载的景点数据，避免重复请求后端
            input: '',//搜索输入框内容
            commendMenu: -1,//推荐导航栏按钮
            hotLocation: [],//热门地点
            loading: true,//骨架屏加载
            listLoading: false,
            page: ['index.html', 'scenery.html', 'strategy.html', 'news.html', 'suggestion.html'],
            isLogin: false,//是否登录
            isLoad:true,
            carousel:[],//走马灯
            drawer:false,//抽屉
            suggestions:[],//
            noRead:null,//未读消息
            checkedSuggestion:{}
        }
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
            this.scenery.length=0
            this.loading=true
            if (this.input===''){
                location.reload()
            }else{
                axios.get('/scenery/search',{
                    params: {
                        key:'%'+this.input+'%'
                    }
                }).then(response => {
                    var datas=response.data
                    console.log(datas)
                    if (datas){
                        var arr1=[]
                        for (var i = 0; i < datas.length; i++) {
                            arr1.push(datas[i])
                            if (arr1.length === 2) {
                                this.scenery.push(arr1);
                                arr1 = []; // 重置 arr1
                            }
                        }
                        if (arr1.length>0){
                            this.scenery.push(arr1)
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
            this.commendMenu=index
            this.scenery.length=0
            if (index === -1){
                this.getScenery(0)
            }else {
                this.hotCityScenery(0,this.hotLocation[index]+'市',0)
            }
            setTimeout(function () {
                IndexVue.loading=false
            },500)
        },
        //返回走马灯图片url
        getImgUrl(){
            axios.get('/scenery/carousel').then(response => {
                this.carousel=response.data
            }).catch(error => {
                console.error('scenery/countAll请求失败:', error);
            });
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
                //获取风景信息
                if (this.count+1<=this.countTotal) {
                    if (this.commendMenu === -1) {
                        this.getScenery(this.count * 2)
                    } else {
                        this.hotCityScenery(this.count * 2, this.hotLocation[this.commendMenu] + '市', this.commendMenu + 1)
                    }
                }
                this.count += 2
                if (this.count>=this.countTotal)
                    this.count=this.countTotal
                this.listLoading = false

            }, 1000)

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
        goProfile(){
            alert('a')
        },
        //获取风景异步请求
        getScenery(pages){
            axios.get('/scenery/hotScenery',{
                params: {
                    page: pages,
                    size:4}
            }).then(response => {
                var datas=response.data
                if (datas){
                    var arr1=[]
                    var arr2=[]
                    for (var i = 0; i < datas.length; i++) {
                        if (i<2){
                            arr1.push(datas[i])
                        }else {
                            arr2.push(datas[i])
                        }
                    }
                    if (arr1.length>0){
                        this.scenery.push(arr1)
                    }
                    if (arr2.length>0){
                        this.scenery.push(arr2)
                    }
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        //根据城市获取风景
        hotCityScenery(pages,city,index){
            axios.get('/scenery/hotCityScenery',{
                params: {
                    page: pages,
                    size:4,
                    city:city}
            }).then(response => {
                var datas=response.data
                if (datas){
                    var arr1=[]
                    var arr2=[]
                    for (var i = 0; i < datas.length; i++) {
                        if (i<2){
                            arr1.push(datas[i])
                        }else {
                            arr2.push(datas[i])
                        }
                    }
                    if (arr1.length>0){
                        this.scenery.push(arr1)
                    }
                    if (arr2.length>0){
                        this.scenery.push(arr2)
                    }
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        //点击头像跳转登录页面
        toLogin(){
            alert('aaa')
            window.location.href='/site/login.html'
        },
        getHotCity(){
            axios.get('/admin/hotCity/get').then(response => {
                this.hotLocation=response.data
            }).catch(error => {
                console.error('/scenery/carousel/add请求失败:', error);
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
        this.loadingView()
        //获取用户信息
        axios.get('/user/info').then(response => {
            if (response.data) {
                this.userToken = response.data
                this.isLogin=true
            }
        }).catch(error => {
            console.error('user/info请求失败:', error);
        });
        //获取风景列表总数
        axios.get('/scenery/countAll').then(response => {
            this.countTotal=Math.ceil(response.data/2)
        }).catch(error => {
            console.error('scenery/countAll请求失败:', error);
        });
        this.getImgUrl()
        //获取风景信息
        this.getScenery(0)
        this.getHotCity()
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
        /*if (IndexVue.count>=IndexVue.countTotal)
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