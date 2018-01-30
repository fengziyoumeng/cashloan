import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Tree,
    TreeSelect,
    Row,
    Col,
    Upload,
    Icon,
    message
} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
let treeData = [];
var tagList = [];
var processList = [];
var sortList = [];
var vlu = '';




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
    handleOk(e) {
        e.preventDefault();
        var me = this;
        var props = me.props;
        var record = props.record;
        var title = props.title;
        var path = me.path
        var name = me.name
        var url = "/act/message/saveorupdate.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }
            if (title == "新增") {
                var data = objectAssign({}, {
                    form: JSON.stringify(objectAssign({'path': path, 'name': name}, values, {}
                    ))
                });
            }
            else if (title == "修改") {
                var data = objectAssign({}, {
                    form: JSON.stringify(objectAssign({'path': path, 'name': name}, values, {}))
                });
            }
            console.log(data)
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
                        });
                        this.props.form.resetFields();
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
            console.log(this.path);
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
        var imageOss = {...getFieldProps('adUrl')}.value;

        var showImg
        if (imageOss == undefined || imageOss =='') {
            showImg = imageUrl
        } else {
            showImg = imageOss
        }
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary" onClick={this.handleOk}>
                提 交
            </button>
        ];
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900"
                   footer={modalBtns}>
                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>

                    <div className="navLine-wrap-left">
                        <h2>基本信息</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="消息类型：">
                                    <Select disabled={!props.canEdit} {...getFieldProps('type', {rules: [{required: true, message: '必填' }]})} >
                                        <Option value={3}>活动</Option>
                                        <Option value={4}>口子</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="消息标题：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('title', {rules: [{required: true, message: '必填' }]})} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="消息主体：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('message', {rules: [{required: true, message: '必填' }]})} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout}  label="活动url：">
                                    <Input disabled={!props.canEdit} placeholder={"消息类型为活动时才须填写"}  {...getFieldProps('skipUrl')} type="text"/>
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
