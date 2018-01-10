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
    handleQuery(date,dateString) {
        beginTime = dateString[0];
        endTime = dateString[1];
        var json = {beginTime:beginTime,endTime:endTime};
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
        this.setState({dataVlaue:date});
        this.target.value = this.state.dataVlaue;
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            pageSize: 10,
            current: 1,
        });
    },
    handleOut() {
        window.open("/act/count/excel/registerPlat.htm?beginTime="+beginTime+"&endTime="+endTime);
    },
    render() {
        const {getFieldProps} = this.props.form;
        return (
            <Form inline>
                <FormItem label="按时间查询:">
                        <RangePicker   onChange={this.handleQuery}/>
                </FormItem>
                <FormItem><Button type="primary" onClick={this.handleOut}>导出</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;