//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data(){

        return {
            input: '',//搜索输入框内容
            commendMenu: 1,//推荐导航栏按钮
            scenerys: [],//风景对象
            countTotal: 4,//风景总数
            loading: true,//骨架屏加载
            userToken:{},//用户信息
            isLogin:false,//是否登录
            count: 4,
            listLoading: false,
            selected: '推荐',//推荐，人气，星级选项
            page: ['index.html', 'scenery.html', 'strategy.html', 'news.html', 'suggestion.html'],
            options: [],//地址选项
            topics: [],//主题选项
            stars: [
                {id: 1, name: '3A'},
                {id: 2, name: '4A'},
                {id: 3, name: '5A'},
                {id: 0, name: '无'}],//星级选项
            checkedTopic: '',//被选中的主题
            checkedGrade: '',//被选中的星级
            addressType: '',//被选中的地址类型
            addressValue: '',//被选中的地址
            isLoad:true,
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
            this.scenerys.length=0
            this.loading=true
            if (this.input===''){
                location.reload()
            }else{
                axios.get('/scenery/select',{
                    params: {
                        key:'%'+this.input+'%'
                    }
                }).then(response => {
                    if (this.scenerys.length===0) {
                        this.scenerys = response.data
                    }
                    else{
                        var datas=response.data
                        for (let i = 0; i < datas.length; i++) {
                            this.scenerys.push(datas[i])
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
                if (this.checkedTopic===''&&this.checkedGrade===''&&this.addressValue==='')
                    this.initScenery(this.count)
                this.count += 4
                if (this.count>=this.countTotal)
                    this.count=this.countTotal
                this.listLoading = false
            }, 1000)

        },
        selectAddress(){
            axios.get('/address/all').then(response => {

                response.data.forEach(item => {
                    const { province, city, country } = item;

                    let provinceItem = this.options.find(option => option.label === province);
                    if (!provinceItem) {
                        provinceItem = { value: province, label: province, children: [] };
                        this.options.push(provinceItem);
                    }

                    let cityItem = provinceItem.children.find(option => option.label === city);
                    if (!cityItem) {
                        cityItem = { value: city, label: city, children: [] };
                        provinceItem.children.push(cityItem);
                    }

                    if (country) {
                        cityItem.children.push({ value: country, label: country });
                        cityItem.isLeaf = false; // 设置为非叶子节点
                    } else {
                        cityItem.isLeaf = true; // 设置为叶子节点
                    }
                });
                console.log(this.this.this.options)
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        selectTopic(){
            axios.get('/topic/all').then(response => {
                    this.topics=response.data
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        //处理地址选择的函数
        handleAddressChange(value){
            switch (value.length){
                case 0:
                    this.addressValue=''
                    this.addressType=''
                    break
                case 1:
                    this.addressType='province'
                    this.addressValue=value[0]
                    break;
                case 2:
                    this.addressType='city'
                    this.addressValue=value[1]
                    break;
                case 3:
                    this.addressType='country'
                    this.addressValue=value[2]
                    break;
            }

            this.selectSceneryByCondition()

        },
        selectSceneryByCondition(){
            this.scenerys.length=0
            axios.get('/scenery/condition',{
                params: {
                    grade:this.checkedGrade,
                    topicName:this.checkedTopic,
                    addressType:this.addressType,
                    addressValue:this.addressValue,
                    }
            }).then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    this.scenerys.push(response.data[i])
                }
                this.loading=true
                setTimeout(function () {
                    IndexVue.loading=false
                },500)
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        initScenery(pages){
            axios.get('/scenery/all',{
                params: {
                    page: pages,
                    size:4}
            }).then(response => {
                if (this.scenerys.length===0) {
                    this.scenerys = response.data
                }
                else{
                    var datas=response.data
                    for (let i = 0; i < datas.length; i++) {
                        this.scenerys.push(datas[i])
                    }
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },//查询风景数量
        selectCount(){
            axios.get('/scenery/countAll').then(response => {
                this.countTotal=response.data
            }).catch(error => {
                console.error('scenery/countAll请求失败:', error);
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
        this.selectAddress()
        this.selectTopic()
        this.initScenery(0)
        this.selectCount()
        this.getAllSuggestion()

    },
    computed: {
        noMore () {
            return this.count >= 13
        },
        disabled () {
            return this.listLoading || this.noMore
        }
    }



});

window.addEventListener('scroll', function() {
    if (IndexVue.count>=this.countTotal)
        return

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