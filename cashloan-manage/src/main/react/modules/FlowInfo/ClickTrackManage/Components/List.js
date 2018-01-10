import React from 'react'
import { Table, Modal, Icon, Button } from 'antd';
import Lookdetails from "./Lookdetails";
var repaymentTypeText = { '10': '待审核', '20': '审核中', '30': '通过', '40': '已拒绝', '50': '还款中', '60': '还款完成', '70': '逾期' }
const objectAssign = require('object-assign');
import UVMonthDetial from "./UVMonthDetial";
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {
                pageSize: 100,
                current: 1
            },
            canEdit: true,
            visible: false,
            visible1: false,
            visible2: false,
            pictureData: [],
            creditReportData: [],
            rowRecord: [],
            dataRecord: '',
            record: "",
            visibleAc: false,

        };
    },

    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    componentDidMount() {
        this.fetch();
        []
    },

    fetch(params = {}) {

        this.setState({
            loading: true
        });
        if (!params.pageSize) {
            var params = {};
            params = {
                pageSize: 100,
                current: 1
            }
        }
        Utils.ajaxData({
            url: '/act/query/trail/recode.htm',
            method: "post",
            data: params,
            callback: (result) => {
                console.info("=======>"+result);
                const pagination = this.state.pagination;
                pagination.current = params.current;
                pagination.pageSize = params.pageSize;
                pagination.total = result.page.total;
                pagination.showTotal = () => `总共 ${result.page.total} 条`;
                if (!pagination.current) {
                    pagination.current = 1
                }
                ;
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination
                });
            }
        });
    },

    //新增跟编辑弹窗
    showModal(title, record, canEdit) {
        var record = record;
        var me = this;
        Utils.ajaxData({
            url: '/act/flowControl/getAllForUVDetail.htm',
            data: {
                id: record.id
            },
            method: 'get',
            callback: (result) => {
                me.setState({
                    canEdit: canEdit,
                    visibleAc: true,
                    title: title,
                    // record: result.data,
                    dataRecord:result.data
                });
            }
        });
    },



    //新增
    addModal(title) {
        Utils.ajaxData({
            url: '/modules/manage/user/list.htm',
            method: "post",
            callback: (result) => {
                this.setState({
                    dataRecord: result.data.list,
                    visibleAc: true,
                    title: title,
                });
            }
        });

    },
    //隐藏弹窗
    hideModal() {
        console.log(1)
        this.setState({
            visible: false,
            visible1: false,
            visible2: false,
            selectedIndex: '',
            selectedRowKeys: [],
            visibleAc: false,
            dataRecord: ''
        });
        this.refreshList();
    },
    rowKey(record) {
        return record.id;
    },

    //选择
    onSelectChange(selectedRowKeys) {
        this.setState({
            selectedRowKeys
        });
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

    refreshList() {
        var pagination = this.state.pagination;
        var params = objectAssign({}, this.props.params, {
            current: pagination.current,
            pageSize: pagination.pageSize
        });
        this.fetch(params);
    },

    //行点击事件
    onRowClick(record) {
        //console.log(record)
        this.setState({
            selectedRowKeys: [record.id],
            selectedRow: record,
            rowRecord: record
        }, () => {
            this
        });
    },

    // 列表添加选中行样式
    rowClassName(record) {
        let selected = this.state.selectedIndex;
        //console.log('selected', this.state.selectedIndex)
        return (record.id == selected && selected !== '') ? 'selectRow' : '';

    },

    //选择
    onSelectAll(selected, selectedRowKeys, selectedRows, changeRows) {
        if (selected) {
            this.setState({
                selectedRowKeys
            })
        } else {
            this.setState({
                selectedRowKeys: []
            })
        }
    },

    render() {
        const {
            loading,
            selectedRowKeys
            } = this.state;
        const rowSelection = {
            type: 'checkbox',
            selectedRowKeys,
            onSelectAll: this.onSelectAll,
        };
        let me = this;
        const hasSelected = selectedRowKeys.length > 0;
        let openEdit = true;
        if (hasSelected && selectedRowKeys.indexOf("0") === -1) {
            openEdit = false;
        }
        var columns = [{
            title: '用户ID',
            dataIndex: 'userId'
        }, {
            title: '访问页面',
            dataIndex: "name"
        }, {
            title: '渠道名称',
            dataIndex: "channelName"
        },{
            title: '访问时间',
            dataIndex: "clickTime"
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
                       onChange={this.handleTableChange} />
                <UVMonthDetial ref="UVMonthDetial"  visible={state.visibleAc}    title={state.title} hideModal={me.hideModal}
                                    record={state.record} dataRecord={state.dataRecord} pagination={state.pagination} canEdit={state.canEdit}/>
            </div>
        );

    }
})
