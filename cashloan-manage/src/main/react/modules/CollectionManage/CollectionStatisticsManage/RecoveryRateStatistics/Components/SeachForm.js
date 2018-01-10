import React from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  DatePicker
} from 'antd';
const createForm = Form.create;
const RangePicker = DatePicker.RangePicker;
const FormItem = Form.Item;
const Option = Select.Option;

let SeachForm = React.createClass({
  getInitialState() {
        return {
            roleList: [],
        }
    },
  handleQuery() {
    var params = this.props.form.getFieldsValue();
    var json = {afterTime:'',beforeTime:''};
     //console.log(params);
     if(params.time){
        var d = new Date(params.time[1]);  
        json.afterTime=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
        var d1 = new Date(params.time[0]);  
        json.beforeTime=d1.getFullYear() + '-' + (d1.getMonth() + 1) + '-' + d1.getDate();
        //console.log(d);
      }
      
    this.props.passParams({
      searchParams : JSON.stringify(json),
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
        <FormItem label="日期:">
           <RangePicker {...getFieldProps('time', { initialValue: '' }) } />
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;