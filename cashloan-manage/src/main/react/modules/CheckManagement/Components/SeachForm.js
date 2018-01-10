import React from 'react';
import {
    Button,
    Form,
    Input,
    Select,
    DatePicker,
    Radio
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const RangePicker = DatePicker.RangePicker;
let SeachForm = React.createClass({
    getInitialState() {
        return {
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = { endTime: '', startTime: '', orderNo: params.orderNo, processResult: params.processResult};
        if (params.registTime) {
            json.startTime = (DateFormat.formatDate(params.registTime[0])).substring(0,10) + ' 00:00:00';
            json.endTime = (DateFormat.formatDate(params.registTime[1])).substring(0,10) + ' 23:59:59';
        }
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1
        });
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            pageSize: 10,
            current: 1
        });
    },
    handleOut() {
        var params = this.props.form.getFieldsValue();
        var json = { endTime: '', startTime: '', orderNo: params.orderNo, processResult: params.processResult};
        if (params.registTime) {
            json.startTime = (DateFormat.formatDate(params.registTime[0])).substring(0,10) + ' 00:00:00';
            json.endTime = (DateFormat.formatDate(params.registTime[1])).substring(0,10) + ' 23:59:59';
        }
        json = JSON.stringify(json);
        window.open("/modules/manage/payCheck/export.htm?searchParams="+json);

    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        return (
            <Form inline>
                <FormItem label="订单号:">
                    <Input type="text" {...getFieldProps('orderNo') } />
                </FormItem>
                <FormItem label="对账时间:">
                    <RangePicker {...getFieldProps('registTime') } />
                </FormItem>
                <FormItem label="处理结果:">
                    <Select style={{ width: 100 }} {...getFieldProps('processResult', { initialValue: '' }) }>
                        <Option value="">全部</Option>
                        <Option value="10">未处理</Option>
                        <Option value="20">已处理</Option>
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
export default SeachForm;/**
 * Created by WIN10 on 2016/10/12.
 */
