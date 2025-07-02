var IndexVue = new Vue({
    el: "#page1",
    data(){
        return {
            selectMenu: '',
            disabled: false,//是否禁用图像上传按钮
            isLogin: false,//是否登录
            userInfo: {id: 0, name: '', sex: 1, age: 18, phone: "12348590",address:{}},
            oldPassword: '',
            newPassword: '',
            okPassword: '',
            order: [],//订单信息
            orderEvaluateDialog: false,//订单评价框
            score: 0,//评分
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            judgement: '',//评价内容
            checkedOrder: {},//当前选中的订单
            strategy: [],
            address: [],//地址信息
            checkedAddress: []
        }


    },
    methods:{
        handleMenuSelect(index){
            this.selectMenu=index
            switch (index){
                case '2':
                    this.getOrderInfo()
                    break;
                case '3':
                    this.getStrategyInfo()
                    break;
                case '4':
                    break;
            }
        },
        handleFileChange(file, fileList) {
            // 获取选中的文件数量
            const selectedFileCount = fileList.length;
            if (selectedFileCount>0){
                this.disabled=true
            }else{
                this.disabled=false
            }
        },
        updateHeadUrl() {
                this.$refs.upload.submit(); // 触发上传
                this.$message.success('修改成功')

            },
        //获取用户信息
        getUserInfo(){
            axios.get('/user/info').then(response => {
                let item=response.data
                if (item) {
                    this.userInfo=item
                    this.isLogin=true
                    this.checkedAddress[0]=item.address.province
                    this.checkedAddress[1]=item.address.city
                    if (item.address.country !==null || item.address.country!=='' )
                        this.checkedAddress[2]=item.address.country
                }
            }).catch(error => {
                console.error('user/info请求失败:', error);
            });
        },
        //修改用户信息
        updateUserInfo(){
            axios.post('/user/update',this.userInfo).then(response => {
               if (response.data===true)
                   this.$message.success('修改成功');
               else
                   this.$message.error('修改失败');
            }).catch(error => {
                console.error('user/update请求失败:', error);
            });
        },
        //修改密码
        updatePassword(){
            if (this.newPassword!==this.okPassword){
                this.$message.error('确认密码不一致');
            }else {
                axios.post('/user/password', {
                    oldPassword: this.oldPassword,
                    newPassword: this.newPassword
                }).then(response => {
                    if (response.data==='error'){
                        this.$message.error('密码错误');
                    }else if (response.data==='ok'){
                        this.$message.success('修改成功');
                        setTimeout(function() {
                            location.href='/index.html'
                        }, 800);
                    }else if (response.data==='ok'){
                        this.$message.error('修改失败');
                    }
                }).catch(error => {
                    console.error('Post请求失败:', error);
                });
            }
        },
        //获取订单信息
        getOrderInfo(){
            if (!this.isLogin)
                return
            axios.get('/order/all').then(response => {
                let datas = response.data;
                if (datas){
                    datas.sort((a,b)=>{return b.createTime-a.createTime});
                    this.order=datas;
                }
                console.log(this.order)
            }).catch(error => {
                console.error('user/update请求失败:', error);
            });
        },
        //评价订单
        evaluate(item){
            this.orderEvaluateDialog=true
            this.checkedOrder=item
        },
        //提交评价
        evaluationSubmit(){
            this.orderEvaluateDialog=false
            axios.post('/order/comment',{
                orderId:this.checkedOrder.id,
                content:this.judgement,
                score:this.score
            }).then(response => {
                if (response.data){
                    this.checkedOrder.status=2
                    this.$message.success("评价成功")
                    this.score=0
                    this.judgement=''
                }else {
                    this.$message.error("评价失败")
                }
            }).catch(error => {
                console.error('user/update请求失败:', error);
            });
        },
        //获取攻略信息
        getStrategyInfo(){
            if (!this.isLogin)
                return
            axios.get('/strategy/setting',{
                params: {
                    uid:this.userInfo.id
                }
            }).then(response => {
                let datas = response.data;
                if (datas){
                    this.strategy=datas
                }
            }).catch(error => {
                console.error('user/update请求失败:', error);
            });
        },
        handleView(index, row) {
            location.href='/site/strategy-detail.html?id='+row.id
        },
        handleDelete(index, row) {
            if (!this.isLogin)
                return
            axios.get('/strategy/delete',{
                params: {
                    strategyId:row.id
                }
            }).then(response => {
                if (response.data){
                    this.strategy.splice(index,1)
                    this.$message.success('删除成功');
                }else {
                    this.$message.error('删除失败');
                }
            }).catch(error => {
                console.error('user/update请求失败:', error);
            });

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
        handleSelectAddress(item){
            this.userInfo.address.province=item[0]
            this.userInfo.address.city=item[1]
            if (item.length===3)
                this.userInfo.address.country=item[2]
        },
        quitTicket(id){
            axios.get('/order/delete',{
                params:{
                    id:id
                }
            }).then(response => {
                this.$message.success('退票成功');
                this.getOrderInfo()
            }).catch(error => {
                console.error('scenery/hotScenery请求失败:', error);
            });
        }
    },
    mounted(){
       this.getUserInfo()
        this.selectAddress()
    }


    

})
