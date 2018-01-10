import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Row,
    Col,
    Select,
    Tabs,
    Cascader
} from 'antd';

import RuleReport from './RuleReport';
import Tab1 from './Tab1';
import Tab2 from './Tab2';
import Tab4 from './Tab4';
import Tab5 from './Tab5';
import Tab6 from './Tab6';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const TabPane = Tabs.TabPane;
var confirm = Modal.confirm;
const options = [{
    value: '26',
    label: '人工复审通过',
}, {
    value: '27',
    label: '人工复审拒绝',
    children: [{
        value: '01',
        label: '停机、关机、空号',
    }, {
        value: '02',
        label: '二次拨打未联系上',
    }, {
        value: '03',
        label: '放弃本次申请',
    }, {
        value: '04',
        label: '活体遮挡',
    }, {
        value: '05',
        label: '身份证非实拍、假证',
    }, {
        value: '06',
        label: '紧急联系人虚假',
    }, {
        value: '07',
        label: '联系人少于20个',
    }, {
        value: '08',
        label: '专线电话超20个',
    }, {
        value: '09',
        label: '通话记录丢失数据',
    }, {
        value: '10',
        label: '通话记录中包含催收电话',
    }, {
        value: '11',
        label: '户籍地不准入及偏远地区',
    }, {
        value: '12',
        label: '职业不准入',
    }, {
        value: '13',
        label: '年龄不符',
    }, {
        value: '14',
        label: '本人不可信',
    }],

}];
var Lookdetails = React.createClass({
    getInitialState() {
        return {
            checked: true,
            disabled: false,
            data: "",
            timeString: "",
            record: "",
            checkOpin:"",
        };
    },
    handleCancel(i) {
        if(this.props.title != '查看' && i){
            Utils.ajaxData({
                url: '/modules/manage/borrow/cancelAudit.htm',
                data: { borrowId: this.state.record.id,auditProcess: this.props.canCheck},
                callback: (result) => {
                    if(result.code == '200'){
                        this.props.form.resetFields();
                        this.props.hideModal();
                    }else{
                        Modal.error({
                            title: result.msg
                        })
                    }
                }
            });
        }
        else{
            this.props.form.resetFields();
            this.props.hideModal();
        }
    },
    componentWillReceiveProps(nextProps) {
        this.setState({
            record: nextProps.record
        })
    },
    onChange(value) {
        this.checkOpin = value
    },
    handleOk() {
        let me = this;
        let params = this.props.form.getFieldsValue();
        let record = this.state.record;
        let checkOpin1=this.checkOpin[0]+"";
        let checkOpin2="";
        if(this.checkOpin.length>1){
            checkOpin2=this.checkOpin[1]+"";
        }

        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                //console.log('Errors in form!!!');
                return;
            }
            var tips = '是否确定提交';
            confirm({
                title: tips,
                onOk: function () {
                    Utils.ajaxData({
                        url: '/modules/manage/borrow/verifyBorrow.htm',
                        data: { borrowId: record.id, checkOpin1: checkOpin1,checkOpin2: checkOpin2, remark: params.remark},
                        callback: (result) => {
                            if (result.code == 200) {
                                me.handleCancel(1);
                            };
                            let resType = result.code == 200 ? 'success' : 'warning';
                            Modal[resType]({
                                title: result.msg,
                            });
                        }
                    });
                },
                onCancel: function () { }
            })

        })

    },
    // onChange(value, selectedOptions) {
    //     console.info(value, selectedOptions)
    // },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        console.log(props)
        var state = this.state;
        var index = props.title != '查看' ? 1 : 0;

        var modalBtns = [
          <button key="back" className="ant-btn" onClick={this.handleCancel.bind(this,index)}>取消</button>,
          <button key="sure" className="ant-btn ant-btn-primary" onClick={this.handleOk}>确定</button>
        ];
        var modalBtnstwo = [
          <button key="back" className="ant-btn" onClick={this.handleCancel.bind(this,index)}>关闭</button>,
        ];
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 12
            },
        };
        const formItemLayouttwo = {
            labelCol: {
                span: 4
            },
            wrapperCol: {
                span: 20
            },
        };
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel.bind(this,index)} width="1200" footer={props.title == "查看" || props.canCheck != '1' ? [modalBtnstwo] : [modalBtns]} maskClosable={false} >
              <div>
                <span>借款时间:{state.record.createTime}</span><span style={{ margin: '0 20px'}} >借款金额(元):{state.record.amount}</span><span>借款天数:{state.record.timeLimit}</span>
                  <span style={{ margin: '0 20px'}} >审核人:{state.record.auditor}</span><span>审核时间:{state.record.auditTime}</span>
              </div>
              <Tabs defaultActiveKey="1"  >
                <TabPane tab="规则报告" key='1'>
                  <RuleReport record={this.props.record} visible={props.visible} />
                </TabPane>
                <TabPane tab="基本信息" key="2">
                  <Tab1 record={props.record} dataForm={props.dataForm} ref="Tab1" canEdit={props.canEdit} visible={props.visible} recordSoure={props.recordSoure} />
                </TabPane>
                <TabPane tab="通讯录" key="3">
                  <Tab2 record={props.record} ref="Tab2" canEdit={props.canEdit} visible={props.visible} />
                </TabPane>
                <TabPane tab="同盾通话记录" key="5">
                  <Tab4 record={props.record} ref="Tab4" canEdit={props.canEdit} visible={props.visible} />
                </TabPane>
                <TabPane tab="短信记录" key="6">
                  <Tab5 record={props.record} ref="Tab5" canEdit={props.canEdit} visible={props.visible} />
                </TabPane>
                <TabPane tab="借款记录" key="7">
                  <Tab6 record={props.record} ref="Tab6" canEdit={props.canEdit} activeKey={this.state.activekey}/>
                </TabPane>
              </Tabs>
              <Form horizontal form={this.props.form}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden" />
                  {props.dataRecord ? <Row>
                    <Col span="24">
                      <FormItem {...formItemLayout} label={props.dataRecord.respCode == 200 ? '审核请求成功，审核结果:' : '审核请求失败，错误信息:'}>
                        <span>{props.dataRecord.rsDesc}</span>
                      </FormItem>
                    </Col>
                  </Row> : ''}
                  {props.canCheck != 'white' ? props.canCheck == '1' ? <div>
                      <Row>
                    <Col span="24">
                        <Input disabled={!props.canEdit} style={props.canEdit==true?{display:"none"}:{display:"block"}} type="text"  {...getFieldProps('state', { initialValue: '' }) } />
                        <Cascader options={options} style={props.canEdit==false?{display:"none"}:{display:"block"}} onChange={this.onChange}  placeholder="请选择" />
                    </Col>
                  </Row>
                    <Row>
                      <Col span="24">
                        <FormItem  {...formItemLayout} label="备注说明:">
                          <Input disabled={!props.canEdit} type="textarea"  placeholder="" rows={4} style={{ width: "500px", height: "100px" }}   {...getFieldProps('remark', { initialValue: '' }) } />
                        </FormItem>
                      </Col>
                    </Row> </div>: <Row>
                    <Col span='24'>
                      <FormItem  {...formItemLayout} label="">
                        <div style={{width: '100%',textAlign:'right'}}>目前正在人工复查中...</div>
                      </FormItem>
                    </Col>
                  </Row> : ''}
              </Form>
            </Modal>
        );
    }
});
Lookdetails = createForm()(Lookdetails);
export default Lookdetails;