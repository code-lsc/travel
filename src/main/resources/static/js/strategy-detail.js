//绑定值
var IndexVue = new Vue({
    el: "#page1",
    data(){
        return {
            userToken: {},//用户信息
            isLogin: false,//是否登录
            date:'',
            strategy:{},
            commentDialog:-1,//评论框
            comment:'',//评论
            strategyComment:'',
            drawer:false,//抽屉
            suggestions:[],//
            noRead:null,//未读消息
            checkedSuggestion:{}
        }
    },
    methods:{
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
        //打开指定的评论框
        openComment(id){
            this.commentDialog=id
            this.comment=''
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
        //点击头像跳转登录页面
        toLogin(){
            alert('aaa')
            window.location.href='/site/login.html'
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
        //获取url中附带的参数
        getStrategyIdByUrl(){
            var currentUrl = window.location.href;
            let url = new URL(currentUrl);
            let id = url.searchParams.get("id");
            return id;
        },
        initial(){
            axios.get('/strategy/detail',{
                params:{
                    id:this.getStrategyIdByUrl()
                }
            }).then(response => {
                if (response.data) {
                    this.strategy=response.data
                    console.log(this.strategy)
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        clickLike(type,item){
            if (!this.isLogin){
                this.$message.error('您还未登录');
                return
            }
            item.isLike=!item.isLike
            if (item.isLike){
                item.likeNumber++
            }else {
                item.likeNumber--
            }
            axios.get('/strategy/like',{
                params: {
                    id:item.id,
                    type:type}
            }).then(response => {}).catch(error => {
                console.error('/strategy/like请求失败:', error);
            });
        },
        //发布评论
        addComment(type,item){
            if (!this.isLogin){
                this.$message.error('您还未登录');
                return
            }
            let commentEntity={
                uid:this.userToken.id,
                type:type,
                entityId:item.id,
                targetId:item.uid,
                content:this.strategyComment
            }
            if (type===1){
                commentEntity.content=this.comment
            }
            axios.post('/strategy/comment',commentEntity).then(response => {
                if (response.data){
                    this.$message.success('评论成功');
                    location.reload()
                }else {
                    this.$message.error('评论失败');
                }
            }).catch(error => {
                console.error('/strategy/like请求失败:', error);
            });

        },
        //删除评论
        deleteComment(item){
            axios.get('/strategy/comment/delete', {
                params:{
                    commentId:item.id
                }
            }).then(response => {
                if (response.data){
                    this.$message.success('删除成功');
                    location.reload()
                }else {
                    this.$message.error('删除失败');
                }
            }).catch(error => {
                console.error('/strategy/like请求失败:', error);
            });
        },
        //删除攻略
        deleteStrategy(item){
            axios.get('/strategy/delete', {
                params:{
                    strategyId:item.id
                }
            }).then(response => {
                if (response.data){
                    window.location.href='/site/strategy.html'
                    this.$message.success('删除成功');
                }else {
                    this.$message.error('删除失败');
                }
            }).catch(error => {
                console.error('/strategy/like请求失败:', error);
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
        this.initial()
        this.getAllSuggestion()


    },
    computed: {

    }



});

