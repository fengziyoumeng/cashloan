import React from 'react';
import {Col, Form, Input, Modal, Row, Select} from 'antd';
// import Lightbox from 'react-native-lightbox';
import Lightbox from 'react-images';


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
        var url = "/act/model/company/auditresult.htm";
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
        var url = "/act/model/company/auditresult.htm";
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

        var licensePic = {...getFieldProps('licensePic')}.value;
        var identityFrontPic = {...getFieldProps('identityFrontPic')}.value;
        var identityReversePic = {...getFieldProps('identityReversePic')}.value;
        var holdCardPic = {...getFieldProps('holdCardPic')}.value;

        var introduction = {...getFieldProps('introduction')}.value;
        var companyName={...getFieldProps('companyName')}.value;
        var legalPersonName={...getFieldProps('legalPersonName')}.value;
        var idnumber={...getFieldProps('idnumber')}.value;
        var contactPerson={...getFieldProps('contactPerson')}.value;
        var contactTel={...getFieldProps('contactTel')}.value;


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

                <Lightbox
                    images={[{ src: 'http://example.com/img1.jpg' }, { src: 'http://example.com/img2.jpg' }]}
                    isOpen={this.state.lightboxIsOpen}
                    onClickPrev={this.gotoPrevious}
                    onClickNext={this.gotoNext}
                    onClose={this.closeLightbox}
                />

                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>

                    <div className="navLine-wrap-left">
                        <h2>基本信息</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="企业名称：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('companyName')} type="text"/>
                                </FormItem>
                            </Col>

                            <Col span="12">
                                <FormItem  {...formItemLayout} label="法人姓名：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('legalPersonName')}/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="身份证号：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('idnumber')} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="企业联系人：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('contactPerson')} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="联系人手机号：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('contactTel')}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="公司简介：">
                                    <Input rows={6} disabled={props.canEdit}  {...getFieldProps('introduction')}  type="textarea"/>
                                </FormItem>
                            </Col>
                        </Row>


                        <h2>图片资料</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="营业执照：">
                                    <img src={licensePic} alt="" className="avatar" style={{width: '300px',marginLeft: '5px'}} onclick={this.fangda}/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="手持身份证：">
                                    <img src={holdCardPic} alt="" className="avatar" style={{width: '300px',marginLeft: '5px'}}/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="身份证正面：">
                                    <img src={identityFrontPic} alt="" className="avatar" style={{width: '300px',marginLeft: '5px'}}/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="身份证反面：">
                                    <img src={identityReversePic} alt="" className="avatar" style={{width: '300px',marginLeft: '5px'}}/>
                                </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="审核理由：">
                                    <Input rows={3} disabled={!props.canEdit}  {...getFieldProps('auditMessage',{
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}  type="textarea"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="审核人：">
                                    <Input rows={3} disabled={!props.canEdit}  {...getFieldProps('auditPerson',{
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
