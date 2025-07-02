var editor={}
var IndexVue = new Vue({
    el: "#page1",
    data(){
        return{
            selectMenu:'1',//选择的那个选项
            imageForm:[''],//走马灯图片
            hotCity:[''],//热门城市
            isLogin:false,//用户登录状态
            userToken:{},//用户信息
            users:[],//用户列表
            sceneryForm:{
                name:'',
                grade:'0',
                topic:[],
                address:{id:'',province:null,city:null,country:null},
                profile:'',
                status:false,
                ticketPrice:0,
                attention:'',
                description:'',
                imageUrl:'',
                urls:['']
            },//风景表单
            address:[],
            checkedAddress:[],
            topics:[],//主题
            scenery:[],
            dialogFormVisible:false,//修改信息的对话框
            checkedScenery:{},//选中的修改的风景
            news:{title:'',content:''},
            suggestions:[],
            suggestionDialogVisible:false,//投诉详情对话框
            checkedSuggestion:{},//选中的建议
            reply: {sid:0,content:''},//对象
            replyDialog:false,//回复内容对话框
            sceneryInput:'',//风景搜索框
            newsInput:'',//新闻输入框
            orders:[],//所有订单信息
            rules: {
                imageUrl: [
                    {required: true, message: '首页图片不能为空', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '名称不能为空', trigger: 'blur'}
                ],
                grade: [
                    {required: true, message: '等级不能为空', trigger: 'change'}
                ],
                topic: [
                    {required: true, message: '主题不能为空', trigger: 'change'}
                ],
                imageUrl: [
                    {required: true, message: '首页图片不能为空', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '名称不能为空', trigger: 'blur'}
                ],
                grade: [
                    {required: true, message: '等级不能为空', trigger: 'change'}
                ],
                address: [
                    {required: true, message: '地址不能为空', trigger: 'change'}
                ],
                profile: [
                    {required: true, message: '景区简介不能为空', trigger: 'blur'}
                ],
                ticketPrice: [
                    {required: true, message: '票价不能为空', trigger: 'blur'}
                ],
                attention: [
                    {required: true, message: '预定须知不能为空', trigger: 'blur'}
                ],
                description: [
                    {required: true, message: '景区描述不能为空', trigger: 'blur'}
                ],
                urls: [
                    {
                        required: true,
                        type: 'array',
                        min: 1,
                        message: '详情图片不能为空',
                        trigger: 'change'
                    }
                ]
            }

        }
    },
    methods:{
        handleMenuSelect(index){
            if (!this.isLogin){
                this.$message.error('您还未登录');
                return
            }
            this.selectMenu=index
            switch (index){
                case '1-1':
                    this.getCarousel()
                    break;
                case '1-2':
                    this.getHotCity()
                    break;
                case '2':
                    this.getAllUser()
                    break
                case '3-1':
                    this.selectAddress()
                    this.clearSceneryForm()
                    break;
                case '3-2':
                    this.getAllScenery()
                    break;
                case '3-3':
                    this.$nextTick(() => {
                        this.loadEditor()
                    });
                    break;
                case '3-4':
                    this.getAllNews()
                    break;
                case '3-5':
                    this.getAllSuggestion()
                    break;
                case '4':
                    this.getAllOrder()
                    break
            }
        },
        addInput(type) {
            console.log(this.sceneryForm.topic)
            if (type === 0){
                this.imageForm.push(''); // 添加一个新的输入框
            }else {
                this.sceneryForm.urls.push('');
            }

        },
        removeInput(index,type) {
            if (type === 0){
                this.imageForm.splice(index, 1); // 删除指定索引的输入框
            }else {
                this.sceneryForm.urls.splice(index,1)
            }

        },
        //修改走马灯
        updateCarousel(){
            axios.post('/scenery/carousel/add',this.imageForm).then(response => {
                this.$message.success('修改成功')
            }).catch(error => {
                console.error('/scenery/carousel/add请求失败:', error);
            });
        },
        //查找走马灯
        getCarousel(){
            axios.get('/scenery/carousel').then(response => {
                this.imageForm=response.data
            }).catch(error => {
                console.error('/scenery/carousel/add请求失败:', error);
            });
        },
        //获取热门城市
        getHotCity(){
            axios.get('/admin/hotCity/get').then(response => {
                this.hotCity=response.data
            }).catch(error => {
                console.error('/scenery/carousel/add请求失败:', error);
            });
        },
        //修改热门城市
        updateHotCity(){
            axios.post('/admin/hotCity/update',this.hotCity).then(response => {
                this.$message.success('修改成功')
            }).catch(error => {
                console.error('/scenery/carousel/add请求失败:', error);
            });
        },
        //退出登录
        logout(command){
            if (command === 'logout'){
                // 处理退出的逻辑
                axios.get('/user/logout').then(response => {
                    if (response.data===true)
                        location.reload()
                }).catch(error => {
                    console.error('scenery/hotScenery请求失败:', error);
                });
            }
        },
        //获取用户信息
        getUserInfo(){
            axios.get('/user/info').then(response => {
                if (response.data) {
                    if (response.data.type===1){
                        this.userToken = response.data
                        this.isLogin=true
                    }
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //取消或设置管理员,或冻结账号
        updateUserInfo(index,item,type){
            if (type===0){
                item.type=!item.type
            }else if (type===1){
                item.status=!item.status
            }
            let type1=0
            let status=0
            if (item.type){
                type1=1
            }
            if (item.status){
                status=1
            }
            axios.get('/admin/user/update',{
                params:{
                    id:item.id,
                    type:type1,
                    status:status
                }
            }).then(response => {
                if (response.data) {
                    this.$message.success('设置成功')
                }else {
                    this.$message.error('设置失败')
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //获取所有用户信息
        getAllUser(){
            axios.get('/admin/user/all').then(response => {
                if (response.data) {
                    this.users=response.data
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
            console.log(this.users)
        },
        selectAddress(){
            axios.get('/address/all').then(response => {
                response.data.forEach(item => {
                    const { province, city, country } = item;

                    let provinceItem = this.address.find(option => option.label === province);
                    if (!provinceItem) {
                        provinceItem = { value: province, label: province, children: [] };
                        this.address.push(provinceItem);
                    }

                    if (country) {
                        let cityItem = provinceItem.children.find(option => option.label === city);
                        if (!cityItem) {
                            cityItem = { value: city, label: city, children: [] };
                            provinceItem.children.push(cityItem);
                        }

                        cityItem.children.push({ value: country, label: country });
                        cityItem.isLeaf = false; // 设置为非叶子节点
                    } else {
                        if (city !== province) { // 排除直辖市本身的情况
                            provinceItem.children.push({ value: city, label: city });
                        }
                    }
                });
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        //发布景点信息
        submitScenery(){
            this.$refs['sceneryForm'].validate(valid => {
                if (valid) {
                    let status=0
                    if (this.sceneryForm.status){
                        status=1
                    }
                    this.sceneryForm.status=status
                    axios.post('/admin/scenery/add',this.sceneryForm).then(response => {
                        if (response.data) {
                            this.$message.success('添加成功')
                            this.clearSceneryForm()
                        }else {
                            this.$message.error('添加失败')
                        }
                    }).catch(error => {
                        console.error('user/info请求失败:', error);
                    });
                } else {

                    return false;
                }
            })
        },
        getTopic(){
            axios.get('/topic/all').then(response => {
                if (response.data) {
                    this.topics=response.data
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        handleSelectAddress(item){
            this.sceneryForm.address.province=item[0]
            this.sceneryForm.address.city=item[1]
            if (item.length===3)
                this.sceneryForm.address.country=item[2]

        },
        //把表单清除干净
        clearSceneryForm(){
            this.sceneryForm={
                name:'',
                grade:'0',
                topic:[],
                status:false,
                address:{province:null,city:null,country:null},
                profile:'',
                ticketPrice:0,
                attention:'',
                description:'',
                imageUrl:'',
                urls:['']
            }
            this.checkedAddress=[]
        },
        //获取所有风景列表
        getAllScenery(){
            axios.get('/admin/scenery/all').then(response => {
                if (response.data) {
                    this.scenery=response.data
                    console.log(this.scenery)
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        loadSceneryInfoById(index,item){
            this.checkedScenery=item
            axios.get('/admin/scenery/search',{
                params: {
                    id:item.id
                }
            }).then(response => {
                if (response.data) {
                    this.sceneryForm=response.data
                    if (this.sceneryForm.urls.length===0)
                        this.sceneryForm.urls=['']
                        this.checkedAddress[0]=this.sceneryForm.address.province
                        this.checkedAddress[1]=this.sceneryForm.address.city
                        if (this.sceneryForm.address.country !==null || this.sceneryForm.address.country!=='' )
                            this.checkedAddress[2]=this.sceneryForm.address.country
                        let topic=this.sceneryForm.topic
                    for (let i = 0; i < topic.length; i++) {
                        for (let j = 0; j < this.topics.length; j++) {
                            if (topic[i]===this.topics[j].id){
                                topic[i]=this.topics[j].name
                            }
                        }
                    }
                    this.sceneryForm.grade=this.sceneryForm.grade+''
                    if (this.sceneryForm.status === 1){
                        this.sceneryForm.status=true
                    }else {
                        this.sceneryForm.status=false
                    }
                }
                console.log(this.sceneryForm)
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
            this.selectAddress()
            this.dialogFormVisible=true
        },
        //修改风景信息
        updateSceneryInfo(){
            this.$refs['sceneryForm'].validate(valid => {
                if (valid) {
                    let status=0
                    if (this.sceneryForm.status){
                        status=1
                    }
                    this.sceneryForm.status=status
                    let topic=this.sceneryForm.topic
                    for (let i = 0; i < topic.length; i++) {
                        for (let j = 0; j < this.topics.length; j++) {
                            if (topic[i]===this.topics[j].name){
                                topic[i]=this.topics[j].id
                            }
                        }
                    }
                    axios.post('/admin/scenery/update',this.sceneryForm).then(response => {
                        if (response.data) {
                            this.$message.success('修改成功')
                            this.clearSceneryForm()
                            this.getAllScenery()
                            let form=this.sceneryForm
                            this.checkedScenery.name=form.name
                            this.checkedScenery.grade=form.grade
                            this.checkedScenery.ticketPrice=form.ticketPrice
                            this.checkedScenery.status=form.status
                            this.dialogFormVisible=false
                        }else {
                            this.$message.error('修改失败')
                        }
                    }).catch(error => {
                        console.error('user/info请求失败:', error);
                    });
                } else {

                    return false;
                }
            })
        },
        //加载富文本编辑框
        loadEditor(){
                const { createEditor, createToolbar } = window.wangEditor
                const editorConfig = {
                placeholder: 'Type here...',
                onChange(editor) {
                const html = editor.getHtml()
                IndexVue.news.content=html
                // 也可以同步到 <textarea>
            }
            }

                editor = createEditor({
                selector: '#editor-container',
                html: '<p><br></p>',
                config: editorConfig,
                mode: 'default', // or 'simple'
            })

                const toolbarConfig = {}

                const toolbar = createToolbar({
                editor,
                selector: '#toolbar-container',
                config: toolbarConfig,
                mode: 'default', // or 'simple'
            })

        },
        //发布新闻
        addNews(){
            axios.post('/news/add',this.news).then(response => {
                if (response.data) {
                    this.$message.success('发布成功')
                    this.clearNews()
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //清除新闻输入框
        clearNews(){
            this.news.title=''
            this.news.content=''
            editor.setHtml('')

        },
        getAllNews(){
            axios.get('/news/getAll').then(response => {
                if (response.data) {
                    this.news=response.data
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //修改新闻
        updateNews(index,row){
            row.status=!row.status
            axios.get('/news/delete',{
                params:{
                    id:row.id,
                    status:row.status?1:0
                }
            }).then(response => {
                if (response.data) {
                    this.$message.success('修改成功')
                }else {
                    this.$message.error('修改失败')
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //获取所有投诉建议
        getAllSuggestion(){
            axios.get('/suggestion/all').then(response => {
                if (response.data) {
                    this.suggestions=response.data
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //查看投诉详情
        viewSuggestion(index,row){
            this.checkedSuggestion=row
            this.suggestionDialogVisible=true
        },
        //处理投诉
        handleSuggestion(index,row){
            this.checkedSuggestion=row
            this.replyDialog=true
            this.reply.sid=row.id

        },
        //确认回复
        sendReply(){
            axios.post('/suggestion/reply',this.reply).then(response => {
                if (response.data) {
                    this.$message.success('回复成功')
                }else {
                    this.$message.error('回复失败')
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
            this.checkedSuggestion.status=1
            const date = new Date();
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');
            const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            this.reply.createTime=formattedDate
            this.checkedSuggestion.reply=this.reply
            this.replyDialog=false
        },
        searchScenery(){
            axios.get('/news/select',{
                params: {
                    key:'%'+this.newsInput+'%'
                }
            }).then(response => {
                var datas=response.data
                if (datas){
                    this.news=datas
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        getAllOrder(){
            axios.get('/order/getAll').then(response => {
                var datas=response.data
                if (datas){
                    this.orders=datas
                }
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        quitTicket(index,row){
            axios.get('/order/delete',{
                params:{
                    id:row.id
                }
            }).then(response => {
                this.$message.success('操作成功');
                this.orders.splice(index,1)
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        },
        writeOff(index,row){
            axios.get('/order/status',{
                params:{
                    id:row.id
                }
            }).then(response => {
                this.$message.success('操作成功');
                row.status=1
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        }



    },
    mounted(){
        this.getUserInfo()
        this.getTopic()
    }
})