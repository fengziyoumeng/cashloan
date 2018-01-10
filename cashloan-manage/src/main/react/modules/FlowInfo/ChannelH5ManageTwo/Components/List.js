import React from 'react'
import {
    Table,
    Modal,
    Popover
} from 'antd';
import Lookdetails from './Lookdetails'
import AddFlowInfo from './AddFlowInfo'
import AssignPermissions from './AssignPermissions'
var confirm = Modal.confirm;
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {},
            canEdit: true,
            visible: false,
            visibleLook: false,
            assignVisible: false,
        };
    },
    hideModal() {
        this.setState({
            visible: false,
            visibleLook: false,
            assignVisible: false
        });
        var pagination = this.state.pagination;
        this.fetch();
    },
    showModal1(title, record, canEdit) {
        // var record = this.state.selectedRow;
        this.setState({
            visibleLook: true,
            canEdit: canEdit,
            record: record,
            title: title
        },() => {
            Utils.ajaxData({
                url: '/act/flowControl/getPlatFormClick.htm',
                method: "post",
                data: {code:record.pcode},
                callback: (result) => {
                    if(result.code == '200'){
                        this.refs.Lookdetails.setFieldsValue(result);
                        this.setState({
                            detail: result.data,
                        });
                    }else{
                        Modal.error({
                            title: result.msg
                        })
                    }

                }
            });
        })
    },
    //新增跟修改弹窗
   /* showModal(title, record, canEdit) {
        var record = record;
        if (title == '修改') {
            var record = record;
            record.ptag = record.ptag.split(',');
            record.pprocess = record.pprocess.split(',');
            this.refs.AddFlowInfo.setFieldsValue(record);
            //console.log(record);
        } else if (title == '新增') {
            record = null
        }
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },*/
    //打开分配弹窗
    showAssignModal(title, record) {
        this.setState({
            assignVisible: true,
            title: title,
            record: record
        });
    },
    rowKey(record) {
        return record.id;
    },

    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        /*this.fetch({
            pageSize: pagination.pageSize,
            current: pagination.current
        });*/
    },
    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    componentDidMount() {
        this.fetch();
        []
    },
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/modules/manage/promotion/for80/registerCountList.htm',
            data: params,
            callback: (result) => {
                console.log("result>>"+result)
                // console.info("=======>"+JSON.stringify(result.data));
                const pagination = this.state.pagination;
                pagination.total = result.totalCount;
                if (!pagination.current) {
                    pagination.current = 1
                };
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination,
                });
                this.clearList();
            }
        });
    },
    clearList() {
        this.setState({
            selectedRowKeys: [],
        });
    },
    refreshList() {
        this.fetch();
    },
    delete(record) {
        //console.log(record)
        var me = this;
        confirm({
            title: '确认要删除这项内容,不可恢复！',
            onOk: function () {
                Utils.ajaxData({
                    url: "/act/flowControl/delete.htm",
                    data: {
                        id: record.id,
                        code: record.code,
                        picName:record.picName
                    },
                    method: 'post',
                    callback: (result) => {
                        Modal.success({
                            title: result.msg,
                        });
                        me.refreshList();
                    }
                });
            },
            onCancel: function () { }
        });
    },
    componentDidMount() {
        this.fetch();
    },
    render() {
        var me = this;
        const {
            loading,
            selectedRowKeys
        } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        var columns = [{
            title: '渠道ID',
            dataIndex: 'channelId'
        }, {
            title: '渠道名称',
            dataIndex: "channelName"
        }, {
            title: '渠道编码',
            dataIndex: "channelCode"
        },{
            title: '昨日注册数',
            dataIndex: "yesterdayRegisterCount"
        },{
            title: '今日注册数',
            dataIndex: "todayRegisterCount"
        },{
            title: '注册总数',
            dataIndex: "totalRegisterCount"
        }];
        var state = this.state;
        return (
            <div className="block-panel">
                {/*<div className="actionBtns" style={{ marginBottom: 16 }}>
                    <button className="ant-btn" onClick={this.showModal.bind(this, '新增', null, true) }>
                        新增
                    </button>
                </div>*/}
                <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange}  />
                <AddFlowInfo ref="AddFlowInfo"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.record}
                             canEdit={state.canEdit}/>
                <Lookdetails ref="Lookdetails" visible={state.visibleLook} title={state.title} hideModal={me.hideModal} record={state.rowRecord}
                             canEdit={state.canEdit} detail={state.detail} />
                <AssignPermissions ref="AssignPermissions"  visible={state.assignVisible}    title={state.title} hideModal={me.hideModal} selectRecord={state.record}
                                   canEdit={state.canEdit}/>
            </div>
        );
    }
})