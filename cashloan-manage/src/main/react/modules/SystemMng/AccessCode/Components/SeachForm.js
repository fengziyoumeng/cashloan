import React from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  DatePicker
} from 'antd';
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
    var params = this.props.form.getFieldsValue();
    var json = {startTime:'',endTime:'',userName:'',name:'',state:''};
     //console.log(params);
     if(params.time){
        var d = new Date(params.time[1]);  
        json.endTime=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' 23:59:59';
        var d1 = new Date(params.time[0]);  
        json.startTime=d1.getFullYear() + '-' + (d1.getMonth() + 1) + '-' + d1.getDate() + ' 00:00:00';
        //console.log(d);
      }
      json.userName = params.userName;
      json.name = params.name;
      json.state = params.state;
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
    const {
      getFieldProps
    } = this.props.form;
    return (
      <Form inline >
        <FormItem label="创建时间:">
          <RangePicker {...getFieldProps('time', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="用户名:">
          <Input  {...getFieldProps('userName', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="用户姓名:">
          <Input  {...getFieldProps('name', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="状态:">
          <Select  {...getFieldProps('state', { initialValue: '' }) } >
            <Option value="">全部</Option>
            <Option value="10">启用</Option>
            <Option value="20">禁用</Option>
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