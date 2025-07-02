var registerVue = new Vue({
    el: "#register",
    data() {

        return {
                form: {
                email: '',
                password: '',
            },
            rules: {
                email: [
                    { required: true, message: '邮箱号不能为空', trigger: 'blur' },
                    { validator:this.validateEmailOrRoot, trigger: ['blur', 'change'] }
                ],
                password: [
                    {required: true, message: '密码不能为空', trigger: 'blur'},
                ]
            },
            isRememberPassword:false

        }


    },
    methods:{
        onSubmit() {
            if (!code)
                return false
            this.$refs.form.validate((valid) => {
                if (valid) {
                    //记住密码
                    if (this.isRememberPassword) {
                        // 执行记住密码的逻辑
                        localStorage.setItem("isRemember",this.isRememberPassword);
                        // 加密账号密码
                        const encrypted = btoa(this.form.email + ':' + this.form.password);

                        // 保存加密后的账号密码到本地存储中
                        localStorage.setItem('rememberedCredentials', encrypted);
                    } else {
                        // 执行取消记住密码的逻辑
                        localStorage.setItem("isRemember",this.isRememberPassword);
                        // 删除本地存储中保存的账号密码
                        localStorage.removeItem('rememberedCredentials');
                    }
                    //发送登录异步请求
                    axios.post('/user/login', {
                        email:this.form.email,
                        password:this.form.password
                    }).then(response => {
                       if (!response.data) {
                           this.$message.error('账号不存在');
                           slide.refreshIcon
                       }else {
                           if (response.data.token==='password'){
                               this.$message.error('密码错误');
                           }else if (response.data.token==='status'){
                               this.$message.error('账号被冻结，请联系管理员');
                           }else {
                               if (response.data.type===0){
                                   window.location.href ='/index.html'
                               }else {
                                   window.location.href='/site/admin.html'
                               }
                           }
                       }
                    }).catch(error => {
                        console.error('Post请求失败:', error);
                    });
                } else {
                    this.$message.error('登陆失败');
                    return false;
                }
            });
        },
        validateEmailOrRoot(rule, value, callback) {
            if (!value) {
                // 如果值为空，直接通过校验
                callback();
            } else if (value === 'root' || this.isEmail(value)) {
                // 如果值为 "root" 或者符合邮箱格式，则通过校验
                callback();
            } else {
                // 否则，校验失败，返回错误信息
                callback(new Error('账号格式不正确'));
            }
        },
        isEmail(value) {
            // 简单的邮箱格式正则匹配
            const emailRegex = /\S+@\S+\.\S+/;
            return emailRegex.test(value);
        }





    },
    mounted(){

        /*const component = this.$refs.myComponent;
        component.style.position = 'absolute';
        component.style.top = '20px';
        component.style.cursor = 'grab';*/
        if (localStorage.getItem("isRemember") === 'true')
            this.isRememberPassword=true
        else if (localStorage.getItem("isRemember") === 'false')
            this.isRememberPassword=false

        if (this.isRememberPassword){
            const rememberedCredentials = localStorage.getItem('rememberedCredentials');
            if (rememberedCredentials) {
                // 解密账号密码
                const decrypted = atob(rememberedCredentials);
                console.log(decrypted)
                const [username, password] = decrypted.split(':');
                // 自动填充账号密码
                this.form.email = username;
                this.form.password = password;
            }
        }
    }
})