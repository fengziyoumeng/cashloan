import React from 'react'
import {
  Table,
  Modal
} from 'antd';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
var testData = require('../../../../TestData/Json1');
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {},
      canEdit: true,
      visible: false,
      visibleAc: false,
      dataRecord:"",
      testData: testData.data13
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visibleAc: false,
      dataRecord:""
    });
    this.refreshList();
  },
  //编辑弹窗
  showModal(title, record, canEdit) {
    var record = record;
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
      record: record
    });
  },
  //新增跟编辑弹窗
  showModalAc(title, record, canEdit) {
    var record = record;
    var searchdata = {};
      searchdata = {
        borrowId: record.id
    }
    if(title=='查看详情'){
     Utils.ajaxData({
        url: '/modules/manage/borrow/borrowPress/findById.htm',
        data: {
          search : JSON.stringify(searchdata),
          pageSize: 5,
          current: 1
        },
        callback: (result) => {
          this.refs.AdjustCreditDetial.setFieldsValue(result.data);
          this.setState({
            canEdit: canEdit,
            visibleAc: true,
            title: title,
            record: record,
            dataRecord:result.data,
            pagination:result.page
          });
        }
      });
    }  
  },
  rowKey(record) {
    return record.key;
  },

  //分页
  handleTableChange(pagination, filters, sorter) {
    const pager = this.state.pagination;
    pager.current = pagination.current;
    this.setState({
      pagination: pager,
    });
    this.refreshList();
  },
  fetch(params = {}) {
    this.setState({
      loading: true
    });
    if (!params.pageSize) {
      var params = {};
      params = {
        pageSize: 10,
        current: 1
      }
    }
    Utils.ajaxData({
      url: '/modules/manage/borrow/repay/urge/collection/urgeCount.htm', 
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.current;
        pagination.pageSize = params.pageSize;
        pagination.total = result.page.total;
        pagination.showTotal = () => `总共 ${result.page.total} 条`;
        if (!pagination.current||result.page.current==1) {
          pagination.current = 1
        }else{
          pagination.current=result.page.current
        };
        for(var i = 0; i < result.data.length; i++){
          result.data[i].key = i;
        }
        this.setState({
          loading: false,
          data: result.data,
          pagination
        });
      }
    });
  },
  clearSelectedList() {
    this.setState({
      selectedRowKeys: [],
    });
  },
  refreshList() {
    var pagination = this.state.pagination;
    var params = objectAssign({}, this.props.params, {
      current: pagination.current,
      pageSize: pagination.pageSize
    });
    this.fetch(params);
  },
  
  componentDidMount() {
    this.fetch();
  },

  onRowClick(record) {
    this.setState({
      selectedRowKeys: [record.id],
      selectedrecord: record
    });
  },
 
  render() {
    var me = this;
    const {
      loading,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      selectedRowKeys,
     
    }; 
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '日期',
      dataIndex: "createTime",
      render:(text) => text.substring(0,10)
    },{
      title: '订单数',
      dataIndex: 'orderCount'
    },{
      title: '成功数',
      dataIndex: "successCount"
    },{
      title: '坏账数',
      dataIndex: 'failCount'
    }, {
      title: '催回率(%)',
      dataIndex: "backRate",
      render:(text) => text.toFixed(2)
    },{
      title: '催收次数',
      dataIndex: 'count'
    }];
    var state = this.state;
    return (
      <div className="block-panel">
           <Table columns={columns} rowKey={this.rowKey}  
             onRowClick={this.onRowClick}  params ={this.props.params}
             dataSource={this.state.data}
             pagination={this.state.pagination}
             loading={this.state.loading}
             onChange={this.handleTableChange}  />
            
         </div>
    );
  }
})