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
    //新增跟修改弹窗
    showModal(title, record, canEdit) {
        var record = record;
        if (title == '修改') {
            var record = record;
            this.refs.AddFlowInfo.setFieldsValue(record);
            console.log(record);
        } else if (title == '新增') {
            record = null
        }
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },
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
        this.fetch({
            pageSize: pagination.pageSize,
            current: pagination.current
        });
    },
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/picController/picManage.htm',
            data: params,
            callback: (result) => {
                 console.log(result)
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
                    url: "/act/bannerInfo/delete.htm",
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
            title: 'Banner位置',
            render: function (value) {
                if (value == 1){
                    return "首页banner";
                }if  (value == 2){
                    return "首页分类图标";
                }if  (value == 3){
                    return "信用卡banner";
                }else{
                    return "未分类";
                }
            },
            dataIndex: "pType"
        },{
            title: 'Banner预览',
            dataIndex: 'url',
            render: function (value) {
               return  <img src={value} alt=""  style={{width: '80px',marginLeft:'40px'}}/>;
            }
        }, {
            title: '跳转类型',
            dataIndex: "skipType",
            render: function (value) {
                if (value == 0){
                    return "不跳转";
                }if  (value == 1){
                    return "跳转到外链";
                }if  (value == 2){
                    return "跳转到图片";
                }if  (value == 3){
                    return "跳转到详情";
                }else{
                    return "未设置";
                }
            }
        },{
            title: '排序',
            dataIndex: "sort"
        }, {
            title: '状态',
            dataIndex: "state"  ,
            render: function (value) {
                if (value == 10)
                    return "启用";
                else return "禁用";
            }
        },{
            title: '操作',
            dataIndex: "",
            render(text, record) {
                return <div style={{ textAlign: "left" }}>
                    <a href="#" onClick={me.showModal.bind(null, '修改', record, true) }>修改 </a>
                    <span className="ant-divider"></span>
                    <a href="#" onClick={me.delete.bind(null, record) }>删除</a>
                    <span className="ant-divider"></span>
                </div>
            }
        }];
        var state = this.state;
        return (
            <div className="block-panel">
                <div className="actionBtns" style={{ marginBottom: 16 }}>
                    <button className="ant-btn" onClick={this.showModal.bind(this, '新增', null, true) }>
                        新增
                    </button>
                </div>
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