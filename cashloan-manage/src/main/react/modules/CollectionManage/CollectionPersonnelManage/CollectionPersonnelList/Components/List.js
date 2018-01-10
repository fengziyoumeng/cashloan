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
      visibleAdd: false,
      testData: testData.data,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visibleAdd:false
    });
    this.refreshList();
  },
  //新增跟编辑弹窗
  showModal(title, record, canEdit) {
    var record = record;
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
      record: record
    },()=>{
      this.refs.CustomerWin.setFieldsValue(record);
    });
  },
  //新增
  addModal(title, record, canEdit){
      this.setState({
        visibleAdd:true,
        title:title,  
      })

  },
  rowKey(record) {
    return record.jobNum;
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
        pageSize: 100,
        current: 1,
      }
    }
    Utils.ajaxData({
      url: '/modules/manage/borrow/repay/urge/collection/member.htm',
      data:params,
      method: 'get',
      callback: (result) => {
         const pagination = this.state.pagination;
                pagination.current = params.current;
                pagination.pageSize = params.pageSize;
                pagination.total = result.page.total;
                pagination.showTotal = () => `总共 ${result.page.total} 条`;
                if (!pagination.current) {
                    pagination.current = 1
                };
        this.setState({
          loading: false,
          data: result.data,
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
      pageSize: pagination.pageSize,
    });
    this.fetch(params);
  },
  changeStatus(title,record) {
    var me = this;
    var selectedRowKeys =me.state.selectedRowKeys;
    var id = record.id;
    var status;
    var msg = "";
    var tips = "";
    var trueurl = "";
      if (title == "加入黑名单") {
        msg = '加入黑名单';
        status = '20';
        tips = '您是否确定加入黑名单';
        trueurl = "/modules/manage/user/updateState.htm"
      } else if (title == "解除黑名单") {
        msg = '解除黑名单成功';
        status = '10';
        tips = '您是否确定解除黑名单';
        trueurl = "/modules/manage/user/updateState.htm"
      }
      confirm({
        title: tips,
        onOk: function() {
          Utils.ajaxData({
            url: trueurl,
            data: {     
              id: id, 
              state:status
            },
            method: 'post',
            callback: (result) => {
              if(result.code==200){
                Modal.success({
                 title: result.msg,
                });     
              }else{
                Modal.error({
                  title:  result.msg,
                });
              }
              me.refreshList();
            }
          });
        },
        onCancel: function() {}
      });
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
      title: '真实姓名',
      dataIndex: 'userName',
    }, {
      title: '用户名',
      dataIndex: "loginName",
    }, {
      title: '工号',
      dataIndex: 'jobNum'
    }, {
      title: '状态',
      dataIndex: 'status',
      render:(text) => text == '1' ? '禁用' : '正常'
    }, {
      title: '催收总数',
      dataIndex: 'count',
    }, {
      title: '待催收数',
      dataIndex: "waitCount",
    }, {
      title: '催收成功数',
      dataIndex: "successCount",
    }, {
      title: '昨日催收数',
      dataIndex: 'yesterdayCount',
    }];
    var state = this.state;
    return (
      <div className="block-panel">
           <div className="actionBtns" style={{ marginBottom: 16 }}>
              {/*<button onClick={me.addModal.bind(me,'新增')} className="ant-btn"> 
                新增(测试)
              </button>*/}
            </div>
           <Table columns={columns} rowKey={this.rowKey}  
             onRowClick={this.onRowClick}  
             dataSource={this.state.data}
             pagination={this.state.pagination}
             loading={this.state.loading}
             onChange={this.handleTableChange}  />
         </div>
    );
  }
})