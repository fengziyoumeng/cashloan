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
    var json = {endTime:'',startTime:'',realName:params.realName,loginName:params.loginName,idNo:params.idNo,channelId: params.channelId,registerClient: params.registerClient};
    if(params.registTime){
      json.startTime = (DateFormat.formatDate(params.registTime[0])).substring(0,10) + ' 00:00:00';
      json.endTime = (DateFormat.formatDate(params.registTime[1])).substring(0,10) + ' 23:59:59';
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
  componentDidMount() {
    this.fetch();
  },
  fetch(){
    // Utils.ajaxData({
    //   url: '/modules/manage/promotion/channel/listChannel.htm',
    //   callback: (result) => {
    //     this.setState({
    //       data: result.data,
    //     });
    //   }
    // });

      var me = this ;
      Utils.ajaxData({
          url: '/modules/manage/promotion/channel/listChannel.htm',
          method: 'post',
          type: 'json',
          callback: (result) => {
              var items  = result.data.map((item)=>{
                  return (<Option key={item.id} value={String(item.id)}>{item.name}</Option>);
              });
              me.setState({roleList:items});
          }
      });
  },
  disabledDate(startValue) {
        var today = new Date();
        return startValue.getTime() > today.getTime();
    },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    var channelList = [];
    if(this.state.data){
      channelList.push(<Option key={'全部'} value= {''} >全部</Option>);
      this.state.data.map(item => {
        channelList.push(<Option key={item.name} value= {item.id} >{item.name}</Option>)
      })
    }
    return (
      <Form inline >
        {/*<FormItem label="真实姓名：">*/}
          {/*<Input  {...getFieldProps('realName') } />*/}
        {/*</FormItem>*/}
        <FormItem label="手机号码：">
          <Input  {...getFieldProps('loginName') } />
        </FormItem>
        {/*<FormItem label="证件号码：">*/}
          {/*<Input  {...getFieldProps('idNo') } />*/}
        {/*</FormItem>*/}
        <FormItem label="注册客户端：">
          <Select style={{ width: 170 }} {...getFieldProps('registerClient',{initialValue: ''})}>
            <Option value= {''} >全部</Option>
            <Option value= {'ios'} >苹果</Option>
            <Option value= {'android'} >安卓</Option>
            <Option value= {'wx'} >微信</Option>
            <Option value= {'h5'} >H5</Option>
          </Select>
        </FormItem>
        <FormItem label="注册渠道：">
          <Select style={{ width: 170 }} {...getFieldProps('channelId',{initialValue: ''})}>
            {this.state.roleList}
          </Select>
        </FormItem>
        <FormItem label="注册时间：">
            <RangePicker disabledDate={this.disabledDate} style={{width:"310"}} {...getFieldProps('registTime', { initialValue: '' }) } />
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;