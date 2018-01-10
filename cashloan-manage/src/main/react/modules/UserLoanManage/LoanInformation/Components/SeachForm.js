import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker} from 'antd';
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
        var json = { orderCreatStartTime: '', orderCreatEndTime: '', realName: params.realName, phone: params.phone, orderNo: params.orderNo, state: params.state};
        if (params.orderCreatTime) {
            json.orderCreatStartTime = (DateFormat.formatDate(params.orderCreatTime[0])).substring(0, 10);
            json.orderCreatEndTime = (DateFormat.formatDate(params.orderCreatTime[1])).substring(0, 10);
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
    handleOut() {
        var params = this.props.form.getFieldsValue();
        var json = JSON.stringify(params);
        window.open("/modules/manage/borrow/export.htm?searchParams="+json);

    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
             <FormItem label="订单生成时间:">
                   <RangePicker style={{width:"310"}} {...getFieldProps('orderCreatTime', { initialValue: '' }) } />
             </FormItem>
             <FormItem label="真实姓名:">
                  <Input  {...getFieldProps('realName')} />
             </FormItem>
             <FormItem label="手机号码:">
                  <Input  {...getFieldProps('phone')} />
             </FormItem>
             <FormItem label="订单号:">
                  <Input  {...getFieldProps('orderNo')} />
             </FormItem>
             <FormItem label="订单状态:">
             <Select style={{ width: 170 }} {...getFieldProps('state',{initialValue: ''})} placeholder='请选择...'>
                        <Option value="10">申请成功待审核</Option>
                        <Option value="20">自动审核通过</Option>
                        <Option value="21">自动审核不通过</Option>
                        <Option value="22">自动审核未决待人工复审</Option>
                        <Option value="26">人工复审通过</Option>
                        <Option value="27">人工复审不通过</Option>
                        <Option value="30">放款成功</Option>
                        <Option value="31">放款失败</Option>
                        <Option value="40">还款成功</Option>
                        <Option value="41">还款成功-金额减免</Option>
                        <Option value="50">逾期</Option>
                        <Option value="90">坏账</Option>
             </Select>
             </FormItem>
             <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
             <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
             <FormItem><Button onClick={this.handleOut}>导出</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;