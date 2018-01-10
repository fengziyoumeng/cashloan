import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const { RangePicker } = DatePicker;
var beginTime;
var endTime;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = { orderCreatStartTime: '', orderCreatEndTime: '',pHandPerson:params.pHandPerson,realName: params.realName, phone: params.phone, orderNo: params.orderNo, state: params.state};
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
    handleExcelReady(date,dateString){
        beginTime = dateString[0];
        endTime = dateString[1];
    },
    handleOut() {
        debugger;
        window.open("/act/count/excel/flowUvInfo.htm?beginTime="+beginTime+"&endTime="+endTime);
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
             {/*<FormItem label="订单生成时间:">*/}
                   {/*<RangePicker style={{width:"310"}} {...getFieldProps('orderCreatTime', { initialValue: '' }) } />*/}
             {/*</FormItem>*/}
             <FormItem label="按录入人搜索:">
                    <Input  {...getFieldProps('pHandPerson')} />
             </FormItem>
             <FormItem label="产品编码:">
                  <Input  {...getFieldProps('realName')} />
             </FormItem>
             <FormItem label="产品名称:">
                  <Input  {...getFieldProps('phone')} />
             </FormItem>
             <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
             <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
             {/*<FormItem><Button onClick={this.handleOut}>导出</Button></FormItem>*/}
             <FormItem label="按时间导出（仅作用于导出功能）:"><RangePicker   onChange={this.handleExcelReady}/></FormItem>
             <FormItem><Button type="primary" onClick={this.handleOut}>导出</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;