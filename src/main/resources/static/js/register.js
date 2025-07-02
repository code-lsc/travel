var registerVue = new Vue({
    el: "#register",
    data() {

        return {

            form: {
                email: '',
                password: '',
                okPassword: '',
                code: '',
            },
            rules: {
                email: [
                    { required: true, message: '邮箱号不能为空', trigger: 'blur' },
                    { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] },
                    { validator: this.validateEmail, trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ],
                okPassword: [
                    { required: true, message: '确认密码不能为空', trigger: 'blur' },
                    { validator: this.validatePassword, trigger: 'blur' } // 在此处添加 trigger: 'blur'
                ],
                code: [
                    { required: true, message: '验证码不能为空', trigger: 'blur' },
                    { validator: this.validateCode, trigger: 'submit' }
                ]
            },
            countdown: 0, // 倒计时秒数
            codeBtnStyle:'no-send-code',
            path: window.location.origin


        }


    },
    methods:{

        onSubmit() {
            // 表单校验，如果没有通过，则不提交表单
            this.$refs.form.validate(valid => {
                if (valid) {
                    axios.post('/user/add', {
                        email:this.form.email,
                        password:this.form.password
                    }).then(response => {
                        if (response.data)
                            this.$message.success("注册成功，3s后自动跳转")

                        setTimeout(function() {
                            window.location.href = '/site/login.html';
                        }, 3000);
                    }).catch(error => {
                            console.error('Post请求失败:', error);
                        });
                } else {
                    this.$message.error('表单填写有误，请检查后再提交');
                    return false;
                }
            });
        },
        startCountdown() {
            this.$refs.form.validateField('email', (errorMessage) => {
                if (!errorMessage) {
                    // 邮箱校验通过
                    this.codeBtnStyle='send-code'
                    if (this.countdown === 0) {
                        // 发送验证码逻辑
                        this.sendVerificationCode();

                        // 开始倒计时
                        this.countdown = 60;

                        // 每秒更新倒计时
                        const timer = setInterval(() => {
                            if (this.countdown > 0) {
                                this.countdown--;
                                if (this.countdown===0)
                                    this.codeBtnStyle='no-send-code'
                            } else {
                                // 倒计时结束，清除定时器
                                clearInterval(timer);
                            }
                        }, 1000);
                    }
                }
            });


        },
        sendVerificationCode() {
            // 执行发送验证码的逻辑
            axios.get('/user/email/send/'+this.form.email).then(function (response) {
                if (response.data){
                    this.$message.success('验证码已发送');
                }
            }).catch(function (error) {
                this.$message.errors('验证码发送失败');
            });

        },
        validatePassword(rule, value, callback){
            if (value !== this.form.password) {
                callback(new Error('两次输入的密码不一致'));
            } else {
                callback();
            }
        },
        //判断邮箱是否存在
        validateEmail(rule, value, callback){
            axios.get('/user/email/'+value).then(function (response) {
                if (response.data){
                    callback(new Error('邮箱已被注册'))
                }else{
                    callback()
                }
            }).catch(function (error) {
                console.error(error);
            });
        },
        validateCode(rule, value, callback){
            axios.post('/user/email/check', {
                email:this.form.email,
                code:this.form.code
            }).then(response => {
                    if (!response.data)
                        callback(new Error("验证码错误"))
                    else
                        callback()
                }).catch(error => {
                    console.error('Post请求失败:', error);
                });
        }

    }
})