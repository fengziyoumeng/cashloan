import React from 'react'
import { Table, Modal, Icon } from 'antd';
import Lookdetails from "./Lookdetails"
const confirm = Modal.confirm;
const objectAssign = require('object-assign');
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {
                pageSize: 10,
                current: 1
            },
            canEdit: true,
            visible: false,
            visible1: false,
            visible2: false,
            pictureData: [],
            creditReportData: [],
            rowRecord: []

        };
    },

    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    componentDidMount() {
        this.fetch();
    },

    fetch(params = {}) {
        this.setState({
            loading: true
        });
        if (!params.pageSize) {
            var params = {};
            params = {
                pageSize: 10,
                current: 1,
                searchParams: JSON.stringify({ state: "20" }),
            }
        }
        if(!params.searchParams){
            params.searchParams= JSON.stringify({state:'20'})
        }
        Utils.ajaxData({
            url: ' /modules/manage/borrow/borrowList.htm',
            method: "post",
            data: params,
            callback: (result) => {
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

    //查看弹窗
    showModal(title, record, canEdit) {
        // var record = this.state.selectedRow;
        this.setState({
            visible: true,
            canEdit: canEdit,
            record: record,
            title: title
        }, () => {
            record.state = title == "复审" ? "26" : record.state;
            this.refs.Lookdetails.setFieldsValue(record);
        })
    },

    //隐藏弹窗
    hideModal(state) {
        this.setState({
            visible: false,
            selectedIndex: '',
            selectedRowKeys: [],
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
            pageSize: pagination.pageSize,
            // searchParams: JSON.stringify({ state: "20" }),
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
            title: '订单号',
            dataIndex: 'orderNo'
        }, {
            title: '真实姓名',
            dataIndex: 'realName'
        }, {
            title: '手机号码',
            dataIndex: "phone"
        }, {
            title: '借款金额(元)',
            dataIndex: "amount"
        }, {
            title: '借款期限(天)',
            dataIndex: "timeLimit"
        }, {
            title: '利息(元)',
            dataIndex: "interest"
        }, {
            title: '实际到账金额(元)',
            dataIndex: "realAmount"
        }, {
            title: '订单生产时间',
            dataIndex: 'createTime'
        }, {
            title: '费用(元)',
            dataIndex: 'fee'
        }, {
            title: '信息认证费(元)',
            dataIndex: "infoAuthFee",
        }, {
            title: '服务费(元)',
            dataIndex: "serviceFee",
        }];

        var state = this.state;
        return (
            <div className="block-panel" >
                <Table columns={columns} rowKey={this.rowKey} ref="table"
                    onRowClick={this.onRowClick}
                    dataSource={this.state.data}
                    rowClassName={this.rowClassName}
                    pagination={this.state.pagination}
                    onChange={this.handleTableChange}
                />
                <Lookdetails ref="Lookdetails" visible={state.visible} title={state.title} hideModal={me.hideModal} record={state.rowRecord}
                    canEdit={state.canEdit} />
            </div >
        );
    }
})
