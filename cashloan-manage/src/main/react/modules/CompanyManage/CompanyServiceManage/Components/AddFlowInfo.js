import React from 'react';
import {Col, Form, Input, Modal, Row, Select} from 'antd';

const { TextArea } = Input;
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
let treeData = [];
var tagList = [];
var processList = [];
var sortList = [];
var vlu = '';

Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data: {
        "typeCode": "FLOWINFO_P_TAG"
    },
    callback: (result) => {
        tagList = result.data;
    }
});


Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data: {
        "typeCode": "FLOWINFO_P_PROCESS"
    },
    callback: (result) => {
        processList = result.data;
    }
});

Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data: {
        "typeCode": "FLOWINFO_SHOW_TYPE"
    },
    callback: (result) => {
        sortList = result.data;
    }
});

function writeObj(obj){
    var description = "";
    for(var i in obj){
        var property=obj[i];
        description+=i+" = "+property+"\n";
    }
    alert(description);
}

function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}

var AddFlowInfo = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
            path: '',
            name: '',
            upFlag: '',
            info: 15
        };
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
        this.setState({
            imageUrl: ''
        })
    },
    handleAuditOk(e){
        e.preventDefault();
        var me = this;
        var props = me.props;
        var record = props.record;
        var title = props.title;
        var path = me.path
        var name = me.name
        var url = "/act/model/companyservice/auditresult.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }

            var data = objectAssign({}, {
                form: JSON.stringify(objectAssign({'pass': 'ok'}, values, {}
                ))
            });

            Utils.ajaxData({
                url: url,
                data: data,
                callback: (result) => {
                    if (result.code == 200) {
                        Modal.success({
                            title: result.msg,
                            onOk: () => {
                                props.hideModal()
                            }
                        });
                        this.setState({
                            imageUrl: ''
                        })
                    }
                }
            });
        })
    },
    handleAuditNo(e){
        e.preventDefault();
        var me = this;
        var props = me.props;
        var url = "/act/model/companyservice/auditresult.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }

            var data = objectAssign({}, {
                form: JSON.stringify(objectAssign({'pass': 'no'}, values, {}
                ))
            });
            Utils.ajaxData({
                url: url,
                data: data,
                callback: (result) => {
                    if (result.code == 200) {
                        Modal.success({
                            title: result.msg,
                            onOk: () => {
                                props.hideModal()
                            }
                        });
                        this.setState({
                            imageUrl: ''
                        })
                    }
                }
            });
        })
    },
    handleChange(info) {
        console.log(info)
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => this.setState({imageUrl}));
            this.path = info.file.response.imgPath
            this.name = info.file.name
        }
    },
    changeVal: function (e) {
        var val = e.target.value;
        var length = val.length;
        /*vlu = val.substring(0,15);*/
        this.setState({"i_val": val.substring(0, 15)});
        length < 16 ? this.setState({"info": (15 - length)}) : "";
    },
    getTagList() {
        return tagList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    getProcessList() {
        return processList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    getSortList() {
        return sortList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    setVlue(vlu) {
        this.setState({"i_val": vlu});
    },

    render() {
        const {
            getFieldProps
        } = this.props.form;
        var imageUrl = this.state.imageUrl;

        var companyInfo = getFieldProps("companyInfo").value;
        var companyProd = getFieldProps("companyProd").value;
        var operativeInfoList = getFieldProps("operativeInfoList").value;

        var name1;
        var tel1;
        var wx1;
        var qq1;
        var job1;
        var module1;

        var name2;
        var tel2;
        var wx2;
        var qq2;
        var job2;
        var module2;

        var companyName;
        var companyType;

        if(companyInfo) {
            companyName = companyInfo.companyName;
        }
        if(companyProd){
            companyType = companyProd.type_name;
        }
        if(operativeInfoList){
            if(operativeInfoList[0]){
                name1 = operativeInfoList[0].name;
                tel1 = operativeInfoList[0].tel;
                wx1 = operativeInfoList[0].wx;
                qq1 = operativeInfoList[0].qq;
                job1 = operativeInfoList[0].job;
                module1 = operativeInfoList[0].module;
            }
            if(operativeInfoList[1]){
                name2 = operativeInfoList[1].name;
                tel2 = operativeInfoList[1].tel;
                wx2 = operativeInfoList[1].wx;
                qq2 = operativeInfoList[1].qq;
                job2 = operativeInfoList[1].job;
                module2 = operativeInfoList[1].module;
            }
        }
        var props = this.props;
        var state = this.state;

        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleAuditOk}>审核通过</button>,
            <button key="button" className="ant-btn ant-btn-primary" onClick={this.handleAuditNo}>审核拒绝</button>
        ];
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 13
            },
        };
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="1100" footer={modalBtns}>
                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>
                    <div className="navLine-wrap-left">
                        <h2>基本信息</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="公司名称：">
                                    <Input disabled={props.canEdit}  value ={companyName} type="text"/>
                                </FormItem>
                            </Col>

                            <Col span="12">
                                <FormItem  {...formItemLayout} label="分类：">
                                    <Input disabled={props.canEdit}  value = {companyType} />
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="LOGO：">
                                    <img src={{...getFieldProps('logo_path')}.value} style={{width: '80px',marginLeft: '5px'}}/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="产品服务介绍：">
                                    <Input disabled={props.canEdit} rows={5}  {...getFieldProps('proc_info')} type="textarea"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <h3>联系人1</h3>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="姓名：">
                                    <Input disabled={props.canEdit}  value = {name1}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="手机：">
                                    <Input  disabled={props.canEdit}  value = {tel1}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="微信：">
                                    <Input disabled={props.canEdit}  value = {wx1}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="QQ：">
                                    <Input  disabled={props.canEdit}  value = {qq1}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="职位：">
                                    <Input disabled={props.canEdit}  value={job1}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="负责模块：">
                                    <Input  disabled={props.canEdit}  value={module1}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <h3>联系人2</h3>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="姓名：">
                                    <Input disabled={props.canEdit}  value = {name2}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="手机：">
                                    <Input  disabled={props.canEdit}  value = {tel2}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="微信：">
                                    <Input disabled={props.canEdit}  value = {wx2}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="QQ：">
                                    <Input disabled={props.canEdit}  value = {qq2}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="职位：">
                                    <Input disabled={props.canEdit}  value={job2}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="负责模块：">
                                    <Input  disabled={props.canEdit}  value={module2}  type="text"/>
                                </FormItem>
                            </Col>
                        </Row>

                        <h2>审核</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="不通过原因（可多选）：">
                                    <Select disabled={!props.canEdit} multiple {...getFieldProps('auditMessage', {rules: [{type: "array" }]})} >
                                        <Option value={1}>产品名称不符合</Option>
                                        <Option value={2}>产品分类不通过</Option>
                                        <Option value={3}>LOGO不符合规范</Option>
                                        <Option value={4}>产品服务介绍不通过</Option>
                                        <Option value={5}>联系人信息不通过</Option>
                                    </Select>
                                </FormItem>
                                {/*<FormItem  {...formItemLayout} label="审核理由：">
                                    <Input rows={3} disabled={!props.canEdit}  {...getFieldProps('auditMessage',{
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}  type="textarea"/>
                                </FormItem>*/}
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="审核人：">
                                    <Input  disabled={!props.canEdit}  {...getFieldProps('auditPerson',{
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}  type="text"/>
                                </FormItem>
                            </Col>

                        </Row>
                    </div>
                </Form>
            </Modal>
        );
    }
});
AddFlowInfo = createForm()(AddFlowInfo);
export default AddFlowInfo;
