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
var imagesList = [];
var vlu = '';


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
        "typeCode":"FLOWINFO_BACKGROUND_IMAGE"
    },
    callback: (result) => {
        imagesList = result.data;
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
    handleOk(e) {
        e.preventDefault();
        var me = this;
        var props = me.props;
        var record = props.record;
        var title = props.title;
        var path = me.path
        var name = me.name
        var url = "/act/flowControl/saveInfo.htm";
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
                        })
                    }else {
                        Modal.error({
                            title: result.msg,
                        });
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
    getBackgroundImageList() {
        return imagesList.map((item, index) => {
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
        var imageOss = {...getFieldProps('picUrl')}.value;
        vlu = {...getFieldProps('pmessage')}.value;

        var showImg
        if (imageOss == undefined) {
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
                                <FormItem  {...formItemLayout} label="产品名称：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pname', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="借款类型">
                                    <Select disabled={!props.canEdit} multiple {...getFieldProps('pType_convert', {rules: [{ required: true, message: '必填',type: "array"}]})}>
                                        {this.getSortList()}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="访问地址：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('phttp', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="编码（如：baidu）：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pcode', {rules: [{required: true,message: '必填'}]})} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="推荐排序：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('psort', {rules: [{required: true,message: '必填'}]})} placeholder={"请输入在热门推荐中的排序号"} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="类别排序：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('typeSort', {rules: [{required: true,message: '必填'}]})} placeholder={"请输入在其他类别中的排序号"} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="是否开启：">
                                    <Select disabled={!props.canEdit} {...getFieldProps('pstate', {rules: [{required: true, message: '必填' }]})} >
                                        <Option value={10}>是</Option>
                                        <Option value={20}>否</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="申请人数：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pBorrowNum', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="营销信息：">

                                    <Input disabled={!props.canEdit}  {...getFieldProps('pmessage', {rules: [{required: true, message: '最多输入15个字!', max: 15 }]})} type="text"/>
                                    {/*<label for="title" >您还可以输入{this.state.info}个字</label>*/}
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="图片（oss）：">
                                    {/*<span style={{marginLeft: '33px',verticalAlign: 'middle'}}>图片地址（oss）：</span>*/}
                                    <Upload
                                        className="avatar-uploader"
                                        name="image"
                                        showUploadList={false}
                                        action="/act/flowControl/uploadImg.htm"
                                        onChange={this.handleChange}>
                                        {
                                            showImg ?
                                                <img src={showImg} alt="" className="avatar" style={{width: '30px',marginLeft: '20px', verticalAlign: 'middle'}}/> :
                                                <Icon type="plus" className="avatar-uploader-trigger"/>
                                        }
                                    </Upload>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="标记（限1个字符）：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pmarks',{rules: [{message: '限1个字符!', max: 1 }]})}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="角标背景图颜色：">
                                    <Select disabled={!props.canEdit} {...getFieldProps('backgroundImage')}>
                                        {this.getBackgroundImageList()}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                    </div>
                    <div className="navLine-wrap-left">
                        <h2>产品信息</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="贷款金额：">
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center'}}
                                           placeholder="最小额度" {...getFieldProps('minLimit', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}/>
                                    <Input style={{width: 31,borderLeft: 0,pointerEvents: 'none',backgroundColor: '#fff',textAlign: 'center'}} placeholder="~" disabled/>
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center', borderLeft: 0}} placeholder="最大额度" {...getFieldProps('maxLimit')}/>
                                    <span style={{marginLeft: 10}}>元</span>
                                    {/*<Input disabled={!props.canEdit}  {...getFieldProps('limits',{ rules: [{required:true,message:'必填'}]})} type="text"  />*/}
                                    {/*<Select defaultValue="1" style={{ width: 80}}>*/}
                                    {/*<Option value="1">元</Option>*/}
                                    {/*</Select>*/}
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="贷款期限：">
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center'}}
                                           placeholder="最短时间" {...getFieldProps('minDay', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}/>
                                    <Input style={{
                                        width: 31,
                                        borderLeft: 0,
                                        pointerEvents: 'none',
                                        backgroundColor: '#fff',
                                        textAlign: 'center'
                                    }} placeholder="~" disabled/>
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center', borderLeft: 0}}
                                           placeholder="最长时间" {...getFieldProps('maxDay')}/>
                                    <span style={{marginLeft: 10}}>天</span>
                                    {/*<Select defaultValue={1} style={{ width: 80}} id={""}>*/}
                                    {/*<Option value={1}>天</Option>*/}
                                    {/*<Option value={2}>月</Option>*/}
                                    {/*</Select>*/}
                                    {/*<Input disabled={!props.canEdit}  {...getFieldProps('orderNo')} type="text"  />*/}
                                </FormItem>
                            </Col>
                        </Row>

                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="放款时间：">
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center'}}
                                           placeholder="最快时间" {...getFieldProps('ploanMinTime', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })}/>
                                    <Input style={{
                                        width: 31,
                                        borderLeft: 0,
                                        pointerEvents: 'none',
                                        backgroundColor: '#fff',
                                        textAlign: 'center'
                                    }} placeholder="~" disabled/>
                                    <Input min="0" type="number" style={{width: 80, textAlign: 'center', borderLeft: 0}}
                                           placeholder="最慢时间" {...getFieldProps('ploanMaxTime')}/>
                                    <Select defaultValue={1} style={{width: 80}} {...getFieldProps('ploanTimeType')}>
                                        <Option value={1}>小时</Option>
                                        <Option value={2}>天</Option>
                                        <Option value={3}>分钟</Option>
                                    </Select>

                                    {/*<Input disabled={!props.canEdit}  {...getFieldProps('limits',{ rules: [{required:true,message:'必填'}]})} type="text"  />*/}
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="日利率：">
                                    <Input min="0" disabled={!props.canEdit}  {...getFieldProps('pinterest')}
                                           type="number"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="产品标签:">
                                    <Select disabled={!props.canEdit} multiple  {...getFieldProps('ptag', {
                                        rules: [{
                                            required: true,
                                            message: '必填',
                                            type: 'array'
                                        }]
                                    })} >
                                        {this.getTagList()}
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="渠道洽谈价格（元）：">
                                    <Input min="0" disabled={!props.canEdit}  {...getFieldProps('pChannelPrice', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="number"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="录入人信息：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pHandPerson', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                    </div>
                    <div className="navLine-wrap-left">
                        <h2>申请条件</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="申请条件：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pcondition', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>

                        </Row>
                    </div>
                    <div className="navLine-wrap-left">
                        <h2>申请流程</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="申请流程：">
                                    <Select disabled={!props.canEdit} multiple  {...getFieldProps('pprocess', {
                                        rules: [{
                                            required: true,
                                            message: '必填',
                                            type: 'array'
                                        }]
                                    })} >
                                        {this.getProcessList()}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                    </div>
                    <div className="navLine-wrap-left">
                        <h2>产品说明</h2>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="产品说明：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('premark', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
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
