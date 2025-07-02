let contents=''
var IndexVue = new Vue({
    el: "#page1",
    data(){
        return{
            pageTitle:'发布攻略',
            dialogImageUrl: '',
            dialogVisible: false,
            disabled: false,
            title:'',//标题
            userId:'',//用户id
            isLogin:false,
            form:{title:'',content:'',scenerys:[]},
            actionUrl:'/strategy/add',
            scenery:[],
            checkedScenery:[],
            userToken:{}
        }
    },
    methods:{
        handleRemove(file) {
            this.dialogImageUrl=''
            this.$refs.upload.clearFiles();
            this.disabled=false
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        submitUpload() {
            if (this.isLogin===false){
                this.$message.error('您还未登录');
            }else if (this.checkedScenery.length===0){
                this.$message.error('请选择需要绑定的风景');
            }else if (this.title===''){
                this.$message.error('请输入标题');
            }else if (!this.disabled){
                this.$message.error('请选择文件');
            }else if (contents==='<p><br></p>'){
                this.$message.error('请输入内容');
            }else{
                this.form.title=this.title
                this.form.content=contents
                this.form.scenerys=this.checkedScenery
                console.log(this.form)
                this.$refs.upload.submit(); // 触发上传
                this.$message.success('发布成功')
                setTimeout(function() {
                    window.location.href = "/site/strategy.html"; // 替换成你要跳转的链接
                }, 800);
            }
        },
        clear(){
            console.log(contents)
            this.title=''
            this.content=''
            this.dialogImageUrl=''
            this.$refs.upload.clearFiles();
            editor.setHtml('')
            this.$message.success('内容已清空')
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
        //修改发布页面的标题
        updateTitle(){
                this.pageTitle=sessionStorage.getItem('title');
                if (this.pageTitle==='发布攻略'){
                    this.actionUrl='/strategy/add'
                }else if (this.pageTitle==='发布美食'){
                    this.actionUrl='/abc'
                }
        },
        //查询所有风景名
        selectSceneryNameAndId(){
            axios.get('/scenery/name_id').then(response => {
                // 将字符串数组转换为el-option期望的格式
                this.scenery=response.data.map(item => ({ label: item.name, value: item.id }));
            }).catch(error => {
                console.error('scenery/name请求失败:', error);
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
    },
    mounted(){
        this.updateTitle()
        this.selectSceneryNameAndId()
        this.getUserInfo()

    }
})
