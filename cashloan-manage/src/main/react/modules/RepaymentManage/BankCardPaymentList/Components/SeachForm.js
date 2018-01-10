import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker } from 'antd';
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
        console.log(params)
        var json = { repayPlanStartTime: '', repayPlanEndTime: '',repayFactStartTime:'',repayFactEndTime:'', orderNo: params.repayAccount, state: params.repayAccount,repayWay:params.repayWay};
        if (params.repayPlanTime) {
            json.repayPlanStartTime = (DateFormat.formatDate(params.repayPlanTime[0])).substring(0, 10);
            json.repayPlanEndTime = (DateFormat.formatDate(params.repayPlanTime[1])).substring(0, 10);
        }
        if (params.repayFactTime) {
            json.repayFactStartTime = (DateFormat.formatDate(params.repayFactTime[0])).substring(0, 10);
            json.repayFactEndTime = (DateFormat.formatDate(params.repayFactTime[1])).substring(0, 10);
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
            searchParams: JSON.stringify({repayWay: "20"}),
        });
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
                <FormItem label="应还款时间:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayPlanTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="实际还款时间:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayFactTime', { initialValue: '' }) } />
                </FormItem>
                <Input type="hidden" {...getFieldProps('repayWay',{initialValue: '20'})} />
                <FormItem label="订单号:">
                    <Input  {...getFieldProps('orderNo')} />
                </FormItem>
                <FormItem label="还款账号:">
                    <Input  {...getFieldProps('repayAccount')} />
                </FormItem>
                <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
                <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;