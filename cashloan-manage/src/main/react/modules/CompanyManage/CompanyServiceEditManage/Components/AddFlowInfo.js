import React from 'react';
import {Col, Form, Input, Modal, Row, Select} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var tagList = [];

Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data: {
        "typeCode": "SERVICE_TAGS"
    },
    callback: (result) => {
        tagList = result.data;
    }
});

var AddFlowInfo = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
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
    handleSave(e){
        e.preventDefault();
        var me = this;
        var props = me.props;
        var url = "/act/model/companyservice/editupdate.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }
            var data = objectAssign({}, {
                form: JSON.stringify(objectAssign(values, {}))
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
    getTagList() {
        return tagList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    render() {
        const { getFieldProps } = this.props.form;

        var props = this.props;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>,
            <button key="button" className="ant-btn ant-btn-primary" onClick={this.handleSave}>保存</button>
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
                                <FormItem  {...formItemLayout} label="简介信息：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('p_message',{
                                        rules: [{
                                            max:'18',
                                            message: '填写少于等于18个字符'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>

                            <Col span="12">
                                <FormItem  {...formItemLayout} label="角标：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('flag_msg',{
                                        rules: [{
                                            max:'4',
                                            message: '填写少于等于4个字符'
                                        }]
                                    })}  />
                                </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="开启热门推荐：">
                                    <Select id="select" size="large"  disabled={!props.canEdit} {...getFieldProps('proc_flag', { initialValue: 0 })} >
                                        <Option value={1}>推荐</Option>
                                        <Option value={0}>不推荐</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="是否开启：">
                                    <Select id="select" size="large"  disabled={!props.canEdit} {...getFieldProps('status', { initialValue: 0 })} >
                                        <Option value={1}>启用</Option>
                                        <Option value={0}>禁用</Option>
                                    </Select>
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
