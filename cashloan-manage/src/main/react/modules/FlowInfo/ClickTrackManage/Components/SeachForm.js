import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const { RangePicker } = DatePicker;
var beginTime;
var endTime;
var userId;
var channelName;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();

        var json = {beginTime:beginTime,endTime:endTime, userId: params.userId,channelName:params.channelName};
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 100,
            current: 1,
        });
        this.setState({dataVlaue:date});
        this.target.value = this.state.dataVlaue;
    },
    handleQueryByDate(date,dateString){
        beginTime = dateString[0];
        endTime = dateString[1];
        var paramsValue = this.props.form.getFieldsValue();
        userId = paramsValue.userId;
        channelName = paramsValue.channelName;
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            pageSize: 100,
            current: 1,
        });
    },
    handleOut1() {
        if(beginTime==''||beginTime==undefined){
            alert("开始日期不能为空");
            return;
        }
        if(endTime==''||endTime==undefined){
            alert("结束日期不能为空");
            return;
        }
        window.open("/act/count/excel/track.htm?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&channelName="+channelName);
    },
    handleOut2() {
        if(beginTime==''||beginTime==undefined){
            alert("开始日期不能为空");
            return;
        }
        if(endTime==''||endTime==undefined){
            alert("结束日期不能为空");
            return;
        }
        if(beginTime!=endTime){
            alert("开始日期和结束日期必须一致，只能查询某一天的数据");
            return;
        }
        window.open("/act/count/excel/channel_survey.htm?beginTime="+beginTime+"&endTime="+endTime);
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
             <FormItem label="按用户Id查询:">
                  <Input  {...getFieldProps('userId')} />
             </FormItem>
             <FormItem label="按渠道名称查询:">
                    <Input  {...getFieldProps('channelName')} />
             </FormItem>
             <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
             <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
                <FormItem label="按时间查询:">
                    <RangePicker  onChange={this.handleQueryByDate}/></FormItem>
                <FormItem>
                    <Button type="primary" onClick={this.handleOut1}>导出用户浏览记录</Button>
                </FormItem>
                <FormItem>
                    <Button type="primary" onClick={this.handleOut2}>导出渠道用户概况</Button>
                </FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;