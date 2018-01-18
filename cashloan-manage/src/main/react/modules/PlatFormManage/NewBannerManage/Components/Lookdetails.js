import React from 'react';
import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  Row,
  Col,
  Select,
  Checkbox,
  Radio,
  message,
  DatePicker,
  Icon
  
} from 'antd';
const CheckboxGroup = Checkbox.Group
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var Lookdetails = React.createClass({
  getInitialState() {
    return {
       checked: true,
       disabled: false,
       data:"",
       timeString:"",
       record:"",
       show: false
    };
  },
  handleCancel() {
    this.props.form.resetFields();
    this.props.hideModal();
  },
  componentWillReceiveProps(nextProps) {
      this.setState({
        record:nextProps.record
      })
  },
  show(){
    this.setState({
      show: !this.state.show
    })
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    // console.log(this.props.form)
    var props = this.props;
    var state = this.state;
   var modalBtnstwo= [
      <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>,
    ];
    const formItemLayout = {
      labelCol: {
        span: 4
      },
      wrapperCol: {
        span: 6
      },
    };
    const formItemLayouttwo = {
      labelCol: {
        span: 8
      },
      wrapperCol: {
        span: 16
      },
    };
 
 
    return (
      <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900" footer={modalBtnstwo} maskClosable={false} >
         <Form horizontal  form={this.props.form}>
           <Row>
            <Col span="12">
              <FormItem  {...formItemLayouttwo} label="今日访问数量:">
                  <Input {...getFieldProps('todayAmount', { initialValue: '' }) } disabled={true} />
              </FormItem>
                <FormItem  {...formItemLayouttwo} label="昨日访问数量:">
                    <Input {...getFieldProps('yestadayAmount', { initialValue: '' }) } disabled={true} />
                </FormItem>
                <FormItem  {...formItemLayouttwo} label="前日访问数量:">
                    <Input {...getFieldProps('afterTomorrowAmount', { initialValue: '' }) } disabled={true} />
                </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayouttwo} label="当月点击总数:">
                  <Input {...getFieldProps('totalAmount', { initialValue: '' }) } disabled={true} />
              </FormItem>
            </Col>
         </Row>
        </Form>
      </Modal>
    );
  }
});
Lookdetails = createForm()(Lookdetails);
export default Lookdetails;