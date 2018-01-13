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
        var json = { companyName: params.companyName,serviceName:params.serviceName};
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
                 <FormItem label="按公司名搜索:">
                      <Input  {...getFieldProps('companyName')} />
                 </FormItem>
                 <FormItem label="按服务名搜索:">
                      <Input  {...getFieldProps('serviceName')} />
                 </FormItem>
                 <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
                 <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;