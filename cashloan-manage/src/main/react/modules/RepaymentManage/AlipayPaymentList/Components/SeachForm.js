import React from 'react';
import { Button, Form, Input, Select, Message,DatePicker } from 'antd';
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
        var json = {startTimePlan:'',endTimePlan:'',startRepayTimeFact:'',endRepayTimeFact:'',orderNo:params.orderNo,repayAccount:params.repayAccount,repayWay:params.repayWay};
        if (params.registTime) {
            json.startTime = (DateFormat.formatDate(params.registTime[0])).substring(0, 10);
            json.endTime = (DateFormat.formatDate(params.registTime[1])).substring(0, 10);
        }
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
    },

    handleQuery() {
        var params = this.props.form.getFieldsValue();
        console.log(params)
        var json = {startTimePlan:'',endTimePlan:'',startRepayTimeFact:'',endRepayTimeFact:'',orderNo:params.orderNo,repayAccount:params.repayAccount};

        //应还款日期
        if(params.repayPlanTime){
            json.startTimePlan = (DateFormat.formatDate(params.repayPlanTime[0])).substring(0, 10);
            json.endTimePlan=(DateFormat.formatDate(params.repayPlanTime[1])).substring(0, 10);
        }
        //实际还款日期
        if(params.repayTime){

            json.startRepayTimeFact = (DateFormat.formatDate(params.repayTime[0])).substring(0, 10);

            json.endRepayTimeFact=(DateFormat.formatDate(params.repayTime[1])).substring(0, 10);
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
            searchParams: JSON.stringify({repayWay: "30"}),
        });
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
                <Input type="hidden" {...getFieldProps('repayWay',{initialValue: '30'})} />
                <FormItem label="订单号:">
                    <Input  {...getFieldProps('orderNo')} />
                </FormItem>
                <FormItem label="还款账号:">
                    <Input  {...getFieldProps('repayAccount')} />
                </FormItem>
                <FormItem label="应还款日期:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayPlanTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="实际还款日期:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
                <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;