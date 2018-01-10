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

Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data : {
        "typeCode":"FLOWINFO_P_TAG"
    },
    callback: (result) => {
        tagList = result.data;
    }
});


Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data : {
        "typeCode":"FLOWINFO_P_PROCESS"
    },
    callback: (result) => {
        processList = result.data;
    }
});

Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data : {
        "typeCode":"FLOWINFO_SHOW_TYPE"
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
            picPath: '',
            picName: '',
            upFlag: '',
            skipEdit:false,
            showValue:0
        };
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
        this.setState({
            imageUrl: '',
            picUrl:''
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
        var picPath = me.picPath
        var picName = me.picName
        var url = "/act/bannerInfo/saveOrUpdate.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }
            if (title == "新增") {
                var data = objectAssign({}, {
                    form: JSON.stringify(objectAssign({'path': path, 'name': name,'picPath':picPath,'picName':picName}, values, {}
                    ))
                });
            }
            else if (title == "修改") {
                var data = objectAssign({}, {
                    form: JSON.stringify(objectAssign({'path': path, 'name': name,'picPath':picPath,'picName':picName}, values, {
                    } ))
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
                            imageUrl: '',
                            picUrl:''
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
            getBase64(info.file.originFileObj, imageUrl => this.setState({ imageUrl }));
            this.path = info.file.response.imgPath
            this.name = info.file.name
        }
    },
    handleImageChange(info) {
        console.log(info)
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, picUrl => this.setState({ picUrl }));
            this.picPath = info.file.response.imgPath
            this.picName = info.file.name
        }
    },
    getTagList() {
        return tagList.map((item, index) => {
            return <Option key={item.itemCode} >{item.itemValue}</Option>
        })
    },
    getProcessList() {
        return processList.map((item, index) => {
            return <Option key={item.itemCode} >{item.itemValue}</Option>
        })
    },
    getSortList() {
        return sortList.map((item, index) => {
            return <Option key={item.itemCode} >{item.itemValue}</Option>
        })
    },
    handleForbidden(event){
        /*this.setState({showValue:event});*/
        if(event==0 || event==2){
            this.setState({skipEdit:true});
        }else{
            this.setState({skipEdit:false});
        }
    },
    setDefalutVlue(value){
        this.setState({showValue:value});
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var skipType = {...getFieldProps('skipType')}.value
        /*this.setDefalutVlue(skipType);*/
        var imageUrl = this.state.imageUrl;
        var picUrl = this.state.picUrl;
        var banner = {...getFieldProps('url')}.value
        var showBanner;
        var skipPic = {...getFieldProps('wUrl')}.value
        var pic;
        if (banner == undefined) {
            showBanner = imageUrl
        } else{
            showBanner = banner
        }
        if (skipPic == undefined) {
            pic = picUrl
        } else{
            pic = skipPic
        }
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
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
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900"  footer={modalBtns} >
                <Form horizontal  form={this.props.form}>
                    <Input  {...getFieldProps('id',  {initialValue:''})} type="hidden"   />

                    <div className="navLine-wrap-left">
                    <h2>基本信息</h2>
                            <Row>
                                <Col span="12">
                                    <FormItem  {...formItemLayout} label="Banner位置：">
                                        <Select  disabled={!props.canEdit} {...getFieldProps('pType',{ rules: [{required:true,message:'必填'}]})} >
                                            <Option selected value={1}>首页</Option>
                                            <Option  value={3}>信用卡模块</Option>
                                        </Select>
                                    </FormItem>
                                </Col>

                                <Col span="12">
                                    <FormItem  {...formItemLayout} label="跳转方式：">{/*onChange={this.handleForbidden}*/}
                                        <Select id='selectId' disabled={!props.canEdit} {...getFieldProps('skipType')}  >
                                            <Option  value={0}>不跳转</Option>
                                            <Option  value={1}>跳转到链接</Option>
                                            <Option  value={2}>跳转到图片</Option>
                                            <Option  value={3}>跳转到详情</Option>
                                        </Select>
                                    </FormItem>
                                </Col>
                            </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="跳转地址：">
                                    <Input disabled={this.state.skipEdit} placeholder="只有在跳转到链接或跳转到详情时才需填写"  {...getFieldProps('wUrl')} type="text" />
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="排序：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('sort',{ rules: [{required:true,message:'必填'}]})} type="text"  />
                                </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="上传Banner图：">
                                    <Upload
                                        className="avatar-uploader"
                                        name="image"
                                        showUploadList={false}
                                        action="/act/flowControl/uploadImg.htm"
                                        onChange={this.handleChange}>
                                        {
                                            showBanner ?
                                                <img src={showBanner} alt="" className="avatar" style={{width: '30px',marginLeft:'20px',verticalAlign: 'middle'}}/> :
                                                <Icon type="plus" className="avatar-uploader-trigger" />
                                        }
                                    </Upload>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="上传跳转图片：">
                                    <Upload
                                        className="avatar-uploader"
                                        name="image"
                                        showUploadList={false}
                                        action="/act/flowControl/uploadImg.htm"
                                        onChange={this.handleImageChange}>
                                        {
                                            pic ?
                                                <img src={pic} alt="" className="avatar" style={{width: '30px',marginLeft:'20px',verticalAlign: 'middle'}}/> :
                                                <Icon type="plus" className="avatar-uploader-trigger" />
                                        }
                                    </Upload><br/>
                                    <label for="title" style={{color: 'blue'}}>若跳转方式不是跳转到图片,则不需上传跳转图片!</label>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="是否开启：">
                                    <Select  disabled={!props.canEdit} {...getFieldProps('state',{ rules: [{required:true,message:'必填'}]})} >
                                        <Option  value={10}>是</Option>
                                        <Option  value={20}>否</Option>
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
