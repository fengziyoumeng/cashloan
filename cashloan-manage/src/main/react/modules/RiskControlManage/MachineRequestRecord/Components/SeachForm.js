import React from 'react';
import { Button, Form, Input, Select, Message, DatePicker } from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const RangePicker = DatePicker.RangePicker;
const Option = Select.Option;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = { startTime: '', endTime: '', realName: params.realName, phone: params.phone, orderNo: params.orderNo, state: params.state };
        if (params.time[0]) {
            var d = new Date(params.time[0]);
            var d1 = new Date(params.time[1]);
            var m1, m2, day1, day2;
            if ((d.getMonth() + 1) < 10) {
                m1 = '0' + (d.getMonth() + 1)
            } else {
                m1 = (d.getMonth() + 1)
            }
            if (d.getDate() < 10) {
                day1 = '0' + d.getDate()
            } else {
                day1 = d.getDate()
            }
            if ((d1.getMonth() + 1) < 10) {
                m2 = '0' + (d1.getMonth() + 1)
            } else {
                m2 = (d1.getMonth() + 1)
            }
            if (d1.getDate() < 10) {
                day2 = '0' + d1.getDate()
            } else {
                day2 = d1.getDate()
            }
            json.startTime = d.getFullYear() + '-' + m1 + '-' + day1;
            json.endTime = d1.getFullYear() + '-' + m2 + '-' + day2;
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

        const { getFieldProps } = this.props.form;

        return (
            <Form inline>
                <FormItem label="订单号:">
                    <Input  {...getFieldProps('orderNo', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="姓名:">
                    <Input  {...getFieldProps('realName', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="手机号码:">
                    <Input  {...getFieldProps('phone', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="创建时间:">
                    <RangePicker {...getFieldProps('time', { initialValue: '' }) } />
                </FormItem>
                <FormItem label="状态:">
                    <Select style={{ width: 100 }} {...getFieldProps('state', { initialValue: '' }) }>
                        <Option value="">全部</Option>
                        <Option value="10">提交申请</Option>
                        <Option value="20">审核通过</Option>
                        <Option value="30">审核不通过</Option>
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