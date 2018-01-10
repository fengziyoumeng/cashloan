import React from 'react';
import {Button, Form, Input, Select,Message,DatePicker } from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const RangePicker = DatePicker.RangePicker;
let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = {auditStartTime:'',auditEndTime:'',orderNo: params.orderNo, phone: params.phone, realName: params.realName,
            auditor: params.auditor,state:params.state,rejectReason:params.rejectReason};
        if (params.auditTime) {
            json.auditStartTime = (DateFormat.formatDate(params.auditTime[0])).substring(0, 10);
            json.auditEndTime = (DateFormat.formatDate(params.auditTime[1])).substring(0, 10);
        }
        if(params.auditor){
            json.auditor = params.auditor ;
        }

        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            pageSize: 10,
            current: 1,
        });
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
             <FormItem label="订单号:">
                  <Input  {...getFieldProps('orderNo', {initialValue: ''})} />
             </FormItem>
             <FormItem label="姓名:">
                  <Input  {...getFieldProps('realName', {initialValue: ''})} />
             </FormItem>
             <FormItem label="手机号码:">
                  <Input  {...getFieldProps('phone', {initialValue: ''})} />
             </FormItem>
             <FormItem label="信审人员:">
                 <Input  {...getFieldProps('auditor', {initialValue: ''})} />
             </FormItem>
                <FormItem label="信审时间:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('auditTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="拒绝理由:">
                    <Select  style={{width:190}}  {...getFieldProps('rejectReason', {initialValue: ''})} >
                        <Option value=''>全部</Option>
                        <Option value='01'>停机、关机、空号</Option>
                        <Option value='02'>二次拨打未联系上</Option>
                        <Option value='03'>放弃本次申请</Option>
                        <Option value='04'>活体遮挡</Option>
                        <Option value='05'>身份证非实拍、假证</Option>
                        <Option value='06'>紧急联系人虚假</Option>
                        <Option value='07'>联系人少于20个</Option>
                        <Option value='08'>专线电话超20个</Option>
                        <Option value='09'>通话记录丢失数据</Option>
                        <Option value='10'>通话记录中包含催收电话</Option>
                        <Option value='11'>户籍地不准入及偏远地区</Option>
                        <Option value='12'>职业不准入</Option>
                        <Option value='13'>年龄不符</Option>
                        <Option value='14'>本人不可信</Option>
                    </Select>
                </FormItem>
             <FormItem label="状态:">
                  <Select  style={{width:130}}  {...getFieldProps('state', {initialValue: ''})} >
                    <Option value=''>全部</Option>
                    <Option value='22'>待人工复审</Option>
                    <Option value='26'>人工复审通过</Option>
                    <Option value='27'>人工复审不通过</Option>   
                  </Select>
             </FormItem>
                <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
                <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;