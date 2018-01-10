import React from 'react'
import {
  Table,
  Modal,
  Form,
  Row,
  Col,
  Input,
  Popover,
} from 'antd';
import Login from "../../../Login";
const createForm = Form.create;
const FormItem = Form.Item;
const objectAssign = require('object-assign');
var UVMonthDetial = React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      canEdit: true,
      visible: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.setState({
                dataRecord: nextProps.dataRecord
            })
  },
  hideModal() {
    this.setState({
      visible: false,
    });

  },
  handleCancel() {
    this.props.hideModal();
    
  },
  rowKey(record) {
    return record.id;
  }, 
  clearSelectedList() {
    this.setState({
      selectedRowKeys: [],
    });
  },
 
  render() {
    var me = this;
    const {
            getFieldProps
        } = me.props.form;
    var props = me.props;
    var state = me.state;
    const {
      loading,
      selectedRowKeys
    } = state;
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [
    //     {
    //   title: '产品编码',
    //   dataIndex: 'pcode'
    // }, {
    //   title: '产品名称',
    //   dataIndex: 'createTime'
    // },
        {
      title:'日期',
      dataIndex:'countDate'
    }, {
      title: 'UV数',
      dataIndex: 'clickCount'
    }];
     const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 12
            },
        };
    var modalBtns  = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
            ];
    return (
      <Modal title={this.props.title} visible={this.props.visible} onCancel={this.handleCancel} width="900"  footer={modalBtns} >
           <Table columns={columns} rowKey={this.rowKey}  size="middle" 
             dataSource={this.state.dataRecord}
             loading={this.state.loading}
             onChange={this.handleTableChange} bordered />   
         </Modal>
        
    );
  }
});
UVMonthDetial = createForm()(UVMonthDetial);
export default UVMonthDetial;