import React from 'react'
import {
    Button,
    Form,
    Input,
    Select,
    Checkbox,
    Modal
} from 'antd';

import './login.css';
const FormItem = Form.Item;
const Option = Select.Option;
let Login = React.createClass({
    getInitialState() {
        return {
            ischecked: 0
        }
    },
    handleSubmit(e) {
        e.preventDefault();
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
            //console.log('Errors in form!!!');
            return;
        }
        //console.log(values);
        var params = values;
        this.login(params);
    });
    },
    login(params) {
        Utils.ajaxData({
            url: '/system/user/login.htm',
            contentType: 'application/x-www-form-urlencoded',
            data: params,
            callback: function(result) {
                if (result.code == 200) {
                    localStorage.isLogin = true;
                    localStorage.Token = result.Token;
                    location.reload();
                } else {
                    Modal.error({
                        title: result.msg
                    });
                }
            }
        });
    },
    componentDidMount() {
        var username = Cookies.get("username");
        var password = Cookies.get("password");
        if (username) {
            this.props.form.setFieldsValue({ username,password });
        }
    },
    render() {

        const {
            getFieldProps
        } = this.props.form;
        return (
            <div>
            <div className="g-loginbox">
            <div className="g-bd">
            <div className="m-loginbg" style={{height:document.body.clientHeight}}>
    </div>
        <div className="m-bgwrap" style={{ cursor: "pointer" }}></div>
        <div className="m-loginboxbg" ></div>
            <div className="m-loginbox">
            <div className="lbinner" id="J_body_bg">
            <div className="login-form">
            <div className="login-hd">急借号管理平台</div>
            <div className="login_input">
            <Form inline-block onSubmit={this.handleSubmit} form={this.props.form}>
    <FormItem hasFeedback>
        <Input type="text" className="ipt ipt-user" name="username" autoComplete="off"
        {...getFieldProps('username', {

            rules: [{
                required: true,
                message: '请输入账户名'
            }],
            trigger: 'onBlur'
        })
        }
        placeholder = "用户名" />
            </FormItem>
            <FormItem >
            <Input type="password" className="ipt ipt-pwd" name="password" autoComplete="off"
        {...getFieldProps('password', {

            rules: [{
                required: true,
                whitespace: false,
                message: '请输入密码'
            }],
            trigger: 'onBlur'
        })
        }
        placeholder="密码"/>
            </FormItem>
            {/*     <FormItem >
             <Input type="password" className="ipt ipt-pwd" name="accessCode" autoComplete="off"
             {...getFieldProps('accessCode', {
             rules: [{
             required: true,
             whitespace: false,
             message: '请输入访问码'
             }],
             trigger: 'onBlur'
             })
             }
             placeholder="访问码"/>
             </FormItem>*/}
            <Button type="primary" size="large" className="ant-input u-loginbtn" htmlType="submit">登录</Button>
            </Form>
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>
    )
    }
})

Login = Form.create()(Login);
export default Login;