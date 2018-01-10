import React from 'react';
import { Button, Modal, Form, Input, Select, Message ,DatePicker} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
// const confirm = Modal.confirm;
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
        var json = { endTimeFact: '', startTimeFact: '',endTime: '', startTime: '', phone: params.phone, serialNumber: params.serialNumber, orderNo: params.orderNo, repayAccount: params.repayAccount};
        if (params.repayPlanTime) {
            //预计还款日期
            json.startTime = (DateFormat.formatDate(params.repayPlanTime[0])).substring(0, 10);
            json.endTime = (DateFormat.formatDate(params.repayPlanTime[1])).substring(0, 10);
        }
        if (params.repayTime) {
            //实际还款日期
            json.startTimeFact = (DateFormat.formatDate(params.repayTime[0])).substring(0, 10);
            json.endTimeFact = (DateFormat.formatDate(params.repayTime[1])).substring(0, 10);
        }
        console.log(json)
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
        window.open("/modules/manage/borrowRepayLog/export.htm?searchParams="+json);

    },
    render() {

        const { getFieldProps } = this.props.form;

        return (
            <Form inline>
		        <FormItem label="手机号:">
		            <Input  {...getFieldProps('phone', { initialValue: '' }) } />
		        </FormItem>
                <FormItem label="订单号:">
                    <Input  {...getFieldProps('orderNo', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="流水号:">
                    <Input  {...getFieldProps('serialNumber', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="还款账号:">
                    <Input  {...getFieldProps('repayAccount', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="应还款日期:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayPlanTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="实际还款日期:">
                    <RangePicker style={{width:"310"}} {...getFieldProps('repayTime', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="是否金额减免:">
                    <Select style={{ width: 100 }} {...getFieldProps('type', { initialValue: '' }) }>
                        <Option value="">全部</Option>
                        <Option value="10">是</Option>
                        <Option value="20">不是</Option>
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